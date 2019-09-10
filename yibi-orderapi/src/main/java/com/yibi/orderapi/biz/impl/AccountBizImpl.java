package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.core.constants.AccountType;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.User;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.orderapi.biz.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class AccountBizImpl extends BaseBizImpl implements AccountBiz {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;


    @Override
    public BigDecimal queryByUser(Integer id, int accountType) {
        List<Account> list = accountService.queryByUserIdAndAccountType(id, accountType);
        BigDecimal totalSumOfCny = new BigDecimal(0);
        for(Account account :list){
            Integer coinType = account.getCointype();
            BigDecimal availBalance = account.getAvailbalance();
            BigDecimal frozenBlance = account.getFrozenblance();
            BigDecimal totalBalance = BigDecimalUtils.add(availBalance, frozenBlance);
            BigDecimal totalOfCny = BigDecimalUtils.multiply(totalBalance, getPriceOfCNY(coinType));
            totalSumOfCny = BigDecimalUtils.add(totalOfCny, totalSumOfCny);
        }
        return totalSumOfCny;
    }

    @Override
    public BigDecimal queryTotalByUser(User user) {
        Integer userId = user.getId();
        List<Integer> accountTypeList = new LinkedList<>();
        accountTypeList.add(AccountType.ACCOUNT_C2C);
        accountTypeList.add(AccountType.ACCOUNT_SPOT);
        accountTypeList.add(AccountType.ACCOUNT_YUBI);
        List<Account> accountList = accountService.queryByUserId(userId);
        BigDecimal totalSumOfCny = new BigDecimal(0);
        for(Integer accountType : accountTypeList){
            for(Account account : accountList){
                if(account.getAccounttype().equals(accountType)){
                    Integer coinType = account.getCointype();
                    BigDecimal availBalance = account.getAvailbalance();
                    BigDecimal frozenBlance = account.getFrozenblance();
                    BigDecimal totalBalance = BigDecimalUtils.add(availBalance, frozenBlance);
                    BigDecimal totalOfCny = BigDecimalUtils.multiply(totalBalance, getPriceOfCNY(coinType));
                    totalSumOfCny = BigDecimalUtils.add(totalOfCny, totalSumOfCny);
                }
            }
        }
        return totalSumOfCny;
    }
}
