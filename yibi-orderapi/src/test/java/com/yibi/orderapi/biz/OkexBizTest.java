package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import com.yibi.orderapi.SpotAPIBaseTests;
import com.zh.module.bean.spot.param.PlaceOrderParam;
import com.zh.module.bean.spot.result.Account;
import com.zh.module.bean.spot.result.OrderResult;
import com.zh.module.service.spot.SpotAccountAPIService;
import com.zh.module.service.spot.SpotOrderAPIServive;
import com.zh.module.service.spot.impl.SpotAccountAPIServiceImpl;
import com.zh.module.service.spot.impl.SpotOrderApiServiceImpl;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class OkexBizTest extends SpotAPIBaseTests {

    @Resource
    private SpotAccountAPIService spotAccountAPIService;

    @Resource
    private SpotOrderAPIServive spotOrderAPIServive;

    @Before
    public void before() {
        this.config = this.config();
        this.spotAccountAPIService = new SpotAccountAPIServiceImpl(this.config);
        this.spotOrderAPIServive = new SpotOrderApiServiceImpl(this.config);
    }
    /**
     * 账户信息
     */
    @Test
    public void getAccounts() {
        final List<Account> accounts = this.spotAccountAPIService.getAccounts();
        System.out.println(accounts);
    }

    /**
     * 下单
     */
    @Test
    public void addOrder() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("pgy20191208");
        order.setInstrument_id("eos-usdt");
        order.setType("market");
        order.setSide("buy");
        order.setNotional("1");
        order.setMargin_trading((byte) 1);
        final OrderResult orderResult = this.spotOrderAPIServive.addOrder(order);
        System.out.println(orderResult);
    }
    
}
