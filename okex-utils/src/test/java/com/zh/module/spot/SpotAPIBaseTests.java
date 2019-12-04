package com.zh.module.spot;

import com.zh.module.BaseTests;
import com.zh.module.config.APIConfiguration;
import com.zh.module.enums.I18nEnum;

public class SpotAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        final APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com/");
        // apiKey，api注册成功后页面上有
        config.setApiKey("");
        config.setSecretKey("");

        config.setPassphrase("");
        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }

}
