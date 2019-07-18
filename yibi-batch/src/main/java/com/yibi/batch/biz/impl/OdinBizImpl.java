package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSON;
import com.yibi.batch.biz.OdinBiz;
import com.yibi.batch.biz.YubiProfitBiz;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.TimeStampUtils;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
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
            changeAccountAndAddFlow(account);
        }
    }

    /**
     * 更新钱包并记录流水
     * @param account
     */
    private void changeAccountAndAddFlow(Account account){
        BigDecimal sumOfBalance = account.getFrozenblance();
        CoinScale coinScale = coinScaleService.queryByCoin(CoinType.YEZI, CoinType.ENC);
        BigDecimal releaseBalance = sumOfBalance.divide(years, coinScale.getCalculscale(), BigDecimal.ROUND_HALF_UP);
        OdinReleaseRecord odinReleaseRecord = new OdinReleaseRecord();
        odinReleaseRecord.setAmount(releaseBalance);
        odinReleaseRecord.setCoinType(CoinType.YEZI);
        odinReleaseRecord.setUserId(account.getUserid());
        odinReleaseRecordService.insertSelective(odinReleaseRecord);
        accountService.updateAccountAndInsertFlow(account.getUserid(), AccountType.ACCOUNT_YUBI, CoinType.YEZI, releaseBalance, BigDecimalUtils.plusMinus(releaseBalance), account.getUserid(), "奥丁-每日释放", odinReleaseRecord.getId());

    }
}
