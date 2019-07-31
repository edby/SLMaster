package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OdinBuyingRank implements Serializable {
    private static final long serialVersionUID = 5765977434482975946L;

    private Integer id;

    private Integer number;

    private String numberOneId;

    private BigDecimal numberOneAmount;

    private String numberTwoId;

    private BigDecimal numberTwoAmount;

    private String numberThreeId;

    private BigDecimal numberThreeAmount;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNumberOneId() {
        return numberOneId;
    }

    public void setNumberOneId(String numberOneId) {
        this.numberOneId = numberOneId;
    }

    public BigDecimal getNumberOneAmount() {
        return numberOneAmount;
    }

    public void setNumberOneAmount(BigDecimal numberOneAmount) {
        this.numberOneAmount = numberOneAmount;
    }

    public String getNumberTwoId() {
        return numberTwoId;
    }

    public void setNumberTwoId(String numberTwoId) {
        this.numberTwoId = numberTwoId;
    }

    public BigDecimal getNumberTwoAmount() {
        return numberTwoAmount;
    }

    public void setNumberTwoAmount(BigDecimal numberTwoAmount) {
        this.numberTwoAmount = numberTwoAmount;
    }

    public String getNumberThreeId() {
        return numberThreeId;
    }

    public void setNumberThreeId(String numberThreeId) {
        this.numberThreeId = numberThreeId;
    }

    public BigDecimal getNumberThreeAmount() {
        return numberThreeAmount;
    }

    public void setNumberThreeAmount(BigDecimal numberThreeAmount) {
        this.numberThreeAmount = numberThreeAmount;
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
        sb.append(", number=").append(number);
        sb.append(", numberOneId=").append(numberOneId);
        sb.append(", numberOneAmount=").append(numberOneAmount);
        sb.append(", numberTwoId=").append(numberTwoId);
        sb.append(", numberTwoAmount=").append(numberTwoAmount);
        sb.append(", numberThreeId=").append(numberThreeId);
        sb.append(", numberThreeAmount=").append(numberThreeAmount);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}