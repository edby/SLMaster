package com.yibi.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1701774470622162459L;

    private Integer id;

    private String phone;

    private String userpassword;

    private String username;

    private Integer referenceid;

    private Integer referenceStatus;

    private Integer uuid;

    private String idcard;

    private Integer idstatus;

    private String headpath;

    private String openid;

    private String secretkey;

    private String token;

    private Date tokencreatetime;

    private String orderpwd;

    private Date logintime;

    private String devicenum;

    private Integer state;

    private Integer role;

    private String nickname;

    private Date createtime;

    private Date updatetime;

    private Integer partnerflag;

}