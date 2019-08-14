package com.yibi.orderapi.biz;

import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.User;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class WalletBizTest extends BaseTest {

    @Resource
    private WalletBiz walletBiz;
    @Resource
    private UserBiz userBiz;
    @Resource
    private UserService userService;
    @Resource
    private AccountService accountService;

    @Test
    public void queryUserTest1(){
        User user = userBiz.queryUser();
        System.out.println(user.toString());
    }
    @Test
    public void queryUserTest(){
        User user = new User();
        user.setId(8);
        String result = walletBiz.queryByUser(user, 1);
        System.out.println(result);
    }
    @Test
    public void accountDetails() throws ParseException {
        User user = new User();
        user.setId(8);
        String result = walletBiz.accountDetails(user, 0, 1, 0, 5);
        System.out.println(result);
    }
    @Test
    public void info() throws ParseException {
        String result = walletBiz.rechargeInfo(2);
        System.out.println(result);
    }
    @Test
    public void withDrawApply() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.withDrawApply(user, "940916", new BigDecimal(101), 1 , "111", 2);
        System.out.println(result);
    }
    @Test
    public void withDrawQueryAll() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.withDrawQueryAll(user,0,5,1,3);
        System.out.println(result);
    }
    @Test
    public void transfer() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.transfer(user, GlobalParams.ACCOUNT_TRANSFER_TYPE_SPOTTOC2C,0,new BigDecimal(1),"000000");
        System.out.println(result);
    }
    @Test
    public void withDrawInfo() throws ParseException {
        User user = userBiz.queryUser();
        String result = walletBiz.withDrawInfo(user, 0, 1);
        System.out.println(result);
    }

    @Test
    public void queryCoinAvailBalanceTest(){
        User user = userBiz.queryUser();
        String res = walletBiz.queryCoinAvailBalance(user,0,0);
        System.out.println(res);
    }
    @Test
    public void addAccount(){
        List<User> list = userService.selectAll(new HashMap<Object, Object>());
        int id;
        Account account = new Account();
        for(User user : list){
            id = user.getId();
            System.out.println(id);
            account.setUserid(id);
            account.setAvailbalance(BigDecimal.ZERO);
            account.setFrozenblance(BigDecimal.ZERO);
            account.setAccounttype(AccountType.ACCOUNT_SPOT);
            account.setCointype(CoinType.YT);
            accountService.insertSelective(account);
            account = new Account();
        }
    }

}
