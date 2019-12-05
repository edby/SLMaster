package com.yibi.websocket.biz.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.websocket.biz.BroadCastBiz;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Log4j2
@Component
public class BroadCastBizImpl extends BaseBizImpl implements BroadCastBiz {
    @Resource
    private RedisTemplate<String, String> redis;

    @Override
    public void broadCast(JSONObject data, Map<String, WebSocketClient> allSocketClients) {
        Object info = data.get("info");

        int scene = data.getIntValue("scene");
        int gear = data.getIntValue("gear");
        int c1 = data.getIntValue("c1");
        int c2 = data.getIntValue("c2");
        for (WebSocketClient client : allSocketClients.values()) {
            if (client.getC1() == c1 && client.getC2() == c2 &&  scene == client.getScene()) {
                if(gear==1){
                    ResultObj resultObj = new ResultObj();
                    resultObj.setInfo(JSONObject.toJSONString(info));
                    resultObj.setScene(client.getScene());
                    sendMessage(client.getChannel(), resultObj);
                }else if(gear == client.getGear() ) {
                    ResultObj resultObj = new ResultObj();
                    resultObj.setInfo(JSONObject.toJSONString(info));
                    resultObj.setScene(client.getScene());
                    sendMessage(client.getChannel(), resultObj);
                }
            }
        }
    }

    @Override
    public void okBroadcast(JSONObject data, Map<String, WebSocketClient> allSocketClients) {
        Object info = data.get("info");

        int scene = data.getIntValue("scene");
        int gear = data.getIntValue("gear");
        int c1 = data.getIntValue("c1");
        int c2 = data.getIntValue("c2");
        //todo 需取消注释
        /*for (WebSocketClient client : allSocketClients.values()) {
            if (client.getC1() == c1 && client.getC2() == c2 &&  scene == client.getScene()) {
                if(gear==1){
                    ResultObj resultObj = new ResultObj();
                    resultObj.setInfo(JSONObject.toJSONString(info));
                    resultObj.setScene(client.getScene());
                    sendMessage(client.getChannel(), resultObj);
                }else if(gear == client.getGear() ) {
                    ResultObj resultObj = new ResultObj();
                    resultObj.setInfo(JSONObject.toJSONString(info));
                    resultObj.setScene(client.getScene());
                    sendMessage(client.getChannel(), resultObj);
                }
            }
        }*/
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(info));
        JSONArray buys = json.getJSONArray("buys");
        for (int i = 0; i < buys.size(); i++) {
            JSONObject buysJSONArray = buys.getJSONObject(i);
            if("1".equals(buysJSONArray.getString("num"))){
                String price = buysJSONArray.getString("price");
                RedisUtil.addString(redis, RedisKey.OKEX_DEPTH_COIN_PRICE_BUYS, price);
            }
        }
        JSONArray sales = json.getJSONArray("sales");
        for (int i = 0; i < sales.size(); i++) {
            JSONObject salesJSONObject = sales.getJSONObject(i);
            if("1".equals(salesJSONObject.getString("num"))){
                String price = salesJSONObject.getString("price");
                RedisUtil.addString(redis, RedisKey.OKEX_DEPTH_COIN_PRICE_SALES, price);
            }
        }
    }
}
