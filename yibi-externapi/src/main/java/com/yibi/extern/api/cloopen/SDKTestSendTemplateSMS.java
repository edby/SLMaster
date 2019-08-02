package com.yibi.extern.api.cloopen;

import java.util.HashMap;
import java.util.Set;


public class SDKTestSendTemplateSMS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        sendTemplateSms("13165373280", "460715");
	}

	public static String sendTemplateSms(String phone, String template){
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.init("app.cloopen.com", "8883");
		// 初始化主帐号和主帐号TOKEN
		restAPI.setAccount("8a216da86c282c6a016c4e9ab05c1833", "db19cc41200d4174a30b9ca5dad93133");
		// 初始化应用ID
		restAPI.setAppId("8a216da86c282c6a016c4e9ab0a71839");
		result = restAPI.sendTemplateSMS(phone,template ,new String[]{});

		System.out.println("SDKTestSendTemplateSMS result=" + result);

		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				return key +" = "+object;
			}
		}else{
			//异常返回输出错误码和错误信息
			return "错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg");
		}
		return null;
	}
}
