package com.zh.module.service.ett.impl;

import com.zh.module.bean.ett.result.CursorPager;
import com.zh.module.bean.ett.result.EttAccount;
import com.zh.module.bean.ett.result.EttLedger;
import com.zh.module.client.APIClient;
import com.zh.module.config.APIConfiguration;
import com.zh.module.service.ett.EttAccountAPIService;

import java.util.List;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttAccountAPIServiceImpl implements EttAccountAPIService {

    private final APIClient client;
    private final EttAccountAPI api;

    public EttAccountAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = this.client.createService(EttAccountAPI.class);
    }

    @Override
    public List<EttAccount> getAccount() {
        return this.client.executeSync(this.api.getAccount());
    }

    @Override
    public EttAccount getAccount(String currency) {
        return this.client.executeSync(this.api.getAccount(currency));
    }

    @Override
    public CursorPager<EttLedger> getLedger(String currency, String before, String after, int limit) {
        return this.client.executeSyncCursorPager(this.api.getLedger(currency, before, after, limit));
    }
}
