package com.yibi.websocket.biz.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.HTTP;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.service.OrderManageService;
import com.yibi.core.service.SysparamsService;
import com.yibi.websocket.biz.JoinBiz;
import com.yibi.websocket.enums.EnumScene;
import com.yibi.websocket.model.ResultCode;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import io.netty.channel.Channel;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.cs.ext.Big5;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
@Transactional
@Log4j2
public class JoinBizImpl extends BaseBizImpl implements JoinBiz {
    @Resource
    private RedisTemplate<String, String> redis;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private SysparamsService sysparamsService;


    @Override
    public void join(Channel channel, JSONObject data, ResultObj resultObj, Map<String, WebSocketClient> allSocketClients) {
        int scene = data.getIntValue("scene");
        int gear = data.getIntValue("gear");
        int c1 = data.getIntValue("c1");
        int c2 = data.getIntValue("c2");
        resultObj.setScene(scene);
        if (scene == 0) {
            resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
            resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
            sendMessage(channel, resultObj);
            return;
        }
        String comingIp = channel.remoteAddress().toString();
        WebSocketClient wsc = allSocketClients.get(comingIp);
        if (wsc == null) {
            wsc = new WebSocketClient(comingIp, scene, gear, c1, c2);
            wsc.setChannel(channel);
            wsc.setScene(scene);
            wsc.setGear(gear);
            wsc.setC1(c1);
            wsc.setC2(c2);
            allSocketClients.put(comingIp, wsc);
        } else {
            wsc.setChannel(channel);
            wsc.setScene(scene);
            wsc.setGear(gear);
            wsc.setC1(c1);
            wsc.setC2(c2);
        }
        resultObj.setCode(ResultCode.TYPE_SUCCESS_JOIN.code());
        resultObj.setMsg(ResultCode.TYPE_SUCCESS_JOIN.message());
        sendMessage(channel, resultObj);
        // 处理返回数据
        // 现货350
        if (scene == EnumScene.SCENE_ORDER.getScene()) {
            dealSceneOrder(data, channel, resultObj);
            //现货选择币种行情
        }else if (scene == EnumScene.SCENE_ORDER_MARKET.getScene()) {
            dealSceneOrderMarket(data, channel, resultObj);
            // 行情
        }else if (scene == EnumScene.SCENE_MARKET_YIBI.getScene() || scene == EnumScene.SCENE_MARKET_ZULIU.getScene()) {
            dealSceneMarket(data, channel, resultObj);
            // K线图
        } else if (scene == EnumScene.SCENE_KLINE_YIBI.getScene() || scene == EnumScene.SCENE_KLINE_ZULIU.getScene()) {
            dealSceneKline(data, channel, resultObj);
            //首页行情
        }else if (scene == EnumScene.SCENE_INDEX.getScene() || (scene == EnumScene.SCENE_INDEX_SORT.getScene())) {
            dealSceneIndex(data, channel, resultObj);
        }else {
            // 什么都不做
            resultObj.setCode(ResultCode.TYPE_ERROR_PARAMS.code());
            resultObj.setMsg(ResultCode.TYPE_ERROR_PARAMS.message());
            sendMessage(channel, resultObj);
            return;
        }
    }

