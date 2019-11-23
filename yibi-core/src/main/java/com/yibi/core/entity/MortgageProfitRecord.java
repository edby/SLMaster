package com.yibi.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MortgageProfitRecord implements Serializable {
    private static final long serialVersionUID = 8264778586236542666L;

    private Integer id;

    private Integer userid;

    private Integer cointype;

    private BigDecimal amount;

    private Byte isTeam;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCointype() {
        return cointype;
    }

    public void setCointype(Integer cointype) {
        this.cointype = cointype;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Byte getIsTeam() {
        return isTeam;
    }

    public void setIsTeam(Byte isTeam) {
        this.isTeam = isTeam;
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
        sb.append(", userid=").append(userid);
        sb.append(", cointype=").append(cointype);
        sb.append(", amount=").append(amount);
        sb.append(", isTeam=").append(isTeam);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}