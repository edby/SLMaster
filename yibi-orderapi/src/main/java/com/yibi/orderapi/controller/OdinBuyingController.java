package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Decrypt;
import com.yibi.orderapi.authorization.annotation.Params;
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

    @Decrypt
    @Authorization
    @ResponseBody
    @RequestMapping(value="buy",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String buy(@CurrentUser User user , @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String amount = json.getString("amount");
            String password = json.getString("password");

            /*参数校验*/
            if(StrUtils.isBlank(amount) || StrUtils.isBlank(password)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            /*验证交易密码合法性*/
            if (!ValidateUtils.isTradePwd(password)) {
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            /*检查金额是否合法*/
            if(odinBiz.checkAmount(amount)){
                return Result.toResult(ResultCode.PARAM_IS_INVALID);
            }
            //找回密码
            return odinBiz.buy(user, amount, password);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }catch (JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        }  catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}
