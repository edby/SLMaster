package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OdinRewardRecoed implements Serializable {
    private static final long serialVersionUID = 1743639086738679932L;

    private Integer id;

    private Integer userId;

    private Integer number;

    private BigDecimal unionAmount;

    private BigDecimal orderAmount;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getUnionAmount() {
        return unionAmount;
    }

    public void setUnionAmount(BigDecimal unionAmount) {
        this.unionAmount = unionAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
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
        sb.append(", number=").append(number);
        sb.append(", unionAmount=").append(unionAmount);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}