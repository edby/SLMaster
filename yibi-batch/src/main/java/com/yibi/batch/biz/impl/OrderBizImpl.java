package com.yibi.batch.biz.impl;

import com.yibi.batch.biz.OrderBiz;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

@Slf4j
@Service
@Transactional
public class OrderBizImpl implements OrderBiz {
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
    public void changePrice() {
        //功能开关
        String onoff = sysparamsService.getValStringByKey(SystemParams.ORDER_SPECIAL_COIN_PRICE_CHANGE_ONOFF);
        if(!StrUtils.isBlank(onoff) && "1".equals(onoff)){
            String basePrice = sysparamsService.getValStringByKey(SystemParams.ORDER_SPECIAL_COIN_BASE_PRICE);
            //获取当前价格对象
            Sysparams nowPrice = sysparamsService.getValByKey(SystemParams.ORDER_SPECIAL_COIN_NEW_PRICE);
            BigDecimal addPrice = new BigDecimal(basePrice).multiply(new BigDecimal(0.01));
            BigDecimal price = addPrice.add(new BigDecimal(nowPrice.getKeyval()));
            nowPrice.setKeyval(price.stripTrailingZeros().toPlainString());
            sysparamsService.updateByPrimaryKeySelective(nowPrice);
        }
    }

    @Override
    public void cancelOrder() {
        //查询所有订单状态是未成交的
        Map<Object,Object> map = new HashMap<>();
        String coin = sysparamsService.getValStringByKey(SystemParams.ORDER_SPECIAL_COIN);
        map.put("ordercointype", coin);
        //未成交的
        map.put("state",0);
        List<OrderSpot> list = orderSpotService.selectAll(map);
        if (list == null || list.size()== 0){
            log.info("【自动撤销订单】- 没有可以撤销的订单");
            return;
        }
        for (OrderSpot orderSpot:list){
            //更新订单状态 已撤销
            orderSpot.setState(2);
            int result = orderSpotService.updateConcurrencyOrder(orderSpot);
            if (result != 1){
                continue;
            }
            //更新账号余额和流水  返回计价币种
            accountService.updateAccountAndInsertFlow(orderSpot.getUserid(),1,orderSpot.getUnitcointype(),
                    orderSpot.getRemain().multiply(orderSpot.getPrice()),BigDecimal.ZERO, 1,"买入撤销",orderSpot.getId());
        }
        log.info("自动撤销订单完成！！");
        //todo 特殊币种计价单位以待商榷
        Map<Object, Object> param = new HashMap<>();
        param.put("ordercointype", coin);
        param.put("unitcointype", CoinType.USDT);
        List<OrderManage> orderManages = orderManageService.selectAll(param);
        OrderManage orderManage = orderManages.size() == 0 ? null : orderManages.get(0);
        if(orderManage != null){
            orderManage.setOnoff(GlobalParams.OFF);
            orderManageService.updateByPrimaryKeySelective(orderManage);
            log.info(coin + "币币交易已关闭！！");
        }
    }

    @Override
    public void open() {
        String coin = sysparamsService.getValStringByKey(SystemParams.ORDER_SPECIAL_COIN);
        //todo 特殊币种计价单位以待商榷
        Map<Object, Object> param = new HashMap<>();
        param.put("ordercointype", coin);
        param.put("unitcointype", CoinType.USDT);
        List<OrderManage> orderManages = orderManageService.selectAll(param);
        OrderManage orderManage = orderManages.size() == 0 ? null : orderManages.get(0);
        if(orderManage != null){
            orderManage.setOnoff(GlobalParams.ON);
            orderManageService.updateByPrimaryKeySelective(orderManage);
            log.info(coin + "币币交易已打开！！");
        }
    }
}
