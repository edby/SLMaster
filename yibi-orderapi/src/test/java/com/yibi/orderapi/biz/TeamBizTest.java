package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class TeamBizTest extends BaseTest {

    @Resource
    private TeamBiz teamBiz;
    @Resource
    private UserBiz userBiz;

    @Test
    public void queryUserTest(){
        User user = userBiz.queryUser();
        PageModel pageModel = new PageModel(1, 5);
        System.out.println(teamBiz.init(user, pageModel));
    }
}
