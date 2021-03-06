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
    private AccountService accountService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private DealDigRecordService dealDigRecordService;

    @Subscribe
    public void doDealDig(DealDigListenerBean event) {
        //获取交易挖矿配置
        Map<Object, Object> params = new HashMap<>();
        Integer orderType = event.getOrderType();
        OrderSpotRecord record = event.getRecord();
        Integer coinType = record.getOrdercointype();
        params.put("ordercointype", coinType);
        params.put("ordertype", orderType);
        params.put("onoff", GlobalParams.ON);
        List<DealDigConfig> list = dealDigConfigService.selectAll(params);
        if (list != null && !list.isEmpty()) {
            DealDigConfig dealDigConfig = list.get(0);
            if (dealDigConfig != null) {
                BigDecimal amount = record.getAmount();
                /*BigDecimal total = record.getTotal();
                BigDecimal dkPrice = getSpotLatestPrice(coinType, CoinType.CNHT);
                BigDecimal dkAmount = total.divide(dkPrice, coinScale.getOrderamtamountscale());*/
                //用户挖矿
                calcAndModifyAccount(record.getBuyuserid(), amount, dealDigConfig.getBuycashback(), record, CoinType.getCoinName(coinType) + " 个人挖矿奖励");
                //todo 暂时关闭卖方挖矿
                /*calcAndModifyAccount(record.getSaleuserid(), dkAmount, dealDigConfig.getSalecashback(), record, "交易挖矿--卖方用户");*/

            }
        }
    }

    /**
     * 根据用户推荐码获取推荐人userId
     * @param user
     * @return
     */
    private User getReferUser(User user){
        return userService.selectByUUID(user.getReferenceid());
    }

    /**
     * 推荐奖励
     * @param buyReferUser 买方推荐人
     * @param saleReferUser 卖方推荐人
     * @param amount 金额
     * @param record 交易记录
     */
    private void referReward(User buyReferUser, User saleReferUser, BigDecimal amount, OrderSpotRecord record){
        String rate;
        //买方奖励开关
        String buyRewardOnOff = sysparamsService.getValStringByKey(SystemParams.DEAL_ORDER_REFER_REWARD_BUY_ONOFF);
        String saleRewardOnOff = sysparamsService.getValStringByKey(SystemParams.DEAL_ORDER_REFER_REWARD_SALE_ONOFF);
        BigDecimal buyRate = new BigDecimal(0.02);
        BigDecimal saleRate = new BigDecimal(0.02);
        /*------一级推荐人-----*/
        if(Integer.valueOf(buyRewardOnOff).equals(GlobalParams.ON) && buyReferUser != null && buyReferUser.getState() == GlobalParams.ACTIVE && buyReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_1) {
            rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_1);
            calcAndModifyAccount(buyReferUser.getId(), amount, new BigDecimal(rate).multiply(buyRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
        }
        if(Integer.valueOf(saleRewardOnOff).equals(GlobalParams.ON) && saleReferUser != null && saleReferUser.getState() == GlobalParams.ACTIVE  && saleReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_1) {
            rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_1);
            calcAndModifyAccount(saleReferUser.getId(), amount, new BigDecimal(rate).multiply(saleRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
        }
        /*------二级推荐人-----*/
        if(buyReferUser != null) {
            buyReferUser = getReferUser(buyReferUser);
            if(Integer.valueOf(buyRewardOnOff).equals(GlobalParams.ON) && buyReferUser != null && buyReferUser.getState() == GlobalParams.ACTIVE  && buyReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_2) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_2);
                calcAndModifyAccount(buyReferUser.getId(), amount, new BigDecimal(rate).multiply(buyRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
        if(saleReferUser != null) {
            saleReferUser = getReferUser(saleReferUser);
            if(Integer.valueOf(saleRewardOnOff).equals(GlobalParams.ON) && saleReferUser != null && saleReferUser.getState() == GlobalParams.ACTIVE  && saleReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_2) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_2);
                calcAndModifyAccount(saleReferUser.getId(), amount, new BigDecimal(rate).multiply(saleRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
        /*------三级推荐人-----*/
        if(buyReferUser != null) {
            buyReferUser = getReferUser(buyReferUser);
            if(Integer.valueOf(buyRewardOnOff).equals(GlobalParams.ON) && buyReferUser != null && buyReferUser.getState() == GlobalParams.ACTIVE  && buyReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_3) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_3);
                calcAndModifyAccount(buyReferUser.getId(), amount, new BigDecimal(rate).multiply(buyRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
        if(saleReferUser != null) {
            saleReferUser = getReferUser(saleReferUser);
            if(Integer.valueOf(saleRewardOnOff).equals(GlobalParams.ON) && saleReferUser != null && saleReferUser.getState() == GlobalParams.ACTIVE  && saleReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_3) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_3);
                calcAndModifyAccount(saleReferUser.getId(), amount, new BigDecimal(rate).multiply(saleRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
        /*------四级推荐人-----*/
        if(buyReferUser != null) {
            buyReferUser = getReferUser(buyReferUser);
            if(Integer.valueOf(buyRewardOnOff).equals(GlobalParams.ON) && buyReferUser != null && buyReferUser.getState() == GlobalParams.ACTIVE  && buyReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_4) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_4);
                calcAndModifyAccount(buyReferUser.getId(), amount, new BigDecimal(rate).multiply(buyRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
        if(saleReferUser != null) {
            saleReferUser = getReferUser(saleReferUser);
            if(Integer.valueOf(saleRewardOnOff).equals(GlobalParams.ON) && saleReferUser != null && saleReferUser.getState() == GlobalParams.ACTIVE  && saleReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_4) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_4);
                calcAndModifyAccount(saleReferUser.getId(), amount, new BigDecimal(rate).multiply(saleRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
        /*------五级推荐人-----*/
        if(buyReferUser != null) {
            buyReferUser = getReferUser(buyReferUser);
            if(Integer.valueOf(buyRewardOnOff).equals(GlobalParams.ON) && buyReferUser != null && buyReferUser.getState() == GlobalParams.ACTIVE  && buyReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_5) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_5);
                calcAndModifyAccount(buyReferUser.getId(), amount, new BigDecimal(rate).multiply(buyRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
        if(saleReferUser != null) {
            saleReferUser = getReferUser(saleReferUser);
            if(Integer.valueOf(saleRewardOnOff).equals(GlobalParams.ON) && saleReferUser != null && saleReferUser.getState() == GlobalParams.ACTIVE  && saleReferUser.getReferenceStatus() >= GlobalParams.REFER_STATUS_5) {
                rate = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_AMOUNT_5);
                calcAndModifyAccount(saleReferUser.getId(), amount, new BigDecimal(rate).multiply(saleRate), record, CoinType.getCoinName(record.getOrdercointype()) + " 团队挖矿奖励");
            }
        }
    }
    private void calcAndModifyAccount(Integer userid, BigDecimal amount, BigDecimal rate, OrderSpotRecord record, String remark) {
        Integer coinType = record.getOrdercointype();
        if(!remark.contains("团队")){
            //统计交易挖矿数据
            String today = DateUtils.getCurrentDateStr() + " 00:00:00";
            List<Map<String, Object>> countList = dealDigRecordService.selectDataCount(userid, today, coinType);
            if (countList.size() != 0) {
                //日交易挖矿金额
                String digDealAmountMax = sysparamsService.getValStringByKey(String.format(SystemParams.DIG_DEAL_AMOUNT_MAX, record.getOrdercointype()));
                //日交易挖矿数量
                String digDealNumberMax = sysparamsService.getValStringByKey(String.format(SystemParams.DIG_DEAL_NUMBER_MAX, record.getOrdercointype()));
                //系统配置不为空
                if(!StrUtils.isBlank(digDealAmountMax) && !StrUtils.isBlank(digDealNumberMax)) {
                    Integer buyId = record.getBuyid();
                    Integer buyUserId = record.getBuyuserid();
                    Map<Object, Object> map = new HashMap<>();
                    map.put("buyid", buyId);
                    map.put("buyuserid", buyUserId);
                    int count = orderSpotRecordMapper.selectCount(map);

                    String sumAmountStr = countList.get(0).get("amount") == null ? "0" : countList.get(0).get("amount").toString();
                    BigDecimal sumAmount = new BigDecimal(sumAmountStr);
                    int number = Integer.parseInt(countList.get(0).get("num").toString());

                    int intDigDealNumberMax = Integer.parseInt(digDealNumberMax);
                    intDigDealNumberMax = intDigDealNumberMax + count - 1;
                    if ((sumAmount.compareTo(new BigDecimal(digDealAmountMax)) >= 0) || (number >= intDigDealNumberMax && number != 0)) {
                        return;
                    }

                    BigDecimal referDigAmount;
                    //推荐人挖矿
                    User buyUser = userService.selectByPrimaryKey(record.getBuyuserid());
                    User buyReferUser = getReferUser(buyUser);
                    User saleUser = userService.selectByPrimaryKey(record.getSaleuserid());
                    User saleReferUser = getReferUser(saleUser);

                    if(new BigDecimal(digDealAmountMax).compareTo(amount.multiply(rate)) < 0){
                        if (userid != null && rate.compareTo(BigDecimal.ZERO) > 0) {
                            User user = userService.selectByPrimaryKey(userid);
                            if (user != null && user.getLogintime() != null) {
                                accountService.updateAccountAndInsertFlow(userid, GlobalParams.ACCOUNT_TYPE_SPOT, coinType, new BigDecimal(digDealAmountMax), BigDecimal.ZERO, GlobalParams.SYSTEM_OPERID, remark, record.getId());
                                DealDigRecord dealDigRecord = new DealDigRecord();
                                dealDigRecord.setAmount(new BigDecimal(digDealAmountMax));
                                dealDigRecord.setCointype(coinType);
                                dealDigRecord.setOpertype(remark);
                                dealDigRecord.setOrderrecordid(record.getId());
                                dealDigRecord.setUserid(userid);
                                dealDigRecordService.insertSelective(dealDigRecord);
                                //若交易数量大于最大交易额度
                                referReward(buyReferUser, saleReferUser, new BigDecimal(digDealAmountMax), record);
                                return;
                            }
                        }
                    }else{
                        referReward(buyReferUser, saleReferUser, amount, record);
                    }
                }
            }
        }
        if (userid != null && rate.compareTo(BigDecimal.ZERO) > 0) {
            User user = userService.selectByPrimaryKey(userid);
            if (user != null && user.getLogintime() != null) {
                BigDecimal addAmount;
                addAmount = amount.multiply(rate);
                accountService.updateAccountAndInsertFlow(userid, GlobalParams.ACCOUNT_TYPE_SPOT, coinType, addAmount, BigDecimal.ZERO, GlobalParams.SYSTEM_OPERID, remark, record.getId());
                DealDigRecord dealDigRecord = new DealDigRecord();
                dealDigRecord.setAmount(addAmount);
                dealDigRecord.setCointype(coinType);
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
        if (coinType == CoinType.CNHT) {
            return BigDecimal.ONE;
        }
        BigDecimal c2cPrice = getC2CLatestPrice(coinType);
        if (c2cPrice.compareTo(BigDecimal.ZERO) == 1) {
            return c2cPrice;
        }
        return getSpotLatestPrice(coinType, CoinType.CNHT);
    }


}

