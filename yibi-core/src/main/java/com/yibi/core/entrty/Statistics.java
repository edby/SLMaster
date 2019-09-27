package com.yibi.core.entrty;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Statistics implements Serializable {
    private static final long serialVersionUID = -8107519183918610397L;

    private Integer id;

    private Integer coinType;

    private BigDecimal dayTotalDealDig;

    private BigDecimal dayTotalReferDealDig;

    private BigDecimal dayTotalPersonDealDig;

    private BigDecimal totalDealDig;

    private BigDecimal regiestDig;

    private Date createtime;

    private Date updatetime;

}