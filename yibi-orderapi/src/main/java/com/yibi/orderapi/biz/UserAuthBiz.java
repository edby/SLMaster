package com.yibi.orderapi.biz;


import com.yibi.core.entity.User;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public interface UserAuthBiz {

    /**
     * 一级认证
     * @param userName
     * @param idCardNumber
     * @param user
     * @return
     */
    String level1(String userName, String idCardNumber, User user);

    /**
     * 三级认证
     * @param videoUrl
     * @param user
     * @return
     */
    String level3(String videoUrl, User user);
}
