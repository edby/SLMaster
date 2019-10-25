package com.yibi.batch.biz.impl;

import com.yibi.batch.biz.OrderBiz;
import com.yibi.batch.biz.StatisticsBiz;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.entrty.Statistics;
import com.yibi.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

@Slf4j
@Service
@Transactional
public class StatisticsBizImpl implements StatisticsBiz {
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private DealDigRecordService dealDigRecordService;
    @Autowired
    private DealDigConfigService dealDigConfigService;
    @Autowired
    private UserService userService;

    @Override
    public void statistiscDay() {
        String yestday = DateUtils.getSomeDay(-1);
        String today = DateUtils.getCurrentDateStr();
        Statistics statistics = new Statistics();
        Map<Object, Object> params = new HashMap<>();
        params.put("onoff", GlobalParams.ON);
        List<DealDigConfig> list = dealDigConfigService.selectAll(params);
        for(DealDigConfig dealDigConfig : list) {
            Integer coinType = dealDigConfig.getOrdercointype();
            //每天后台挖矿的总额度
            String dayTotalDealDig = dealDigRecordService.getDayTotalDealDig(coinType, yestday, today);
            //每天推荐人挖矿总量
            String dayTotalReferDealDig = dealDigRecordService.getDayTotalReferDealDig(coinType, yestday, today);
            //每天个人挖矿总量
            String dayTotalPersonDealDig = dealDigRecordService.getDayTotalPersonDealDig(coinType, yestday, today);
            //总挖矿量
            String totalDealDig = dealDigRecordService.getTotalDealDig(coinType);
            Map<Object, Object> map = new HashMap<>();
            map.put("idstatus", GlobalParams.REALNAME_NEW_STATE_TWO);
            //注册挖矿量
            int regiestNumber = userService.selectCount(map);
            statistics.setCoinType(coinType);
            statistics.setDayTotalDealDig(new BigDecimal(dayTotalDealDig));
            statistics.setDayTotalPersonDealDig(new BigDecimal(dayTotalPersonDealDig));
            statistics.setDayTotalReferDealDig(new BigDecimal(dayTotalReferDealDig));
            statistics.setTotalDealDig(new BigDecimal(totalDealDig));
            statistics.setRegiestDig(new BigDecimal(regiestNumber));
            statisticsService.insertSelective(statistics);
        }
    }
}