package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OdinBuyingRecord implements Serializable {
    private static final long serialVersionUID = 2526342927869236738L;

    private Integer id;

    private Integer userId;

    private BigDecimal amount;

    private BigDecimal buyPrice;

    private Integer number;

    private BigDecimal getOdinAmount;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getGetOdinAmount() {
        return getOdinAmount;
    }

    public void setGetOdinAmount(BigDecimal getOdinAmount) {
        this.getOdinAmount = getOdinAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", amount=").append(amount);
        sb.append(", buyPrice=").append(buyPrice);
        sb.append(", number=").append(number);
        sb.append(", getOdinAmount=").append(getOdinAmount);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}