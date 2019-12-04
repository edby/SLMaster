package com.yibi.orderapi.biz;

import com.yibi.core.entity.User;
import com.yibi.orderapi.BaseTest;
import com.yibi.orderapi.SpotAPIBaseTests;
import com.zh.module.bean.spot.result.Account;
import com.zh.module.service.spot.SpotAccountAPIService;
import com.zh.module.service.spot.impl.SpotAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
public class OkexBizTest extends SpotAPIBaseTests {

    @Resource
    private SpotAccountAPIService spotAccountAPIService;
    @Before
    public void before() {
        this.config = this.config();
        this.spotAccountAPIService = new SpotAccountAPIServiceImpl(this.config);
    }
    /**
     * 账户信息
     */
    @Test
    public void getAccounts() {
        final List<Account> accounts = this.spotAccountAPIService.getAccounts();
        System.out.println(accounts);
    }

    
}
