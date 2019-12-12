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
        config.setApiKey("6b1c2672-f6c5-4eff-a6ad-c02ec0ad678a");
        config.setSecretKey("A29185350023C10F6F8AA7FF36B5C736");

        config.setPassphrase("qq940916");
        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);

        return config;
    }


}
