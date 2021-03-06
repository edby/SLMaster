package com.yibi.batch.biz;

import com.alibaba.fastjson.JSONObject;
import com.yibi.batch.BaseTest;
import com.yibi.common.utils.WebsocketClientUtils;
import com.yibi.core.entity.AccountChain;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.service.CoinManageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/24 0024.
 */
public class marketTest extends BaseTest{
    @Autowired
    private MarketBiz marketBiz;
    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private MortgageBiz mortgageBiz;


    @Test
    public void test(){
        marketBiz.getDayPrice();
    }
    @Test
    public void orderOff(){
        orderBiz.cancelOrder();
    }
    @Test
    public void test2(){
        marketBiz.changeMarket();
    }
    @Test
    public void test3(){
        marketBiz.changeMood();
    }
    @Test
    public void test4(){
        mortgageBiz.release();
    }
    @Test
    public void test5(){
        orderBiz.changePrice();
    }
    @Test
    public void initSocket(){
        JSONObject broadcast = new JSONObject();
        broadcast.put("action", "join");
        JSONObject broadcastData = new JSONObject();
        broadcastData.put("c1", 1);
        broadcastData.put("c2", 1);
        broadcastData.put("scene", 354);
        broadcastData.put("gear", 1);
        broadcast.put("data", broadcastData);
        WebsocketClientUtils.sendTextMessage(broadcast.toJSONString());
        for(;;) {
            broadcast.put("action", "ping");
            WebsocketClientUtils.sendTextMessage(broadcast.toJSONString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
