package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DateUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        param.remove("isTeam");
        param.remove("startTime");
        param.remove("endTime");
        String totalProfit = mortgageProfitRecordService.getYestodayProfit(param);
        Account account = accountService.getAccountByUserAndCoinTypeAndAccount(user.getId(), coinType, AccountType.ACCOUNT_SPOT);
        //查询可用余额
        String digCycle = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_CYCLE);
        String digCycleRate = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_CYCLE_RATE);
        JSONArray cycle = JSON.parseArray(digCycle);
        JSONArray rate = JSON.parseArray(digCycleRate);
        result.put("yesTodayProfit", yestodayProfit);
        result.put("totalProfit", totalProfit);
        result.put("digList", mortgageProfitRecords);
        result.put("rate", rate.get(rate.size() - 1));
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
}
