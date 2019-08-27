package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSON;
import com.yibi.batch.biz.OdinWalletBiz;
import com.yibi.batch.biz.YubiProfitBiz;
import com.yibi.common.utils.*;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class OdinWalletBizImpl implements OdinWalletBiz {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private AccountTransferService accountTransferService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redis;
    @Override
    public void start() {
        String cointypes = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_COINTYPE);
        if(StrUtils.isBlank(cointypes)){
            return;
        }
        cointypes = cointypes.replace("[","").replace("]","");
        String[] coinTypeList = cointypes.split(",");
        for (String coinType : coinTypeList) {
            Map<Object, Object> map = new HashMap<>();
            map.put("accounttype", AccountType.ACCOUNT_YUBI);
            map.put("cointype", coinType);
            List<Account> accountList = accountService.selectAll(map);
            for (Account account : accountList) {
                if ((account.getFrozenblance().compareTo(BigDecimal.ZERO) == 0)) {
                    continue;
                }
                //缓存中未到时间的充值
                String redisKey = String.format(RedisKey.ODIN_WALLET_ACCOUNT, account.getUserid());
                String redisAmount = RedisUtil.searchString(redis, redisKey);
                if (!StrUtils.isBlank(redisAmount)) {
                    BigDecimal frozenAmount = new BigDecimal(redisAmount);
                    if (account.getFrozenblance().compareTo(frozenAmount) > 0) {
                        changeAccountAndAddFlow(account, frozenAmount);
                    }
                } else {
                    changeAccountAndAddFlow(account, BigDecimal.ZERO);
                }
            }
        }
    }

    private void changeAccountAndAddFlow(Account account, BigDecimal frozenAmount) {
        //个人收益率
        String rate = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_USER_RATE);
        //变化额 = （冻结资产 - 未到时间充值金额） * 个人利率
        BigDecimal totalAmount = account.getFrozenblance().subtract(frozenAmount);
        BigDecimal amount = totalAmount.multiply(new BigDecimal(rate)).setScale(4, BigDecimal.ROUND_HALF_UP);
        accountService.updateAccountAndInsertFlow(account.getUserid(), AccountType.ACCOUNT_YUBI, account.getCointype(), amount, BigDecimal.ZERO, account.getUserid(), "奥丁钱包奖励释放-" + CoinType.getCoinName(account.getCointype()), account.getUserid());
        //直推 一级推荐人所得金额
        Integer referUserId = userService.selectByUUID(userService.selectByPrimaryKey(account.getUserid()).getReferenceid()).getId();
        //直推 一级推荐人收益率
        String referRate = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_REFER_USER_RATE_HIGH);
        amount = totalAmount.multiply(new BigDecimal(referRate).setScale(4, BigDecimal.ROUND_HALF_UP));
        accountService.updateAccountAndInsertFlow(referUserId, AccountType.ACCOUNT_YUBI, account.getCointype(), amount, BigDecimal.ZERO, referUserId, "奥丁钱包推荐人奖励释放-" + CoinType.getCoinName(account.getCointype()), referUserId);

        //直推 二级推荐人所得金额
        User headUser = userService.selectByPrimaryKey(referUserId);
        if(headUser == null){
            return;
        }
        Integer headUserId = userService.selectByUUID(headUser.getReferenceid()).getId();
        //直推 二级推荐人收益率
        referRate = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_REFER_USER_RATE_HEAD);
        amount = totalAmount.multiply(new BigDecimal(referRate).setScale(4, BigDecimal.ROUND_HALF_UP));
        accountService.updateAccountAndInsertFlow(headUserId, AccountType.ACCOUNT_YUBI, account.getCointype(), amount, BigDecimal.ZERO, headUserId, "奥丁钱包推荐人奖励释放-" + CoinType.getCoinName(account.getCointype()), headUserId);

    }

}
