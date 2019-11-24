package com.yibi.orderapi.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.entity.User;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.authorization.annotation.Params;
import com.yibi.orderapi.biz.MortgageBiz;
import com.yibi.orderapi.biz.TeamBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 抵押挖矿
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/mortgage")
public class MortgageController extends BaseController{
	@Autowired
	private MortgageBiz mortgageBiz;

	/**
	 * 初始化
	 * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="init",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String init(@CurrentUser User user, @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer coinType = json.getInteger("coinType");
			coinType = coinType == null ?  CoinType.PGY : coinType;
			return mortgageBiz.init(user, coinType);

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
	 * 提交
	 * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="commit",method= RequestMethod.POST,produces="application/json;charset=utf-8")
	public String commit(@CurrentUser User user, @Params Object params){
		try {
			if(params==null||!(params instanceof JSONObject)){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			JSONObject json = (JSONObject)params;
			Integer coinType = json.getInteger("coinType");
			String amount = json.getString("amount");
			String rate = json.getString("rate");
			//持续时间
			Integer time = json.getInteger("time");
			coinType = coinType == null ?  CoinType.PGY : coinType;
			/*参数校验*/
			if(StrUtils.isBlank(amount) ||StrUtils.isBlank(rate) || time == null){
				return Result.toResult(ResultCode.PARAM_IS_BLANK);
			}
			return mortgageBiz.commit(user, coinType, amount, rate, time);

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
