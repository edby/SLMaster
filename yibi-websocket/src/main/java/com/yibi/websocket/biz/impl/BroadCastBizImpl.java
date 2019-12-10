package com.yibi.websocket.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.websocket.biz.BroadCastBiz;
import com.yibi.websocket.enums.EnumScene;
import com.yibi.websocket.model.ResultObj;
import com.yibi.websocket.model.WebSocketClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Log4j2
@Component
public class BroadCastBizImpl extends BaseBizImpl implements BroadCastBiz {
    @Resource
    private RedisTemplate<String, String> redis;
    @Resource
    private OrderSpotService orderSpotService;
    @Resource
    private CommissionRecordService commissionRecordService;
    @Resource
    private UserService userService;
    @Resource
    private OrderSpotRecordService orderSpotRecordService;
    @Resource
    private AccountService accountService;
    @Resource
    private OrderManageService orderManageService;
    @Resource
    private CoinExchangeConfigService coinExchangeConfigService;
    @Resource
    private SysparamsService sysparamsService;

    @Override
    public void broadCast(JSONObject data, Map<String, WebSocketClient> allSocketClients) {
        Object info = data.get("info");

        int scene = data.getIntValue("scene");
        int gear = data.getIntValue("gear");
        int c1 = data.getIntValue("c1");
        int c2 = data.getIntValue("c2");
        for (WebSocketClient client : allSocketClients.values()) {
            if (client.getC1() == c1 && client.getC2() == c2 &&  scene == client.getScene()) {

                    ResultObj resultObj = new ResultObj();
                    resultObj.setInfo(JSONObject.toJSONString(info));
                    resultObj.setScene(client.getScene());
                    sendMessage(client.getChannel(), resultObj);
            }
            if(scene == EnumScene.SCENE_INDEX.getScene() || scene == EnumScene.SCENE_MARKET_YIBI.getScene()|| scene == EnumScene.SCENE_INDEX_SORT.getScene()){
                if(scene == client.getScene()){
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

        //根据依赖币种获取相应币种列表
        List<CoinExchangeConfig> coinExchangeConfigs = coinExchangeConfigService.selectByRelyCoin(c2);
        for(CoinExchangeConfig coinExchangeConfig : coinExchangeConfigs) {
            c1 = coinExchangeConfig.getUnionCoin();
            c2 = coinExchangeConfig.getOrderCoin();
            data.put("c1", c1);
            data.put("c2", c2);
            //现货页处理未完成订单 修改订单价格与数量
            if (scene == EnumScene.SCENE_ORDER.getScene()) {
                data = orderDealMarket(info, c1, c2, data);
                //todo 需取消注释
                broadCast(data, allSocketClients);

            }
            //行情处理 存入缓存 推送
            if (scene == EnumScene.SCENE_MARKET_YIBI.getScene()) {
                orderDealKLine(info, c1, c2, data);
                broadCast(data, allSocketClients);
                data.put("scene", EnumScene.SCENE_INDEX.getScene());
                broadCast(data, allSocketClients);
            }
            //trade最新成交记录处理
            if (scene == EnumScene.SCENE_KLINE_YIBI.getScene()) {
                tradeDeal(info, c1, c2, data);
                broadCast(data, allSocketClients);
            }
        }
    }

    /**
     * 最新成交记录处理
     * @param info
     * @param c1
     * @param c2
     */
    private void tradeDeal(Object info, int c1, int c2, JSONObject data) {
        CoinExchangeConfig coinExchangeConfig = coinExchangeConfigService.selectByCoin(c1, c2);
        //价格 数量浮动
        BigDecimal priceRise = coinExchangeConfig.getPriceRise();
        BigDecimal amountRise = coinExchangeConfig.getAmountRise();

        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(info));
        JSONObject recordObject = json.getJSONObject("records");
        recordObject.put("price", new BigDecimal(recordObject.getString("price")).add(priceRise));
        recordObject.put("amount", new BigDecimal(recordObject.getString("amount")).multiply(amountRise).stripTrailingZeros());
        String key = String.format(RedisKey.ORDER_RECORD_LIST, c1, c2);
        List<Object> listRecord = new ArrayList<Object>();
        JSONObject records = json.getJSONObject("records");
        listRecord.add(records);
        RedisUtil.addList(redis, key, listRecord);
        redis.opsForList().trim(key, 0, 20);
        List<String> list = RedisUtil.searchList(redis, key, 0, 20);
        List<JSONObject> record = new LinkedList<>();

        for(String str : list){
            JSONObject jsonObject = JSONObject.parseObject(str);
            BigDecimal price = new BigDecimal(jsonObject.getString("price"));
            BigDecimal amount = new BigDecimal(jsonObject.getString("amount"));
            jsonObject.put("price", price);
            jsonObject.put("amount", amount);
            record.add(jsonObject);
        }
        json.put("records", record);
        key = String.format(RedisKey.MARKET, 1, c1, c2);
        String market = RedisUtil.searchString(redis, key);
        JSONObject marketJson = JSONObject.parseObject(market);
        json.put("market", marketJson);
        data.put("info", json);
    }


    /**
     * 推送行情数据
     * @param info
     * @param c1
     * @param c2
     */
    private void orderDealKLine(Object info, int c1, int c2, JSONObject data) {
        String coinList = sysparamsService.getValStringByKey(SystemParams.HOMEPAGE_MARKET_COIN_LIST);
        CoinExchangeConfig coinExchangeConfig = coinExchangeConfigService.selectByCoin(c1, c2);
        //价格浮动
        BigDecimal priceRise = coinExchangeConfig.getPriceRise();
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(info));
        json.put("orderCoinName", CoinType.getCoinName(c2));
        json.put("orderCoinCnName", CoinType.getCoinName(c2));
        json.put("orderCoinType", c2);
        BigDecimal price = new BigDecimal(json.get("newPrice").toString()).add(priceRise);
        json.put("newPrice", price.toPlainString());
        json.put("high", new BigDecimal(json.get("high").toString()).add(priceRise));
        json.put("low", new BigDecimal(json.get("low").toString()).add(priceRise));
        json.put("newPriceCNY", price.multiply(new BigDecimal(7.04)).setScale(2, BigDecimal.ROUND_HALF_UP));
        String redisKey = String.format(RedisKey.MARKET, 1, c1, c2);
        RedisUtil.addStringObj(redis, redisKey, json);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<String> list1 =  Arrays.asList(coinList.split(","));
        for(String coin : list1) {
            redisKey = String.format(RedisKey.MARKET, 1, c1, coin);
            String marketVal = RedisUtil.searchString(redis, redisKey);
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            if (marketVal != null) {
                jsonMap = JSON.parseObject(marketVal, Map.class);
            }
            list.add(jsonMap);
        }
        data.put("info", list);
    }

    /**
     * 处理未完成订单
     * @param info
     * @param c1
     * @param c2
     */
    private JSONObject orderDealMarket(Object info, Integer c1, Integer c2, JSONObject data){
        /*记录okex最新买卖一价*/
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(info));
        JSONArray buys = json.getJSONArray("buys");
        BigDecimal buyPrice = new BigDecimal(0);
        BigDecimal salePrice = new BigDecimal(0);
        //获取币种浮动配置
        CoinExchangeConfig coinExchangeConfig = coinExchangeConfigService.selectByCoin(c1, c2);
        if(coinExchangeConfig == null){
            coinExchangeConfig = new CoinExchangeConfig();
            coinExchangeConfig.setAmountRise(new BigDecimal(1));
            coinExchangeConfig.setPriceRise(new BigDecimal(0));
        }
        //价格浮动
        BigDecimal priceRise = coinExchangeConfig.getPriceRise();
        //数量浮动
        BigDecimal amountRise = coinExchangeConfig.getAmountRise();
        JSONArray saleArray = new JSONArray();
        JSONArray buysArray = new JSONArray();
        for (int i = 0; i < buys.size(); i++) {
            JSONObject jsonObject = buys.getJSONObject(i);
            if ("1".equals(jsonObject.getString("num"))) {
                String price = jsonObject.getString("price");
                RedisUtil.addString(redis, String.format(RedisKey.OKEX_DEPTH_COIN_PRICE_BUYS, c2), price);
                buyPrice = new BigDecimal(price);
            }
            //获取原推送数据，根据配置进行修改
            BigDecimal price = new BigDecimal(jsonObject.getString("price") == null ? "1" : jsonObject.getString("price"));
            BigDecimal number = new BigDecimal(jsonObject.getString("remain") == null ? "1" : jsonObject.getString("remain"));
            jsonObject.put("price", price.add(priceRise).toPlainString());
            jsonObject.put("remain", number.multiply(amountRise).setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString());
            buysArray.add(jsonObject);
        }
        JSONArray sales = json.getJSONArray("sales");
        for (int i = 0; i < sales.size(); i++) {
            JSONObject object = sales.getJSONObject(i);
            if ("1".equals(object.getString("num"))) {
                String price = object.getString("price");
                RedisUtil.addString(redis, String.format(RedisKey.OKEX_DEPTH_COIN_PRICE_SALES, c2), price);
                salePrice = new BigDecimal(price);
            }
            BigDecimal price = new BigDecimal(object.getString("price") == null ? "1" : object.getString("price"));
            BigDecimal number = new BigDecimal(object.getString("remain") == null ? "1" : object.getString("remain"));
            object.put("price", price.add(priceRise).toPlainString());
            object.put("remain", number.multiply(amountRise).setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString());
            saleArray.add(object);
        }
        json.put("buys", buysArray);
        json.put("sales", saleArray);
        data.put("info", json);
        /*end*/
        /*遍历未成交挂单*/
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", c2);
        manageParams.put("unitcointype", c1);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        //未成交买单 对比挂单价格与最新OK价格
        Map<Object, Object> matchingParams = new HashMap<Object, Object>();
        matchingParams.put("ordercointype", c2);
        matchingParams.put("unitcointype", c1);
        matchingParams.put("type", GlobalParams.ORDER_TYPE_BUY);
        matchingParams.put("price", buyPrice);
        List<OrderSpot> matchingList = orderSpotService.selectAllMatching(matchingParams);
        for (OrderSpot matchingOrder : matchingList) {
            orderDeal(buyPrice, matchingOrder.getPrice(), matchingOrder.getAmount(), matchingOrder.getUserid(), c2, c1, manage, true, false);
        }
        //未成交卖单 对比挂单价格与最新OK价格
        matchingParams = new HashMap<Object, Object>();
        matchingParams.put("ordercointype", c2);
        matchingParams.put("unitcointype", c1);
        matchingParams.put("type", GlobalParams.ORDER_TYPE_SALE);
        matchingParams.put("price", salePrice);
        matchingList = orderSpotService.selectAllMatching(matchingParams);
        for (OrderSpot matchingOrder : matchingList) {
            orderDeal(salePrice, matchingOrder.getPrice(), matchingOrder.getAmount(), matchingOrder.getUserid(), c2, c1, manage, false, false);
        }
        return data;
    }

