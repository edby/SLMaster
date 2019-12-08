package com.yibi.orderapi.event;

import com.google.common.eventbus.Subscribe;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.OrderSpot;
import com.yibi.core.entity.OrderSpotRecord;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.OrderSpotService;
import com.zh.module.bean.spot.param.PlaceOrderParam;
import com.zh.module.bean.spot.result.OrderResult;
import com.zh.module.service.spot.SpotOrderAPIServive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 与okex进行交易
 */
@Slf4j
public class ExchangeListener {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderSpotService orderSpotService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Resource
    private SpotOrderAPIServive spotOrderAPIServive;

    @Subscribe
    public void cancelOrder(ExchangeListenerBean event) {
        OrderSpotRecord record = event.getRecord();
        PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("pgy20191208");
        order.setInstrument_id("eos-usdt");
        order.setType("market");
        order.setSide("buy");
        order.setNotional("1");
        order.setMargin_trading((byte) 1);
        OrderResult orderResult = this.spotOrderAPIServive.addOrder(order);
    }




}

