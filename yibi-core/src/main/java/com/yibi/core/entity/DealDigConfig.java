package com.yibi.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class DealDigConfig implements Serializable {
    private static final long serialVersionUID = -7421725888860492119L;

    private Integer id;

    private Integer ordercointype;

    private Integer onoff;

    private Integer buyrole;

    private Integer salerole;

    private BigDecimal feerate;

    private BigDecimal salecashback;

    private BigDecimal buycashback;

    private BigDecimal salerefcashback;

    private BigDecimal buyrefcashback;

    private Integer ordertype;

    private Date createtime;

    private Date updatetime;

}