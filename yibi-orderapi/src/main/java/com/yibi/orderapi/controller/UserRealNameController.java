package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.authorization.annotation.Sign;
import com.yibi.orderapi.biz.UserAuthBiz;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户登录注册
* @author lina 
* @date 2017-12-26
* @version V1.0
 */
@Controller
@RequestMapping("/realname")
public class UserRealNameController extends BaseController{
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private UserAuthBiz userAuthBiz;

	/**
	 * 实人认证初始化
	 * @param user
	 * @return
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="init",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String initRealName(@CurrentUser User user ){
		try {

			return userBiz.getToken(user);
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
	/**
	 * 实人认证页面初始化
	 * @param user
	 * @return
	 */
	@Authorization
	@ResponseBody
	@RequestMapping(value="getInfo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getInfo(@CurrentUser User user){
		try {

			return userAuthBiz.getInfo(user);
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


	/**
	 * 获取实人认证信息
	 * @param user
	 * @param params
	 * @return
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="status",method= RequestMethod.GET,produces="application/json;charset=utf-8")
	public String getStatus(@CurrentUser User user , @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String taskId = json.getString("taskId");
			/*参数校验*/
			if(StrUtils.isBlank(taskId)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			//设置头像
			return userBiz.getStatus(user, taskId);
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

	/**
	 * 一级认证
	 * @param user
	 * @param params
	 * @return
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="level1",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String level1(@CurrentUser User user, @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String userName = json.getString("userName");
			String idCardNumber = json.getString("idCardNumber");
			/*参数校验*/
			if(StrUtils.isBlank(idCardNumber) || StrUtils.isBlank(userName)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			if(idCardNumber.length() != 15 && idCardNumber.length()!= 18){
				return Result.toResult(ResultCode.IDCARD_ERROR);
			}
			return userAuthBiz.level1(userName, idCardNumber, user);
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
	/**
	 * 三级认证
	 * @param user
	 * @param params
	 * @return
	 */
	@Sign
	@Authorization
	@ResponseBody
	@RequestMapping(value="level3",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String level3(@CurrentUser User user, @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			String videoUrl = json.getString("videoUrl");
			/*参数校验*/
			if(StrUtils.isBlank(videoUrl)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			return userAuthBiz.level3(videoUrl, user);
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
