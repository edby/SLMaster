package com.yibi.orderapi.biz.impl;

import com.google.common.eventbus.EventBus;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.OrderV2Biz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import com.yibi.orderapi.event.AfterOrderListenerBean;
import com.yibi.orderapi.event.CancelOrderListenerBean;
import com.yibi.orderapi.event.DealDigListenerBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service(value = "orderV2Biz")
@Transactional
@Slf4j
public class OrderV2BizImpl extends BaseBizImpl implements OrderV2Biz {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private OrderSpotService orderSpotService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderSpotRecordService orderSpotRecordService;
    @Autowired
    private CommissionRecordService commissionRecordService;
    @Autowired
    private SysparamsService sysparamsService;
    @Resource
    private EventBus orderEventBus;
    @Autowired
    private RedisTemplate<String, String> redis;

    /**
     * 与平台进行陈嘉庚
     * @param okexPrice okex价格
     * @param price 用户价格
     * @param amount 成交数量
     * @param user 用户
     * @param orderCoin 交易币
     * @param unitCoin 计价币
     * @param manage 币种管理
     * @param isBuyer 是否买家
     * @param isLimit 是否限价
     */
    private void orderDeal(BigDecimal okexPrice, BigDecimal price, BigDecimal amount, User user, Integer orderCoin, Integer unitCoin, OrderManage manage, boolean isBuyer, boolean isLimit){
        if(isBuyer) {
            if (price.compareTo(okexPrice) < 0 && isLimit) {
                //买方订单
                OrderSpot buyOrder = new OrderSpot();
                buyOrder.setUserid(user.getId());
                buyOrder.setAmount(amount);
                buyOrder.setAverage(new BigDecimal(0));
                buyOrder.setLevflag(0);
                buyOrder.setOrdercointype(orderCoin);
                buyOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
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
                buyOrder.setUserid(user.getId());
                buyOrder.setAmount(amount);
                buyOrder.setAverage(new BigDecimal(0));
                buyOrder.setLevflag(0);
                buyOrder.setOrdercointype(orderCoin);
                buyOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
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
                saleOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
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
                record.setBuyid(user.getId());
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
                /*//卖方扣除交易币
                accountService.updateAccountAndInsertFlow(saleOrder.getUserid(), GlobalParams.ACCOUNT_TYPE_SPOT, orderCoin, BigDecimalUtils.plusMinus(minusTotal), BigDecimal.ZERO, saleOrder.getUserid(), "币币交易卖出扣除", saleOrder.getId());*/
            }
        }else {
            if (price.compareTo(okexPrice) > 0 && isLimit) {
                //卖方订单
                OrderSpot salesOrder = new OrderSpot();
                salesOrder.setUserid(user.getId());
                salesOrder.setAmount(amount);
                salesOrder.setAverage(new BigDecimal(0));
                salesOrder.setLevflag(0);
                salesOrder.setOrdercointype(orderCoin);
                salesOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
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
                buyOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
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
                saleOrder.setUserid(user.getId());
                saleOrder.setAmount(amount);
                saleOrder.setAverage(new BigDecimal(0));
                saleOrder.setLevflag(0);
                saleOrder.setOrdercointype(orderCoin);
                saleOrder.setOrdernum("O" + user.getId() + System.currentTimeMillis());
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
                record.setBuyid(user.getId());
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

    @Override
    public String limitPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMinspottransnum();
            minTotal = coinScale.getMinspottransamt();
        }
        //格式化价格小数位数
        BigDecimal priceBd = BigDecimalUtils.roundDown(new BigDecimal(price), priceScale);
        //格式化数量小数位数
        BigDecimal amountBd = BigDecimalUtils.roundDown(new BigDecimal(amount), amountScale);
        //判断数量是否大于最低交易数量
        if (amountBd.compareTo(minAmount) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_AMOUNT_LESS_THAN_MIN, BigDecimalUtils.toString(minAmount));
        }
        //验证成交总额是否小于最小成交额
        BigDecimal totalAmount = priceBd.multiply(amountBd);
        if (totalAmount.compareTo(minTotal) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_PRICE_LESS_THAN_MIN, BigDecimalUtils.toString(minTotal));
        }
        //判断账户余额是否足够 普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", unitCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(priceBd.multiply(amountBd)) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        //OKEX上记录的最低购买价格
        String okexPrice = RedisUtil.searchString(redis, String.format(RedisKey.OKEX_DEPTH_COIN_PRICE_BUYS, orderCoin));
        BigDecimal okexPriceBd = new BigDecimal(okexPrice);
        //挂单处理
        orderDeal(okexPriceBd, priceBd, amountBd, user, orderCoin, unitCoin, manage, true, true);

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String limitPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMinspottransnum();
            minTotal = coinScale.getMinspottransamt();
        }
        //格式化价格小数位数
        BigDecimal priceBd = BigDecimalUtils.roundDown(new BigDecimal(price), priceScale);
        //格式化数量小数位数
        BigDecimal amountBd = BigDecimalUtils.roundDown(new BigDecimal(amount), amountScale);
        //判断数量是否大于最低交易数量
        if (amountBd.compareTo(minAmount) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_AMOUNT_LESS_THAN_MIN, BigDecimalUtils.toString(minAmount));
        }
        //验证成交总额是否小于最小成交额
        BigDecimal totalAmount = priceBd.multiply(amountBd);
        if (totalAmount.compareTo(minTotal) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_PRICE_LESS_THAN_MIN, BigDecimalUtils.toString(minTotal));
        }
        //判断账户余额是否足够
        //杠杆
        if (levFlag == 1) {
            //TODO 判断杠杆账户余额是否充足
        }
        //普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", orderCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(amountBd) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        //OKEX上记录的最低购买价格
        String okexPrice = RedisUtil.searchString(redis, String.format(RedisKey.OKEX_DEPTH_COIN_PRICE_SALES, orderCoin));
        BigDecimal okexPriceBd = new BigDecimal(okexPrice);
        //挂单处理
        orderDeal(okexPriceBd, priceBd, amountBd, user, orderCoin, unitCoin, manage, false, true);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String marketPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String total, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMinspottransnum();
            minTotal = coinScale.getMarketbuyminamt();
        }
        //验证成交总额是否大于最小成交额
        BigDecimal totalAmount = new BigDecimal(total);
        totalAmount = BigDecimalUtils.roundDown(totalAmount, priceScale);
        if (totalAmount.compareTo(minTotal) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_PRICE_LESS_THAN_MIN, BigDecimalUtils.toString(minTotal));
        }
        //判断账户余额是否足够
        //杠杆
        if (levFlag == 1) {
            //TODO 判断杠杆账户余额是否充足
        }
        //普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", unitCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(totalAmount) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        //OKEX上记录的最低购买价格
        String okexPrice = RedisUtil.searchString(redis, String.format(RedisKey.OKEX_DEPTH_COIN_PRICE_BUYS, orderCoin));
        BigDecimal okexPriceBd = new BigDecimal(okexPrice);
        //挂单处理
        orderDeal(okexPriceBd, BigDecimal.ZERO, totalAmount, user, orderCoin, unitCoin, manage, true, false);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String marketPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String amount, String password) {
        Map<Object, Object> manageParams = new HashMap<Object, Object>();
        manageParams.put("ordercointype", orderCoin);
        manageParams.put("unitcointype", unitCoin);
        OrderManage manage = null;
        List<OrderManage> orderManageList = orderManageService.selectAll(manageParams);
        if (orderManageList != null && !orderManageList.isEmpty()) {
            manage = orderManageList.get(0);
        }
        /*---------------------------------------------订单合法验证-----------------------------------------------------*/
        String result = validateOrder(user, orderCoin, unitCoin, password, manage);
        if (result != null) {
            return result;
        }
        CoinScale coinScale = coinScaleService.queryByCoin(orderCoin, unitCoin);
        int priceScale = 2;//价格小数位数
        int amountScale = 4;//数量小数位数
        BigDecimal minAmount = new BigDecimal(0.0001);//最小交易量
        BigDecimal minTotal = new BigDecimal(0.01);//最小交易额
        if (coinScale != null) {
            priceScale = coinScale.getOrderamtpricescale();
            amountScale = coinScale.getOrderamtamountscale();
            minAmount = coinScale.getMarketsaleminnum();
            minTotal = coinScale.getMinspottransamt();
        }
        //验证成交总量是否大于最小成交量
        BigDecimal totalAmount = new BigDecimal(amount);
        totalAmount = BigDecimalUtils.roundDown(totalAmount, amountScale);
        if (totalAmount.compareTo(minAmount) < 0) {
            return Result.toResultFormat(ResultCode.ORDER_SPOT_AMOUNT_LESS_THAN_MIN, BigDecimalUtils.toString(minAmount));
        }
        //判断账户余额是否足够
        //杠杆
        if (levFlag == 1) {
            //TODO 判断杠杆账户余额是否充足
        }
        //普通现货
        if (levFlag == 0) {
            Map<Object, Object> accountParams = new HashMap<Object, Object>();
            accountParams.put("userid", user.getId());
            accountParams.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
            accountParams.put("cointype", orderCoin);
            List<Account> listAccount = accountService.selectAll(accountParams);
            if (listAccount == null || listAccount.isEmpty()) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
            Account account = listAccount.get(0);
            if (account.getAvailbalance().compareTo(totalAmount) < 0) {
                return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
            }
        }
        //OKEX上记录的最低购买价格
        String okexPrice = RedisUtil.searchString(redis, String.format(RedisKey.OKEX_DEPTH_COIN_PRICE_SALES, orderCoin));
        BigDecimal okexPriceBd = new BigDecimal(okexPrice);
        //挂单处理
        orderDeal(okexPriceBd, BigDecimal.ZERO, totalAmount, user, orderCoin, unitCoin, manage, false, false);
        return Result.toResult(ResultCode.SUCCESS);
    }

    /**
     * 交易订单验证
     *
     * @return
     */
    private String validateOrder(User user, Integer orderCoin, Integer unitCoin, String password, OrderManage orderManage) {
        /*判断功能是否关闭*/
        if (orderManage == null || orderManage.getOnoff() == GlobalParams.INACTIVE) {
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        /*实名认证检查*/
        if (user.getIdstatus() == GlobalParams.INACTIVE) {
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }
        /*交易密码验证*/
        String result = validateOrderPassword(user, password);
        if (result != null) {
            return result;
        }
        return null;

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
