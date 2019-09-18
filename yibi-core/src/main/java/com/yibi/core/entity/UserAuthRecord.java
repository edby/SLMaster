package com.yibi.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAuthRecord implements Serializable {
    private static final long serialVersionUID = 8325216853187726777L;

    private Integer id;

    private Integer userId;
    /**
     * 认证类型 1一级 2二级 3三级
     */
    private Integer type;
    /**
     * 结果 0失败 1成功 2待验证
     */
    private Integer state;
    private String videoUrl;

    private Date createtime;

    private Date updatetime;
}