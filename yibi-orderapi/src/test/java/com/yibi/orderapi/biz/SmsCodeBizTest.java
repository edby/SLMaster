package com.yibi.orderapi.biz;

import com.yibi.common.model.FeiGeSmsResponse;
import com.yibi.common.utils.FeigeSmsUtils;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class SmsCodeBizTest extends BaseTest {

    @Resource
    private SmsCodeBiz smsCodeBiz;
    @Test
    public void queryUserTest(){
        String result = smsCodeBiz.getValidateCode("13165373280",1);
        System.out.println(result);
    }
}
