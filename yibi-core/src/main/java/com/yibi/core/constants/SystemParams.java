package com.yibi.core.constants;


public class SystemParams {

	//短信开关
	public static final String SMS_ONOFF = "SMS_ONOFF";

	

	//注册功能开关
	public static final String REGIST_ONOFF = "REGIST_ONOFF"; 
	//https开关
	public static final String HTTPS_ONOFF = "HTTPS_ONOFF"; 


	//短信次数限制
	public static final String SMS_COUNTS_LIMIT = "SMS_COUNTS_LIMIT";
	//短信时间限制   （分钟）
	public static final String SMS_TIME_LIMIT = "SMS_TIME_LIMIT";

	
	
	
	public static final String ORDERPWD_LOCK_INTERVAL = "ORDERPWD_LOCK_INTERVAL";//交易密码锁定时间(分钟)
	public static final String ORDERPWD_ERROR_INTERVAL = "ORDERPWD_ERROR_INTERVAL";//交易密码错误时间(分钟)
	public static final String ORDERPWD_ERROR_TIMES = "ORDERPWD_ERROR_TIMES";//交易密码错误次数

	
	public static final String SYSTEM_URL = "SYSTEM_URL";//系统地址
	
	public static final String MARKET_ORDER_SIZE_MAX = "MARKET_ORDER_SIZE_MAX";//行情最大档位

	public static final String REAL_NAME_ONOFF = "REAL_NAME_ONOFF";//实名认证开关
	public static final String IDCARD_VALIDATE_TIMES_LIMIT = "IDCARD_VALIDATE_TIMES_LIMIT";//身份证验证次数限制
	
	public static final String ORDER_C2C_NOTPAY_INACTIVE_INTERVAL = "ORDER_C2C_NOTPAY_INACTIVE_INTERVAL";//C2C代付款的失效时间(单位：分钟)
	public static final String ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL = "ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL";//C2C待确认的失效时间(单位：分钟)

	/*APP启动参数配置*/
	public static final String APP_CONFIG_VERSION = "APP_CONFIG_VERSION";//版本号
	public static final String APP_CONFIG_AGREENMENT_URL = "APP_CONFIG_AGREENMENT_URL";//注册协议
	public static final String APP_CONFIG_RATEDETAILS_URL = "APP_CONFIG_RATEDETAILS_URL";//费率
	public static final String APP_CONFIG_RECHARGEDOC_URL = "APP_CONFIG_EXCHANGERATE_URL";//充值帮助文档
	public static final String APP_CONFIG_WITHDRAWDOC_URL = "APP_CONFIG_EXCHANGERATE_URL";//提现帮助文档
	public static final String APP_CONFIG_LOGGED_SHARE_URL = "APP_CONFIG_LOGGED_SHARE_URL";//已登录分享地址
	public static final String APP_CONFIG_SHARE_TITLE = "APP_CONFIG_SHARE_TITLE";//分享标题
	public static final String APP_CONFIG_SHARE_DES = "APP_CONFIG_SHARE_DES";//分享描述
	public static final String APP_CONFIG_HTTPS_FLAG = "APP_CONFIG_HTTPS_FLAG";//HTTPS开关
	public static final String APP_CONFIG_SPOTCOIN_PAIR = "APP_CONFIG_SPOTCOIN_PAIR";//现货交易币种选择（json串）
	public static final String APP_CONFIG_SPOTQUERY_COINPAIR = "APP_CONFIG_SPOTQUERY_COINPAIR";//现货委托查询币种选择（json串）
	public static final String APP_CONFIG_ORDERCOUNT = "APP_CONFIG_ORDERCOUNT";//现货交易档位（json串）
	public static final String APP_CONFIG_C2CCOIN = "APP_CONFIG_C2CCOIN";//c2c交易币种（json串）
	public static final String APP_CONFIG_YUBIBAO_COIN = "APP_CONFIG_YUBIBAO_COIN";//余币宝币种（json串）
	public static final String APP_CONFIG_YUBIBAO_HELP_DOC = "APP_CONFIG_YUBIBAO_HELP_DOC";//余币宝帮助

	public static final String ORDER_C2C_MAKER_MINTOTAL = "ORDER_C2C_MAKER_MINTOTAL";//C2C商家最低交易额

	public static final String COMMISSION_REALNAME_ONOFF = "COMMISSION_REALNAME_ONOFF";//实名奖励开关
	public static final String COMMISSION_REALNAME_COIN = "COMMISSION_REALNAME_COIN";//实名奖励币种 josn
	public static final String COMMISSION_REALNAME_COIN_AMOUNT_USER = "COMMISSION_REALNAME_COIN_AMOUNT_USER";//实名奖励币种对应奖励数量 json
	public static final String COMMISSION_REALNAME_COIN_AMOUNT_REFER = "COMMISSION_REALNAME_COIN_AMOUNT_REFER";//实名奖励币种对应奖励数量 json

	public static final String APP_DOWNLAOD_URL = "APP_DOWNLAOD_URL";//app下载地址
	public static final String APP_SHARE_URL = "APP_SHARE_URL";//app分享地址
	
	public static final String DEFAULT_HEAD_IMG_URL = "DEFAULT_HEAD_IMG_URL";//默认头像地址
	
	public static final String WEBSITE_URL = "WEBSITE_URL";//官网URL

	public static final String TRANS_TO_MAIN_COIN = "TRANS_TO_MAIN_COIN";//需把余额转账至主账户的币种对
	public static final String NET_INDEX_URL = "NET_INDEX_URL";//官网

