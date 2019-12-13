package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yibi.batch.Contants;
import com.yibi.batch.biz.MarketBiz;
import com.yibi.batch.biz.OrderBiz;
import com.yibi.common.utils.*;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.OrderManage;
import com.yibi.core.entity.OrderSpot;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.OrderManageService;
import com.yibi.core.service.OrderSpotService;
import com.yibi.core.service.SysparamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

@Slf4j
@Service
@Transactional
public class MarketBizImpl implements MarketBiz {
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private OrderSpotService orderSpotService;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RedisTemplate<String, String> redis;

    @Override
    public void changeMarket() {
        String marketList = sysparamsService.getValStringByKey(SystemParams.MARKET_COIN_LIST);
        String result = "";
        List<Map<String, Object>> list = new LinkedList<>();
        JSONObject broadcast = new JSONObject();
        broadcast.put("action", "broadcast");
        for(String coin : Arrays.asList(marketList.split(","))){
            Map<String, Object> map = new HashMap<>();
            try {
                result = HTTP.get(String.format(Contants.HUO_BI_MARKET, coin), null);
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
            if(todayPriceDec.equals(BigDecimal.ZERO) || todayPriceDec.compareTo(new BigDecimal(0.00)) == 0){
                percentage = new BigDecimal(100);
            }else {
                percentage = price.subtract(todayPriceDec).divide(todayPriceDec, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            }
            StringBuffer percentageStr = new StringBuffer();
            if(percentage.compareTo(BigDecimal.ZERO) > 0){
                percentageStr = percentageStr.append("+").append(percentage.toPlainString()).append("%");
            }else{
                percentageStr = percentageStr.append(percentage.toPlainString()).append("%");
            }
            //usdt价格
            map.put("newPrice", price.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            //cny价格
            map.put("newPriceCNY", cnyPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            //交易量
            map.put("sumAmount", vol.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString());
            //百分比
            map.put("chgPrice", percentage.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
            //币种
            map.put("orderCoinType", CoinType.getCode(coin.toUpperCase()));
            map.put("unitCoinType", 9);
            map.put("orderCoinCnName", coin.toUpperCase());
            map.put("orderCoinName", "1");
            map.put("unitCoinName", "1");
            map.put("high", "1");
            map.put("low", "1");
            list.add(map);
        }
        JSONObject broadcastData = new JSONObject();
        broadcastData.put("c1", 0);
        broadcastData.put("c2", 0);
        broadcastData.put("scene", 3512);
        broadcastData.put("gear", 0);
        broadcastData.put("info", list);
        broadcast.put("data", broadcastData);
        WebsocketClientUtils.sendTextMessage(broadcast.toJSONString());
    }

    @Override
    public void getDayPrice() {
        String marketList = sysparamsService.getValStringByKey(SystemParams.MARKET_COIN_LIST);
        for(String coin : Arrays.asList(marketList.split(","))) {
            String result = "";
            try {
                result = HTTP.get(String.format(Contants.HUO_BI_MARKET, coin), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject tick = jsonObject.getJSONObject("tick");
            BigDecimal price = new BigDecimal(tick.getString("close")).setScale(2, BigDecimal.ROUND_HALF_UP);
            RedisUtil.addString(redis, String.format(RedisKey.OTHER_COIN_TODAY_PRICE, coin), price.toPlainString());
        }
    }

    @Override
    public void changeMood() {
        RedisUtil.addString(redis, String.format(RedisKey.MARKET_MOOD, 0), "50");
        RedisUtil.addString(redis, String.format(RedisKey.MARKET_MOOD, 1), "50");
    }
}
