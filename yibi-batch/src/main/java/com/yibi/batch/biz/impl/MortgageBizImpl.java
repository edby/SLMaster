package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.yibi.batch.Contants;
import com.yibi.batch.biz.MarketBiz;
import com.yibi.batch.biz.MortgageBiz;
import com.yibi.common.utils.*;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

@Slf4j
@Service
@Transactional
public class MortgageBizImpl implements MortgageBiz {
    @Autowired
    private MortgageProfitRecordService mortgageProfitRecordService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MortgageConfigService mortgageConfigService;
    @Autowired
    private MortgageRecordService mortgageRecordService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private UserService userService;

    @Override
    public void release() {
        //当前开启抵押挖矿的币种
        List<MortgageConfig> mortgageConfigs = mortgageConfigService.selectAll(new HashMap<>());
        List<Integer> mortgage = new LinkedList<>();
        for(MortgageConfig mortgageConfig : mortgageConfigs){
            mortgage.add(mortgageConfig.getCointype());
        }
        for(Integer coinTpye : mortgage) {
            Map<Object, Object> params = new HashMap<>();
            params.put("state", 0);
            params.put("cointype", coinTpye);
            List<MortgageRecord> mortgageRecords = mortgageRecordService.selectAll(params);
            for(MortgageRecord mortgageRecord : mortgageRecords) {
                User user = userService.selectByPrimaryKey(mortgageRecord.getUserid());
                //若用户为注销 不执行
                if(user.getState() == GlobalParams.USER_STATE_CANCEL){
                    continue;
                }
                long space = DateUtils.strToDate(mortgageRecord.getEndTime()).getTime() - System.currentTimeMillis();
                //如果已到期，修改当前状态
                if(space < 0){
                    mortgageRecord.setState((byte) 1);
                    mortgageRecordService.updateByPrimaryKeySelective(mortgageRecord);
                    accountService.updateAccountAndInsertFlow(mortgageRecord.getUserid(), AccountType.ACCOUNT_YUBI, mortgageRecord.getCointype(),
                            BigDecimal.ZERO, BigDecimalUtils.plusMinus(mortgageRecord.getAmount()), mortgageRecord.getUserid(), "抵押冻结金额释放", mortgageRecord.getId());
                    accountService.updateAccountAndInsertFlow(mortgageRecord.getUserid(), AccountType.ACCOUNT_SPOT, mortgageRecord.getCointype(),
                            mortgageRecord.getAmount(), BigDecimal.ZERO, mortgageRecord.getUserid(), "抵押冻结金额释放", mortgageRecord.getId());
                }else{
                    //获取今日释放金额
                    BigDecimal profit = mortgageRecord.getAmount().multiply(mortgageRecord.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                    //修改账户 插入流水
                    accountService.updateAccountAndInsertFlow(mortgageRecord.getUserid(), AccountType.ACCOUNT_SPOT, mortgageRecord.getCointype(),
                            profit, BigDecimal.ZERO, mortgageRecord.getUserid(), "个人抵押挖矿收益", mortgageRecord.getId());
                    //插入每日奖励记录
                    MortgageProfitRecord mortgageProfitRecord = new MortgageProfitRecord();
                    mortgageProfitRecord.setAmount(profit);
                    mortgageProfitRecord.setCointype(mortgageRecord.getCointype());
                    //0个人奖励 1团队奖励
                    mortgageProfitRecord.setIsTeam((byte) 0);
                    mortgageProfitRecord.setUserid(mortgageRecord.getUserid());
                    mortgageProfitRecordService.insertSelective(mortgageProfitRecord);
                    //推荐人奖励
                    referReword(getReferUser(userService.selectByPrimaryKey(mortgageRecord.getUserid())), profit, mortgageRecord);
                }
            }
        }
    }

    private void referReword(User referUser, BigDecimal amount, MortgageRecord mortgageRecord){
        String rate;
        /*一级推荐人*/
        if(referUser != null && referUser.getState() == GlobalParams.ACTIVE && referUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_1) {
            rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_1);
            modifyAccount(referUser.getId(), new BigDecimal(rate).multiply(amount), mortgageRecord, "团队抵押挖矿收益");
        }
        /*二级推荐人*/
        if(referUser != null) {
            referUser = getReferUser(referUser);
            if(referUser != null && referUser.getState() == GlobalParams.ACTIVE && referUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_2) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_2);
                modifyAccount(referUser.getId(), new BigDecimal(rate).multiply(amount), mortgageRecord, "团队抵押挖矿收益");
            }
        }
        /*三级推荐人*/
        if(referUser != null) {
            referUser = getReferUser(referUser);
            if(referUser != null && referUser.getState() == GlobalParams.ACTIVE && referUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_3) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_3);
                modifyAccount(referUser.getId(), new BigDecimal(rate).multiply(amount), mortgageRecord, "团队抵押挖矿收益");
            }
        }
        /*四级推荐人*/
        if(referUser != null) {
            referUser = getReferUser(referUser);
            if(referUser != null && referUser.getState() == GlobalParams.ACTIVE && referUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_4) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_4);
                modifyAccount(referUser.getId(), new BigDecimal(rate).multiply(amount), mortgageRecord, "团队抵押挖矿收益");
            }
        }
        /*五级推荐人*/
        if(referUser != null) {
            referUser = getReferUser(referUser);
            if(referUser != null && referUser.getState() == GlobalParams.ACTIVE && referUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_5) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_5);
                modifyAccount(referUser.getId(), new BigDecimal(rate).multiply(amount), mortgageRecord, "团队抵押挖矿收益");
            }
        }

    }

    private void modifyAccount(Integer userid, BigDecimal amount, MortgageRecord mortgageRecord, String remark){
        User user = userService.selectByPrimaryKey(userid);
        //该用户需存在且登录过
        if (user != null && user.getLogintime() != null) {
            accountService.updateAccountAndInsertFlow(userid, AccountType.ACCOUNT_SPOT, mortgageRecord.getCointype(),
                    amount, BigDecimal.ZERO, mortgageRecord.getUserid(), remark, mortgageRecord.getId());
            MortgageProfitRecord mortgageProfitRecord = new MortgageProfitRecord();
            mortgageProfitRecord.setAmount(amount);
            mortgageProfitRecord.setCointype(mortgageRecord.getCointype());
            //0个人奖励 1团队奖励
            mortgageProfitRecord.setIsTeam((byte) 1);
            mortgageProfitRecord.setUserid(userid);
            mortgageProfitRecordService.insertSelective(mortgageProfitRecord);
        }
    }
    /**
     * 根据用户推荐码获取推荐人userId
     * @param user
     * @return
     */
    private User getReferUser(User user){
        return userService.selectByUUID(user.getReferenceid());
    }
}
