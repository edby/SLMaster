package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Decrypt;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.biz.BannerBiz;
import com.yibi.orderapi.biz.HomePageBiz;
import com.yibi.orderapi.biz.TeamBiz;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 团队
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/team")
public class TeamController extends BaseController{
	@Autowired
	private TeamBiz teamBiz;

	/**
	 * 初始化
	 * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="init",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String outIndex(@CurrentUser User user, @Params Object params){
		try {
			if (!(params instanceof JSONObject)) {
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject) params;
			Integer page = json.getInteger("page");
			Integer rows = json.getInteger("rows");
			if (page == null) {
				page = 0;
			}
			PageModel pageModel = new PageModel(page, rows);
			return teamBiz.init(user, pageModel);

		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}
	/**
	 * 团队列表
	 * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="list",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String list(@CurrentUser User user){
		try {
			return teamBiz.list(user);

		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}
	/**
	 * 直推列表
	 * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="directList",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String directList(@CurrentUser User user, @Params Object params){
		try {
            if (!(params instanceof JSONObject)) {
                return Result.toResult(ResultCode.PARAM_IS_BLANK);
            }
            JSONObject json = (JSONObject) params;
            Integer page = json.getInteger("page");
            Integer rows = json.getInteger("rows");
            if (page == null) {
                page = 0;
            }
            PageModel pageModel = new PageModel(page, rows);
			return teamBiz.directList(user, pageModel);

		}catch (NumberFormatException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (JSONException e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.PARAM_TYPE_BIND_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SYSTEM_INNER_ERROR);
		}
	}
}
