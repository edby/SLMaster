package com.zh.module.service.spot.impl;

import com.zh.module.bean.spot.result.*;
import com.zh.module.client.APIClient;
import com.zh.module.config.APIConfiguration;
import com.zh.module.service.spot.SpotProductAPIService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 公共数据相关接口
 *
 **/
public class SpotProductAPIServiceImpl implements SpotProductAPIService {

    private final APIClient client;
    private final SpotProductAPI spotProductAPI;

    public SpotProductAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.spotProductAPI = this.client.createService(SpotProductAPI.class);
    }


    @Override
    public Ticker getTickerByProductId(final String product) {
        return this.client.executeSync(this.spotProductAPI.getTickerByProductId(product));
    }

    @Override
    public List<Ticker> getTickers() {
        return this.client.executeSync(this.spotProductAPI.getTickers());
    }

    @Override
    public Book bookProductsByProductId(final String product, final String size, final BigDecimal depth) {
        return this.client.executeSync(this.spotProductAPI.bookProductsByProductId(product, size, depth));
    }

    @Override
    public List<Product> getProducts() {
        return this.client.executeSync(this.spotProductAPI.getProducts());
    }

    @Override
    public List<Trade> getTrades(final String product, final String from, final String to, final String limit) {
        return this.client.executeSync(this.spotProductAPI.getTrades(product, from, to, limit));
    }

    @Override
    public List<KlineDto> getCandles(final String product, final String granularity, final String start, final String end) {
        return this.client.executeSync(this.spotProductAPI.getCandles(product, granularity, start, end));
    }

    @Override
    public List<String[]> getCandles_1(final String product, final String granularity, final String start, final String end) {
        return this.client.executeSync(this.spotProductAPI.getCandles_1(product, granularity, start, end));
    }

}
