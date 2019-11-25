package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:16
 */
public interface MortgageBiz {

    /**
     * 初始化
     * @param user
     * @param coinType
     * @param pageModel
     * @return
     */
    String init(User user, Integer coinType, PageModel pageModel);

    /**
     * 提交抵押
     * @param user
     * @param coinType
     * @param amount 金额
     * @param rate 利率
     * @param time 持续时间
     * @return
     */
    String commit(User user, Integer coinType, String amount, String rate, Integer time);
}
