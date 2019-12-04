package com.zh.module;

import com.zh.module.bean.futures.result.ExchangeRate;
import com.zh.module.bean.futures.result.ServerTime;
import com.zh.module.futures.FuturesAPIBaseTests;
import com.zh.module.service.GeneralAPIService;
import com.zh.module.service.futures.impl.GeneralAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General API Tests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/12 14:34
 */
public class GeneralAPITests extends FuturesAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralAPITests.class);

    private GeneralAPIService generalAPIService;


    @Before
    public void before() {
        config = config();
        generalAPIService = new GeneralAPIServiceImpl(config);
    }


    @Test
    public void testServerTime() {
        ServerTime time = generalAPIService.getServerTime();
        toResultString(LOG, "ServerTime", time);
    }

    @Test
    public void testExchangeRate() {
        ExchangeRate exchangeRate = generalAPIService.getExchangeRate();
        toResultString(LOG, "ExchangeRate", exchangeRate);
    }
}