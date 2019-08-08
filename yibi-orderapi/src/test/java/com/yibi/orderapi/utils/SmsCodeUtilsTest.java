package com.yibi.orderapi.utils;

import com.yibi.core.entity.User;
import com.yibi.extern.api.aliyun.smscode.SMSCodeUtil;
import com.yibi.orderapi.BaseTest;
import com.yibi.orderapi.biz.UserBiz;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class SmsCodeUtilsTest extends BaseTest {

    @Resource
    private SMSCodeUtil smsCodeUtil;
    @Resource
    private UserBiz userBiz;

    @Test
    public void sendCodeTest(){
    }
    @Test
    public void realNameTest(){
        User user = userBiz.queryUser();
        System.out.println(userBiz.getToken(user));
    }
}
