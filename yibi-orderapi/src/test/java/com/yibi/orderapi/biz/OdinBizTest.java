package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class OdinBizTest extends BaseTest {
    @Autowired
    private OdinBiz odinBiz;
    @Autowired
    private UserService userService;
    @Test
    public void testMainpageInfo() {
        User user = new User();
        user.setId(1);
        String result = odinBiz.reward(user);
        System.out.println(result);
    }
    @Test
    public void inviteList() {
        User user = new User();
        user.setId(1);
        user.setUuid(22222222);
        Integer page = 0;
        Integer rows = 1;
        page = page + 1;
        PageModel pageModel = new PageModel(page, rows);
        String result = odinBiz.inviteList(user, pageModel);
        System.out.println(result);
    }
    @Test
    public void moreRank() {
        User user = new User();
        user.setId(1);
        user.setUuid(11111110);
        Integer page = 1;
        Integer rows = 10;
        page = page + 1;
        PageModel pageModel = new PageModel(null ,null);
        String result = odinBiz.moreRank(user, pageModel);
        System.out.println(result);
    }
    @Test
    public void buy() {
        User user = new User();
        user.setId(1);
        user.setUuid(11111110);
        user.setReferenceid(11111111);
        user.setOrderpwd("4cc32b28e748f7a5cf1cdca3707c8640");
        String amount = "10000";
        String ecnAmount = "10000";
        String password = "940916";
        String result = odinBiz.buy(user, amount, ecnAmount, password);
        System.out.println(result);
    }
}
