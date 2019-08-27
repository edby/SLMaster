package com.yibi.batch.biz;

import com.yibi.batch.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2018/7/30 0030.
 */
public class OdinWalletBizTest extends BaseTest {

    @Autowired
    private OdinWalletBiz odinWalletBiz;

    @Test
    public void startTest(){
        odinWalletBiz.start();
    }
}
