package com.zh.module.service.ett.impl;

import com.zh.module.bean.ett.result.EttConstituentsResult;
import com.zh.module.bean.ett.result.EttSettlementDefinePrice;
import com.zh.module.client.APIClient;
import com.zh.module.config.APIConfiguration;
import com.zh.module.service.ett.EttProductAPIService;

import java.util.List;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
public class EttProductAPIServiceImpl implements EttProductAPIService {

    private final APIClient client;
    private final EttProductAPI api;

    public EttProductAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = this.client.createService(EttProductAPI.class);
    }

    @Override
    public EttConstituentsResult getConstituents(String ett) {
        return this.client.executeSync(this.api.getConstituents(ett));
    }

    @Override
    public List<EttSettlementDefinePrice> getDefinePrice(String ett) {
        return this.client.executeSync(this.api.getDefinePrice(ett));
    }
}
