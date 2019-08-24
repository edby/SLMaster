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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", ordercointype=").append(ordercointype);
        sb.append(", buyrole=").append(buyrole);
        sb.append(", salerole=").append(salerole);
        sb.append(", feerate=").append(feerate);
        sb.append(", salecashback=").append(salecashback);
        sb.append(", buycashback=").append(buycashback);
        sb.append(", salerefcashback=").append(salerefcashback);
        sb.append(", buyrefcashback=").append(buyrefcashback);
        sb.append(", ordertype=").append(ordertype);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}