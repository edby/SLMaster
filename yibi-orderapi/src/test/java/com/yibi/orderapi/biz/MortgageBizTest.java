package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class MortgageBizTest extends BaseTest {

    @Resource
    private MortgageBiz mortgageBiz;
    @Resource
    private UserBiz userBiz;

    @Test
    public void init(){
        User user = userBiz.queryUser();
        System.out.println(mortgageBiz.init(user, 8));
    }
}
