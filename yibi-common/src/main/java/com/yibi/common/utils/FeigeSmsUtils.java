package com.yibi.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 飞鸽短信
 * @author: zhaohe
 * @create: 2019-10-10 15:17
 */
public class FeigeSmsUtils {
    /**
     * 发送短信
     * @param phone
     * @param content 内容
     */
    public static void SendSms(String phone, String content) {
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                List<BasicNameValuePair> formparams = new ArrayList<>();
                formparams.add(new BasicNameValuePair("Account","18660769100"));
                formparams.add(new BasicNameValuePair("Pwd","ea78a8d62fcb7d7c1fc3e6bda"));//登录后台 首页显示
                formparams.add(new BasicNameValuePair("Content",content));
                formparams.add(new BasicNameValuePair("Mobile",phone));
                formparams.add(new BasicNameValuePair("SignId","181054"));//登录后台 添加签名获取id

                HttpPost httpPost = new HttpPost("http://api.feige.ee/SmsService/Send");
                httpPost.setEntity(new UrlEncodedFormEntity(formparams,"UTF-8"));
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SendSms("18660769100", "18:16");
    }
}