    //处理现货选择币种 行情
    public void dealSceneOrderMarket(JSONObject data, Channel incoming, ResultObj resultObj) {
        String c1 = data.getString("c1");//计价币
        Map<Object, Object> map = new HashMap<>();
        map.put("unitcointype", c1);
        map.put("onoff", GlobalParams.ON);
        List<OrderManage> orderManages = orderManageService.selectAllOrderBySeque(map);
        try {
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
            resultObj.setInfo(JSONArray.toJSONString(list));
            sendMessage(incoming, resultObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //处理初始化首页行情数据
    private void dealSceneIndex(JSONObject data, Channel incoming, ResultObj resultObj) {
        int scene = data.getIntValue("scene");
        String coinLists = sysparamsService.getValStringByKey(SystemParams.HOMEPAGE_MARKET_COIN_LIST);
        List<String> coinList =  Arrays.asList(coinLists.split(","));
        try {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (String orderCoinType :coinList) {
                Integer unitCoin = CoinType.USDT;
                Integer orderCoin = Integer.valueOf(orderCoinType);
                String redisKey = String.format(RedisKey.MARKET, 1, unitCoin, orderCoin);
                String redisVal = RedisUtil.searchString(redis, redisKey);
                if (redisVal != null && !redisVal.equals("")) {
                    Map<String, Object> jsonMap = JSON.parseObject(redisVal, Map.class);
                    list.add(jsonMap);
                }
            }
            if(scene == EnumScene.SCENE_INDEX_SORT.getScene()){
                sortList(list);
            }
            //todo:涨幅榜、新币榜没做
            resultObj.setInfo(JSONArray.toJSONString(list));
            sendMessage(incoming, resultObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理初始化现货交易场景
    public void dealSceneOrder(JSONObject data, Channel incoming, ResultObj resultObj) {
        String c1 = data.getString("c1");//计价币
        String c2 = data.getString("c2");//交易币
        int gear = data.getIntValue("gear");
        int scene = data.getIntValue("scene");
        int count = 10;// 默认10挡
        if (gear != 0)
            count = gear;
        List saleslist = null;
        List buyslist = null;
        String price = "";
        List orderRecordList = null;
        List zline = null;

        try {
            String rediskey = String.format(RedisKey.BUY_ORDER_LIST, c1, c2);
            buyslist = RedisUtil.searchList(redis, rediskey, 0, count - 1);
            rediskey = String.format(RedisKey.SALE_ORDER_LIST, c1, c2);
            int size = (int)RedisUtil.searchListSize(redis, rediskey);
            saleslist = RedisUtil.searchList(redis, rediskey, size - count + 1 > 0 ? size - count + 1 : 0, size);
            rediskey = String.format(RedisKey.LATEST_TRANS_PRICE, c1, c2);
            price = RedisUtil.searchString(redis, rediskey);
            rediskey = String.format(RedisKey.ORDER_RECORD_LIST, c1, c2);
            orderRecordList = RedisUtil.searchList(redis, rediskey, 0, 19);
            rediskey = String.format(RedisKey.KLINEYB,1,c1,c2);
            int zlineSize = (int)RedisUtil.searchListSize(redis, rediskey);
            zline = RedisUtil.searchList(redis, rediskey, zlineSize - 59 <= 0 ? 0 :zlineSize - 59, zlineSize);
            JSONArray buyjson = JSONObject.parseArray(buyslist.toString());
            JSONArray zlinejson = JSONObject.parseArray(zline.toString());
            JSONArray salejson = JSONObject.parseArray(saleslist.toString());
            JSONArray recordjson = JSONObject.parseArray(orderRecordList.toString());
            JSONObject json = new JSONObject();
            json.put("buys", buyjson);
            json.put("sales", salejson);
            json.put("price", price);
            json.put("records", recordjson);
            json.put("zline", zlinejson);
            log.info("发送现货数据包:" + json.toString());
            resultObj.setInfo(json.toJSONString());
            sendMessage(incoming, resultObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化行情数据
     */
    public void dealSceneMarket(JSONObject data, Channel incoming, ResultObj resultObj) {
        int scene = data.getIntValue("scene");
        Map<Object, Object> params = new HashMap<Object, Object>();
        int marketType = 1;
        params.put("onoff", GlobalParams.ON);
        if (scene == EnumScene.SCENE_MARKET_ZULIU.getScene()) {
            String marketList = sysparamsService.getValStringByKey(SystemParams.MARKET_COIN_LIST);
            String result = "";
            List<Map<String, Object>> list = new LinkedList<>();
            JSONObject broadcast = new JSONObject();
            broadcast.put("action", "broadcast");
            for(String coin : Arrays.asList(marketList.split(","))){
                Map<String, Object> map = new HashMap<>();
                try {
                    result = HTTP.get(String.format("https://api.hadax.com/market/detail?symbol=%susdt", coin), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONObject tick = jsonObject.getJSONObject("tick");
                BigDecimal price = new BigDecimal(tick.getString("close")).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal cnyPrice = price.multiply(new BigDecimal(7.1)).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal vol = new BigDecimal(tick.getString("amount")).setScale(0, BigDecimal.ROUND_HALF_UP);
                String todayPrice = RedisUtil.searchString(redis, String.format(RedisKey.OTHER_COIN_TODAY_PRICE, coin));
                todayPrice = todayPrice == null || "".equals(todayPrice) ? "0" : todayPrice;
                BigDecimal todayPriceDec = new BigDecimal(todayPrice);
                BigDecimal percentage;
                if(todayPriceDec.compareTo(BigDecimal.ZERO) == 0){
                    percentage = new BigDecimal(100);
                }else {
                    percentage = price.subtract(todayPriceDec).divide(todayPriceDec, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                }
                StringBuffer percentageStr = new StringBuffer();
                if(percentage.compareTo(BigDecimal.ZERO) > 0){
                    percentageStr = percentageStr.append("+").append(percentage.toPlainString()).append("%");
                }else{
                    percentageStr = percentageStr.append("-").append(percentage.toPlainString()).append("%");
                }
                //usdt价格
                map.put("newPrice", price.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                //cny价格
                map.put("newPriceCNY", cnyPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                //交易量
                map.put("sumAmount", vol.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                //百分比
                map.put("chgPrice", percentage.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
                //币种
                map.put("orderCoinType", 0);
                map.put("unitCoinType", 0);
                map.put("orderCoinCnName", coin.toUpperCase());
                map.put("orderCoinName", "1");
                map.put("unitCoinName", "1");
                map.put("high", "1");
                map.put("low", "1");
                list.add(map);
            }
            resultObj.setInfo(JSONArray.toJSONString(list));
            sendMessage(incoming, resultObj);
        }else {
            try {
                List<OrderManage> listOM = orderManageService.selectAllOrderBySeque(params);
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (OrderManage orderManage : listOM) {
                    Integer unitCoin = orderManage.getUnitcointype();
                    Integer orderCoin = orderManage.getOrdercointype();
                    String redisKey = String.format(RedisKey.MARKET, marketType, unitCoin, orderCoin);
                    String redisVal = RedisUtil.searchString(redis, redisKey);
                    if (redisVal != null && !redisVal.equals("")) {
                        Map<String, Object> jsonMap = JSON.parseObject(redisVal, Map.class);
                        list.add(jsonMap);
                    }
                }
                resultObj.setInfo(JSONArray.toJSONString(list));
                sendMessage(incoming, resultObj);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void dealSceneKline(JSONObject data, Channel incoming, ResultObj resultObj) {
        int scene = data.getIntValue("scene");
        Integer c1 = data.getIntValue("c1");
        Integer c2 = data.getIntValue("c2");
        Integer gear = data.getIntValue("gear");
        int marketType = 0;
        try {
            String redisKey = "";
            if (scene == EnumScene.SCENE_KLINE_YIBI.getScene()) { //一币K图
                marketType = 1;
                redisKey = String.format(RedisKey.KLINEYB, gear, c1, c2);
            }
            if (scene == EnumScene.SCENE_KLINE_ZULIU.getScene()) {//主流k图
                marketType = 2;
                redisKey = String.format(RedisKey.KLINEOK, gear, c1, c2);
            }
            List klineList = RedisUtil.searchList(redis, redisKey, 0, 299);
            JSONArray jsonArray = JSONObject.parseArray(klineList.toString());
            JSONObject json = new JSONObject();
            json.put("kline", jsonArray);
            redisKey = String.format(RedisKey.ORDER_RECORD_LIST, c1, c2);
            List orderRecordList = RedisUtil.searchList(redis, redisKey, 0, 19);
            JSONArray recordJson = JSONObject.parseArray(orderRecordList.toString());
            json.put("records", recordJson);
            redisKey = String.format(RedisKey.MARKET, marketType, c1, c2);
            String market = RedisUtil.searchString(redis, redisKey);
            Map<String, Object> marketMap = JSON.parseObject(market, Map.class);
            json.put("market", marketMap);
            resultObj.setInfo(json.toJSONString());
            log.info("发送K线初始化信息数据包:" + json.toString());
            sendMessage(incoming, resultObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getParam(String key){
        Sysparams param = sysparamsService.getValByKey(key);
        if(param ==null){
            return "";
        }else{
            return param.getKeyval();
        }
    }

    private void sortList(List<Map<String, Object>> list) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return new BigDecimal(o2.get("chgPrice").toString()).compareTo(new BigDecimal(o1.get("chgPrice").toString()));
            }
        });
    }
}
