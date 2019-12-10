package com.yibi.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class CoinExchangeConfig implements Serializable {
    private static final long serialVersionUID = -4095391078085668865L;

    private Integer id;

    private Byte unionCoin;

    private Byte orderCoin;

    private Byte relyCoin;

    private BigDecimal priceRise;

    private BigDecimal amountRise;
    private BigDecimal sumRise;

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
        sb.append(", unionCoin=").append(unionCoin);
        sb.append(", orderCoin=").append(orderCoin);
        sb.append(", relyCoin=").append(relyCoin);
        sb.append(", priceRise=").append(priceRise);
        sb.append(", amountRise=").append(amountRise);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}