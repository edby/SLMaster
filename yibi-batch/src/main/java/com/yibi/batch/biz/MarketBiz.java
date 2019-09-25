package com.yibi.batch.biz;

import com.yibi.core.entity.AccountChain;

import java.util.List;

/**
 * Created by ZhaoHe on 2018/7/12 0012.
 */
public interface MarketBiz {

    void changeMarket();

    void getDayPrice();

    void changeMood();
}
