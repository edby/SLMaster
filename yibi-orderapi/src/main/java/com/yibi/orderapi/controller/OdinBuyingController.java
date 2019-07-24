package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.ValidateUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.*;
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

    /**
     * 认购
     * @param user
     * @param params
     * @return
     */
    @Sign
    @Authorization
    @ResponseBody
    @RequestMapping(value="buy",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String buy(@CurrentUser User user, @Params Object params){
        try {
            if(params==null||!(params instanceof JSONObject)){
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject)params;
            String amount = json.getString("amount");
            String ecnAmount = json.getString("ecnAmount");
            String password = json.getString("password");

            /*参数校验*/
            if(StrUtils.isBlank(amount) || StrUtils.isBlank(password) || StrUtils.isBlank(ecnAmount)){
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
            return odinBiz.buy(user, amount, ecnAmount, password);
        }catch (NumberFormatException | JSONException e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 奖励页面初始化
     * @return
     */
    @Authorization
    @Sign
    @ResponseBody
    @RequestMapping(value="reward",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String reward(@CurrentUser User user){
        try {
            return odinBiz.reward(user);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 个人推广记录
     * @return
     */
    @Authorization
    @Sign
    @ResponseBody
    @RequestMapping(value="inviteList",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String inviteList(@CurrentUser User user, @Params Object params){
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            if (page == null) {
                page = 0;
            }
            page = page + 1;
            PageModel pageModel = new PageModel(page, rows);
            return odinBiz.inviteList(user, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
    /**
     * 更多排行
     * @return
     */
    @Authorization
    @Sign
    @ResponseBody
    @RequestMapping(value="moreRank",method= RequestMethod.POST,produces="application/json;charset=utf-8")
    public String moreRank(@CurrentUser User user, @Params Object params){
        try {
            if (params == null || !(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            if (page == null) {
                page = 0;
            }
            page = page + 1;
            PageModel pageModel = new PageModel(page, rows);
            return odinBiz.moreRank(user, pageModel);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
        }
    }
}