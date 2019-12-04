package com.zh.module.ett;

import com.zh.module.bean.ett.result.CursorPager;
import com.zh.module.bean.ett.result.EttAccount;
import com.zh.module.bean.ett.result.EttLedger;
import com.zh.module.service.ett.EttAccountAPIService;
import com.zh.module.service.ett.impl.EttAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EttAccountAPITests extends EttAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(EttAccountAPITests.class);

    private EttAccountAPIService ettAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.ettAccountAPIService = new EttAccountAPIServiceImpl(this.config);
    }

    @Test
    public void getAccounts() {
        List<EttAccount> result = this.ettAccountAPIService.getAccount();
        this.toResultString(EttAccountAPITests.LOG, "result", result);
    }

    @Test
    public void getAccount() {
        EttAccount result = this.ettAccountAPIService.getAccount("btc");
        this.toResultString(EttAccountAPITests.LOG, "result", result);
    }

    @Test
    public void getLedger() {
        CursorPager<EttLedger> result = this.ettAccountAPIService.getLedger("oka", null, null, 1);
        this.toResultString(EttAccountAPITests.LOG, "result", result);
    }

}
