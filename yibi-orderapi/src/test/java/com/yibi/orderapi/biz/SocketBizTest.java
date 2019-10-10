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
import java.math.BigDecimal;
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
    @Test
    public void queryUserTest3(){
            HashMap<String, Object> map = new HashMap<>();
            List<Map<String, Object>> list = new LinkedList<>();
                String redisVal = "{\n" +
                        "            \"orderCoinType\":1,\n" +
                        "            \"high\":\"1.4989\",\n" +
                        "            \"unitCoinType\":0,\n" +
                        "            \"newPriceCNY\":\"0.65\",\n" +
                        "            \"low\":\"0.6300\",\n" +
                        "            \"unitCoinName\":\"ECN\",\n" +
                        "            \"chgPrice\":\"3.18\",\n" +
                        "            \"orderCoinName\":\"ODIN\",\n" +
                        "            \"orderCoinCnName\":\"ODIN\",\n" +
                        "            \"newPrice\":\"0.6500\",\n" +
                        "            \"sumAmount\":\"5625.1519\"\n" +
                        "        }";
                String redisVal2 = "{\n" +
                        "\"orderCoinType\": 1,\n" +
                        "\"high\": \"1.4989\",\n" +
                        "\"unitCoinType\": 0,\n" +
                        "\"newPriceCNY\": \"0.65\",\n" +
                        "\"low\": \"0.6300\",\n" +
                        "\"unitCoinName\": \"ECN\",\n" +
                        "\"chgPrice\": \"-4.18\",\n" +
                        "\"orderCoinName\": \"ODIN\",\n" +
                        "\"orderCoinCnName\": \"ODIN\",\n" +
                        "\"newPrice\": \"0.6500\",\n" +
                        "\"sumAmount\": \"5625.1519\"\n" +
                        "}";
                String redisVal3 = "{\n" +
                        "            \"orderCoinType\":1,\n" +
                        "            \"high\":\"1.4989\",\n" +
                        "            \"unitCoinType\":0,\n" +
                        "            \"newPriceCNY\":\"0.65\",\n" +
                        "            \"low\":\"0.6300\",\n" +
                        "            \"unitCoinName\":\"ECN\",\n" +
                        "            \"chgPrice\":\"1.18\",\n" +
                        "            \"orderCoinName\":\"ODIN\",\n" +
                        "            \"orderCoinCnName\":\"ODIN\",\n" +
                        "            \"newPrice\":\"0.6500\",\n" +
                        "            \"sumAmount\":\"5625.1519\"\n" +
                        "        }";
                    Map<String, Object> jsonMap = JSON.parseObject(redisVal, Map.class);
                    Map<String, Object> jsonMap2 = JSON.parseObject(redisVal2, Map.class);
                    Map<String, Object> jsonMap3 = JSON.parseObject(redisVal3, Map.class);
                    String chgPrice = jsonMap.get("chgPrice").toString();
                    String chgPrice2 = jsonMap2.get("chgPrice").toString();
                    String chgPrice3 = jsonMap3.get("chgPrice").toString();
        list.add(jsonMap);
        list.add(jsonMap2);
        list.add(jsonMap3);
                /*    map.put(chgPrice, jsonMap);
                    map.put(chgPrice2, jsonMap2);
                    map.put(chgPrice3, jsonMap3);*/
            sortList(list);
        System.out.print(JSONArray.toJSONString(list));
    }

    private void sortList(List<Map<String, Object>> list) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return new BigDecimal(o2.get("chgPrice").toString()).compareTo(new BigDecimal(o1.get("chgPrice").toString()));
            }
        });
    }

    public static HashMap<String, Object> sortHashMap(HashMap<String, Object> map){
            //從HashMap中恢復entry集合，得到全部的鍵值對集合
            Set<Map.Entry<String, Object>> entey = map.entrySet();
            //將Set集合轉為List集合，為了實用工具類的排序方法
            List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(entey);
            //使用Collections工具類對list進行排序
            Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
                @Override
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    //按照age倒敘排列
                    return new BigDecimal(o2.getKey()).compareTo(new BigDecimal(o1.getKey()));
                }
            });
            //創建一個HashMap的子類LinkedHashMap集合
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
            //將list中的數據存入LinkedHashMap中
            for(Map.Entry<String, Object> entry:list){
                linkedHashMap.put(entry.getKey(),entry.getValue());
            }
            return linkedHashMap;
        }
}
