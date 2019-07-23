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
        Integer page = 0;
        Integer rows = 1;
        page = page + 1;
        PageModel pageModel = new PageModel(page, rows);
        String result = odinBiz.moreRank(user, pageModel);
        System.out.println(result);
    }
}
