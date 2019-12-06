package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CoinExchangeConfig implements Serializable {
    private static final long serialVersionUID = 5069938474563381093L;

    private Integer id;

    private Byte cointype;

    private BigDecimal priceRise;

    private BigDecimal amountRise;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getCointype() {
        return cointype;
    }

    public void setCointype(Byte cointype) {
        this.cointype = cointype;
    }

    public BigDecimal getPriceRise() {
        return priceRise;
    }

    public void setPriceRise(BigDecimal priceRise) {
        this.priceRise = priceRise;
    }

    public BigDecimal getAmountRise() {
        return amountRise;
    }

    public void setAmountRise(BigDecimal amountRise) {
        this.amountRise = amountRise;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", cointype=").append(cointype);
        sb.append(", priceRise=").append(priceRise);
        sb.append(", amountRise=").append(amountRise);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}