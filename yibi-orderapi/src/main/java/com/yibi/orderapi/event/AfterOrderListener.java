package com.yibi.orderapi.event;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.WebsocketClientUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.dao.OrderSpotRecordMapper;
import com.yibi.core.dao.OrderTakerMapper;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 行情、k线、交易深度加入缓存
 */
@Slf4j
public class AfterOrderListener {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private OrderSpotRecordService orderSpotRecordService;
    @Resource
    private RedisTemplate<String, String> redis;
    @Resource
    private OrderSpotRecordMapper orderSpotRecordMapper;
    @Resource
    private OrderTakerMapper orderTakerMapper;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private OrderSpotService orderSpotService;


    @Subscribe
    public void afterOrder(AfterOrderListenerBean event) {
        try {
            Integer orderCoinType = event.getOrderCoin();
            Integer unitCoinType = event.getUnitCoin();
            CoinScale coinScale = coinScaleService.queryByCoin(orderCoinType, unitCoinType);
            Date endTime = new Date();
            OrderSpotRecord newRecord = orderSpotRecordService.getNewRecord(unitCoinType, orderCoinType, endTime);
            BigDecimal newPrice = newRecord == null ? BigDecimal.ZERO : newRecord.getPrice();

            /*-------------------------------------------redis记录最新价格----------------------------------------------------*/
            String redisKey = String.format(RedisKey.LATEST_TRANS_PRICE, unitCoinType, orderCoinType);
            RedisUtil.addString(redis, redisKey, BigDecimalUtils.toString(newPrice, coinScale.getOrderamtpricescale()));
            log.info("更新最新成交价");

        } catch (Exception e) {
            log.error("交易完成保存行情缓存--->失败," + e.getMessage());
            e.printStackTrace();
        }
    }

    public BigDecimal getSpotLatestPrice(Integer orderCoinType, Integer unitCoinType) {
        String key = String.format(RedisKey.LATEST_TRANS_PRICE, unitCoinType, orderCoinType);
        String price = RedisUtil.searchString(redis, key);
        if (StrUtils.isBlank(price)) {
            Map<Object, Object> map = Maps.newHashMap();
            map.put("ordercointype", orderCoinType);
            map.put("unitcointype", unitCoinType);
            map.put("firstResult", 0);
            map.put("maxResult", 1);

            List<OrderSpotRecord> records = orderSpotRecordMapper.selectPaging(map);
            return records == null || records.isEmpty() ? BigDecimal.ZERO : records.get(0).getPrice();
        }
        return new BigDecimal(price);
    }


    public BigDecimal getC2CLatestPrice(Integer coinType) {
        String key = String.format(RedisKey.C2C_PRICE, coinType);
        String price = RedisUtil.searchString(redis, key);

        if (StrUtils.isBlank(price)) {
            Map<Object, Object> map = Maps.newHashMap();
            map.put("cointype", coinType);
            map.put("firstResult", 0);
            map.put("maxResult", 1);
            map.put("state", GlobalParams.C2C_ORDER_STATE_FINISHED);

            List<OrderTaker> records = orderTakerMapper.selectPaging(map);
            return records == null || records.isEmpty() ? BigDecimal.ZERO : records.get(0).getPrice();
        }
        return StrUtils.isBlank(price) ? new BigDecimal(0) : new BigDecimal(price);
    }


    public BigDecimal getPriceOfCNY(Integer coinType) {
        if (coinType == CoinType.CNHT) {
            return BigDecimal.ONE;
        }
        BigDecimal c2cPrice = getC2CLatestPrice(coinType);
        if (c2cPrice.compareTo(BigDecimal.ZERO) == 1) {
            return c2cPrice;
        }
        return getSpotLatestPrice(coinType, CoinType.CNHT);
    }

    public void putOrderRecordsToRedis(List<OrderSpotRecord> list, Integer orderCoinType, Integer unitCoinType, SimpleDateFormat sdf, CoinScale coinScale) {
        if (!list.isEmpty()) {
            List<Map<String, Object>> listRecord = new ArrayList<Map<String, Object>>();
            for (OrderSpotRecord record : list) {
                if(record!=null){
                    Map<String, Object> map = new HashMap<String, Object>();
                    //record = orderSpotRecordService.selectByPrimaryKey(record.getId());
                    //log.info("coinScale:{}",coinScale);
                    map.put("amount", BigDecimalUtils.toStringInZERO(record.getAmount(), coinScale.getOrderamtamountscale()));
                        map.put("createTime", sdf.format(record.getCreatetime()));
                    map.put("price", BigDecimalUtils.toStringInZERO(record.getPrice(), coinScale.getOrderamtpricescale()));
                    if (record.getBuyid() > record.getSaleid()) {
                        map.put("orderType", 0);
                    } else {
                        map.put("orderType", 1);
                    }
                    listRecord.add(map);
                }else{
                    log.info("record is null ");
                }

            }
            String redisKey = String.format(RedisKey.ORDER_RECORD_LIST, unitCoinType, orderCoinType);

            RedisUtil.addList(redis, redisKey, listRecord);
            redis.opsForList().trim(redisKey, 0, 20);
        }

    }
}

