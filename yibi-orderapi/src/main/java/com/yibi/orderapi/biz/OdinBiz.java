package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
public interface OdinBiz {

    /**
     * 初始化
     * @return
     */
    String init();

    /**
     * 检查金额是否合法
     * @param amount
     * @return
     */
    boolean checkAmount(String amount);

    /**
     * 认购
     * @param user
     * @param amount
     * @param ecnAmount
     * @param password
     * @return
     */
    String buy(User user, String amount, String ecnAmount, String password);

    /**
     * 奖励页面初始化
     * @param user
     * @return
     */
    String reward(User user);
}
