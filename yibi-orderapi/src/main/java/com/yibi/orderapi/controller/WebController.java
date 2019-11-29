package com.yibi.orderapi.controller;

import com.yibi.common.utils.PatternUtil;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Doc;
import com.yibi.core.entity.News;
import com.yibi.core.entity.Notice;
import com.yibi.core.entity.User;
import com.yibi.core.service.*;
import com.yibi.orderapi.authorization.annotation.Authorization;
import com.yibi.orderapi.authorization.annotation.CurrentUser;
import com.yibi.orderapi.biz.NoticeBiz;
import com.yibi.orderapi.biz.SmsCodeBiz;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行管理
* @author zhaohe
* @date 2018-7-16
* @version V1.0
 */
@Controller
@RequestMapping("/web")
public class WebController extends BaseController{
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private SmsCodeBiz smsCodeBiz;
	@Autowired
	private NoticeBiz noticeBiz;
	@Autowired
	private DocService docService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private SysparamsService sysparamsService;
	@Autowired
	private CoinIntroductionService coinIntroductionService;

	@Autowired
	private RedisTemplate<String, String> redis;


	/**
	 * 跳转注册页
	 * @param map
     * @return
     */
	@RequestMapping(value="register",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String toRegister(Map<String, Object> map, String uuid){
		String url = sysparamsService.getValStringByKey(SystemParams.APP_DOWNLAOD_URL);
		map.put("uuid", uuid);
		map.put("downloadUrl", url);
		return "regiest";
	}

	/**
	 * 用户WEB注册
	 * @param phone
	 * @param userPassword
	 * @param code
	 * @param codeId
	 * @param referPhone
     * @return
     */
	@ResponseBody
	@RequestMapping(value="submitRegister",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String register(String phone,String userPassword,String code,Integer codeId,String referPhone){
		/*正则校验*/
		if(phone.length() != 11){
			return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
		}
		if(referPhone.length() != 8){
			return Result.toResult(ResultCode.PHONE_TYPE_ERROR);
		}
		if(!PatternUtil.isVerificationCode(code)){
			return Result.toResult(ResultCode.SMSCODE_TYPE_ERROR);
		}
		if(!PatternUtil.isDigitalAndWord(userPassword)){
			return Result.toResult(ResultCode.PASSWORD_TYPE_ERROR);
		}
		try {
			return userBiz.register(phone, code, codeId, userPassword, referPhone, "", 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.toResult(ResultCode.REGISTER_ERROR);
	}

	/**
	 * 获取校验码
	 * @param phone
	 * @param type
     * @return
     */
	@ResponseBody
	@RequestMapping(value="smscode",method=RequestMethod.POST ,produces = "application/json;charset=utf-8")
	public String getValidateCode(String phone ,Integer type,String VerificationCode){
		try {
			String randCode = RedisUtil.searchString(redis,"RandCode"+phone);
			if (!VerificationCode.equalsIgnoreCase(randCode)){
				return Result.toResult(ResultCode.VCODE_FALSE);
			}
			//获取校验码
			String returnStr = smsCodeBiz.getValidateCode(phone, type);
			return returnStr;
		}catch (Exception e) {
			e.printStackTrace();
			return Result.toResult(ResultCode.SMS_ERROR);
		}
	}


	/**
	 * 分享
	 * @param map
     * @return
     */
	@RequestMapping(value = "share", method = RequestMethod.GET)
	public String share(Map<String, Object> map, String phone) {
		if(StrUtils.isBlank(phone)){
			return "share";
		}
		map.put("url", sysparamsService.getValStringByKey(SystemParams.APP_CONFIG_LOGGED_SHARE_URL) + phone);
		return "share";
	}

	/**
	 * 公告
	 * @param type
	 * @param map
     * @return
     */
	@RequestMapping(value="news/type/{type}",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String noticeByType(@PathVariable("type")Integer type, Map<String, Object> map){
		try {
			//查询文章
			News news = newsService.getByType(type);
			map.put("news", news);
			return "news";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "news";
	}
	/**
	 * 公告
	 * @param id
	 * @param map
     * @return
     */
	@RequestMapping(value="news/id/{id}",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String noticeById(@PathVariable("id")Integer id, Map<String, Object> map){
		try {
			//查询文章
			News news = newsService.selectByPrimaryKey(id);
			map.put("news", news);
			return "news";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "news";
	}

	/**
	 * 币种介绍
	 * @param map
	 * @param coinType
     * @return
     */
	@RequestMapping(value="coin/intro",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String queryCoinInroduction(Map<String, Object> map, Integer coinType){
		coinIntroductionService.queryCoinInroduction(map,coinType);
		return "coinIntroduction";
	}
	/**
	 * 认购页
     * @return
     */
	@RequestMapping(value="activity",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String activity(){
		return "activity";
	}
	/**
	 * 获取邀请页二维码内容 -- 注册
     * @return
     */
	@Authorization
	@ResponseBody
	@RequestMapping(value="getQCodeInfo",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	public String getQCodeInfo(@CurrentUser User user){
		String url = "http://api.pgy.pub/web/register.action?uuid=" + user.getUuid();
		url = "http://api.k780.com:88/?app=qr.get&data=" + url;
		return Result.toResult(ResultCode.SUCCESS, url);
	}
	/**
	 * 获取邀请页二维码内容 -- 下载
     * @return
     */
	@ResponseBody
	@RequestMapping(value="downloadInfo",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String downloadInfo(){
		String url = sysparamsService.getValStringByKey(SystemParams.APP_DOWNLAOD_URL);
		url = "http://api.k780.com:88/?app=qr.get&data=" + url;
		return Result.toResult(ResultCode.SUCCESS, url);
	}
	@RequestMapping(value="downloadPage",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	public String downloadPage(Map<String, Object> map){
		String url = sysparamsService.getValStringByKey(SystemParams.APP_DOWNLAOD_URL);
		map.put("downloadUrl", url);
		return "download";
	}
}
