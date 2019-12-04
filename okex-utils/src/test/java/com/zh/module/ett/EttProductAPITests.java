package com.zh.module.ett;

import com.zh.module.bean.ett.result.EttConstituentsResult;
import com.zh.module.bean.ett.result.EttSettlementDefinePrice;
import com.zh.module.service.ett.EttProductAPIService;
import com.zh.module.service.ett.impl.EttProductAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EttProductAPITests extends EttAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(EttProductAPITests.class);

    private EttProductAPIService ettProductAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.ettProductAPIService = new EttProductAPIServiceImpl(this.config);
    }

    @Test
    public void getConstituents() {
        EttConstituentsResult result = this.ettProductAPIService.getConstituents("ok06ett");
        this.toResultString(EttProductAPITests.LOG, "result", result);
    }

    @Test
    public void getDefinePrice() {
        List<EttSettlementDefinePrice> result = this.ettProductAPIService.getDefinePrice("ok06ett");
        this.toResultString(EttProductAPITests.LOG, "result", result);
    }

}