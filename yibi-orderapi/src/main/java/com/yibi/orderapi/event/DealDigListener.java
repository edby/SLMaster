package com.yibi.orderapi.event;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易挖矿
 */
@Slf4j
public class DealDigListener {
    @Autowired
    private UserService userService;
    @Autowired
    private DealDigConfigService dealDigConfigService;
    @Resource
    private RedisTemplate<String, String> redis;
    @Resource
    private OrderSpotRecordMapper orderSpotRecordMapper;
    @Resource
    private OrderTakerMapper orderTakerMapper;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private DealDigRecordService dealDigRecordService;

    @Subscribe
    public void doDealDig(DealDigListenerBean event) {
        //获取交易挖矿配置
        Map<Object, Object> params = new HashMap<>();
        Integer orderType = event.getOrderType();
        OrderSpotRecord record = event.getRecord();
        params.put("ordercointype", record.getOrdercointype());
        params.put("ordertype", orderType);
        List<DealDigConfig> list = dealDigConfigService.selectAll(params);
        if (list != null && !list.isEmpty() && record.getOrdercointype() == CoinType.YT) {
            //获取DK--KN的coinscale
            CoinScale coinScale = coinScaleService.queryByCoin(CoinType.YT, CoinType.ENC);
            DealDigConfig dealDigConfig = list.get(0);
            if (dealDigConfig != null) {
                BigDecimal amount = record.getAmount();
                //获取折合DK币的交易量
                BigDecimal cnyPrice = getPriceOfCNY(record.getOrdercointype());
                BigDecimal dkPrice = getSpotLatestPrice(CoinType.YT, CoinType.ENC);
                BigDecimal dkAmount = amount.multiply(cnyPrice).divide(dkPrice, coinScale.getOrderamtamountscale());
//                BigDecimal rate = new BigDecimal(0);
//                BigDecimal userRate = new BigDecimal(0);
//                BigDecimal referRate = new BigDecimal(0);
//                //获取交易手续费 用户本人和推荐人之和
//                if (orderType == GlobalParams.ORDER_ORDERTYPE_LIMIT) {
//                    userRate = orderManage == null ? BigDecimal.ZERO : orderManage.getPerformrate();
//                    referRate = orderManage == null ? BigDecimal.ZERO : orderManage.getReferrate();
//                    rate = userRate.add(referRate);
//                } else {
//                    userRate = orderManage == null ? BigDecimal.ZERO : orderManage.getMarketpPerformRate();
//                    referRate = orderManage == null ? BigDecimal.ZERO : orderManage.getMarketReferRate();
//                    rate = userRate.add(referRate);
//                }
                //用户挖矿
                calcAndModifyAccount(record.getBuyuserid(), dkAmount, dealDigConfig.getBuycashback(), record, "交易挖矿--买方用户");
                calcAndModifyAccount(record.getSaleuserid(), dkAmount, dealDigConfig.getSalecashback(), record, "交易挖矿--卖方用户");
                User user = userService.selectByPrimaryKey(record.getBuyuserid());
                //推荐人挖矿
                Integer referId = userService.selectByUUID(user.getReferenceid()).getId();
                calcAndModifyAccount(referId, dkAmount, dealDigConfig.getBuyrefcashback(), record, "交易挖矿--买方推荐人");
                user = userService.selectByPrimaryKey(record.getSaleuserid());
                referId = userService.selectByUUID(user.getReferenceid()).getId();
                calcAndModifyAccount(referId, dkAmount, dealDigConfig.getSalerefcashback(), record, "交易挖矿--卖方推荐人");
            }
        }
    }

    private void calcAndModifyAccount(Integer userid, BigDecimal amount, BigDecimal rate, OrderSpotRecord record, String remark) {
        if(!remark.contains("推荐人")){
            //统计交易挖矿数据
            String today = DateUtils.getCurrentDateStr() + " 00:00:00";
            List<Map<String, Object>> countList = flowService.selectDataCount(userid, today);
            if (countList.size() != 0) {
                //日交易挖矿金额
                String digDealAmountMax = sysparamsService.getValStringByKey(String.format(SystemParams.DIG_DEAL_AMOUNT_MAX, record.getOrdercointype()));
                //日交易挖矿数量
                String digDealNumberMax = sysparamsService.getValStringByKey(String.format(SystemParams.DIG_DEAL_NUMBER_MAX, record.getOrdercointype()));
                //系统配置不为空
                if(!StrUtils.isBlank(digDealAmountMax) && !StrUtils.isBlank(digDealNumberMax)) {
                    String sumAmountStr = countList.get(0).get("amount") == null ? "0" : countList.get(0).get("amount").toString();
                    BigDecimal sumAmount = new BigDecimal(sumAmountStr);
                    Integer number = Integer.valueOf(countList.get(0).get("num").toString());
                    if ((sumAmount.compareTo(new BigDecimal(digDealAmountMax)) > 0) || number > Integer.valueOf(digDealNumberMax)) {
                        return;
                    }
                }
            }
        }
        if (userid != null && rate.compareTo(BigDecimal.ZERO) == 1) {
            User user = userService.selectByPrimaryKey(userid);
            if (user != null && user.getLogintime() != null) {
                Integer coinType = record.getOrdercointype();
                CoinManage cm = coinManageService.queryByCoinType(coinType);
                BigDecimal addAmount = new BigDecimal(0);
                //计算应返利YT数量 若是合伙人并且交易币不是YT 则返2倍
                if(user.getPartnerflag() == GlobalParams.ROLE_TYPE_PARTNER && coinType != CoinType.YT){
                    addAmount = amount.multiply(rate).multiply(new BigDecimal(2));
                }else{
                    addAmount = amount.multiply(rate);
                }
                accountService.updateAccountAndInsertFlow(userid, GlobalParams.ACCOUNT_TYPE_SPOT, CoinType.YT, addAmount, BigDecimal.ZERO, GlobalParams.SYSTEM_OPERID, remark, record.getId());
                DealDigRecord dealDigRecord = new DealDigRecord();
                dealDigRecord.setAmount(addAmount);
                dealDigRecord.setCointype(CoinType.YT);
                dealDigRecord.setOpertype(remark);
                dealDigRecord.setOrderrecordid(record.getId());
                dealDigRecord.setUserid(userid);
                dealDigRecordService.insertSelective(dealDigRecord);
            }

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
        if (coinType == CoinType.ENC) {
            return BigDecimal.ONE;
        }
        BigDecimal c2cPrice = getC2CLatestPrice(coinType);
        if (c2cPrice.compareTo(BigDecimal.ZERO) == 1) {
            return c2cPrice;
        }
        return getSpotLatestPrice(coinType, CoinType.ENC);
    }


}

