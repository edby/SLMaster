package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.MortgageConfig;
import com.yibi.core.entity.User;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.MortgageConfigService;
import com.yibi.core.service.SysparamsService;
import com.yibi.orderapi.biz.AccountBiz;
import com.yibi.orderapi.biz.MortgageBiz;
import com.yibi.orderapi.biz.SystemBiz;
import com.yibi.orderapi.biz.WalletBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:17
 */
@Service
public class MortgageBizImpl implements MortgageBiz {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private MortgageConfigService mortgageConfigService;
    @Override
    public String init(User user, Integer coinType) {
        Map<String, Object> result = new HashMap<>();
        MortgageConfig mortgageConfig = mortgageConfigService.selectByCoin(coinType);
        if(mortgageConfig == null){
            return Result.toResult(ResultCode.MORTGAGE_OFF);
        }
        Account account = accountService.getAccountByUserAndCoinTypeAndAccount(user.getId(), coinType, AccountType.ACCOUNT_SPOT);
        //查询可用余额
        result.put("availbalance", account.getAvailbalance());
        String digCycle = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_CYCLE);
        String digCycleRate = sysparamsService.getValStringByKey(SystemParams.MORTGAGE_DIG_CYCLE_RATE);
        JSONArray cycle = JSON.parseArray(digCycle);
        JSONArray rate = JSON.parseArray(digCycleRate);
        result.put("cycle", cycle);
        result.put("rate", rate);
        return Result.toResult(ResultCode.SUCCESS, result);
    }
}
