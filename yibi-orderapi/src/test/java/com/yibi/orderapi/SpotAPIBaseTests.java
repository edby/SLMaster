package com.yibi.orderapi;

import com.zh.module.config.APIConfiguration;
import com.zh.module.enums.I18nEnum;

public class SpotAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        final APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com/");
        // apiKey，api注册成功后页面上有
        config.setApiKey("6b1c2672-f6c5-4eff-a6ad-c02ec0ad678a");
        config.setSecretKey("A29185350023C10F6F8AA7FF36B5C736");

        config.setPassphrase("qq940916");
        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }

}
