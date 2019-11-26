package com.yibi.batch.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.yibi.batch.Contants;
import com.yibi.batch.biz.MarketBiz;
import com.yibi.batch.biz.MortgageBiz;
import com.yibi.common.utils.HTTP;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.WebsocketClientUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.OrderManageService;
import com.yibi.core.service.OrderSpotService;
import com.yibi.core.service.SysparamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

@Slf4j
@Service
@Transactional
public class MortgageBizImpl implements MortgageBiz {
    @Autowired
    private SysparamsService sysparamsService;

    @Override
    public void release() {

    }
}
