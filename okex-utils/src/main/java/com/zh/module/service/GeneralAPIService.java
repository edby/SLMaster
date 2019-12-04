package com.zh.module.service;

import com.zh.module.bean.futures.result.ExchangeRate;
import com.zh.module.bean.futures.result.ServerTime;

/**
 * OKEX general api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 16:06
 */
public interface GeneralAPIService {
    /**
     * Time of the server running OKEX's REST API.
     */
    ServerTime getServerTime();

    /**
     * The exchange rate of legal tender pairs
     */
    ExchangeRate getExchangeRate();
}
