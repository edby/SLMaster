package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;

public interface OrderV2Biz {
    /**
     * 限价买入
     */
    String limitPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password);

    /**
     * 限价卖出
     */
    String limitPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String price, String amount, String password);

    /**
     * 市价买入
     */
    String marketPriceBuy(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String total, String password);

    /**
     * 市价卖出
     */
    String marketPriceSale(User user, Integer orderCoin, Integer unitCoin, Integer levFlag, String amount, String password);

}
