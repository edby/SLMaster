package com.yibi.orderapi.biz;

import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.WebsocketClientUtils;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class SocketBizTest extends BaseTest {

    @Test
    public void queryUserTest(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        map.put("action", "ping");
        for(;;) {
            WebsocketClientUtils.sendTextMessage(JSONObject.toJSONString(map));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            params.put("action", "join");
            params.put("scene", 354);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("scene", 354);
            jsonObject.put("c1", 0);
            params.put("data", jsonObject);
            WebsocketClientUtils.sendTextMessage(JSONObject.toJSONString(params));
        }
    }

}