	public static final String ORDER_C2C_CANCEL_LIMIT_TAKER = "ORDER_C2C_CANCEL_LIMIT_TAKER";//普通用户取消订单次数限制
	public static final String ORDER_C2C_CANCEL_LIMIT_MAKER = "ORDER_C2C_CANCEL_LIMIT_MAKER";//商家取消订单次数限制
	public static final String NOTSIGN_CALCUL_FORCE_RATE = "NOTSIGN_CALCUL_FORCE_RATE";//未连续签到，减去算力/天数 的比例

	public static final String ORDER_FORCE_PER = "ORDER_FORCE_PER";//每次交易增加的算力

	public static final String RECHARGE_ONOFF_TOTAL = "RECHARGE_ONOFF_TOTAL";//钱包充值开关

	public static final String RECHARGE_COINTYPES = "RECHARGE_COINTYPES";//后台可以充值的币种

	//默认交易密码
	public static final String ORDER_PASSWORD_DEFAULT="ORDER_PASSWORD_DEFAULT";

	//机器人价格
	public static final String ROBOT_USDT_PRICE="ROBOT_USDT_PRICE";

	//默认用户密码
	public static final String USER_DEFAULT_PASSWORD="USER_DEFAULT_PASSWORD";

	//自动交易开关
	public static final String AUTO_WITHDRAW_ONOFF="AUTO_WITHDRAW_ONOFF";

	/*充值*/
    public static final String SYSTEM_RECHARGE_URL="SYSTEM_RECHARGE_URL";

    //交易挖矿每日获取金额最大值
    public static final String DIG_DEAL_AMOUNT_MAX="DIG_DEAL_AMOUNT_MAX_%s";
	//交易挖矿每日获取数量最大值
    public static final String DIG_DEAL_NUMBER_MAX="DIG_DEAL_NUMBER_MAX_%s";

	//首页行情展示币种 默认
	public static final String HOMEPAGE_MARKET_COIN_LIST="HOMEPAGE_MARKET_COIN_LIST";

	/*蒲公英特殊币种*/
	//特殊币种价格变动开关
	public static final String ORDER_SPECIAL_COIN_PRICE_CHANGE_ONOFF="ORDER_SPECIAL_COIN_PRICE_CHANGE_ONOFF";
	//特殊币种
	public static final String ORDER_SPECIAL_COIN="ORDER_SPECIAL_COIN";
	//基础价格
	public static final String ORDER_SPECIAL_COIN_BASE_PRICE="ORDER_SPECIAL_COIN_BASE_PRICE";
	//当前价格
	public static final String ORDER_SPECIAL_COIN_NEW_PRICE="ORDER_SPECIAL_COIN_NEW_PRICE";
	//最大溢价
	public static final String ORDER_SPECIAL_COIN_PRICE_MAX="ORDER_SPECIAL_COIN_PRICE_MAX";

	//认证等级对应24小时提币额度
	public static final String WITHDRAW_QUOTA_AUTH="WITHDRAW_QUOTA_AUTH_%s";
    //认证等级对应法币交易单笔额度
	public static final String C2C_QUOTA_AUTH="C2C_QUOTA_AUTH_%s";

	//第三方行情币种列表
	public static final String MARKET_COIN_LIST="MARKET_COIN_LIST";

	//推荐人邀请等级
	public static final String REFER_STATUS_NUMBER_1="REFER_STATUS_NUMBER_1";
	public static final String REFER_STATUS_NUMBER_2="REFER_STATUS_NUMBER_2";
	public static final String REFER_STATUS_NUMBER_3="REFER_STATUS_NUMBER_3";
	public static final String REFER_STATUS_NUMBER_4="REFER_STATUS_NUMBER_4";
	public static final String REFER_STATUS_NUMBER_5="REFER_STATUS_NUMBER_5";
	public static final String REFER_STATUS_NUMBER_AMOUNT_1="REFER_STATUS_NUMBER_AMOUNT_1";
	public static final String REFER_STATUS_NUMBER_AMOUNT_2="REFER_STATUS_NUMBER_AMOUNT_2";
	public static final String REFER_STATUS_NUMBER_AMOUNT_3="REFER_STATUS_NUMBER_AMOUNT_3";
	public static final String REFER_STATUS_NUMBER_AMOUNT_4="REFER_STATUS_NUMBER_AMOUNT_4";
	public static final String REFER_STATUS_NUMBER_AMOUNT_5="REFER_STATUS_NUMBER_AMOUNT_5";
	//推荐人交易挖矿开关
	public static final String DEAL_ORDER_REFER_REWARD_BUY_ONOFF="DEAL_ORDER_REFER_REWARD_BUY_ONOFF";
	public static final String DEAL_ORDER_REFER_REWARD_SALE_ONOFF="DEAL_ORDER_REFER_REWARD_SALE_ONOFF";

	//实名奖励资金划转须交易次数
    public static final String DEAL_DIG_CAN_TRANS_COUNT = "DEAL_DIG_CAN_TRANS_COUNT";

    //抵押挖矿周期
    public static final String MORTGAGE_DIG_CYCLE = "MORTGAGE_DIG_CYCLE";
	//抵押挖矿周期反利率
    public static final String MORTGAGE_DIG_CYCLE_RATE = "MORTGAGE_DIG_CYCLE_RATE";
    //抵押挖矿文档url
    public static final String MORTGAGE_DIG_DOC = "MORTGAGE_DIG_DOC";
    //抵押挖矿协议
    public static final String MORTGAGE_DIG_AGREEMENT = "MORTGAGE_DIG_AGREEMENT";
}
