package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/7/14 0014.
 */
public class OrderMakerBizTest extends BaseTest {

    @Resource
    private OrderMakerBiz orderMakerBiz;
    @Test
    public void makerReleaseDealTest(){
        User user = new User();
        user.setId(1);
        user.setPhone("13165373280");
        user.setIdstatus(1);
        user.setOrderpwd("c4ca4238a0b923820dcc509a6f75849b");
        try {
            String s = orderMakerBiz.makerReleaseDeal(user, 0, new BigDecimal(2), new BigDecimal(2), 8, new BigDecimal(1), new BigDecimal(50), 0, "1");
            String res = s;
            System.out.println(res);
        } catch (BanlanceNotEnoughException e) {
            e.printStackTrace();
            System.out.println("余额不足");
        }
    }

    @Test
    public void queryOrderListTest(){
        User user = new User();
        user.setId(1);
        String res = orderMakerBiz.queryOrderList(user,1,1,1,0,10);
        System.out.println(res);
    }

    @Test
    public void queryavailBalanceAndPriceTest(){
        User user = new User();
        user.setId(8);
        String res = orderMakerBiz.queryavailBalanceAndPrice(user,8);
        System.out.println(res);
    }

    @Test
    public void cancelOrderTest(){
        User user = new User();
        user.setId(9);
        user.setOrderpwd("e10adc3949ba59abbe56e057f20f883e");
        String res = orderMakerBiz.cancelOrder(user,42,"123456");
        System.out.println(res);
    }

    @Test
    public void receiptOrderTest(){
        User user = new User();
        user.setId(8);
        String res = orderMakerBiz.receiptOrder(user,1);
        System.out.println(res);
    }

    @Test
    public void queryOrderList1Test(){
        User user = new User();
        user.setId(3);
        String res = orderMakerBiz.queryOrderList(0,0,0,10);
        System.out.println(res);
    }
}
