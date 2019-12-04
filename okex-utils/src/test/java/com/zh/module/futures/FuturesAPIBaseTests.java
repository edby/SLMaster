package com.zh.module.futures;

import com.zh.module.BaseTests;
import com.zh.module.config.APIConfiguration;
import com.zh.module.enums.I18nEnum;

/**
 * Futures api basetests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/13 18:23
 */
public class FuturesAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com");


        config.setApiKey("");
        config.setSecretKey("");


        config.setPassphrase("");
        config.setPrint(true);
        config.setI18n(I18nEnum.ENGLISH);

        return config;
    }

    int from = 0;
    int to = 0;
    int limit = 20;

    String instrument_id = "EOS-USD-181102";


}
