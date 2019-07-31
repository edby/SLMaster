package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSON;
import com.yibi.batch.biz.OdinBiz;
import com.yibi.batch.biz.YubiProfitBiz;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.OrderSpotMapper;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

@Slf4j
@Service
@Transactional
public class OdinBizImpl implements OdinBiz {
    private static BigDecimal years = new BigDecimal(365);

    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private OdinReleaseRecordService odinReleaseRecordService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private UserService userService;
    @Autowired
    private OdinBuyingRankService odinBuyingRankService;
    @Autowired
    private RedisTemplate<String, String> redis;

    @Override
    public void start() {
        //修改期数
        Sysparams sysparams = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_NUMBER);
        sysparams.setKeyval(String.valueOf(Integer.valueOf(sysparams.getKeyval()) + 1));
        sysparamsService.updateByPrimaryKeySelective(sysparams);
        //修改本期价格
        sysparams = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_THIS_PRICE);
        sysparams.setKeyval(sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_NEXT_PRICE));
        sysparamsService.updateByPrimaryKeySelective(sysparams);
        //修改下期价格
        sysparams = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_NEXT_PRICE);
        String rate = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_RATE);
        BigDecimal nextPrice = new BigDecimal(sysparams.getKeyval()).multiply(new BigDecimal(rate)).add(new BigDecimal(sysparams.getKeyval())).setScale(4, BigDecimal.ROUND_HALF_UP);
        sysparams.setKeyval(nextPrice.toEngineeringString());
        sysparamsService.updateByPrimaryKeySelective(sysparams);
    }

    @Override
    public void changeOrderPrice() {
        //查询最新交易价格
        BigDecimal nowPrice = baseService.getSpotLatestPrice(CoinType.YEZI, CoinType.ENC);
        Sysparams sysparams = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_NOW_ORDER_PRICE);
        sysparams.setKeyval(nowPrice.toEngineeringString());
        sysparamsService.updateByPrimaryKeySelective(sysparams);
        log.info("【最新交易价格】------------" + nowPrice + "------------");
    }

    @Override
    public void release() {
        Map<Object, Object> map = new HashMap<>();
        map.put("accounttype", AccountType.ACCOUNT_YUBI);
        map.put("cointype", CoinType.YEZI);
        List<Account> accountList = accountService.selectAll(map);
        for(Account account : accountList){
            if(account.getFrozenblance().compareTo(BigDecimal.ZERO) == 0){
                continue;
            }
            changeAccountAndAddFlow(account);
        }
    }

    /**
     * 更新钱包并记录流水
     * @param account
     */
    private void changeAccountAndAddFlow(Account account){
        BigDecimal sumOfBalance = account.getFrozenblance();
        BigDecimal releaseBalance = sumOfBalance.divide(years, 2, BigDecimal.ROUND_HALF_UP);
        if(releaseBalance.compareTo(BigDecimal.ZERO) == 0){
            return;
        }
        OdinReleaseRecord odinReleaseRecord = new OdinReleaseRecord();
        odinReleaseRecord.setAmount(releaseBalance);
        odinReleaseRecord.setCoinType(CoinType.YEZI);
        odinReleaseRecord.setUserId(account.getUserid());
        odinReleaseRecordService.insertSelective(odinReleaseRecord);
        accountService.updateAccountAndInsertFlow(account.getUserid(), AccountType.ACCOUNT_YUBI, CoinType.YEZI, releaseBalance, BigDecimalUtils.plusMinus(releaseBalance), account.getUserid(), "ODIN每日释放", odinReleaseRecord.getId());
    }

    @Override
    public void calculationRank() {
        //获取本周期数
        List<Integer> numbers = odinReleaseRecordService.getNumberList();
        //获取本周推荐的人排行前三的用户
        List<Map<String, Object>> odinBuyingRecords = odinReleaseRecordService.getRankList(numbers);
        List<OdinBuyingRank> ranks = new LinkedList<>();
        //获取开奖期数
        Sysparams number = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_RANK_NUMBER);
        for (int i = 0; i < odinBuyingRecords.size(); i++) {
            Map<String, Object> map = odinBuyingRecords.get(i);
            Integer referenceId = (Integer) map.get("referenceid");
            //根据推荐人id查询用户
            User user = userService.selectByReferId(referenceId);
            String phone = user.getPhone();
            phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
            OdinBuyingRank odinBuyingRank = new OdinBuyingRank();
            odinBuyingRank.setNumber(Integer.valueOf(number.getKeyval()) + 1);
            if(i == 0){
                odinBuyingRank.setNumberOneId(Integer.valueOf(phone));
                odinBuyingRank.setNumberOneAmount(new BigDecimal(map.get("amount").toString()));
            }else if(i == 1){
                odinBuyingRank.setNumberTwoId(Integer.valueOf(phone));
                odinBuyingRank.setNumberTwoAmount(new BigDecimal(map.get("amount").toString()));
            }else{
                odinBuyingRank.setNumberThreeId(Integer.valueOf(phone));
                odinBuyingRank.setNumberThreeAmount(new BigDecimal(map.get("amount").toString()));
            }
            odinBuyingRankService.insertSelective(odinBuyingRank);
            ranks.add(odinBuyingRank);
        }
        RedisUtil.addList(redis, String.format(RedisKey.ODIN_BUYING_RANK, number), ranks);
        number.setKeyval(String.valueOf(Integer.valueOf(number.getKeyval()) + 1));
        //修改开奖期数
        sysparamsService.updateByPrimaryKeySelective(number);
    }
}
