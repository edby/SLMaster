package com.zh.module.service.ett.impl;

import com.zh.module.bean.ett.param.EttCreateOrderParam;
import com.zh.module.bean.ett.result.CursorPager;
import com.zh.module.bean.ett.result.EttCancelOrderResult;
import com.zh.module.bean.ett.result.EttCreateOrderResult;
import com.zh.module.bean.ett.result.EttOrder;
import com.zh.module.client.APIClient;
import com.zh.module.config.APIConfiguration;
import com.zh.module.service.ett.EttOrderAPIService;

import java.math.BigDecimal;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttOrderAPIServiceImpl implements EttOrderAPIService {

    private final APIClient client;
    private final EttOrderAPI api;

    public EttOrderAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = this.client.createService(EttOrderAPI.class);
    }

    @Override
    public EttCreateOrderResult createOrder(String ett, Integer type, BigDecimal amount, BigDecimal size, String clientOid) {
        EttCreateOrderParam param = new EttCreateOrderParam();
        param.setEtt(ett);
        param.setType(type);
        param.setAmount(amount);
        param.setSize(size);
        param.setClientOid(clientOid);
        return this.client.executeSync(this.api.createOrder(param));
    }

    @Override
    public EttCancelOrderResult cancelOrder(String orderId) {
        return this.client.executeSync(this.api.cancelOrder(orderId));
    }

    @Override
    public CursorPager<EttOrder> getOrder(String ett, Integer type, Integer status, String before, String after, int limit) {
        return this.client.executeSyncCursorPager(this.api.getOrder(ett, type, status, before, after, limit));
    }

    @Override
    public EttOrder getOrder(String orderId) {
        return this.client.executeSync(this.api.getOrder(orderId));
    }
}