    /**
     * 与平台进行陈嘉庚
     * @param okexPrice okex价格
     * @param price 用户价格
     * @param amount 成交数量
     * @param orderCoin 交易币
     * @param unitCoin 计价币
     * @param manage 币种管理
     * @param isBuyer 是否买家
     * @param isLimit 是否限价
     */
    private void orderDeal(BigDecimal okexPrice, BigDecimal price, BigDecimal amount, Integer userId, Integer orderCoin, Integer unitCoin, OrderManage manage, boolean isBuyer, boolean isLimit){
        if(isBuyer) {
            if (price.compareTo(okexPrice) < 0 && isLimit) {
                //买方订单
                OrderSpot buyOrder = new OrderSpot();
                buyOrder.setUserid(userId);
                buyOrder.setAmount(amount);
                buyOrder.setAverage(new BigDecimal(0));
                buyOrder.setLevflag(0);
                buyOrder.setOrdercointype(orderCoin);
                buyOrder.setOrdernum("O" + userId + System.currentTimeMillis());
                buyOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
                buyOrder.setPrice(price);
                buyOrder.setRemain(amount);
                buyOrder.setState(GlobalParams.ORDER_STATE_UNTREATED);
                buyOrder.setTotal(BigDecimal.ZERO);
                buyOrder.setType(GlobalParams.ORDER_TYPE_BUY);
                buyOrder.setUnitcointype(unitCoin);
                buyOrder.setDealAmount(BigDecimal.ZERO);
                orderSpotService.insertSelective(buyOrder);
            } else {
                //买方订单
                OrderSpot buyOrder = new OrderSpot();
                buyOrder.setUserid(userId);
                buyOrder.setAmount(amount);
                buyOrder.setAverage(new BigDecimal(0));
                buyOrder.setLevflag(0);
                buyOrder.setOrdercointype(orderCoin);
                buyOrder.setOrdernum("O" + userId + System.currentTimeMillis());
                buyOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
                buyOrder.setPrice(okexPrice);
                buyOrder.setRemain(BigDecimal.ZERO);
                buyOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                buyOrder.setTotal(amount.multiply(okexPrice));
                buyOrder.setType(GlobalParams.ORDER_TYPE_BUY);
                buyOrder.setUnitcointype(unitCoin);
                buyOrder.setDealAmount(amount);
                orderSpotService.insertSelective(buyOrder);
                //卖方订单
                OrderSpot saleOrder = new OrderSpot();
                saleOrder.setUserid(1);
                saleOrder.setAmount(amount);
                saleOrder.setAverage(new BigDecimal(0));
                saleOrder.setLevflag(0);
                saleOrder.setOrdercointype(orderCoin);
                saleOrder.setOrdernum("O" + userId + System.currentTimeMillis());
                saleOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
                saleOrder.setPrice(okexPrice);
                saleOrder.setRemain(BigDecimal.ZERO);
                saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                saleOrder.setTotal(amount.multiply(okexPrice));
                saleOrder.setType(GlobalParams.ORDER_TYPE_SALE);
                saleOrder.setUnitcointype(unitCoin);
                saleOrder.setDealAmount(amount);
                orderSpotService.insertSelective(saleOrder);

                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(amount);
                record.setBuyid(userId);
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(okexPrice);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(amount.multiply(okexPrice));
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);

                //手续费扣除后金额
                BigDecimal availIncrement = calcFee(manage, amount.multiply(okexPrice), saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT);
                //卖方获得计价币
                accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), availIncrement, new BigDecimal(0), saleOrder.getUserid(), "币币交易卖出收入", record.getId());
                //买方获得交易币
                accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), buyOrder.getDealAmount(), new BigDecimal(0), buyOrder.getUserid(), "币币交易买入收入", buyOrder.getId());

                BigDecimal minusTotal = BigDecimalUtils.multiply(buyOrder.getRemain(), buyOrder.getPrice(), 4).add(buyOrder.getTotal());
                //买方扣除计价币
                accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, unitCoin, BigDecimalUtils.plusMinus(minusTotal), BigDecimal.ZERO, buyOrder.getUserid(), "币币交易买入扣除", buyOrder.getId());
            }
        }else {
            if (price.compareTo(okexPrice) > 0 && isLimit) {
                //卖方订单
                OrderSpot salesOrder = new OrderSpot();
                salesOrder.setUserid(userId);
                salesOrder.setAmount(amount);
                salesOrder.setAverage(new BigDecimal(0));
                salesOrder.setLevflag(0);
                salesOrder.setOrdercointype(orderCoin);
                salesOrder.setOrdernum("O" + userId + System.currentTimeMillis());
                salesOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
                salesOrder.setPrice(price);
                salesOrder.setRemain(amount);
                salesOrder.setState(GlobalParams.ORDER_STATE_UNTREATED);
                salesOrder.setTotal(BigDecimal.ZERO);
                salesOrder.setType(GlobalParams.ORDER_TYPE_BUY);
                salesOrder.setUnitcointype(unitCoin);
                salesOrder.setDealAmount(BigDecimal.ZERO);
                orderSpotService.insertSelective(salesOrder);
            } else {
                //买方订单
                OrderSpot buyOrder = new OrderSpot();
                buyOrder.setUserid(1);
                buyOrder.setAmount(amount);
                buyOrder.setAverage(new BigDecimal(0));
                buyOrder.setLevflag(0);
                buyOrder.setOrdercointype(orderCoin);
                buyOrder.setOrdernum("O" + userId + System.currentTimeMillis());
                buyOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
                buyOrder.setPrice(okexPrice);
                buyOrder.setRemain(BigDecimal.ZERO);
                buyOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                buyOrder.setTotal(amount.multiply(okexPrice));
                buyOrder.setType(GlobalParams.ORDER_TYPE_BUY);
                buyOrder.setUnitcointype(unitCoin);
                buyOrder.setDealAmount(amount);
                orderSpotService.insertSelective(buyOrder);
                //卖方订单
                OrderSpot saleOrder = new OrderSpot();
                saleOrder.setUserid(userId);
                saleOrder.setAmount(amount);
                saleOrder.setAverage(new BigDecimal(0));
                saleOrder.setLevflag(0);
                saleOrder.setOrdercointype(orderCoin);
                saleOrder.setOrdernum("O" + userId + System.currentTimeMillis());
                saleOrder.setOrdertype(GlobalParams.ORDER_ORDERTYPE_LIMIT);
                saleOrder.setPrice(okexPrice);
                saleOrder.setRemain(BigDecimal.ZERO);
                saleOrder.setState(GlobalParams.ORDER_STATE_TREATED);
                saleOrder.setTotal(amount.multiply(okexPrice));
                saleOrder.setType(GlobalParams.ORDER_TYPE_SALE);
                saleOrder.setUnitcointype(unitCoin);
                saleOrder.setDealAmount(amount);
                orderSpotService.insertSelective(saleOrder);

                OrderSpotRecord record = new OrderSpotRecord();
                record.setAmount(amount);
                record.setBuyid(userId);
                record.setBuyuserid(buyOrder.getUserid());
                record.setOrdercointype(buyOrder.getOrdercointype());
                record.setPrice(okexPrice);
                record.setSaleid(saleOrder.getId());
                record.setSaleuserid(saleOrder.getUserid());
                record.setTotal(amount.multiply(okexPrice));
                record.setUnitcointype(buyOrder.getUnitcointype());
                record.setCreatetime(new Timestamp(System.currentTimeMillis()));
                orderSpotRecordService.insertSelective(record);

                //手续费扣除
                BigDecimal availIncrement = calcFee(manage, amount.multiply(okexPrice), saleOrder, record.getId(), GlobalParams.ORDER_ORDERTYPE_LIMIT);
                //卖方获得计价币收益
                accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getUnitcointype(), availIncrement, new BigDecimal(0), saleOrder.getUserid(), "币币交易卖出收入", record.getId());
                //买方获得成交交易币数量
                accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, buyOrder.getOrdercointype(), buyOrder.getDealAmount(), new BigDecimal(0), buyOrder.getUserid(), "币币交易卖出收入", buyOrder.getId());

                /*//买方扣除计价币
                accountService.updateAccountAndInsertFlow(buyOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, unitCoin, BigDecimalUtils.plusMinus(minusTotal), BigDecimal.ZERO, buyOrder.getUserid(), "币币交易买入", buyOrder.getId());*/
                //卖方扣除成交交易币数量
                accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, saleOrder.getOrdercointype(), BigDecimalUtils.plusMinus(buyOrder.getAmount()), BigDecimal.ZERO, saleOrder.getUserid(), "币币交易卖出", buyOrder.getId());
            }
        }
    }


    /**
     * 计算手续费
     *
     * @param manage
     * @param amount
     * @param order
     * @param id
     * @return
     */
    private BigDecimal calcFee(OrderManage manage, BigDecimal amount, OrderSpot order, Integer id, Integer orderType) {
        if(orderType == GlobalParams.ORDER_ORDERTYPE_LIMIT) {
            if (manage.getPerformrate().compareTo(BigDecimal.ZERO) < 1 && manage.getReferrate().compareTo(BigDecimal.ZERO) < 1) {
                return amount;
            }
        }else{
            if (manage.getMarketpPerformRate().compareTo(BigDecimal.ZERO) < 1 && manage.getMarketReferRate().compareTo(BigDecimal.ZERO) < 1) {
                return amount;
            }
        }
        //手续费币种
        int commCoinType = order.getType() == GlobalParams.ORDER_TYPE_BUY ? order.getOrdercointype() : order.getUnitcointype();

        /*计算手续费*/
        BigDecimal feeOfPerform = new BigDecimal(0);
        BigDecimal feeOfReference = new BigDecimal(0);
        User user = userService.selectByPrimaryKey(order.getUserid());
        User refUser = null;
        if (user.getReferenceid() != null) {
            refUser = userService.selectByUUID(user.getReferenceid());
        }
        if (user.getReferenceid() != null && user.getReferenceid() > 0 && refUser != null && refUser.getLogintime() != null) {
            if(orderType == GlobalParams.ORDER_ORDERTYPE_LIMIT) {
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getPerformrate());
            }else{
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getMarketpPerformRate());
            }
        } else {
            if(orderType == GlobalParams.ORDER_ORDERTYPE_LIMIT) {
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getPerformrate().add(manage.getReferrate()));
            }else{
                feeOfPerform = BigDecimalUtils.multiply(amount, manage.getMarketpPerformRate().add(manage.getMarketReferRate()));
            }
        }

        if ((feeOfPerform.compareTo(BigDecimal.ZERO) > 0) && order.getDealAmount() == null) {
            /*保存平台手续费记录*/
            CommissionRecord comm = new CommissionRecord();
            comm.setUserid(user.getId());
            comm.setCommamount(feeOfPerform);
            comm.setCommcointype(commCoinType);
            comm.setOrderamount(amount);
            comm.setOrdercointype(order.getOrdercointype());
            comm.setType(GlobalParams.COMMISSION_TYPE_PERFORM);
            comm.setReferenceid(GlobalParams.SYSTEM_OPERID);
            comm.setOrderid(order.getId());
            commissionRecordService.insertSelective(comm);

        }

        //todo： 推荐人交易奖励
        return BigDecimalUtils.subtract(amount, BigDecimalUtils.add(feeOfPerform, feeOfReference));
    }

}
