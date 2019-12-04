package com.zh.module.account;

import com.zh.module.BaseTests;
import com.zh.module.config.APIConfiguration;
import com.zh.module.enums.I18nEnum;

/**
 * Account api basetests
 *
 * @author hucj
 * @version 1.0.0
 * @date 2018/7/04 18:23
 */
public class AccountAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com");
        config.setApiKey("");
        config.setSecretKey("");

        config.setPassphrase("");
        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }


}
