package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CoinExchangeConfig implements Serializable {
    private static final long serialVersionUID = -4120995968020063828L;

    private Integer id;

    private Byte unionCoin;

    private Byte orderCoin;

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

    public Byte getUnionCoin() {
        return unionCoin;
    }

    public void setUnionCoin(Byte unionCoin) {
        this.unionCoin = unionCoin;
    }

    public Byte getOrderCoin() {
        return orderCoin;
    }

    public void setOrderCoin(Byte orderCoin) {
        this.orderCoin = orderCoin;
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
        sb.append(", unionCoin=").append(unionCoin);
        sb.append(", orderCoin=").append(orderCoin);
        sb.append(", priceRise=").append(priceRise);
        sb.append(", amountRise=").append(amountRise);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}