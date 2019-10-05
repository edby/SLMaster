package com.yibi.orderapi.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.WebsocketClientUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.service.OrderManageService;
import com.yibi.core.service.SysparamsService;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class SocketBizTest extends BaseTest {

    @Resource
    private OrderManageService orderManageService;
    @Resource
    private SysparamsService sysparamsService;
    @Resource
    private RedisTemplate<String, String> redis;

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
    @Test
    public void queryUserTest2(){
        String coinList = sysparamsService.getValStringByKey(SystemParams.HOMEPAGE_MARKET_COIN_LIST);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (String orderCoinType : Collections.singletonList(coinList)) {
                Integer unitCoin = CoinType.USDT;
                Integer orderCoin = Integer.valueOf(orderCoinType);
                String redisKey = String.format(RedisKey.MARKET, 1, unitCoin, orderCoin);
                String redisVal = RedisUtil.searchString(redis, redisKey);
                if (redisVal != null && !redisVal.equals("")) {
                    Map<String, Object> jsonMap = JSON.parseObject(redisVal, Map.class);
                    list.add(jsonMap);
                }
            }
            //todo:涨幅榜、新币榜没做
            System.out.printf(JSONArray.toJSONString(list));
    }

    @Test
    public void queryUserTest1(){
        String c1 = "0";
        Map<Object, Object> map = new HashMap<>();
        map.put("unitcointype", c1);
        map.put("onoff", GlobalParams.ON);
        List<OrderManage> orderManages = orderManageService.selectAllOrderBySeque(map);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (OrderManage orderManage : orderManages) {
            Integer unitCoin = Integer.valueOf(c1);
            Integer orderCoin = orderManage.getOrdercointype();
            String redisKey = String.format(RedisKey.MARKET, 1, unitCoin, orderCoin);
            String redisVal = RedisUtil.searchString(redis, redisKey);
            if (redisVal != null && !redisVal.equals("")) {
                Map<String, Object> jsonMap = JSON.parseObject(redisVal, Map.class);
                list.add(jsonMap);
            }
        }
        System.out.print(JSONArray.toJSONString(list));
    }
}
