package com.yibi.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CoinManageModel implements Serializable {
    private static final long serialVersionUID = -7740286190199711275L;

    private Integer id;

    private Integer cointype;

    private String coinname;

    private String cnname;

    //依赖币种名称
    private String relycoin;

    private String description;

    private String imgurl;

    private BigDecimal minC2cTransAmt;   //最低法币交易额限制

    private String minC2cTransNum;   //最低法币交易额限制

    private BigDecimal minwithdrawNum;   //最低提现数量限制

    private String withdrawNum;   //最低提现数量限制

    private Integer c2cNumScale;    //法币交易数量小数位数

    private Integer yubiScale;      //余币宝计算小数位数

    private Integer calculScale;   //资金划转小数位数

    private Integer c2cPriceScale;   //法币交易价格小数位数

    private Integer withdrawScale;  //提现小数位数


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", cointype=").append(cointype);
        sb.append(", coinname=").append(coinname);
        sb.append(", cnname=").append(cnname);
        sb.append(", description=").append(description);
        sb.append(", imgurl=").append(imgurl);
        sb.append("]");
        return sb.toString();
    }
}