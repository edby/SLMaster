package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.OdinBuyingRecordService;
import com.yibi.core.service.OrderSpotService;
import com.yibi.core.service.SysparamsService;
import com.yibi.orderapi.biz.OdinBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class OdinBizImpl extends BaseBizImpl implements OdinBiz {

    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisTemplate<String, String> redis;

    @Autowired
    private OdinBuyingRecordService odinBuyingRecordService;

    @Override
    public String buy(User user, String amount, String password) {
        //验证功能开关
        String onoff = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_ONOFF);
        if(GlobalParams.INACTIVE == Integer.valueOf(onoff)){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        Integer userId = user.getId();
        BigDecimal amountBig = new BigDecimal(amount);
        //验证交易密码
        String validateOrderPassword = validateOrderPassword(user, password);
        if (validateOrderPassword != null) {
            return validateOrderPassword;
        }
        //验证余额
        Account account = accountService.getAccountByUserAndCoinTypeAndAccount(userId, CoinType.ENC, AccountType.ACCOUNT_YUBI);
        if(account.getAvailbalance().compareTo(amountBig) < 0){
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }

        /*--------------------验证购买条件是否满足-----------------*/
        //期数
        Integer number = sysparamsService.getValIntByKey(SystemParams.ODIN_BUYING_NUMBER);
        //个人单日限额标准
        String personQuota = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_PERSON_QUOTA);
        //个人单日限额
        String quota = odinBuyingRecordService.countUserOnceDayAmount(userId, number);
        if(new BigDecimal(personQuota).compareTo(new BigDecimal(quota)) < 0){
            return Result.toResult(ResultCode.ODIN_BUY_PERSON_MORE);
        }
        quota = odinBuyingRecordService.countPlatFormOnceDayAmount(number);
        //平台单日限额
        String platformQuota = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_PLATFORM_QUOTA);
        if(new BigDecimal(platformQuota).compareTo(new BigDecimal(quota)) < 0){
            //修改认购功能开关
            Sysparams buyOnOff = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_ONOFF);
            buyOnOff.setKeyval(String.valueOf(GlobalParams.INACTIVE));
            sysparamsService.updateByPrimaryKeySelective(buyOnOff);
            return Result.toResult(ResultCode.ODIN_BUY_PLATFORM_MORE);
        }
        /*--------------------保存认购记录------------------------*/
        OdinBuyingRecord odinBuyingRecord = new OdinBuyingRecord();
        odinBuyingRecord.setUserId(userId);
        odinBuyingRecord.setAmount(amountBig);
        //本期购买金额
        BigDecimal buyPrice = new BigDecimal(sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_THIS_PRICE));
        odinBuyingRecord.setBuyPrice(buyPrice);
        odinBuyingRecord.setNumber(number);
        //下期购买金额
        BigDecimal nextPrice = new BigDecimal(sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_NEXT_PRICE));
        //可购买奥丁数量
        odinBuyingRecord.setGetOdinAmount(amountBig.divide(nextPrice, 2, BigDecimal.ROUND_HALF_UP));
        //保存认购记录
        odinBuyingRecordService.insertSelective(odinBuyingRecord);

        /*-------------------更新账户和记录流水-------------------*/
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_YUBI, CoinType.YEZI, BigDecimal.ZERO, amountBig, userId, "奥丁币认购-节点账户余额增加", odinBuyingRecord.getId());
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_YUBI, CoinType.YEZI, BigDecimalUtils.plusMinus(amountBig), BigDecimal.ZERO, userId, "奥丁币认购-币币账户余额扣除", odinBuyingRecord.getId());

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String init() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new LinkedList<>();
        list.add(SystemParams.ODIN_BUYING_NUMBER);
        list.add(SystemParams.ODIN_BUYING_TIME);
        list.add(SystemParams.ODIN_BUYING_THIS_PRICE);
        list.add(SystemParams.ODIN_BUYING_NEXT_PRICE);
        list.add(SystemParams.ODIN_BUYING_NOW_ORDER_PRICE);
        list.add(SystemParams.ODIN_BUYING_BAR);
        list.add(SystemParams.ODIN_BUYING_AMOUNT_LIST);
        for(String sysKey : list){
            Sysparams sysparams = sysparamsService.getValByKey(sysKey);
            if(SystemParams.ODIN_BUYING_AMOUNT_LIST.equals(sysKey)){
                String c = sysparams.getKeyval().replace("[","").replace("]","");
                String[] arrays = c.split(",");
                List<String> lists = new LinkedList<>(Arrays.asList(arrays));
                map.put(sysparams.getKeyname(), lists);
            }else {
                map.put(sysparams.getKeyname(), sysparams.getKeyval());
            }
        }
        String nextPrice = map.get(SystemParams.ODIN_BUYING_NEXT_PRICE).toString();
        List<String> amountList = (List<String>) map.get(SystemParams.ODIN_BUYING_AMOUNT_LIST);
        List<Map<String, Object>> canAmount = new ArrayList<>();
        for(String amount : amountList){
            Map<String, Object> maps = new HashMap<>();
            maps.put(amount, new BigDecimal(amount).divide(new BigDecimal(nextPrice), 2, BigDecimal.ROUND_HALF_UP).toPlainString());
            canAmount.add(maps);
        }
        map.put("cannGetAmount", canAmount);
        String sysPath = sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL);
        map.put("docUrl", sysPath + "/web/doc/1.action");
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public boolean checkAmount(String amount) {
        Sysparams sysparams = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_AMOUNT_LIST);
        String c = sysparams.getKeyval().replace("[","").replace("]","");
        String[] arrays = c.split(",");
        List<String> lists = new LinkedList<>(Arrays.asList(arrays));
        for(String val : lists){
            if(val.equals(amount)){
                return true;
            }
        }
        return false;
    }
}
