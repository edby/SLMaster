package com.zh.module.ett;

import com.zh.module.BaseTests;
import com.zh.module.config.APIConfiguration;
import com.zh.module.enums.I18nEnum;

public class EttAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        final APIConfiguration config = new APIConfiguration();
        config.setEndpoint("https://www.okex.com/");
        config.setApiKey("");
        config.setSecretKey("");

        config.setPassphrase("");
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);
        config.setPrint(true);

        return config;
    }

}
