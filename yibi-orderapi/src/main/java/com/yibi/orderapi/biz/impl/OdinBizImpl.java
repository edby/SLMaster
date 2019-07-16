package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.CoinScale;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.SysparamsService;
import com.yibi.orderapi.biz.AccountBiz;
import com.yibi.orderapi.biz.OdinBiz;
import com.yibi.orderapi.biz.SystemBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class OdinBizImpl extends BaseBizImpl implements OdinBiz {

    @Autowired
    private SysparamsService sysparamsService;
    @Override
    public String init() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new LinkedList<>();
        list.add(SystemParams.ODIN_BUYING_NUMBER);
        list.add(SystemParams.ODIN_BUYING_TIME);
        list.add(SystemParams.ODIN_BUYING_THIS_PRICE);
        list.add(SystemParams.ODIN_BUYING_NEXT_PRICE);
        list.add(SystemParams.ODIN_BUYING_NOW_ORDER_PRICE);
        list.add(SystemParams.ODIN_BUYING_BAR);
        list.add(SystemParams.ODIN_BUYING_AMOUNT_LIST);
        for(String sysKey : list){
            Sysparams sysparams = sysparamsService.getValByKey(sysKey);
            if("ODIN_BUYING_AMOUNT_LIST".equals(sysKey)){
                String c = sysparams.getKeyval().replace("[","").replace("]","");
                String[] arrays = c.split(",");
                List<String> lists = new LinkedList<>(Arrays.asList(arrays));
                map.put(sysparams.getKeyname(), lists);
            }else {
                map.put(sysparams.getKeyname(), sysparams.getKeyval());
            }
        }
        return Result.toResult(ResultCode.SUCCESS, map);
    }
}
