package com.yibi.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CoinScale implements Serializable {
    private static final long serialVersionUID = -4070088190247624944L;

    private Integer id;

    private Integer ordercointype;

    private Integer unitcointype;

    private Integer orderamtpricescale;

    private Integer orderamtamountscale;

    private Integer availofspotunitscale;

    private Integer availofspotorderscale;

    private Integer marketpriceofcnyscale;

    private Integer markettradenumscale;

    private Integer klinepricescale;

    private Integer calculscale;

    private Integer availofcnyscale;

    private Integer yubiscale;

    private Integer c2cpricescale;

    private Integer c2cnumscale;

    private Integer withdrawScale;

    private Integer c2ctotalamtscale;

    private BigDecimal minspottransamt;

    private BigDecimal minspottransnum;

    private BigDecimal minc2ctransamt;

    private BigDecimal minwithdrawnum;

    private BigDecimal marketbuyminamt;

    private BigDecimal marketsaleminnum;

    private Date createtime;

    private Date updatetime;

    public static CoinScale newObject(Integer orderCoinType, Integer unitcointype){
        CoinScale coinScale = new CoinScale();
        coinScale.setAvailofcnyscale(4);
        coinScale.setAvailofspotorderscale(4);
        coinScale.setAvailofspotunitscale(4);
        coinScale.setC2cnumscale(4);
        coinScale.setC2cpricescale(4);
        coinScale.setCalculscale(4);
        coinScale.setKlinepricescale(4);
        coinScale.setMarketbuyminamt(new BigDecimal(4));
        coinScale.setMarketpriceofcnyscale(4);
        coinScale.setMarketsaleminnum(new BigDecimal(4));
        coinScale.setMarkettradenumscale(4);
        coinScale.setMinc2ctransamt(new BigDecimal(4));
        coinScale.setMinspottransamt(new BigDecimal(4));
        coinScale.setMinspottransnum(new BigDecimal(4));
        coinScale.setMinwithdrawnum(new BigDecimal(4));
        coinScale.setOrderamtamountscale(4);
        coinScale.setOrderamtpricescale(4);
        coinScale.setOrdercointype(orderCoinType);
        coinScale.setUnitcointype(unitcointype);
        coinScale.setWithdrawScale(4);
        return coinScale;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", ordercointype=").append(ordercointype);
        sb.append(", unitcointype=").append(unitcointype);
        sb.append(", orderamtpricescale=").append(orderamtpricescale);
        sb.append(", orderamtamountscale=").append(orderamtamountscale);
        sb.append(", availofspotunitscale=").append(availofspotunitscale);
        sb.append(", availofspotorderscale=").append(availofspotorderscale);
        sb.append(", marketpriceofcnyscale=").append(marketpriceofcnyscale);
        sb.append(", markettradenumscale=").append(markettradenumscale);
        sb.append(", klinepricescale=").append(klinepricescale);
        sb.append(", calculscale=").append(calculscale);
        sb.append(", availofcnyscale=").append(availofcnyscale);
        sb.append(", yubiscale=").append(yubiscale);
        sb.append(", c2cpricescale=").append(c2cpricescale);
        sb.append(", c2cnumscale=").append(c2cnumscale);
        sb.append(", c2ctotalamtscale=").append(c2ctotalamtscale);
        sb.append(", minspottransamt=").append(minspottransamt);
        sb.append(", minspottransnum=").append(minspottransnum);
        sb.append(", minc2ctransamt=").append(minc2ctransamt);
        sb.append(", minwithdrawnum=").append(minwithdrawnum);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append("]");
        return sb.toString();
    }
}