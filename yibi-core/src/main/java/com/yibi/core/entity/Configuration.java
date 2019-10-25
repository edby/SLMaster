package com.yibi.core.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;




/**
 * Created by xuqingzhong on 2018/1/16 0016.
 * 配置文件
 */
@Data
public class Configuration implements Serializable{
	
	private static final long serialVersionUID = -4328261889873040163L;
	private int versionCode;    /*版本号*/
    private Map<Integer, String> coinTypeAndName;    /*币种及其名称对应*/
    private Map<Integer, String> coinTypeAndUrl;    /*币种及其对应图标地址*/ 
    private Map<Integer,List<Integer>> spotCoinPair;    /*现货顶部币币交易*/ 
    private Map<Integer,List<Integer>> spotQueryCoinPair;    /*现货顶部币币交易*/ 
    /**各种动态页面url*/
    private  String agreenmentUrl;     /*注册协议*/
    private  String c2cHelpDocUrl;     /*帮助文档*/
    private  String rateDocUrl;     /*费率文档*/
    private  String indexUrl;     /*官网*/
    private  String dealDigDocUrl; //交易挖矿URL
    private  int[] transCoinType ;/*转账*/
    private  List<Integer> orderCount ;/*交易档位*/
    private String shareTitle;/*分享标题*/
    private String shareDes;/*分享描述*/

    private List<Integer> c2cCoin;/*c2c交易币种*/
    private List<Integer> rechAndWithCoin;/*充值提现币种*/
    private Map<Integer, CoinManageModel> coinInfo;
    private String maxCancelOfMaker;   /*商家最高取消次数*/
    private String maxCancelOfTaker;   /*普通用户最高取消次数*/
    
    private  List<Integer> digCoinType ;/*挖矿币种*/
    private boolean httpsFlag;
    private List<Integer> dealDigCoinTypes;/*交易挖矿记录币种*/
    private String coinIntroUrl ;//币种介绍



}
