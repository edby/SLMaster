package com.yibi.orderapi.biz;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;
import com.yibi.core.service.DigcalRecordService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserDiginfoService;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class OrderBizTest extends BaseTest {
    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private OrderV2Biz orderV2Biz;
    @Autowired
    private UserService userService;
    @Autowired
    private DigcalRecordService digcalRecordService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private UserDiginfoService userDiginfoService;

    @Test
    public void testMainpageInfo() {
        User user = new User();
        user.setId(8);
        String result = orderBiz.mainpageInfo(user, 1, 0, 0);
        System.out.println(result);
    }

    @Test
    public void testLimitPriceBuy2() throws InterruptedException {
        User user = userService.selectByPrimaryKey(2);
        Integer orderCoin = 2;
        Integer unitCoin = 0;
        Integer levFlag = 0;
        String price = "10000";
        String amount = "4";
        String password = "123456";
        String result = orderV2Biz.limitPriceBuy(user, orderCoin, unitCoin, levFlag, price, amount, password);
        System.out.println(result);
        Thread.sleep(1000 * 1000);
    }
    @Test
    public void testLimitPriceBuy() throws InterruptedException {
        User user = userService.selectByPrimaryKey(12);
        Integer orderCoin = 2;
        Integer unitCoin = 0;
        Integer levFlag = 0;
        String price = "4";
        String amount = "4";
        String password = "123456";
        String result = orderBiz.limitPriceBuy(user, orderCoin, unitCoin, levFlag, price, amount, password);
        System.out.println(result);
        Thread.sleep(1000 * 1000);
    }

    @Test
    public void testLimitPriceSale() throws InterruptedException {
        User user = userService.selectByPrimaryKey(8);
        Integer orderCoin = 1;
        Integer unitCoin = 0;
        Integer levFlag = 0;
        String price = "1";
        String amount = "10";
        String password = "1";
        String result = orderBiz.limitPriceSale(user, orderCoin, unitCoin, levFlag, price, amount, password);
        System.out.println(result);
        Thread.sleep(1000*1000);
    }

    @Test
    public void testMarketPriceBuy() throws InterruptedException {
        User user = userService.selectByPrimaryKey(12);
        Integer orderCoin = 1;
        Integer unitCoin = 0;
        Integer levFlag = 0;
        String password = "123456";
        String total = "5";
        String result = orderBiz.marketPriceBuy(user, orderCoin, unitCoin, levFlag, total, password);
        System.out.println(result);
        Thread.sleep(1000*10);
    }

    @Test
    public void testMarketPriceSale() throws InterruptedException {
        User user = userService.selectByPrimaryKey(12);
        Integer orderCoin = 2;
        Integer unitCoin = 0;
        Integer levFlag = 0;
        String password = "123456";
        String amount = "2000000";
        String result = orderBiz.marketPriceSale(user, orderCoin, unitCoin, levFlag, amount, password);
        System.out.println(result);
        Thread.sleep(1000*10);
    }

    @Test
    public void orderRecord(){
        User user = userService.selectByPrimaryKey(8);
        Integer orderCoin = 1;
        Integer unitCoin = 0;
        Integer type = 0;
        PageModel pageModel = new PageModel(1, 30);
        String result = orderBiz.orderRecord(user, orderCoin, unitCoin, type, pageModel);
        System.out.println(result);
    }

    @Test
    public void orderCancel() throws InterruptedException {
        Integer id = 1;
        String result = orderBiz.orderCancel(id);
        System.out.println(result);
        Thread.sleep(1000*10);
    }

    @Test
    public void orderDetail() {
        Integer id = 1;
        String result = orderBiz.orderDetail(id);
        System.out.println(result);
        log.info(result);
    }
    @Test
    public void dealDigList() {
        User user = new User();
        user.setId(21);
        String result = orderBiz.dealDigRecordList(user, 0, 10, null);
        System.out.println(result);
    }
    @Test
    public void Kline() {
        String result = orderBiz.minKLine(1, 0);
        System.out.println(result);
    }
}
