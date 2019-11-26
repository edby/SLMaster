package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DATE;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.MortgageBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:17
 */
@Service
@Transactional
public class MortgageBizImpl implements MortgageBiz {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private MortgageConfigService mortgageConfigService;
    @Autowired
    private MortgageRecordService mortgageRecordService;
    @Autowired
    private MortgageProfitRecordService mortgageProfitRecordService;
    @Override
    public String init(User user, Integer coinType, PageModel pageModel) {
        Map<String, Object> result = new HashMap<>();
        MortgageConfig mortgageConfig = mortgageConfigService.selectByCoin(coinType);
        if(mortgageConfig == null){
            return Result.toResult(ResultCode.MORTGAGE_OFF);
        }
        //抵押挖矿币种
        List<MortgageConfig> mortgageConfigs = mortgageConfigService.selectAll(new HashMap<>());
        List<Integer> mortgage = new LinkedList<>();
        for(MortgageConfig config : mortgageConfigs){
            mortgage.add(config.getCointype());
        }
        Map<Object, Object> param = new HashMap<>();
        param.put("userid", user.getId());
        param.put("cointype", coinType);
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<MortgageProfitRecord> mortgageProfitRecords = mortgageProfitRecordService.selectPaging(param);
        String yestoday = DateUtils.getCurrentDateStr();
        //is_team 0个人奖励 1团队奖励
        Integer isTeam = 0;
        //昨日个人收益
        param = new HashMap<>();
        param.put("userid", user.getId());
        param.put("cointype", coinType);
        param.put("isTeam", isTeam);
        param.put("startTime", yestoday + " 00:00:00");
        param.put("endTime", yestoday + " 23:59:59");
        String yestodayProfit = mortgageProfitRecordService.getYestodayProfit(param);
        yestodayProfit = StrUtils.isBlank(yestodayProfit) ? "0": yestodayProfit;
        param.remove("isTeam");
        param.remove("startTime");
        param.remove("endTime");
        String totalProfit = mortgageProfitRecordService.getYestodayProfit(param);
        totalProfit = StrUtils.isBlank(totalProfit) ? "0": totalProfit;
        String digDoc = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_DOC);
        String digCycleRate = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_CYCLE_RATE);
        JSONArray rate = JSON.parseArray(digCycleRate);

        result.put("yesTodayProfit", new BigDecimal(yestodayProfit).setScale(2, BigDecimal.ROUND_HALF_UP));
        result.put("totalProfit", new BigDecimal(totalProfit).setScale(2, BigDecimal.ROUND_HALF_UP));
        result.put("digList", mortgageProfitRecords);
        result.put("rate", rate.get(rate.size() - 1));
        result.put("digDoc", digDoc);
        result.put("mortgageCoinList", mortgage);
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    @Override
    public String commit(User user, Integer coinType, String amount, String rate, Integer time) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = user.getId();
        MortgageConfig mortgageConfig = mortgageConfigService.selectByCoin(coinType);
        if(mortgageConfig == null){
            return Result.toResult(ResultCode.MORTGAGE_OFF);
        }
        //统计当前用户已抵押金额
        String totalAmount = mortgageRecordService.selectTotalByUserAndCoinType(userId, coinType);
        BigDecimal total = new BigDecimal(amount).add(new BigDecimal(totalAmount));
        if(total.compareTo(mortgageConfig.getMaxAmount()) > 0){
            return Result.toResult(ResultCode.MORTGAGE_MAX_ERROR);
        }
        //插入抵押记录
        MortgageRecord mortgageRecord = new MortgageRecord();
        mortgageRecord.setCointype(coinType);
        mortgageRecord.setAmount(new BigDecimal(amount));
        mortgageRecord.setRate(new BigDecimal(rate));
        mortgageRecord.setUserid(userId);
        mortgageRecord.setEndTime(DateUtils.getSomeDay(time) + " 23:59:59");
        mortgageRecordService.insertSelective(mortgageRecord);
        //修改账户流水
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_SPOT, coinType,
                BigDecimalUtils.plusMinus(new BigDecimal(amount)), BigDecimal.ZERO, userId, "抵押挖矿扣除", mortgageRecord.getId());
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_YUBI, coinType,
                BigDecimal.ZERO, new BigDecimal(amount), userId, "抵押挖矿金额增加", mortgageRecord.getId());
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String info(User user, Integer coinType) {
        Map<String, Object> result = new HashMap<>();
        Account account = accountService.getAccountByUserAndCoinTypeAndAccount(user.getId(), coinType, AccountType.ACCOUNT_SPOT);
        //查询可用余额
        String digCycle = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_CYCLE);
        String digCycleRate = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_CYCLE_RATE);
        String agreement = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_AGREEMENT);
        JSONArray cycle = JSON.parseArray(digCycle);
        JSONArray rate = JSON.parseArray(digCycleRate);
        Map<Object, Object> map = new HashMap<>();
        for(int i = 0; i < rate.size(); i++){
            map.put(cycle.get(i), rate.get(i));
        }
        result.put("amount", account.getAvailbalance());
        result.put("rate", map);
        result.put("cycle", cycle);
        result.put("agreement", agreement);
        result.put("date", DateUtils.getSomeDay(1));
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    @Override
    public String list(User user, Integer coinType, PageModel pageModel) {
        Map<Object, Object> param = new HashMap<>();
        param.put("userid", user.getId());
        param.put("cointype", coinType);
        param.put("firstResult", pageModel.getFirstResult());
        param.put("maxResult", pageModel.getMaxResult());
        List<MortgageRecord> mortgageRecords = mortgageRecordService.selectPaging(param);
        List<Map<String, Object>> list = new LinkedList<>();
        for(MortgageRecord mortgageRecord : mortgageRecords){
            Map<String, Object> map = new HashMap<>();
            String endTime = mortgageRecord.getEndTime();
            String startTime = DateUtils.getDateFormate(mortgageRecord.getCreatetime());
            int dayNumber = DateUtils.daysBetween(endTime, startTime);
            int todayNumber = DateUtils.daysBetween(DateUtils.getCurrentTimeStr(), startTime);
            map.put("amount", mortgageRecord.getAmount());
            map.put("endTime", endTime.substring(0, 10));
            map.put("startTime", startTime.substring(0, 10));
            map.put("rate", mortgageRecord.getRate());
            if(dayNumber != 0) {
                BigDecimal percentage = new BigDecimal(todayNumber).divide(new BigDecimal(dayNumber), 4, BigDecimal.ROUND_HALF_UP);
                map.put("percentage", percentage);
            }
            list.add(map);
        }
        return Result.toResult(ResultCode.SUCCESS, list);
    }
}
