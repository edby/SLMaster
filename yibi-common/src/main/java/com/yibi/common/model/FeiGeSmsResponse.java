package com.yibi.common.model;

import lombok.Data;

/**
 * @description: 模板快速短信
 * @author: zhaohe
 * @create: 2019-10-14 11:27
 */
@Data
public class FeiGeSmsResponse {

    /**
     * 短信回执编号，为唯一识别码，用户可通过此编号获取记录详情
     */
    private String sendId;

    /**
     * 无效号码数量
     */
    private String invalidCount;

    /**
     * 有效号码数量
     */
    private String successCount;

    /**
     * 黑名单号码数量
     */
    private String blackCount;

    /**
     * 短信回执状态码，判断成功失败的标志
     */
    private String code;

    /**
     * 短信回执状态描述
     */
    private String message;
}
