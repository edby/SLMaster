package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.yibi.orderapi.biz.OdinBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 奥丁认购控制器
 * @author: zhaohe
 * @create: 2019-07-15 15:24
 */
@Controller
@RequestMapping("/odingbuying")
public class OdinBuyingController {

    @Autowired
    private OdinBiz odinBiz;
    /**
     * 初始化
     * @return
     */
    @ResponseBody
    @RequestMapping(value="init",method= RequestMethod.GET,produces="application/json;charset=utf-8")
    public String outIndex(){
        try {
            return odinBiz.init();
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
