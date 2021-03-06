/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.7.23-log : Database - sl_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sl_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `sl_db`;

/*Table structure for table `about_info` */

DROP TABLE IF EXISTS `about_info`;

CREATE TABLE `about_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `imgpath` varchar(255) NOT NULL COMMENT '图片地址',
  `type` int(2) NOT NULL COMMENT '类型',
  `keyval` varchar(255) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `about_info` */

insert  into `about_info`(`id`,`imgpath`,`type`,`keyval`,`createtime`,`updatetime`) values (1,'http://47.104.142.76:8080/file/showImg?imgUrl=/20190810/1565418819111_98.jpg',1,'微信群','2019-06-13 14:15:25','2019-06-13 14:15:25'),(2,'http://47.104.142.76:8080/file/showImg?imgUrl=/20190731/1564503720286_670.png',2,'正在开发中','2019-06-13 14:15:35','2019-06-13 14:15:35');

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `availbalance` decimal(16,8) NOT NULL COMMENT '可用余额',
  `frozenblance` decimal(16,8) NOT NULL COMMENT '冻结余额',
  `accounttype` int(2) NOT NULL COMMENT '账户类型',
  `cointype` int(2) NOT NULL COMMENT '币种',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `accountTypePk` (`accounttype`),
  KEY `cointypePk` (`cointype`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `account` */

insert  into `account`(`id`,`userid`,`availbalance`,`frozenblance`,`accounttype`,`cointype`,`createtime`,`updatetime`) values (5,2,0.00000000,0.00000000,0,8,'2019-09-20 11:24:42','2019-09-20 11:24:42'),(6,2,2.00000000,0.00000000,0,0,'2019-09-20 11:24:42','2019-09-20 11:24:42'),(7,2,3.00000000,0.00000000,1,8,'2019-09-20 11:24:42','2019-09-20 11:24:42'),(8,2,2.00000000,0.00000000,1,0,'2019-09-20 11:24:42','2019-09-20 11:24:42'),(9,2,0.00000000,0.00000000,4,0,'2019-09-20 11:26:17','2019-09-20 11:26:17');

/*Table structure for table `account_transfer` */

DROP TABLE IF EXISTS `account_transfer`;

CREATE TABLE `account_transfer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `cointype` int(2) NOT NULL COMMENT '币种类型',
  `toaccount` int(2) NOT NULL COMMENT '目标钱包',
  `fromaccount` int(2) NOT NULL COMMENT '起始钱包',
  `amount` decimal(16,8) NOT NULL COMMENT '转帐金额',
  `relatedid` int(11) NOT NULL COMMENT '相关id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `account_transfer` */

/*Table structure for table `app_version` */

DROP TABLE IF EXISTS `app_version`;

CREATE TABLE `app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `phonetype` int(2) NOT NULL COMMENT '手机类型',
  `appversion` int(11) NOT NULL COMMENT 'APP版本',
  `type` int(2) NOT NULL COMMENT '更新方式',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `size` int(255) NOT NULL COMMENT '大小',
  `verificate` varchar(255) NOT NULL COMMENT 'verificate',
  `state` int(2) NOT NULL COMMENT '状态',
  `minversion` varchar(255) NOT NULL COMMENT '小版本号',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `content` varchar(8191) NOT NULL COMMENT '更新内容',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `app_version` */

/*Table structure for table `bank` */

DROP TABLE IF EXISTS `bank`;

CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(63) NOT NULL COMMENT '银行名称',
  `state` int(2) NOT NULL COMMENT '状态',
  `operid` int(11) NOT NULL COMMENT '操作人id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bank` */

/*Table structure for table `banner` */

DROP TABLE IF EXISTS `banner`;

CREATE TABLE `banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `imgpath` varchar(255) NOT NULL COMMENT '图片地址',
  `bannertype` int(2) NOT NULL COMMENT '功能性',
  `type` int(2) NOT NULL COMMENT '类型',
  `address` varchar(255) NOT NULL COMMENT '目标地址',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `state` int(2) NOT NULL COMMENT '状态',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `banner` */

insert  into `banner`(`id`,`imgpath`,`bannertype`,`type`,`address`,`title`,`state`,`createtime`,`updatetime`) values (1,'1',1,1,'1','banner测试',1,'2019-09-25 16:44:53','2019-09-25 16:44:53'),(2,'1',1,1,'1','banner测试',1,'2019-09-25 16:44:59','2019-09-25 16:44:59'),(3,'1',0,1,'1','banner测试',1,'2019-09-25 16:44:53','2019-09-25 16:44:53');

/*Table structure for table `bind_info` */

DROP TABLE IF EXISTS `bind_info`;

CREATE TABLE `bind_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `type` int(2) NOT NULL COMMENT '类型',
  `account` varchar(255) NOT NULL COMMENT '账户',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `imgurl` varchar(255) NOT NULL COMMENT '图片地址',
  `bankname` varchar(50) NOT NULL COMMENT '银行名称',
  `branchname` varchar(255) NOT NULL COMMENT '开户行',
  `state` int(2) NOT NULL COMMENT '状态',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bind_info` */

/*Table structure for table `code_dbinfo` */

DROP TABLE IF EXISTS `code_dbinfo`;

CREATE TABLE `code_dbinfo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '别名',
  `db_driver` varchar(100) NOT NULL COMMENT '数据库驱动',
  `db_url` varchar(200) NOT NULL COMMENT '数据库地址',
  `db_user_name` varchar(100) NOT NULL COMMENT '数据库账户',
  `db_password` varchar(100) NOT NULL COMMENT '连接密码',
  `db_type` varchar(10) DEFAULT NULL COMMENT '数据库类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据库链接信息';

/*Data for the table `code_dbinfo` */

/*Table structure for table `coin_introduction` */

DROP TABLE IF EXISTS `coin_introduction`;

CREATE TABLE `coin_introduction` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cointype` int(2) NOT NULL COMMENT '币种',
  `coinname` varchar(63) NOT NULL COMMENT '名称',
  `releasetime` varchar(255) NOT NULL COMMENT '发行时间',
  `releasetotalamt` varchar(255) NOT NULL COMMENT '总量',
  `circulationtotalamt` varchar(255) NOT NULL COMMENT '确认数',
  `crowdprice` varchar(255) NOT NULL COMMENT '发行价',
  `whitepaper` varchar(255) NOT NULL COMMENT '白皮书地址',
  `officialneturl` varchar(255) NOT NULL COMMENT '官网',
  `blockquery` varchar(255) NOT NULL COMMENT '区块链浏览器',
  `introduction` varchar(255) NOT NULL COMMENT '介绍',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `cointypePk` (`cointype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `coin_introduction` */

/*Table structure for table `coin_manage` */

DROP TABLE IF EXISTS `coin_manage`;

CREATE TABLE `coin_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cointype` int(2) NOT NULL COMMENT '币种',
  `seque` int(11) NOT NULL COMMENT '排序',
  `coinname` varchar(63) NOT NULL COMMENT '名称',
  `cnname` varchar(63) NOT NULL COMMENT '中文名',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `imgurl` varchar(255) NOT NULL COMMENT 'logo',
  `c2conoff` int(2) NOT NULL COMMENT '法币交易开关',
  `digonoff` int(2) NOT NULL COMMENT '挖矿开关',
  `spottoc2conoff` int(2) NOT NULL COMMENT '现货转c2c开关',
  `c2ctospotonoff` int(2) NOT NULL COMMENT 'c2c转现货开关',
  `rechspotrate` decimal(8,2) NOT NULL COMMENT '充值到现货手续费',
  `rechspotonoff` int(2) NOT NULL COMMENT '充值到现货开关',
  `withspotonoff` int(2) NOT NULL COMMENT '提现到现货开关',
  `withspotrate` decimal(2,0) NOT NULL COMMENT '提现从现货手续费',
  `withc2conoff` int(2) NOT NULL COMMENT '提现到c2c开关',
  `c2corderdeposit` decimal(16,8) NOT NULL COMMENT 'c2c交易金额',
  `withamountmax` decimal(16,8) NOT NULL COMMENT '最高提现',
  `withamountmin` decimal(16,8) NOT NULL COMMENT '最低提现',
  `withdrawcountmax` int(10) NOT NULL COMMENT '最高提现次数',
  `digtospotonoff` int(2) NOT NULL COMMENT '挖矿提现到现货开关',
  `digwithdrwaonoff` int(2) NOT NULL COMMENT '挖矿提现开关',
  `redpacketmaxamtsingle` decimal(16,8) NOT NULL COMMENT '红包发送金额上限',
  `transfermaxamtday` decimal(16,8) NOT NULL COMMENT '转账每日金额上限',
  `get_address` varchar(255) NOT NULL COMMENT '获取信息地址',
  `list_transactions` varchar(255) NOT NULL COMMENT '交易记录地址',
  `transfer_address` varchar(255) NOT NULL COMMENT '转账地址',
  `spottoyubionoff` int(2) NOT NULL COMMENT '现货到余币宝开关',
  `yubitospotonoff` int(2) NOT NULL COMMENT '余币宝到现货开关',
  `yubitransmin` decimal(16,8) NOT NULL COMMENT '余币宝每日最大小转账金额',
  `yubitransmax` decimal(16,8) NOT NULL COMMENT '余币宝每日最大大转账金额',
  `yubirate` decimal(16,8) NOT NULL COMMENT '余币宝转账费率',
  `yubiprofitminamt` decimal(16,8) NOT NULL COMMENT '余币宝每日收益',
  `autorechargeamt` decimal(16,8) NOT NULL COMMENT '自动转入金额',
  `rechargeinfo` varchar(255) NOT NULL COMMENT '充值信息',
  `withdrawinfo` varchar(255) NOT NULL COMMENT '提现信息',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `cointypePk` (`cointype`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `coin_manage` */

insert  into `coin_manage`(`id`,`cointype`,`seque`,`coinname`,`cnname`,`description`,`imgurl`,`c2conoff`,`digonoff`,`spottoc2conoff`,`c2ctospotonoff`,`rechspotrate`,`rechspotonoff`,`withspotonoff`,`withspotrate`,`withc2conoff`,`c2corderdeposit`,`withamountmax`,`withamountmin`,`withdrawcountmax`,`digtospotonoff`,`digwithdrwaonoff`,`redpacketmaxamtsingle`,`transfermaxamtday`,`get_address`,`list_transactions`,`transfer_address`,`spottoyubionoff`,`yubitospotonoff`,`yubitransmin`,`yubitransmax`,`yubirate`,`yubiprofitminamt`,`autorechargeamt`,`rechargeinfo`,`withdrawinfo`,`createtime`,`updatetime`) values (1,0,1,'USDT','USDT','USDT','',0,0,0,0,0.00,0,0,0,0,0.00000000,0.00000000,0.00000000,0,0,0,0.00000000,0.00000000,'0','0','0',0,0,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,'0','0','2019-09-20 10:20:12','2019-09-20 10:20:12'),(2,8,2,'PGY','PGY','PGY','',0,0,0,0,0.00,0,0,0,0,0.00000000,0.00000000,0.00000000,0,0,0,0.00000000,0.00000000,'0','0','0',0,0,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,'0','0','2019-09-20 10:20:56','2019-09-20 10:20:56');

/*Table structure for table `coin_scale` */

DROP TABLE IF EXISTS `coin_scale`;

CREATE TABLE `coin_scale` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ordercointype` int(2) NOT NULL COMMENT '交易币',
  `unitcointype` int(2) NOT NULL COMMENT '计价币',
  `orderamtpricescale` int(2) NOT NULL COMMENT '交易价格小数位',
  `orderamtamountscale` int(2) NOT NULL COMMENT '交易金额小数位',
  `availofspotunitscale` int(2) NOT NULL COMMENT '可用现货计价小数位',
  `availofspotorderscale` int(2) NOT NULL COMMENT '可用现货交易小数位',
  `marketpriceofcnyscale` int(2) NOT NULL COMMENT '市价人民币小数位',
  `markettradenumscale` int(2) NOT NULL COMMENT '交易额显示小数位',
  `klinepricescale` int(2) NOT NULL COMMENT 'K线价格小数位',
  `calculscale` int(2) NOT NULL COMMENT '计算小数位',
  `availofcnyscale` int(2) NOT NULL COMMENT '可用人民币小数位',
  `yubiscale` int(2) NOT NULL COMMENT '余币宝小数位',
  `c2cpricescale` int(2) NOT NULL COMMENT 'C2C价格小数位',
  `c2cnumscale` int(2) NOT NULL COMMENT 'C2C数量小数位',
  `withdrawScale` int(2) NOT NULL COMMENT '提现小数位',
  `c2ctotalamtscale` int(2) NOT NULL COMMENT 'C2C账户余额小数位',
  `minspottransamt` decimal(16,8) NOT NULL COMMENT '最小转账金额',
  `minspottransnum` decimal(16,8) NOT NULL COMMENT '最小转账数量',
  `minc2ctransamt` decimal(16,8) NOT NULL COMMENT '最小C2C金额',
  `minwithdrawnum` decimal(16,8) NOT NULL COMMENT '最小提现数量',
  `marketbuyminamt` decimal(16,8) NOT NULL COMMENT '市价买金额',
  `marketsaleminnum` decimal(16,8) NOT NULL COMMENT '市价卖数量',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `orderCointypePk` (`ordercointype`),
  KEY `unitCointypePk` (`unitcointype`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `coin_scale` */

insert  into `coin_scale`(`id`,`ordercointype`,`unitcointype`,`orderamtpricescale`,`orderamtamountscale`,`availofspotunitscale`,`availofspotorderscale`,`marketpriceofcnyscale`,`markettradenumscale`,`klinepricescale`,`calculscale`,`availofcnyscale`,`yubiscale`,`c2cpricescale`,`c2cnumscale`,`withdrawScale`,`c2ctotalamtscale`,`minspottransamt`,`minspottransnum`,`minc2ctransamt`,`minwithdrawnum`,`marketbuyminamt`,`marketsaleminnum`,`createtime`,`updatetime`) values (1,8,0,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4.00000000,4.00000000,4.00000000,4.00000000,4.00000000,4.00000000,'2019-09-20 10:21:16','2019-09-20 10:21:16'),(2,8,-1,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4.00000000,4.00000000,4.00000000,4.00000000,4.00000000,4.00000000,'2019-09-20 14:58:00','2019-09-20 14:58:00'),(3,0,-1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2.00000000,2.00000000,2.00000000,2.00000000,2.00000000,2.00000000,'2019-09-20 15:10:16','2019-09-20 15:10:16');

/*Table structure for table `commission_invite` */

DROP TABLE IF EXISTS `commission_invite`;

CREATE TABLE `commission_invite` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `type` int(11) NOT NULL COMMENT '类型',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `referuserid` int(11) NOT NULL COMMENT '推荐人id',
  `referamount` decimal(16,8) NOT NULL COMMENT '返佣金额',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `commission_invite` */

/*Table structure for table `commission_record` */

DROP TABLE IF EXISTS `commission_record`;

CREATE TABLE `commission_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `commamount` decimal(16,8) NOT NULL COMMENT '返佣金额',
  `commcointype` int(11) NOT NULL COMMENT '返佣类型',
  `orderamount` decimal(16,8) NOT NULL COMMENT '交易金额',
  `ordercointype` int(11) NOT NULL COMMENT '交易币种',
  `type` int(11) NOT NULL COMMENT '类型',
  `referenceid` int(11) NOT NULL COMMENT '相关id',
  `orderid` int(11) NOT NULL COMMENT '交易id',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `commission_record` */

/*Table structure for table `deal_dig_config` */

DROP TABLE IF EXISTS `deal_dig_config`;

CREATE TABLE `deal_dig_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ordercointype` int(11) NOT NULL COMMENT '交易币种',
  `onoff` int(2) NOT NULL DEFAULT '0' COMMENT '开关 0关 1开',
  `buyrole` int(11) NOT NULL COMMENT '买权限',
  `salerole` int(11) NOT NULL COMMENT '卖权限',
  `feerate` decimal(16,8) NOT NULL COMMENT '免除手续费',
  `salecashback` decimal(16,8) NOT NULL COMMENT '卖返佣',
  `buycashback` decimal(16,8) NOT NULL COMMENT '买返佣',
  `salerefcashback` decimal(16,8) NOT NULL COMMENT '卖邀请人返佣',
  `buyrefcashback` decimal(16,8) NOT NULL COMMENT '买邀请人返佣',
  `ordertype` int(16) NOT NULL COMMENT '交易类型',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `ordercointypePk` (`ordercointype`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `deal_dig_config` */

insert  into `deal_dig_config`(`id`,`ordercointype`,`onoff`,`buyrole`,`salerole`,`feerate`,`salecashback`,`buycashback`,`salerefcashback`,`buyrefcashback`,`ordertype`,`createtime`,`updatetime`) values (1,8,1,2,2,0.00100000,0.00100000,0.00100000,0.00100000,0.00100000,0,'2019-09-20 16:04:16','2019-09-20 16:04:16'),(2,8,1,2,2,0.00100000,0.00100000,0.00100000,0.00100000,0.00100000,1,'2019-09-20 16:04:26','2019-09-20 16:04:26');

/*Table structure for table `deal_dig_record` */

DROP TABLE IF EXISTS `deal_dig_record`;

CREATE TABLE `deal_dig_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `orderrecordid` int(11) NOT NULL COMMENT '交易记录id',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `opertype` varchar(50) NOT NULL COMMENT '操作类型',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `deal_dig_record` */

/*Table structure for table `dig_record` */

DROP TABLE IF EXISTS `dig_record`;

CREATE TABLE `dig_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `state` int(11) NOT NULL COMMENT '状态',
  `inactivetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dig_record` */

/*Table structure for table `digcal_record` */

DROP TABLE IF EXISTS `digcal_record`;

CREATE TABLE `digcal_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `digcalcul` int(11) NOT NULL COMMENT '算力',
  `allcalculforce` int(11) NOT NULL COMMENT '算力',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `type` int(11) NOT NULL COMMENT '类型',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `digcal_record` */

/*Table structure for table `file_manager` */

DROP TABLE IF EXISTS `file_manager`;

CREATE TABLE `file_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '文件类型',
  `address` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '访问链接',
  `reserve1` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '名称',
  `reserve2` int(11) DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `file_manager` */

/*Table structure for table `flow` */

DROP TABLE IF EXISTS `flow`;

CREATE TABLE `flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `opertype` varchar(255) NOT NULL COMMENT '操作类型',
  `relateid` int(11) NOT NULL COMMENT '关联id',
  `accounttype` int(11) NOT NULL COMMENT '账户类型',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `operid` int(11) NOT NULL COMMENT '操作人id',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `result_amount` varchar(255) DEFAULT NULL COMMENT '操作后金额',
  `accamount` decimal(16,8) NOT NULL COMMENT '操作金额',
  `time` varchar(255) DEFAULT NULL COMMENT '时间',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `flow` */

/*Table structure for table `idcard_validate` */

DROP TABLE IF EXISTS `idcard_validate`;

CREATE TABLE `idcard_validate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `identificationnumber` varchar(255) NOT NULL COMMENT '身份证号',
  `idcardtype` varchar(255) NOT NULL COMMENT '身份证类型',
  `idcardexpiry` varchar(255) NOT NULL COMMENT '有效期',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `sex` varchar(20) NOT NULL COMMENT '性别',
  `idcardfrontpic` varchar(255) NOT NULL COMMENT '正面照片',
  `idcardbackpic` varchar(255) NOT NULL COMMENT '反面照片',
  `facepic` varchar(20) NOT NULL COMMENT '脸部照片',
  `state` int(2) NOT NULL COMMENT '状态',
  `taskid` varchar(255) NOT NULL COMMENT 'taskid',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `idcard_validate` */

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标题',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '类型',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `news` */

insert  into `news`(`id`,`title`,`content`,`type`,`createtime`,`updatetime`) values (3,'公告','111',0,'2019-09-20 17:01:50','2019-09-20 17:01:50'),(4,'关于','&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;NOTICE_ABOUT&lt;/span&gt;&lt;/pre&gt;',1,'2019-09-20 17:02:10','2019-09-20 17:02:10'),(5,'帮助','&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;NOTICE_HELP&lt;/span&gt;&lt;/pre&gt;',2,'2019-09-20 17:02:21','2019-09-20 17:02:21'),(6,'费率','&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;NOTICE_RATE&lt;/span&gt;&lt;/pre&gt;',3,'2019-09-20 17:02:33','2019-09-20 17:02:33'),(7,'算法','&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;NOTICE_ALGORITHM&lt;/span&gt;&lt;/pre&gt;',4,'2019-09-20 17:03:05','2019-09-20 17:03:05'),(8,'注册协议','&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;\n&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;NOTICE_REGISTER_AGREEMENT&lt;/span&gt;&lt;/pre&gt;\n&lt;/span&gt;&lt;/pre&gt;',5,'2019-09-20 17:03:17','2019-09-20 17:03:17'),(9,'买币指南','&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;\n&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;\n&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;NOTICE_BUY_USING&lt;/span&gt;&lt;/pre&gt;\n&lt;/span&gt;&lt;/pre&gt;\n&lt;/span&gt;&lt;/pre&gt;',6,'2019-09-20 17:03:27','2019-09-20 17:03:27'),(10,'玩转资金账户','&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;\n&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;\n&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;\n&lt;pre&gt;&lt;span style=\"color:#9876aa;font-style:italic;\"&gt;NOTICE_ACCOUNT_USING&lt;/span&gt;&lt;/pre&gt;\n&lt;/span&gt;&lt;/pre&gt;\n&lt;/span&gt;&lt;/pre&gt;\n&lt;/span&gt;&lt;/pre&gt;',7,'2019-09-20 17:03:35','2019-09-20 17:03:35');

/*Table structure for table `order_maker` */

DROP TABLE IF EXISTS `order_maker`;

CREATE TABLE `order_maker` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `type` int(11) NOT NULL COMMENT '类型',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `paytype` int(11) NOT NULL COMMENT '支付方式',
  `price` decimal(16,8) NOT NULL COMMENT '价格',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `totalmin` decimal(16,8) NOT NULL COMMENT '总量小',
  `totalmax` decimal(16,8) NOT NULL COMMENT '总量大',
  `remain` decimal(16,8) NOT NULL COMMENT '剩余',
  `frozen` decimal(16,8) NOT NULL COMMENT '冻结',
  `deposit` decimal(16,8) NOT NULL COMMENT '定金',
  `orderflag` tinyint(4) NOT NULL COMMENT '交易状态',
  `state` int(2) NOT NULL COMMENT '状态',
  `ordernum` varchar(55) NOT NULL COMMENT '数量',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`),
  KEY `cointypePk` (`cointype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_maker` */

/*Table structure for table `order_manage` */

DROP TABLE IF EXISTS `order_manage`;

CREATE TABLE `order_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ordercointype` int(11) NOT NULL DEFAULT '1' COMMENT '交易币',
  `unitcointype` int(11) NOT NULL DEFAULT '1' COMMENT '计价币',
  `onoff` int(11) NOT NULL DEFAULT '0' COMMENT '开关',
  `performrate` decimal(16,8) NOT NULL COMMENT '现价卖家利率',
  `marketpPerformRate` decimal(16,8) NOT NULL COMMENT '市价卖家利率',
  `referrate` decimal(16,8) NOT NULL COMMENT '现价买家利率',
  `marketReferRate` decimal(16,8) NOT NULL COMMENT '市价买家利率',
  `marketseque` int(11) NOT NULL DEFAULT '1' COMMENT '顺序',
  `okcoinflag` int(11) DEFAULT NULL COMMENT 'ok是否有',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_manage` */

/*Table structure for table `order_spot` */

DROP TABLE IF EXISTS `order_spot`;

CREATE TABLE `order_spot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `ordercointype` int(11) NOT NULL COMMENT '交易币',
  `unitcointype` int(11) NOT NULL COMMENT '计价币',
  `type` int(11) NOT NULL COMMENT '类型',
  `ordertype` int(11) NOT NULL COMMENT '交易类型',
  `price` decimal(16,8) NOT NULL COMMENT '价格',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `remain` decimal(16,8) NOT NULL COMMENT '剩余',
  `state` int(11) NOT NULL COMMENT '状态',
  `levflag` int(11) NOT NULL COMMENT '是否杠杆',
  `total` decimal(16,8) NOT NULL COMMENT '总额',
  `average` decimal(16,8) NOT NULL COMMENT '平均',
  `ordernum` varchar(50) NOT NULL COMMENT '订单数量',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_spot` */

/*Table structure for table `order_spot_record` */

DROP TABLE IF EXISTS `order_spot_record`;

CREATE TABLE `order_spot_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `buyid` int(11) NOT NULL COMMENT '买订单id',
  `saleid` int(11) NOT NULL COMMENT '卖订单id',
  `buyuserid` int(11) NOT NULL COMMENT '买家id',
  `saleuserid` int(11) NOT NULL COMMENT '卖家id',
  `ordercointype` int(11) NOT NULL COMMENT '交易币',
  `unitcointype` int(11) NOT NULL COMMENT '计价币',
  `price` decimal(16,8) NOT NULL COMMENT '价格',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `total` decimal(16,8) NOT NULL COMMENT '总额',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_spot_record` */

/*Table structure for table `order_taker` */

DROP TABLE IF EXISTS `order_taker`;

CREATE TABLE `order_taker` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `type` int(11) NOT NULL COMMENT '类型',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `makeruserid` int(11) NOT NULL COMMENT 'makerId',
  `makerid` int(11) NOT NULL COMMENT '订单id',
  `price` decimal(16,8) NOT NULL COMMENT '价格',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `total` decimal(16,8) NOT NULL COMMENT '总额',
  `payid` int(11) DEFAULT NULL COMMENT '支付id',
  `ordernum` varchar(50) NOT NULL COMMENT '订单数量',
  `flagnum` varchar(50) NOT NULL COMMENT '数量标志',
  `state` int(255) NOT NULL COMMENT '状态',
  `inactivetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  `remark` varchar(11) NOT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_taker` */

/*Table structure for table `poster` */

DROP TABLE IF EXISTS `poster`;

CREATE TABLE `poster` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `imgpath` varchar(255) NOT NULL COMMENT '图片地址',
  `maintitle` varchar(255) NOT NULL COMMENT '主标题',
  `subtitle` varchar(255) NOT NULL COMMENT '副标题',
  `share_url` varchar(255) NOT NULL COMMENT '分享地址',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `poster` */

/*Table structure for table `recharge` */

DROP TABLE IF EXISTS `recharge`;

CREATE TABLE `recharge` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `address` varchar(255) NOT NULL COMMENT '充值地址',
  `amount` decimal(16,8) NOT NULL COMMENT '数量',
  `fee` decimal(16,8) NOT NULL COMMENT '手续费',
  `remain` decimal(16,8) NOT NULL COMMENT '剩余',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `ordernum` varchar(255) NOT NULL COMMENT '交易数量',
  `state` int(11) NOT NULL COMMENT '状态',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '感谢时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `recharge` */

/*Table structure for table `robot` */

DROP TABLE IF EXISTS `robot`;

CREATE TABLE `robot` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `onoff` int(2) NOT NULL COMMENT '开关',
  `operid` int(11) NOT NULL COMMENT '操作人id',
  `state` int(2) NOT NULL COMMENT '状态0删除了 1使用',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `robot` */

/*Table structure for table `robot_statistics` */

DROP TABLE IF EXISTS `robot_statistics`;

CREATE TABLE `robot_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `robotandrobotdealnum` decimal(16,8) NOT NULL COMMENT '机器人对机器人数量',
  `robotandrobotdealamt` decimal(16,8) NOT NULL COMMENT '机器人对机器人金额',
  `robotandpersondealnum` decimal(16,8) NOT NULL COMMENT '机器人对人数量',
  `robotandpersondealamt` decimal(16,8) NOT NULL COMMENT '机器人对人金额',
  `personandpersondealnum` decimal(16,8) NOT NULL COMMENT '人对机器人数量',
  `personandpersondealamt` decimal(16,8) NOT NULL COMMENT '人对机器人金额',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `robot_statistics` */

/*Table structure for table `robot_task` */

DROP TABLE IF EXISTS `robot_task`;

CREATE TABLE `robot_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `robotid` int(11) NOT NULL COMMENT '机器人id',
  `type` int(11) NOT NULL COMMENT '类型0买入挂单 1卖出挂单 2买入成交 3卖出成交 4买入撤销 5卖出撤销',
  `baseprice` decimal(16,8) NOT NULL COMMENT '基础价格',
  `priceradiomax` double(9,5) NOT NULL COMMENT '价格浮动区间上限',
  `priceradiomin` double(9,5) NOT NULL COMMENT '价格浮动区间下限',
  `numbermax` decimal(16,8) NOT NULL COMMENT '数量最大值',
  `numbermin` decimal(16,8) NOT NULL COMMENT '数量最小值',
  `starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `endtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `timeinterval` int(11) NOT NULL COMMENT '时间间隔（秒）',
  `countmax` int(11) NOT NULL COMMENT '交易次数最大值',
  `countmin` int(11) NOT NULL COMMENT '交易次数最小值',
  `excuteuserid` int(11) NOT NULL COMMENT 'excuteuserid',
  `onoff` int(2) NOT NULL COMMENT '开关',
  `operid` int(11) NOT NULL COMMENT '操作人id',
  `jobname` varchar(50) NOT NULL COMMENT '任务名称',
  `jobgroupname` varchar(50) NOT NULL COMMENT '小组名称',
  `triggername` varchar(255) NOT NULL COMMENT '触发器名称',
  `triggergroupname` varchar(255) NOT NULL COMMENT '触发器组名',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `robot_task` */

/*Table structure for table `sms_record` */

DROP TABLE IF EXISTS `sms_record`;

CREATE TABLE `sms_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `phone` varchar(15) NOT NULL COMMENT '手机号',
  `type` int(2) NOT NULL COMMENT '类型',
  `state` int(2) NOT NULL COMMENT '状态',
  `code` varchar(11) NOT NULL COMMENT '验证码',
  `times` int(11) NOT NULL COMMENT '时间',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sms_record` */

/*Table structure for table `startup_param` */

DROP TABLE IF EXISTS `startup_param`;

CREATE TABLE `startup_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `key_name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '参数名称',
  `key_val` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '功能值',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `startup_param` */

/*Table structure for table `statistics` */

DROP TABLE IF EXISTS `statistics`;

CREATE TABLE `statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coin_type` int(2) NOT NULL COMMENT '币种',
  `day_total_deal_dig` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '每天后台挖矿的总量',
  `day_total_refer_deal_dig` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '每天推荐人挖矿总量',
  `day_total_person_deal_dig` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '每天个人挖矿总量',
  `total_deal_dig` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '总挖矿量',
  `regiest_dig` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '注册挖矿量',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `statistics` */

/*Table structure for table `sys_dept` */

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='部门表';

/*Data for the table `sys_dept` */

insert  into `sys_dept`(`id`,`num`,`pid`,`pids`,`simplename`,`fullname`,`tips`,`version`) values (25,2,24,'[0],[24],','开发部','开发部','',NULL),(26,3,24,'[0],[24],','运营部','运营部','',NULL),(27,4,24,'[0],[24],','战略部','战略部','',NULL),(28,5,1,NULL,'测试','测试fullname','测试tips',1),(29,5,1,NULL,'测试','测试fullname','测试tips',1),(30,5,1,NULL,'测试','测试fullname','测试tips',1);

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `code` varchar(255) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典表';

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`num`,`pid`,`name`,`tips`,`code`) values (50,0,0,'性别',NULL,'sys_sex'),(51,1,50,'男',NULL,'1'),(52,2,50,'女',NULL,'2'),(53,0,0,'状态',NULL,'sys_state'),(54,1,53,'启用',NULL,'1'),(55,2,53,'禁用',NULL,'2'),(56,0,0,'账号状态',NULL,'account_state'),(57,1,56,'启用',NULL,'1'),(58,2,56,'冻结',NULL,'2'),(59,3,56,'已删除',NULL,'3'),(60,0,0,'字典测试','这是一个字典测试','test'),(61,1,60,'测试1',NULL,'1'),(62,2,60,'测试2',NULL,'2'),(63,0,0,'测试','备注','tes'),(64,1,63,'测试1',NULL,'1'),(65,2,63,'测试2',NULL,'2'),(66,0,0,'会员角色','','user_role'),(67,1,66,'合伙人',NULL,'1'),(68,2,66,'普通用户',NULL,'2'),(69,0,0,'交易类型','','order_type'),(70,0,69,'市价交易',NULL,'0'),(71,1,69,'限价交易',NULL,'1'),(72,0,0,'功能开关','','onoff'),(73,0,72,'关闭',NULL,'0'),(74,1,72,'开启',NULL,'1'),(78,0,0,'是否接单','','orderflag'),(79,0,78,'不接单',NULL,'0'),(80,1,78,'接单',NULL,'1'),(81,0,0,'订单状态','','orderState'),(82,0,81,'未成交',NULL,'0'),(83,1,81,'已成交',NULL,'1'),(84,2,81,'已撤销',NULL,'2'),(85,0,0,'订单类型','','orderType'),(86,0,85,'买入',NULL,'0'),(87,1,85,'卖出',NULL,'1'),(88,0,0,'支付方式','','paytype'),(89,0,88,'支付宝',NULL,'0'),(90,1,88,'微信',NULL,'1'),(91,2,88,'银行卡',NULL,'2'),(92,0,0,'普通用户订单状态','','orderStateUser'),(93,0,92,'待付款',NULL,'0'),(94,1,92,'待确认',NULL,'1'),(95,2,92,'冻结',NULL,'2'),(96,3,92,'已完成',NULL,'3'),(97,4,92,'已取消',NULL,'4'),(98,5,92,'超时取消',NULL,'5'),(102,0,0,'币币订单状态','','CoinDealState'),(103,0,102,'未成交',NULL,'0'),(104,1,102,'已成交',NULL,'1'),(105,2,102,'交易撤销',NULL,'2'),(106,3,102,'交易失败',NULL,'3'),(114,0,0,'邀请奖励类型','','commissionType'),(115,0,114,'登录奖励',NULL,'0'),(116,1,114,'实名奖励',NULL,'1'),(117,0,0,'邀请记录类型','','commissionRecordType'),(118,0,117,'平台手续费',NULL,'0'),(119,1,117,'推荐人手续费',NULL,'1'),(125,0,0,'用户绑定信息类型','','bindInfoType'),(126,1,125,'有效',NULL,'1'),(127,-1,125,'未绑定支付宝',NULL,'-1'),(128,-2,125,'未绑定微信',NULL,'-2'),(129,-3,125,'未绑定银行卡',NULL,'-3'),(130,0,0,'用户绑定信息状态','','bindInfoState'),(131,0,130,'未激活',NULL,'0'),(132,1,130,'已激活',NULL,'1'),(133,0,0,'充值订单状态','','rechargeState'),(134,0,133,'未支付',NULL,'0'),(135,1,133,'已支付',NULL,'1'),(136,2,133,'已撤销',NULL,'2'),(137,3,133,'已失败',NULL,'3'),(138,0,0,'提现类型','','withdrawType'),(139,0,138,'普通提现',NULL,'0'),(140,1,138,'提现到现货账户',NULL,'1'),(141,0,0,'提现账户类型','','WithdrawAccountType'),(142,0,141,'C2C',NULL,'0'),(143,1,141,'现货',NULL,'1'),(144,2,141,'挖矿',NULL,'2'),(145,3,141,'杠杆',NULL,'3'),(146,4,141,'余币宝',NULL,'4'),(150,0,0,'会员状态','','userState'),(151,1,150,'正常',NULL,'1'),(152,2,150,'冻结',NULL,'2'),(153,3,150,'注销',NULL,'3'),(154,0,0,'用户权限','','userRole'),(155,0,154,'普通',NULL,'0'),(156,0,0,'实名认证状态','','IdcardVailstate'),(157,-1,156,'认证中',NULL,'-1'),(158,0,156,'未认证',NULL,'0'),(159,1,156,'认证通过',NULL,'1'),(160,2,156,'认证不通过',NULL,'2'),(161,3,156,'年龄不合法',NULL,'3'),(162,4,156,'身份证号已存在',NULL,'4'),(167,0,0,'机器人交易类型','','robot_task_type'),(168,0,167,'买入挂单',NULL,'0'),(169,1,167,'卖出挂单',NULL,'1'),(170,2,167,'买入成交',NULL,'2'),(171,3,167,'卖出成交',NULL,'3'),(172,4,167,'买入撤销',NULL,'4'),(173,5,167,'卖出撤销',NULL,'5'),(174,0,0,'币种','','coinType'),(175,0,174,'ECN',NULL,'0'),(176,1,174,'ODIN',NULL,'1'),(177,2,174,'YT',NULL,'2'),(178,8,174,'PGY',NULL,'8'),(179,0,0,'公告类型','','news_type'),(180,0,179,'公告',NULL,'0'),(181,1,179,'关于',NULL,'1'),(182,2,179,'帮助',NULL,'2'),(183,3,179,'费率',NULL,'3'),(184,4,179,'算法',NULL,'4'),(185,5,179,'注册协议',NULL,'5'),(186,6,179,'买币指南',NULL,'6'),(187,7,179,'玩转资金账户',NULL,'7');

/*Table structure for table `sys_expense` */

DROP TABLE IF EXISTS `sys_expense`;

CREATE TABLE `sys_expense` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` decimal(20,2) DEFAULT NULL COMMENT '报销金额',
  `desc` varchar(255) DEFAULT '' COMMENT '描述',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '状态: 1.待提交  2:待审核   3.审核通过 4:驳回',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `processId` varchar(255) DEFAULT NULL COMMENT '流程定义id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报销表';

/*Data for the table `sys_expense` */

/*Table structure for table `sys_login_log` */

DROP TABLE IF EXISTS `sys_login_log`;

CREATE TABLE `sys_login_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '管理员id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='登录记录';

/*Data for the table `sys_login_log` */

insert  into `sys_login_log`(`id`,`logname`,`userid`,`createtime`,`succeed`,`message`,`ip`) values (1,'登录日志',1,'2019-09-11 17:30:39','成功',NULL,'0:0:0:0:0:0:0:1'),(2,'登录日志',1,'2019-09-19 15:38:48','成功',NULL,'0:0:0:0:0:0:0:1'),(3,'登录日志',1,'2019-09-27 10:57:19','成功',NULL,'0:0:0:0:0:0:0:1'),(4,'登录日志',1,'2019-10-06 11:38:07','成功',NULL,'0:0:0:0:0:0:0:1');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1153943788219473984 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`code`,`pcode`,`pcodes`,`name`,`icon`,`url`,`num`,`levels`,`ismenu`,`tips`,`status`,`isopen`) values (105,'system','0','[0],','系统管理','fa-user','#',4,1,1,NULL,1,1),(106,'mgr','system','[0],[system],','用户管理','','/mgr',1,2,1,NULL,1,0),(107,'mgr_add','mgr','[0],[system],[mgr],','添加用户',NULL,'/mgr/add',1,3,0,NULL,1,0),(108,'mgr_edit','mgr','[0],[system],[mgr],','修改用户',NULL,'/mgr/edit',2,3,0,NULL,1,0),(109,'mgr_delete','mgr','[0],[system],[mgr],','删除用户',NULL,'/mgr/delete',3,3,0,NULL,1,0),(110,'mgr_reset','mgr','[0],[system],[mgr],','重置密码',NULL,'/mgr/reset',4,3,0,NULL,1,0),(111,'mgr_freeze','mgr','[0],[system],[mgr],','冻结用户',NULL,'/mgr/freeze',5,3,0,NULL,1,0),(112,'mgr_unfreeze','mgr','[0],[system],[mgr],','解除冻结用户',NULL,'/mgr/unfreeze',6,3,0,NULL,1,0),(113,'mgr_setRole','mgr','[0],[system],[mgr],','分配角色',NULL,'/mgr/setRole',7,3,0,NULL,1,0),(114,'role','system','[0],[system],','角色管理',NULL,'/role',2,2,1,NULL,1,0),(115,'role_add','role','[0],[system],[role],','添加角色',NULL,'/role/add',1,3,0,NULL,1,0),(116,'role_edit','role','[0],[system],[role],','修改角色',NULL,'/role/edit',2,3,0,NULL,1,0),(117,'role_remove','role','[0],[system],[role],','删除角色',NULL,'/role/remove',3,3,0,NULL,1,0),(118,'role_setAuthority','role','[0],[system],[role],','配置权限',NULL,'/role/setAuthority',4,3,0,NULL,1,0),(119,'menu','system','[0],[system],','菜单管理',NULL,'/menu',4,2,1,NULL,1,0),(120,'menu_add','menu','[0],[system],[menu],','添加菜单',NULL,'/menu/add',1,3,0,NULL,1,0),(121,'menu_edit','menu','[0],[system],[menu],','修改菜单',NULL,'/menu/edit',2,3,0,NULL,1,0),(122,'menu_remove','menu','[0],[system],[menu],','删除菜单',NULL,'/menu/remove',3,3,0,NULL,1,0),(128,'log','system','[0],[system],','业务日志',NULL,'/log',6,2,1,NULL,1,0),(130,'druid','system','[0],[system],','监控管理',NULL,'/druid',7,2,1,NULL,1,NULL),(131,'dept','system','[0],[system],','部门管理',NULL,'/dept',3,2,1,NULL,1,NULL),(132,'dict','system','[0],[system],','字典管理',NULL,'/dict',4,2,1,NULL,1,NULL),(133,'loginLog','system','[0],[system],','登录日志',NULL,'/loginLog',6,2,1,NULL,1,NULL),(134,'log_clean','log','[0],[system],[log],','清空日志',NULL,'/log/delLog',3,3,0,NULL,1,NULL),(135,'dept_add','dept','[0],[system],[dept],','添加部门',NULL,'/dept/add',1,3,0,NULL,1,NULL),(136,'dept_update','dept','[0],[system],[dept],','修改部门',NULL,'/dept/update',1,3,0,NULL,1,NULL),(137,'dept_delete','dept','[0],[system],[dept],','删除部门',NULL,'/dept/delete',1,3,0,NULL,1,NULL),(138,'dict_add','dict','[0],[system],[dict],','添加字典',NULL,'/dict/add',1,3,0,NULL,1,NULL),(139,'dict_update','dict','[0],[system],[dict],','修改字典',NULL,'/dict/update',1,3,0,NULL,1,NULL),(140,'dict_delete','dict','[0],[system],[dict],','删除字典',NULL,'/dict/delete',1,3,0,NULL,1,NULL),(141,'notice','system','[0],[system],','通知管理',NULL,'/notice',9,2,1,NULL,1,NULL),(142,'notice_add','notice','[0],[system],[notice],','添加通知',NULL,'/notice/add',1,3,0,NULL,1,NULL),(143,'notice_update','notice','[0],[system],[notice],','修改通知',NULL,'/notice/update',2,3,0,NULL,1,NULL),(144,'notice_delete','notice','[0],[system],[notice],','删除通知',NULL,'/notice/delete',3,3,0,NULL,1,NULL),(145,'hello','0','[0],','通知','fa-rocket','/notice/hello',1,1,1,NULL,1,NULL),(148,'code','0','[0],','代码生成','fa-code','/code',3,1,1,NULL,1,NULL),(150,'to_menu_edit','menu','[0],[system],[menu],','菜单编辑跳转','','/menu/menu_edit',4,3,0,NULL,1,NULL),(151,'menu_list','menu','[0],[system],[menu],','菜单列表','','/menu/list',5,3,0,NULL,1,NULL),(152,'to_dept_update','dept','[0],[system],[dept],','修改部门跳转','','/dept/dept_update',4,3,0,NULL,1,NULL),(153,'dept_list','dept','[0],[system],[dept],','部门列表','','/dept/list',5,3,0,NULL,1,NULL),(154,'dept_detail','dept','[0],[system],[dept],','部门详情','','/dept/detail',6,3,0,NULL,1,NULL),(155,'to_dict_edit','dict','[0],[system],[dict],','修改菜单跳转','','/dict/dict_edit',4,3,0,NULL,1,NULL),(156,'dict_list','dict','[0],[system],[dict],','字典列表','','/dict/list',5,3,0,NULL,1,NULL),(157,'dict_detail','dict','[0],[system],[dict],','字典详情','','/dict/detail',6,3,0,NULL,1,NULL),(158,'log_list','log','[0],[system],[log],','日志列表','','/log/list',2,3,0,NULL,1,NULL),(159,'log_detail','log','[0],[system],[log],','日志详情','','/log/detail',3,3,0,NULL,1,NULL),(160,'del_login_log','loginLog','[0],[system],[loginLog],','清空登录日志','','/loginLog/delLoginLog',1,3,0,NULL,1,NULL),(161,'login_log_list','loginLog','[0],[system],[loginLog],','登录日志列表','','/loginLog/list',2,3,0,NULL,1,NULL),(162,'to_role_edit','role','[0],[system],[role],','修改角色跳转','','/role/role_edit',5,3,0,NULL,1,NULL),(163,'to_role_assign','role','[0],[system],[role],','角色分配跳转','','/role/role_assign',6,3,0,NULL,1,NULL),(164,'role_list','role','[0],[system],[role],','角色列表','','/role/list',7,3,0,NULL,1,NULL),(165,'to_assign_role','mgr','[0],[system],[mgr],','分配角色跳转','','/mgr/role_assign',8,3,0,NULL,1,NULL),(166,'to_user_edit','mgr','[0],[system],[mgr],','编辑用户跳转','','/mgr/user_edit',9,3,0,NULL,1,NULL),(167,'mgr_list','mgr','[0],[system],[mgr],','用户列表','','/mgr/list',10,3,0,NULL,1,NULL),(168,'systemManager','0','[0],','系统配置','fa-cog','/',5,1,1,NULL,1,NULL),(169,'orderManager','0','[0],','交易管理','fa-money','/',6,1,1,NULL,1,NULL),(171,'userManager','0','[0],','会员管理','fa-user','/',8,1,1,NULL,1,NULL),(172,'accountManager','0','[0],','账户管理','fa-cny','/',9,1,1,NULL,1,NULL),(173,'coinManager','0','[0],','币种管理','fa-btc','/',10,1,1,NULL,1,NULL),(174,'aboutInfo','systemManager','[0],[systemManager],','关于页面信息','','/aboutInfo',99,2,1,NULL,1,0),(175,'aboutInfo_list','aboutInfo','[0],[systemManager],[aboutInfo],','关于页面信息列表','','/aboutInfo/list',99,3,0,NULL,1,0),(176,'aboutInfo_add','aboutInfo','[0],[systemManager],[aboutInfo],','关于页面信息添加','','/aboutInfo/add',99,3,0,NULL,1,0),(177,'aboutInfo_update','aboutInfo','[0],[systemManager],[aboutInfo],','关于页面信息更新','','/aboutInfo/update',99,3,0,NULL,1,0),(178,'aboutInfo_delete','aboutInfo','[0],[systemManager],[aboutInfo],','关于页面信息删除','','/aboutInfo/delete',99,3,0,NULL,1,0),(179,'aboutInfo_detail','aboutInfo','[0],[systemManager],[aboutInfo],','关于页面信息详情','','/aboutInfo/detail',99,3,0,NULL,1,0),(186,'account','accountManager','[0],[accountManager],','账户信息管理','','/account',1,2,1,NULL,1,0),(187,'account_list','account','[0],[accountManager],[account],','账户信息管理列表','','/account/list',99,3,0,NULL,1,0),(188,'account_add','account','[0],[accountManager],[account],','账户信息管理添加','','/account/add',99,3,0,NULL,1,0),(189,'account_update','account','[0],[accountManager],[account],','账户信息管理更新','','/account/update',99,3,0,NULL,1,0),(190,'account_delete','account','[0],[accountManager],[account],','账户信息管理删除','','/account/delete',99,3,0,NULL,1,0),(191,'account_detail','account','[0],[accountManager],[account],','账户信息管理详情','','/account/detail',99,3,0,NULL,1,0),(192,'accountTransfer','accountManager','[0],[accountManager],','转账记录','','/accountTransfer',99,2,1,NULL,1,0),(193,'accountTransfer_list','accountTransfer','[0],[accountManager],[accountTransfer],','转账记录列表','','/accountTransfer/list',99,3,0,NULL,1,0),(194,'accountTransfer_add','accountTransfer','[0],[accountManager],[accountTransfer],','转账记录添加','','/accountTransfer/add',99,3,0,NULL,1,0),(195,'accountTransfer_update','accountTransfer','[0],[accountManager],[accountTransfer],','转账记录更新','','/accountTransfer/update',99,3,0,NULL,1,0),(196,'accountTransfer_delete','accountTransfer','[0],[accountManager],[accountTransfer],','转账记录删除','','/accountTransfer/delete',99,3,0,NULL,1,0),(197,'accountTransfer_detail','accountTransfer','[0],[accountManager],[accountTransfer],','转账记录详情','','/accountTransfer/detail',99,3,0,NULL,1,0),(198,'appVersion','systemManager','[0],[systemManager],','版本控制','','/appVersion',99,2,1,NULL,1,0),(199,'appVersion_list','appVersion','[0],[systemManager],[appVersion],','版本控制列表','','/appVersion/list',99,3,0,NULL,1,0),(200,'appVersion_add','appVersion','[0],[systemManager],[appVersion],','版本控制添加','','/appVersion/add',99,3,0,NULL,1,0),(201,'appVersion_update','appVersion','[0],[systemManager],[appVersion],','版本控制更新','','/appVersion/update',99,3,0,NULL,1,0),(202,'appVersion_delete','appVersion','[0],[systemManager],[appVersion],','版本控制删除','','/appVersion/delete',99,3,0,NULL,1,0),(203,'appVersion_detail','appVersion','[0],[systemManager],[appVersion],','版本控制详情','','/appVersion/detail',99,3,0,NULL,1,0),(204,'bank','systemManager','[0],[systemManager],','银行信息配置','','/bank',99,2,1,NULL,1,0),(205,'bank_list','bank','[0],[systemManager],[bank],','银行信息配置列表','','/bank/list',99,3,0,NULL,1,0),(206,'bank_add','bank','[0],[systemManager],[bank],','银行信息配置添加','','/bank/add',99,3,0,NULL,1,0),(207,'bank_update','bank','[0],[systemManager],[bank],','银行信息配置更新','','/bank/update',99,3,0,NULL,1,0),(208,'bank_delete','bank','[0],[systemManager],[bank],','银行信息配置删除','','/bank/delete',99,3,0,NULL,1,0),(209,'bank_detail','bank','[0],[systemManager],[bank],','银行信息配置详情','','/bank/detail',99,3,0,NULL,1,0),(210,'banner','systemManager','[0],[systemManager],','Banner管理','','/banner',99,2,1,NULL,1,0),(211,'banner_list','banner','[0],[systemManager],[banner],','Banner管理列表','','/banner/list',99,3,0,NULL,1,0),(212,'banner_add','banner','[0],[systemManager],[banner],','Banner管理添加','','/banner/add',99,3,0,NULL,1,0),(213,'banner_update','banner','[0],[systemManager],[banner],','Banner管理更新','','/banner/update',99,3,0,NULL,1,0),(214,'banner_delete','banner','[0],[systemManager],[banner],','Banner管理删除','','/banner/delete',99,3,0,NULL,1,0),(215,'banner_detail','banner','[0],[systemManager],[banner],','Banner管理详情','','/banner/detail',99,3,0,NULL,1,0),(216,'bindInfo','userManager','[0],[userManager],','绑定信息','','/bindInfo',99,2,1,NULL,1,0),(217,'bindInfo_list','bindInfo','[0],[userManager],[bindInfo],','绑定信息列表','','/bindInfo/list',99,3,0,NULL,1,0),(218,'bindInfo_add','bindInfo','[0],[userManager],[bindInfo],','绑定信息添加','','/bindInfo/add',99,3,0,NULL,1,0),(219,'bindInfo_update','bindInfo','[0],[userManager],[bindInfo],','绑定信息更新','','/bindInfo/update',99,3,0,NULL,1,0),(220,'bindInfo_delete','bindInfo','[0],[userManager],[bindInfo],','绑定信息删除','','/bindInfo/delete',99,3,0,NULL,1,0),(221,'bindInfo_detail','bindInfo','[0],[userManager],[bindInfo],','绑定信息详情','','/bindInfo/detail',99,3,0,NULL,1,0),(222,'coinIntroduction','coinManager','[0],[coinManager],','币种静态信息','','/coinIntroduction',2,2,1,NULL,1,0),(223,'coinIntroduction_list','coinIntroduction','[0],[coinManager],[coinIntroduction],','币种静态信息列表','','/coinIntroduction/list',99,3,0,NULL,1,0),(224,'coinIntroduction_add','coinIntroduction','[0],[coinManager],[coinIntroduction],','币种静态信息添加','','/coinIntroduction/add',99,3,0,NULL,1,0),(225,'coinIntroduction_update','coinIntroduction','[0],[coinManager],[coinIntroduction],','币种静态信息更新','','/coinIntroduction/update',99,3,0,NULL,1,0),(226,'coinIntroduction_delete','coinIntroduction','[0],[coinManager],[coinIntroduction],','币种静态信息删除','','/coinIntroduction/delete',99,3,0,NULL,1,0),(227,'coinIntroduction_detail','coinIntroduction','[0],[coinManager],[coinIntroduction],','币种静态信息详情','','/coinIntroduction/detail',99,3,0,NULL,1,0),(228,'coinManage','coinManager','[0],[coinManager],','币种管理','','/coinManage',1,2,1,NULL,1,0),(229,'coinManage_list','coinManage','[0],[coinManager],[coinManage],','币种管理列表','','/coinManage/list',99,3,0,NULL,1,0),(230,'coinManage_add','coinManage','[0],[coinManager],[coinManage],','币种管理添加','','/coinManage/add',99,3,0,NULL,1,0),(231,'coinManage_update','coinManage','[0],[coinManager],[coinManage],','币种管理更新','','/coinManage/update',99,3,0,NULL,1,0),(232,'coinManage_delete','coinManage','[0],[coinManager],[coinManage],','币种管理删除','','/coinManage/delete',99,3,0,NULL,1,0),(233,'coinManage_detail','coinManage','[0],[coinManager],[coinManage],','币种管理详情','','/coinManage/detail',99,3,0,NULL,1,0),(234,'coinScale','coinManager','[0],[coinManager],','币种小数位数管理','','/coinScale',99,2,1,NULL,1,0),(235,'coinScale_list','coinScale','[0],[coinManager],[coinScale],','币种小数位数管理列表','','/coinScale/list',99,3,0,NULL,1,0),(236,'coinScale_add','coinScale','[0],[coinManager],[coinScale],','币种小数位数管理添加','','/coinScale/add',99,3,0,NULL,1,0),(237,'coinScale_update','coinScale','[0],[coinManager],[coinScale],','币种小数位数管理更新','','/coinScale/update',99,3,0,NULL,1,0),(238,'coinScale_delete','coinScale','[0],[coinManager],[coinScale],','币种小数位数管理删除','','/coinScale/delete',99,3,0,NULL,1,0),(239,'coinScale_detail','coinScale','[0],[coinManager],[coinScale],','币种小数位数管理详情','','/coinScale/detail',99,3,0,NULL,1,0),(240,'commissionManager','userManager','[0],[userManager],','返佣管理','','/',3,2,1,NULL,1,NULL),(241,'commissionInvite','commissionManager','[0],[userManager],[commissionManager],','邀请返佣','','/commissionInvite',99,3,1,NULL,1,0),(242,'commissionInvite_list','commissionInvite','[0],[userManager],[commissionManager],[commissionInvite],','邀请返佣列表','','/commissionInvite/list',99,3,0,NULL,1,0),(243,'commissionInvite_add','commissionInvite','[0],[userManager],[commissionManager],[commissionInvite],','邀请返佣添加','','/commissionInvite/add',99,3,0,NULL,1,0),(244,'commissionInvite_update','commissionInvite','[0],[userManager],[commissionManager],[commissionInvite],','邀请返佣更新','','/commissionInvite/update',99,3,0,NULL,1,0),(245,'commissionInvite_delete','commissionInvite','[0],[userManager],[commissionManager],[commissionInvite],','邀请返佣删除','','/commissionInvite/delete',99,3,0,NULL,1,0),(246,'commissionInvite_detail','commissionInvite','[0],[userManager],[commissionManager],[commissionInvite],','邀请返佣详情','','/commissionInvite/detail',99,3,0,NULL,1,0),(247,'commissionRecord','commissionManager','[0],[userManager],[commissionManager],','交易返佣','','/commissionRecord',99,3,1,NULL,1,0),(248,'commissionRecord_list','commissionRecord','[0],[userManager],[commissionManager],[commissionRecord],','交易返佣列表','','/commissionRecord/list',99,3,0,NULL,1,0),(249,'commissionRecord_add','commissionRecord','[0],[userManager],[commissionManager],[commissionRecord],','交易返佣添加','','/commissionRecord/add',99,3,0,NULL,1,0),(250,'commissionRecord_update','commissionRecord','[0],[userManager],[commissionManager],[commissionRecord],','交易返佣更新','','/commissionRecord/update',99,3,0,NULL,1,0),(251,'commissionRecord_delete','commissionRecord','[0],[userManager],[commissionManager],[commissionRecord],','交易返佣删除','','/commissionRecord/delete',99,3,0,NULL,1,0),(252,'commissionRecord_detail','commissionRecord','[0],[userManager],[commissionManager],[commissionRecord],','交易返佣详情','','/commissionRecord/detail',99,3,0,NULL,1,0),(253,'dealDigConfig','orderManager','[0],[orderManager],','交易挖矿配置','','/dealDigConfig',4,2,1,NULL,1,0),(254,'dealDigConfig_list','dealDigConfig','[0],[orderManager],[dealDigConfig],','交易挖矿配置列表','','/dealDigConfig/list',99,3,0,NULL,1,0),(255,'dealDigConfig_add','dealDigConfig','[0],[orderManager],[dealDigConfig],','交易挖矿配置添加','','/dealDigConfig/add',99,3,0,NULL,1,0),(256,'dealDigConfig_update','dealDigConfig','[0],[orderManager],[dealDigConfig],','交易挖矿配置更新','','/dealDigConfig/update',99,3,0,NULL,1,0),(257,'dealDigConfig_delete','dealDigConfig','[0],[orderManager],[dealDigConfig],','交易挖矿配置删除','','/dealDigConfig/delete',99,3,0,NULL,1,0),(258,'dealDigConfig_detail','dealDigConfig','[0],[orderManager],[dealDigConfig],','交易挖矿配置详情','','/dealDigConfig/detail',99,3,0,NULL,1,0),(259,'dealDigRecord','orderManager','[0],[orderManager],','交易挖矿记录','','/dealDigRecord',4,2,1,NULL,1,0),(260,'dealDigRecord_list','dealDigRecord','[0],[orderManager],[dealDigRecord],','交易挖矿记录列表','','/dealDigRecord/list',99,3,0,NULL,1,0),(261,'dealDigRecord_add','dealDigRecord','[0],[orderManager],[dealDigRecord],','交易挖矿记录添加','','/dealDigRecord/add',99,3,0,NULL,1,0),(262,'dealDigRecord_update','dealDigRecord','[0],[orderManager],[dealDigRecord],','交易挖矿记录更新','','/dealDigRecord/update',99,3,0,NULL,1,0),(263,'dealDigRecord_delete','dealDigRecord','[0],[orderManager],[dealDigRecord],','交易挖矿记录删除','','/dealDigRecord/delete',99,3,0,NULL,1,0),(264,'dealDigRecord_detail','dealDigRecord','[0],[orderManager],[dealDigRecord],','交易挖矿记录详情','','/dealDigRecord/detail',99,3,0,NULL,1,0),(265,'doc','systemManager','[0],[systemManager],','文档配置','','/doc',3,2,1,NULL,1,0),(266,'doc_list','doc','[0],[systemManager],[doc],','文档配置列表','','/doc/list',99,3,0,NULL,1,0),(267,'doc_add','doc','[0],[systemManager],[doc],','文档配置添加','','/doc/add',99,3,0,NULL,1,0),(268,'doc_update','doc','[0],[systemManager],[doc],','文档配置更新','','/doc/update',99,3,0,NULL,1,0),(269,'doc_delete','doc','[0],[systemManager],[doc],','文档配置删除','','/doc/delete',99,3,0,NULL,1,0),(270,'doc_detail','doc','[0],[systemManager],[doc],','文档配置详情','','/doc/detail',99,3,0,NULL,1,0),(271,'flow','accountManager','[0],[accountManager],','流水记录','','/flow',4,2,1,NULL,1,0),(272,'flow_list','flow','[0],[accountManager],[flow],','流水记录列表','','/flow/list',99,3,0,NULL,1,0),(273,'flow_add','flow','[0],[accountManager],[flow],','流水记录添加','','/flow/add',99,3,0,NULL,1,0),(274,'flow_update','flow','[0],[accountManager],[flow],','流水记录更新','','/flow/update',99,3,0,NULL,1,0),(275,'flow_delete','flow','[0],[accountManager],[flow],','流水记录删除','','/flow/delete',99,3,0,NULL,1,0),(276,'flow_detail','flow','[0],[accountManager],[flow],','流水记录详情','','/flow/detail',99,3,0,NULL,1,0),(277,'idcardValidate','userManager','[0],[userManager],','实名管理','','/idcardValidate',2,2,1,NULL,1,0),(278,'idcardValidate_list','idcardValidate','[0],[userManager],[idcardValidate],','实名管理列表','','/idcardValidate/list',99,3,0,NULL,1,0),(279,'idcardValidate_add','idcardValidate','[0],[userManager],[idcardValidate],','实名管理添加','','/idcardValidate/add',99,3,0,NULL,1,0),(280,'idcardValidate_update','idcardValidate','[0],[userManager],[idcardValidate],','实名管理更新','','/idcardValidate/update',99,3,0,NULL,1,0),(281,'idcardValidate_delete','idcardValidate','[0],[userManager],[idcardValidate],','实名管理删除','','/idcardValidate/delete',99,3,0,NULL,1,0),(282,'idcardValidate_detail','idcardValidate','[0],[userManager],[idcardValidate],','实名管理详情','','/idcardValidate/detail',99,3,0,NULL,1,0),(295,'orderMaker','orderManager','[0],[orderManager],','C2C商家管理','','/orderMaker',5,2,1,NULL,1,0),(296,'orderMaker_list','orderMaker','[0],[orderManager],[orderMaker],','C2C商家管理列表','','/orderMaker/list',99,3,0,NULL,1,0),(297,'orderMaker_add','orderMaker','[0],[orderManager],[orderMaker],','C2C商家管理添加','','/orderMaker/add',99,3,0,NULL,1,0),(298,'orderMaker_update','orderMaker','[0],[orderManager],[orderMaker],','C2C商家管理更新','','/orderMaker/update',99,3,0,NULL,1,0),(299,'orderMaker_delete','orderMaker','[0],[orderManager],[orderMaker],','C2C商家管理删除','','/orderMaker/delete',99,3,0,NULL,1,0),(300,'orderMaker_detail','orderMaker','[0],[orderManager],[orderMaker],','C2C商家管理详情','','/orderMaker/detail',99,3,0,NULL,1,0),(301,'orderManage','orderManager','[0],[orderManager],','交易管理','','/orderManage',3,2,1,NULL,1,0),(302,'orderManage_list','orderManage','[0],[orderManager],[orderManage],','交易管理列表','','/orderManage/list',99,3,0,NULL,1,0),(303,'orderManage_add','orderManage','[0],[orderManager],[orderManage],','交易管理添加','','/orderManage/add',99,3,0,NULL,1,0),(304,'orderManage_update','orderManage','[0],[orderManager],[orderManage],','交易管理更新','','/orderManage/update',99,3,0,NULL,1,0),(305,'orderManage_delete','orderManage','[0],[orderManager],[orderManage],','交易管理删除','','/orderManage/delete',99,3,0,NULL,1,0),(306,'orderManage_detail','orderManage','[0],[orderManager],[orderManage],','交易管理详情','','/orderManage/detail',99,3,0,NULL,1,0),(307,'orderSpot','orderManager','[0],[orderManager],','币币挂单管理','','/orderSpot',5,2,1,NULL,1,0),(308,'orderSpot_list','orderSpot','[0],[orderManager],[orderSpot],','币币挂单管理列表','','/orderSpot/list',99,3,0,NULL,1,0),(309,'orderSpot_add','orderSpot','[0],[orderManager],[orderSpot],','币币挂单管理添加','','/orderSpot/add',99,3,0,NULL,1,0),(310,'orderSpot_update','orderSpot','[0],[orderManager],[orderSpot],','币币挂单管理更新','','/orderSpot/update',99,3,0,NULL,1,0),(311,'orderSpot_delete','orderSpot','[0],[orderManager],[orderSpot],','币币挂单管理删除','','/orderSpot/delete',99,3,0,NULL,1,0),(312,'orderSpot_detail','orderSpot','[0],[orderManager],[orderSpot],','币币挂单管理详情','','/orderSpot/detail',99,3,0,NULL,1,0),(313,'orderSpotRecord','orderManager','[0],[orderManager],','币币交易记录','','/orderSpotRecord',99,2,1,NULL,1,0),(314,'orderSpotRecord_list','orderSpotRecord','[0],[orderManager],[orderSpotRecord],','币币交易记录列表','','/orderSpotRecord/list',99,3,0,NULL,1,0),(315,'orderSpotRecord_add','orderSpotRecord','[0],[orderManager],[orderSpotRecord],','币币交易记录添加','','/orderSpotRecord/add',99,3,0,NULL,1,0),(316,'orderSpotRecord_update','orderSpotRecord','[0],[orderManager],[orderSpotRecord],','币币交易记录更新','','/orderSpotRecord/update',99,3,0,NULL,1,0),(317,'orderSpotRecord_delete','orderSpotRecord','[0],[orderManager],[orderSpotRecord],','币币交易记录删除','','/orderSpotRecord/delete',99,3,0,NULL,1,0),(318,'orderSpotRecord_detail','orderSpotRecord','[0],[orderManager],[orderSpotRecord],','币币交易记录详情','','/orderSpotRecord/detail',99,3,0,NULL,1,0),(319,'orderTaker','orderManager','[0],[orderManager],','C2C普通用户管理','','/orderTaker',6,2,1,NULL,1,0),(320,'orderTaker_list','orderTaker','[0],[orderManager],[orderTaker],','C2C普通用户管理列表','','/orderTaker/list',99,3,0,NULL,1,0),(321,'orderTaker_add','orderTaker','[0],[orderManager],[orderTaker],','C2C普通用户管理添加','','/orderTaker/add',99,3,0,NULL,1,0),(322,'orderTaker_update','orderTaker','[0],[orderManager],[orderTaker],','C2C普通用户管理更新','','/orderTaker/update',99,3,0,NULL,1,0),(323,'orderTaker_delete','orderTaker','[0],[orderManager],[orderTaker],','C2C普通用户管理删除','','/orderTaker/delete',99,3,0,NULL,1,0),(324,'orderTaker_detail','orderTaker','[0],[orderManager],[orderTaker],','C2C普通用户管理详情','','/orderTaker/detail',99,3,0,NULL,1,0),(325,'picture','systemManager','[0],[systemManager],','图片管理','','/picture',99,2,1,NULL,1,0),(326,'picture_list','picture','[0],[systemManager],[picture],','图片管理列表','','/picture/list',99,3,0,NULL,1,0),(327,'picture_add','picture','[0],[systemManager],[picture],','图片管理添加','','/picture/add',99,3,0,NULL,1,0),(328,'picture_update','picture','[0],[systemManager],[picture],','图片管理更新','','/picture/update',99,3,0,NULL,1,0),(329,'picture_delete','picture','[0],[systemManager],[picture],','图片管理删除','','/picture/delete',99,3,0,NULL,1,0),(330,'picture_detail','picture','[0],[systemManager],[picture],','图片管理详情','','/picture/detail',99,3,0,NULL,1,0),(331,'poster','systemManager','[0],[systemManager],','海报管理','','/poster',99,2,1,NULL,1,0),(332,'poster_list','poster','[0],[systemManager],[poster],','海报管理列表','','/poster/list',99,3,0,NULL,1,0),(333,'poster_add','poster','[0],[systemManager],[poster],','海报管理添加','','/poster/add',99,3,0,NULL,1,0),(334,'poster_update','poster','[0],[systemManager],[poster],','海报管理更新','','/poster/update',99,3,0,NULL,1,0),(335,'poster_delete','poster','[0],[systemManager],[poster],','海报管理删除','','/poster/delete',99,3,0,NULL,1,0),(336,'poster_detail','poster','[0],[systemManager],[poster],','海报管理详情','','/poster/detail',99,3,0,NULL,1,0),(337,'recharge','accountManager','[0],[accountManager],','充值管理','','/recharge',2,2,1,NULL,1,0),(338,'recharge_list','recharge','[0],[accountManager],[recharge],','充值管理列表','','/recharge/list',99,3,0,NULL,1,0),(339,'recharge_add','recharge','[0],[accountManager],[recharge],','充值管理添加','','/recharge/add',99,3,0,NULL,1,0),(340,'recharge_update','recharge','[0],[accountManager],[recharge],','充值管理更新','','/recharge/update',99,3,0,NULL,1,0),(341,'recharge_delete','recharge','[0],[accountManager],[recharge],','充值管理删除','','/recharge/delete',99,3,0,NULL,1,0),(342,'recharge_detail','recharge','[0],[accountManager],[recharge],','充值管理详情','','/recharge/detail',99,3,0,NULL,1,0),(343,'smsRecord','systemManager','[0],[systemManager],','短信记录','','/smsRecord',99,2,1,NULL,1,0),(344,'smsRecord_list','smsRecord','[0],[systemManager],[smsRecord],','短信记录列表','','/smsRecord/list',99,3,0,NULL,1,0),(345,'smsRecord_add','smsRecord','[0],[systemManager],[smsRecord],','短信记录添加','','/smsRecord/add',99,3,0,NULL,1,0),(346,'smsRecord_update','smsRecord','[0],[systemManager],[smsRecord],','短信记录更新','','/smsRecord/update',99,3,0,NULL,1,0),(347,'smsRecord_delete','smsRecord','[0],[systemManager],[smsRecord],','短信记录删除','','/smsRecord/delete',99,3,0,NULL,1,0),(348,'smsRecord_detail','smsRecord','[0],[systemManager],[smsRecord],','短信记录详情','','/smsRecord/detail',99,3,0,NULL,1,0),(349,'sysparams','systemManager','[0],[systemManager],','系统参数配置','','/sysparams',1,2,1,NULL,1,0),(350,'sysparams_list','sysparams','[0],[systemManager],[sysparams],','系统参数配置列表','','/sysparams/list',99,3,0,NULL,1,0),(351,'sysparams_add','sysparams','[0],[systemManager],[sysparams],','系统参数配置添加','','/sysparams/add',99,3,0,NULL,1,0),(352,'sysparams_update','sysparams','[0],[systemManager],[sysparams],','系统参数配置更新','','/sysparams/update',99,3,0,NULL,1,0),(353,'sysparams_delete','sysparams','[0],[systemManager],[sysparams],','系统参数配置删除','','/sysparams/delete',99,3,0,NULL,1,0),(354,'sysparams_detail','sysparams','[0],[systemManager],[sysparams],','系统参数配置详情','','/sysparams/detail',99,3,0,NULL,1,0),(361,'withdraw','accountManager','[0],[accountManager],','提现管理','','/withdraw',3,2,1,NULL,1,0),(362,'withdraw_list','withdraw','[0],[accountManager],[withdraw],','提现管理列表','','/withdraw/list',99,3,0,NULL,1,0),(363,'withdraw_add','withdraw','[0],[accountManager],[withdraw],','提现管理添加','','/withdraw/add',99,3,0,NULL,1,0),(364,'withdraw_update','withdraw','[0],[accountManager],[withdraw],','提现管理更新','','/withdraw/update',99,3,0,NULL,1,0),(365,'withdraw_delete','withdraw','[0],[accountManager],[withdraw],','提现管理删除','','/withdraw/delete',99,3,0,NULL,1,0),(366,'withdraw_detail','withdraw','[0],[accountManager],[withdraw],','提现管理详情','','/withdraw/detail',99,3,0,NULL,1,0),(367,'yubiProfit','accountManager','[0],[accountManager],','节点挖矿','','/yubiProfit',5,2,1,NULL,1,0),(368,'yubiProfit_list','yubiProfit','[0],[accountManager],[yubiProfit],','余币宝盈利列表','','/yubiProfit/list',99,3,0,NULL,1,0),(369,'yubiProfit_add','yubiProfit','[0],[accountManager],[yubiProfit],','余币宝盈利添加','','/yubiProfit/add',99,3,0,NULL,1,0),(370,'yubiProfit_update','yubiProfit','[0],[accountManager],[yubiProfit],','余币宝盈利更新','','/yubiProfit/update',99,3,0,NULL,1,0),(371,'yubiProfit_delete','yubiProfit','[0],[accountManager],[yubiProfit],','余币宝盈利删除','','/yubiProfit/delete',99,3,0,NULL,1,0),(372,'yubiProfit_detail','yubiProfit','[0],[accountManager],[yubiProfit],','余币宝盈利详情','','/yubiProfit/detail',99,3,0,NULL,1,0),(373,'er','userManager','[0],[userManager],','会员管理','','/er',1,2,1,NULL,1,0),(374,'er_list','er','[0],[userManager],[er],','会员管理列表','','/er/list',99,3,0,NULL,1,0),(375,'er_add','er','[0],[userManager],[er],','会员管理添加','','/er/add',99,3,0,NULL,1,0),(376,'er_update','er','[0],[userManager],[er],','会员管理更新','','/er/update',99,3,0,NULL,1,0),(377,'er_delete','er','[0],[userManager],[er],','会员管理删除','','/er/delete',99,3,0,NULL,1,0),(378,'er_detail','er','[0],[userManager],[er],','会员管理详情','','/er/detail',99,3,0,NULL,1,0),(379,'fileManager','systemManager','[0],[systemManager],','文件管理','','/fileManager',2,2,1,NULL,1,0),(380,'fileManager_list','fileManager','[0],[systemManager],[fileManager],','文件管理列表','','/fileManager/list',99,3,0,NULL,1,0),(381,'fileManager_add','fileManager','[0],[systemManager],[fileManager],','文件管理添加','','/fileManager/add',99,3,0,NULL,1,0),(382,'fileManager_update','fileManager','[0],[systemManager],[fileManager],','文件管理更新','','/fileManager/update',99,3,0,NULL,1,0),(383,'fileManager_delete','fileManager','[0],[systemManager],[fileManager],','文件管理删除','','/fileManager/delete',99,3,0,NULL,1,0),(384,'fileManager_detail','fileManager','[0],[systemManager],[fileManager],','文件管理详情','','/fileManager/detail',99,3,0,NULL,1,0),(1153943788219473934,'robot','0','[0],','机器人管理','fa-android','/',10,1,1,NULL,1,NULL),(1153943788219473935,'robot1','robot','[0],[robot],','机器人管理','','/robot',1,2,1,NULL,1,0),(1153943788219473936,'robot_list','robot1','[0],[robot],[robot1],','robotManager列表','','/robot/list',99,3,0,NULL,1,0),(1153943788219473937,'robot_add','robot1','[0],[robot],[robot1],','robotManager添加','','/robot/add',99,3,0,NULL,1,0),(1153943788219473938,'robot_update','robot1','[0],[robot],[robot1],','robotManager更新','','/robot/update',99,3,0,NULL,1,0),(1153943788219473939,'robot_delete','robot1','[0],[robot],[robot1],','robotManager删除','','/robot/delete',99,3,0,NULL,1,0),(1153943788219473940,'robot_detail','robot1','[0],[robot],[robot1],','robotManager详情','','/robot/detail',99,3,0,NULL,1,0),(1153943788219473941,'robotStatistics','robot','[0],[robot],','机器人统计','','/robotStatistics',3,2,1,NULL,1,0),(1153943788219473942,'robotStatistics_list','robotStatistics','[0],[robot],[robotStatistics],','机器人统计列表','','/robotStatistics/list',99,3,0,NULL,1,0),(1153943788219473943,'robotStatistics_add','robotStatistics','[0],[robot],[robotStatistics],','机器人统计添加','','/robotStatistics/add',99,3,0,NULL,1,0),(1153943788219473944,'robotStatistics_update','robotStatistics','[0],[robot],[robotStatistics],','机器人统计更新','','/robotStatistics/update',99,3,0,NULL,1,0),(1153943788219473945,'robotStatistics_delete','robotStatistics','[0],[robot],[robotStatistics],','机器人统计删除','','/robotStatistics/delete',99,3,0,NULL,1,0),(1153943788219473946,'robotStatistics_detail','robotStatistics','[0],[robot],[robotStatistics],','机器人统计详情','','/robotStatistics/detail',99,3,0,NULL,1,0),(1153943788219473947,'robotTask','robot','[0],[robot],','机器人配置','','/robotTask',2,2,1,NULL,1,0),(1153943788219473948,'robotTask_list','robotTask','[0],[robot],[robotTask],','机器人配置列表','','/robotTask/list',99,3,0,NULL,1,0),(1153943788219473949,'robotTask_add','robotTask','[0],[robot],[robotTask],','机器人配置添加','','/robotTask/add',99,3,0,NULL,1,0),(1153943788219473950,'robotTask_update','robotTask','[0],[robot],[robotTask],','机器人配置更新','','/robotTask/update',99,3,0,NULL,1,0),(1153943788219473951,'robotTask_delete','robotTask','[0],[robot],[robotTask],','机器人配置删除','','/robotTask/delete',99,3,0,NULL,1,0),(1153943788219473952,'robotTask_detail','robotTask','[0],[robot],[robotTask],','机器人配置详情','','/robotTask/detail',99,3,0,NULL,1,0),(1153943788219473953,'news','systemManager','[0],[systemManager],','文章管理','','/news',99,2,1,NULL,1,0),(1153943788219473954,'news_list','news','[0],[systemManager],[news],','文章管理列表','','/news/list',99,3,0,NULL,1,0),(1153943788219473955,'news_add','news','[0],[systemManager],[news],','文章管理添加','','/news/add',99,3,0,NULL,1,0),(1153943788219473956,'news_update','news','[0],[systemManager],[news],','文章管理更新','','/news/update',99,3,0,NULL,1,0),(1153943788219473957,'news_delete','news','[0],[systemManager],[news],','文章管理删除','','/news/delete',99,3,0,NULL,1,0),(1153943788219473958,'news_detail','news','[0],[systemManager],[news],','文章管理详情','','/news/detail',99,3,0,NULL,1,0),(1153943788219473959,'startupParam','systemManager','[0],[systemManager],','功能配置','','/startupParam',99,2,1,NULL,1,0),(1153943788219473960,'startupParam_list','startupParam','[0],[systemManager],[startupParam],','功能配置列表','','/startupParam/list',99,3,0,NULL,1,0),(1153943788219473961,'startupParam_add','startupParam','[0],[systemManager],[startupParam],','功能配置添加','','/startupParam/add',99,3,0,NULL,1,0),(1153943788219473962,'startupParam_update','startupParam','[0],[systemManager],[startupParam],','功能配置更新','','/startupParam/update',99,3,0,NULL,1,0),(1153943788219473963,'startupParam_delete','startupParam','[0],[systemManager],[startupParam],','功能配置删除','','/startupParam/delete',99,3,0,NULL,1,0),(1153943788219473964,'startupParam_detail','startupParam','[0],[systemManager],[startupParam],','功能配置详情','','/startupParam/detail',99,3,0,NULL,1,0),(1153943788219473965,'userAuthRecord','userManager','[0],[userManager],','认证管理','','/userAuthRecord',99,2,1,NULL,1,0),(1153943788219473966,'userAuthRecord_list','userAuthRecord','[0],[userManager],[userAuthRecord],','认证管理列表','','/userAuthRecord/list',99,3,0,NULL,1,0),(1153943788219473967,'userAuthRecord_add','userAuthRecord','[0],[userManager],[userAuthRecord],','认证管理添加','','/userAuthRecord/add',99,3,0,NULL,1,0),(1153943788219473968,'userAuthRecord_update','userAuthRecord','[0],[userManager],[userAuthRecord],','认证管理更新','','/userAuthRecord/update',99,3,0,NULL,1,0),(1153943788219473969,'userAuthRecord_delete','userAuthRecord','[0],[userManager],[userAuthRecord],','认证管理删除','','/userAuthRecord/delete',99,3,0,NULL,1,0),(1153943788219473970,'userAuthRecord_detail','userAuthRecord','[0],[userManager],[userAuthRecord],','认证管理详情','','/userAuthRecord/detail',99,3,0,NULL,1,0),(1153943788219473978,'statistics','0','[0],','统计','fa-bar-chart ','/statistics',99,1,1,NULL,1,0),(1153943788219473979,'statistics_list','statistics','[0],[statistics],','统计列表','','/statistics/list',99,2,0,NULL,1,0),(1153943788219473980,'statistics_add','statistics','[0],[statistics],','统计添加','','/statistics/add',99,2,0,NULL,1,0),(1153943788219473981,'statistics_update','statistics','[0],[statistics],','统计更新','','/statistics/update',99,2,0,NULL,1,0),(1153943788219473982,'statistics_delete','statistics','[0],[statistics],','统计删除','','/statistics/delete',99,2,0,NULL,1,0),(1153943788219473983,'statistics_detail','statistics','[0],[statistics],','统计详情','','/statistics/detail',99,2,0,NULL,1,0);

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表';

/*Data for the table `sys_notice` */

/*Table structure for table `sys_operation_log` */

DROP TABLE IF EXISTS `sys_operation_log`;

CREATE TABLE `sys_operation_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `message` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='操作日志';

/*Data for the table `sys_operation_log` */

insert  into `sys_operation_log`(`id`,`logtype`,`logname`,`userid`,`classname`,`method`,`createtime`,`succeed`,`message`) values (1,'业务日志','删除菜单',1,'cn.stylefeng.guns.modular.system.controller.MenuController','remove','2019-09-19 15:56:35','成功','菜单id=1153943091713351687'),(2,'业务日志','修改字典',1,'cn.stylefeng.guns.modular.system.controller.DictController','update','2019-09-20 15:08:44','成功','字典名称=币种,字典内容=0:ECN:0;1:ODIN:1;2:YT:2;8:PGY:8;;;;'),(3,'业务日志','添加字典记录',1,'cn.stylefeng.guns.modular.system.controller.DictController','add','2019-09-20 17:04:43','成功','字典名称=公告类型,字典内容=0:公告:0;1:关于:1;2:帮助:2;3:费率:3;4:算法:4;5:注册协议:5;6:买币指南:6;7:玩转资金账户:7;'),(4,'异常日志','',1,NULL,NULL,'2019-09-20 17:11:53','失败','org.springframework.jdbc.BadSqlGrammarException: \r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\r\n### The error may exist in cn/stylefeng/guns/modular/system/dao/OticeMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT  id AS id,title,pic,roundup,`type`,`state`,createid,operid,createtime,updatetime,content  FROM notice\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:234)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:73)\r\n	at org.mybatis.spring.SqlSessionTemplateTSqlSessionInterceptor.invoke(SqlSessionTemplate.java:446)\r\n	at com.sun.proxy.TProxy93.selectList(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:230)\r\n	at org.apache.ibatis.binding.MapperMethod.executeForMany(MapperMethod.java:139)\r\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:76)\r\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:59)\r\n	at com.sun.proxy.TProxy156.selectList(Unknown Source)\r\n	at com.baomidou.mybatisplus.service.impl.ServiceImpl.selectList(ServiceImpl.java:394)\r\n	at com.baomidou.mybatisplus.service.impl.ServiceImplTTFastClassBySpringCGLIBTT3e2398a4.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxyTCglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:747)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at com.alibaba.druid.support.spring.stat.DruidStatInterceptor.invoke(DruidStatInterceptor.java:72)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\r\n	at org.springframework.aop.framework.CglibAopProxyTDynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\r\n	at cn.stylefeng.guns.modular.systemConfig.service.impl.OticeServiceImplTTEnhancerBySpringCGLIBTT8c06a81b.selectList(<generated>)\r\n	at cn.stylefeng.guns.modular.systemConfig.controller.OticeController.list(OticeController.java:63)\r\n	at cn.stylefeng.guns.modular.systemConfig.controller.OticeControllerTTFastClassBySpringCGLIBTTa6cf7936.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxyTCglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:747)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)\r\n	at cn.stylefeng.guns.core.interceptor.SessionHolderInterceptor.sessionKit(SessionHolderInterceptor.java:44)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)\r\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)\r\n	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\r\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:92)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\r\n	at org.springframework.aop.framework.CglibAopProxyTDynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\r\n	at cn.stylefeng.guns.modular.systemConfig.controller.OticeControllerTTEnhancerBySpringCGLIBTT3c9cda62.list(<generated>)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:209)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:136)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:877)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:661)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:742)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\r\n	at org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\r\n	at org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\r\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\r\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\r\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\r\n	at org.apache.shiro.web.servlet.AbstractShiroFilterT1.call(AbstractShiroFilter.java:365)\r\n	at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\r\n	at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\r\n	at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\r\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\r\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at cn.stylefeng.roses.core.xss.XssFilter.doFilter(XssFilter.java:31)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:123)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:81)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)\r\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\r\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:496)\r\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)\r\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)\r\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)\r\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)\r\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:803)\r\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\r\n	at org.apache.coyote.AbstractProtocolTConnectionHandler.process(AbstractProtocol.java:790)\r\n	at org.apache.tomcat.util.net.NioEndpointTSocketProcessor.doRun(NioEndpoint.java:1459)\r\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\r\n	at java.util.concurrent.ThreadPoolExecutorTWorker.run(ThreadPoolExecutor.java:624)\r\n	at org.apache.tomcat.util.threads.TaskThreadTWrappingRunnable.run(TaskThread.java:61)\r\n	at java.lang.Thread.run(Thread.java:748)\r\nCaused by: java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:118)\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:95)\r\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\r\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:960)\r\n	at com.mysql.cj.jdbc.ClientPreparedStatement.execute(ClientPreparedStatement.java:388)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3409)\r\n	at com.alibaba.druid.filter.FilterEventAdapter.preparedStatement_execute(FilterEventAdapter.java:440)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3407)\r\n	at com.alibaba.druid.wall.WallFilter.preparedStatement_execute(WallFilter.java:619)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3407)\r\n	at com.alibaba.druid.proxy.jdbc.PreparedStatementProxyImpl.execute(PreparedStatementProxyImpl.java:167)\r\n	at com.alibaba.druid.pool.DruidPooledPreparedStatement.execute(DruidPooledPreparedStatement.java:498)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.logging.jdbc.PreparedStatementLogger.invoke(PreparedStatementLogger.java:59)\r\n	at com.sun.proxy.TProxy106.execute(Unknown Source)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:63)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:79)\r\n	at sun.reflect.GeneratedMethodAccessor98.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)\r\n	at com.sun.proxy.TProxy104.query(Unknown Source)\r\n	at sun.reflect.GeneratedMethodAccessor98.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)\r\n	at com.sun.proxy.TProxy104.query(Unknown Source)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:63)\r\n	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:326)\r\n	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)\r\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)\r\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:83)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)\r\n	at com.sun.proxy.TProxy103.query(Unknown Source)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:148)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:141)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.mybatis.spring.SqlSessionTemplateTSqlSessionInterceptor.invoke(SqlSessionTemplate.java:433)\r\n	... 108 more\r\n'),(5,'异常日志','',1,NULL,NULL,'2019-09-20 17:12:03','失败','org.springframework.jdbc.BadSqlGrammarException: \r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\r\n### The error may exist in cn/stylefeng/guns/modular/system/dao/OticeMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT  id AS id,title,pic,roundup,`type`,`state`,createid,operid,createtime,updatetime,content  FROM notice\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:234)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:73)\r\n	at org.mybatis.spring.SqlSessionTemplateTSqlSessionInterceptor.invoke(SqlSessionTemplate.java:446)\r\n	at com.sun.proxy.TProxy93.selectList(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:230)\r\n	at org.apache.ibatis.binding.MapperMethod.executeForMany(MapperMethod.java:139)\r\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:76)\r\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:59)\r\n	at com.sun.proxy.TProxy156.selectList(Unknown Source)\r\n	at com.baomidou.mybatisplus.service.impl.ServiceImpl.selectList(ServiceImpl.java:394)\r\n	at com.baomidou.mybatisplus.service.impl.ServiceImplTTFastClassBySpringCGLIBTT3e2398a4.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxyTCglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:747)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at com.alibaba.druid.support.spring.stat.DruidStatInterceptor.invoke(DruidStatInterceptor.java:72)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\r\n	at org.springframework.aop.framework.CglibAopProxyTDynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\r\n	at cn.stylefeng.guns.modular.systemConfig.service.impl.OticeServiceImplTTEnhancerBySpringCGLIBTT8c06a81b.selectList(<generated>)\r\n	at cn.stylefeng.guns.modular.systemConfig.controller.OticeController.list(OticeController.java:63)\r\n	at cn.stylefeng.guns.modular.systemConfig.controller.OticeControllerTTFastClassBySpringCGLIBTTa6cf7936.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxyTCglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:747)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:89)\r\n	at cn.stylefeng.guns.core.interceptor.SessionHolderInterceptor.sessionKit(SessionHolderInterceptor.java:44)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644)\r\n	at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633)\r\n	at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\r\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:92)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\r\n	at org.springframework.aop.framework.CglibAopProxyTDynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\r\n	at cn.stylefeng.guns.modular.systemConfig.controller.OticeControllerTTEnhancerBySpringCGLIBTT3c9cda62.list(<generated>)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:209)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:136)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:877)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:661)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:742)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\r\n	at org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\r\n	at org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\r\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\r\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\r\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\r\n	at org.apache.shiro.web.servlet.AbstractShiroFilterT1.call(AbstractShiroFilter.java:365)\r\n	at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\r\n	at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\r\n	at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\r\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\r\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at cn.stylefeng.roses.core.xss.XssFilter.doFilter(XssFilter.java:31)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:123)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:81)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)\r\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\r\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:496)\r\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)\r\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)\r\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)\r\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)\r\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:803)\r\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\r\n	at org.apache.coyote.AbstractProtocolTConnectionHandler.process(AbstractProtocol.java:790)\r\n	at org.apache.tomcat.util.net.NioEndpointTSocketProcessor.doRun(NioEndpoint.java:1459)\r\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\r\n	at java.util.concurrent.ThreadPoolExecutorTWorker.run(ThreadPoolExecutor.java:624)\r\n	at org.apache.tomcat.util.threads.TaskThreadTWrappingRunnable.run(TaskThread.java:61)\r\n	at java.lang.Thread.run(Thread.java:748)\r\nCaused by: java.sql.SQLSyntaxErrorException: Table \'sl_db.notice\' doesn\'t exist\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:118)\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:95)\r\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\r\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:960)\r\n	at com.mysql.cj.jdbc.ClientPreparedStatement.execute(ClientPreparedStatement.java:388)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3409)\r\n	at com.alibaba.druid.filter.FilterEventAdapter.preparedStatement_execute(FilterEventAdapter.java:440)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3407)\r\n	at com.alibaba.druid.wall.WallFilter.preparedStatement_execute(WallFilter.java:619)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3407)\r\n	at com.alibaba.druid.proxy.jdbc.PreparedStatementProxyImpl.execute(PreparedStatementProxyImpl.java:167)\r\n	at com.alibaba.druid.pool.DruidPooledPreparedStatement.execute(DruidPooledPreparedStatement.java:498)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.logging.jdbc.PreparedStatementLogger.invoke(PreparedStatementLogger.java:59)\r\n	at com.sun.proxy.TProxy106.execute(Unknown Source)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:63)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:79)\r\n	at sun.reflect.GeneratedMethodAccessor98.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)\r\n	at com.sun.proxy.TProxy104.query(Unknown Source)\r\n	at sun.reflect.GeneratedMethodAccessor98.invoke(Unknown Source)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)\r\n	at com.sun.proxy.TProxy104.query(Unknown Source)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:63)\r\n	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:326)\r\n	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)\r\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)\r\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:83)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:63)\r\n	at com.sun.proxy.TProxy103.query(Unknown Source)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:148)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:141)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\r\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.lang.reflect.Method.invoke(Method.java:498)\r\n	at org.mybatis.spring.SqlSessionTemplateTSqlSessionInterceptor.invoke(SqlSessionTemplate.java:433)\r\n	... 108 more\r\n'),(6,'业务日志','删除菜单',1,'cn.stylefeng.guns.modular.system.controller.MenuController','remove','2019-09-20 17:18:24','成功','菜单id=289'),(7,'业务日志','菜单新增',1,'cn.stylefeng.guns.modular.system.controller.MenuController','add','2019-09-27 10:59:06','成功','菜单名称=统计');

/*Table structure for table `sys_relation` */

DROP TABLE IF EXISTS `sys_relation`;

CREATE TABLE `sys_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` bigint(11) DEFAULT NULL COMMENT '菜单id',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11326 DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

/*Data for the table `sys_relation` */

insert  into `sys_relation`(`id`,`menuid`,`roleid`) values (11073,105,1),(11074,106,1),(11075,107,1),(11076,108,1),(11077,109,1),(11078,110,1),(11079,111,1),(11080,112,1),(11081,113,1),(11082,165,1),(11083,166,1),(11084,167,1),(11085,114,1),(11086,115,1),(11087,116,1),(11088,117,1),(11089,118,1),(11090,162,1),(11091,163,1),(11092,164,1),(11093,119,1),(11094,120,1),(11095,121,1),(11096,122,1),(11097,150,1),(11098,151,1),(11099,128,1),(11100,134,1),(11101,158,1),(11102,159,1),(11103,130,1),(11104,131,1),(11105,135,1),(11106,136,1),(11107,137,1),(11108,152,1),(11109,153,1),(11110,154,1),(11111,132,1),(11112,138,1),(11113,139,1),(11114,140,1),(11115,155,1),(11116,156,1),(11117,157,1),(11118,133,1),(11119,160,1),(11120,161,1),(11121,141,1),(11122,142,1),(11123,143,1),(11124,144,1),(11125,145,1),(11126,148,1),(11127,168,1),(11128,174,1),(11129,175,1),(11130,176,1),(11131,177,1),(11132,178,1),(11133,179,1),(11134,198,1),(11135,199,1),(11136,200,1),(11137,201,1),(11138,202,1),(11139,203,1),(11140,210,1),(11141,211,1),(11142,212,1),(11143,213,1),(11144,214,1),(11145,215,1),(11146,343,1),(11147,344,1),(11148,345,1),(11149,346,1),(11150,347,1),(11151,348,1),(11152,349,1),(11153,350,1),(11154,351,1),(11155,352,1),(11156,353,1),(11157,354,1),(11158,379,1),(11159,380,1),(11160,381,1),(11161,382,1),(11162,383,1),(11163,384,1),(11164,1153943788219473953,1),(11165,1153943788219473954,1),(11166,1153943788219473955,1),(11167,1153943788219473956,1),(11168,1153943788219473957,1),(11169,1153943788219473958,1),(11170,1153943788219473959,1),(11171,1153943788219473960,1),(11172,1153943788219473961,1),(11173,1153943788219473962,1),(11174,1153943788219473963,1),(11175,1153943788219473964,1),(11176,169,1),(11177,253,1),(11178,254,1),(11179,255,1),(11180,256,1),(11181,257,1),(11182,258,1),(11183,259,1),(11184,260,1),(11185,261,1),(11186,262,1),(11187,263,1),(11188,264,1),(11189,295,1),(11190,296,1),(11191,297,1),(11192,298,1),(11193,299,1),(11194,300,1),(11195,301,1),(11196,302,1),(11197,303,1),(11198,304,1),(11199,305,1),(11200,306,1),(11201,307,1),(11202,308,1),(11203,309,1),(11204,310,1),(11205,311,1),(11206,312,1),(11207,313,1),(11208,314,1),(11209,315,1),(11210,316,1),(11211,317,1),(11212,318,1),(11213,319,1),(11214,320,1),(11215,321,1),(11216,322,1),(11217,323,1),(11218,324,1),(11219,171,1),(11220,216,1),(11221,217,1),(11222,218,1),(11223,219,1),(11224,220,1),(11225,221,1),(11226,240,1),(11227,241,1),(11228,242,1),(11229,243,1),(11230,244,1),(11231,245,1),(11232,246,1),(11233,247,1),(11234,248,1),(11235,249,1),(11236,250,1),(11237,251,1),(11238,252,1),(11239,373,1),(11240,374,1),(11241,375,1),(11242,376,1),(11243,377,1),(11244,378,1),(11245,1153943788219473965,1),(11246,1153943788219473966,1),(11247,1153943788219473967,1),(11248,1153943788219473968,1),(11249,1153943788219473969,1),(11250,1153943788219473970,1),(11251,172,1),(11252,186,1),(11253,187,1),(11254,188,1),(11255,189,1),(11256,190,1),(11257,191,1),(11258,192,1),(11259,193,1),(11260,194,1),(11261,195,1),(11262,196,1),(11263,197,1),(11264,271,1),(11265,272,1),(11266,273,1),(11267,274,1),(11268,275,1),(11269,276,1),(11270,337,1),(11271,338,1),(11272,339,1),(11273,340,1),(11274,341,1),(11275,342,1),(11276,361,1),(11277,362,1),(11278,363,1),(11279,364,1),(11280,365,1),(11281,366,1),(11282,173,1),(11283,222,1),(11284,223,1),(11285,224,1),(11286,225,1),(11287,226,1),(11288,227,1),(11289,228,1),(11290,229,1),(11291,230,1),(11292,231,1),(11293,232,1),(11294,233,1),(11295,234,1),(11296,235,1),(11297,236,1),(11298,237,1),(11299,238,1),(11300,239,1),(11301,1153943788219473934,1),(11302,1153943788219473935,1),(11303,1153943788219473936,1),(11304,1153943788219473937,1),(11305,1153943788219473938,1),(11306,1153943788219473939,1),(11307,1153943788219473940,1),(11308,1153943788219473941,1),(11309,1153943788219473942,1),(11310,1153943788219473943,1),(11311,1153943788219473944,1),(11312,1153943788219473945,1),(11313,1153943788219473946,1),(11314,1153943788219473947,1),(11315,1153943788219473948,1),(11316,1153943788219473949,1),(11317,1153943788219473950,1),(11318,1153943788219473951,1),(11319,1153943788219473952,1),(11320,1153943788219473978,1),(11321,1153943788219473979,1),(11322,1153943788219473980,1),(11323,1153943788219473981,1),(11324,1153943788219473982,1),(11325,1153943788219473983,1);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` int(11) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `deptid` int(11) DEFAULT NULL COMMENT '部门名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`num`,`pid`,`name`,`deptid`,`tips`,`version`) values (1,1,0,'超级管理员',24,'administrator',1);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptid` int(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='管理员表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`avatar`,`account`,`password`,`salt`,`name`,`birthday`,`sex`,`email`,`phone`,`roleid`,`deptid`,`status`,`createtime`,`version`) values (1,'girl.gif','admin','38a62e432ea55b2e78a20af222cf966a','8pgby','张三','2017-05-05 00:00:00',2,'sn93@qq.com','18200000000','1',27,1,'2016-01-29 08:49:53',25),(44,NULL,'test','45abb7879f6a8268f1ef600e6038ac73','ssts3','test','2017-05-01 00:00:00',1,'abc@123.com','','5',26,3,'2017-05-16 20:33:37',NULL),(45,NULL,'boss','71887a5ad666a18f709e1d4e693d5a35','1f7bf','老板','2017-12-04 00:00:00',1,'','','1',24,3,'2017-12-04 22:24:02',NULL),(46,NULL,'manager','b53cac62e7175637d4beb3b16b2f7915','j3cs9','经理','2017-12-04 00:00:00',1,'','','5',24,3,'2017-12-04 22:24:24',NULL);

/*Table structure for table `sysparams` */

DROP TABLE IF EXISTS `sysparams`;

CREATE TABLE `sysparams` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `keyname` varchar(255) NOT NULL COMMENT 'key',
  `keyval` varchar(255) NOT NULL COMMENT 'value',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;

/*Data for the table `sysparams` */

insert  into `sysparams`(`id`,`keyname`,`keyval`,`remark`,`createtime`,`updatetime`) values (1,'RECORDREQUEST_ONOFF','1','记录请求地址开关','2019-06-24 12:16:58','2019-06-24 12:16:58'),(2,'SMS_ONOFF','1','短信开关','2019-06-24 12:17:09','2019-06-24 12:17:09'),(3,'SMS_COUNTS_LIMIT','5','短信次数限制','2019-06-24 13:38:02','2019-06-24 13:38:02'),(4,'SMS_TIME_LIMIT','5','短信时间限制   （分钟）','2019-06-24 13:38:14','2019-06-24 13:38:14'),(9,'REGIST_ONOFF','1','注册功能开关','2019-06-24 13:46:58','2019-06-24 13:46:58'),(11,'TRANSFER_ONOFF','1','转账开关','2019-06-24 13:47:16','2019-06-24 13:47:16'),(20,'TRANSFER_RATE','1','转账充值费率','2019-06-24 13:48:31','2019-06-24 13:48:31'),(29,'WITHDRAW_COUNT_LIMIT','10','日提现次数限制','2019-06-24 13:49:39','2019-06-24 13:49:39'),(30,'WITHDRAW_SUM_LIMIT','1000','日提现金额限制','2019-06-24 13:49:46','2019-06-24 13:49:46'),(31,'ORDERPWD_LOCK_INTERVAL','1','交易密码锁定时间 分钟','2019-06-24 13:49:56','2019-06-24 13:49:56'),(32,'ORDERPWD_ERROR_INTERVAL','1','交易密码错误时间 分钟','2019-06-24 13:50:04','2019-06-24 13:50:04'),(33,'ORDERPWD_ERROR_TIMES','3','交易密码错误次数','2019-06-24 13:50:10','2019-06-24 13:50:10'),(38,'SYSTEM_URL','http://47.104.142.76:8081/orderapi/','系统地址','2019-06-24 13:50:49','2019-06-24 13:50:49'),(39,'MARKET_ORDER_SIZE_MAX','100','行情最大档位','2019-06-24 13:50:58','2019-06-24 13:50:58'),(40,'DIG_ONOFF_%s','1','挖矿开关','2019-06-24 13:51:07','2019-06-24 13:51:07'),(41,'REAL_NAME_ONOFF','1','实名认证开关','2019-06-24 13:51:14','2019-06-24 13:51:14'),(42,'IDCARD_VALIDATE_TIMES_LIMIT','10','身份证验证次数限制','2019-06-24 13:51:23','2019-06-24 13:51:23'),(43,'ORDER_C2C_NOTPAY_INACTIVE_INTERVAL','10','C2C代付款的失效时间& #40;单位：分钟& #41;','2019-06-24 13:51:38','2019-06-24 13:51:38'),(44,'ORDER_C2C_NOTCONFIRM_INACTIVE_INTERVAL','30','C2C待确认的失效时间& #40;单位：分钟& #41;','2019-06-24 13:51:47','2019-06-24 13:51:47'),(45,'ORDER_C2C_CANCEL_LIMIT_TAKER','10','普通用户取消订单次数限制','2019-06-24 13:51:54','2019-06-24 13:51:54'),(46,'ORDER_C2C_CANCEL_LIMIT_MAKER','5','商家取消订单次数限制','2019-06-24 13:52:01','2019-06-24 13:52:01'),(47,'APP_CONFIG_VERSION','1','版本号','2019-06-24 13:52:07','2019-06-24 13:52:07'),(48,'APP_CONFIG_CONTACTUS_URL','1','在线客服（联系我们）','2019-06-24 13:52:20','2019-06-24 13:52:20'),(49,'APP_CONFIG_AGREENMENT_URL','http://47.104.142.76:8081/orderapi/web/doc/5.action','注册协议','2019-06-24 13:52:28','2019-06-24 13:52:28'),(50,'APP_CONFIG_RATEDETAILS_URL','/web/doc/3.action','费率','2019-06-24 13:52:35','2019-06-24 13:52:35'),(51,'APP_CONFIG_HELPDOC_URL','1','c2c帮助文档','2019-06-24 13:52:43','2019-06-24 13:52:43'),(52,'APP_CONFIG_EXCHANGERATE_URL','1','充值提现帮助文档','2019-06-24 13:52:49','2019-06-24 13:52:49'),(53,'APP_CONFIG_NOTLOGGED_SHARE_URL','1','未登录分享地址','2019-06-24 13:52:57','2019-06-24 13:52:57'),(54,'APP_CONFIG_LOGGED_SHARE_URL','1','已登录分享地址','2019-06-24 13:53:04','2019-06-24 13:53:04'),(55,'APP_CONFIG_INVITE_URL','1','要求返佣地址','2019-06-24 13:53:11','2019-06-24 13:53:11'),(56,'APP_CONFIG_SHARE_TITLE','蒲公英','分享标题','2019-06-24 13:53:18','2019-06-24 13:53:18'),(57,'APP_CONFIG_SHARE_DES','蒲公英数字货币交易中心','分享描述','2019-06-24 13:53:26','2019-06-24 13:53:26'),(58,'APP_CONFIG_HTTPS_FLAG','1','HTTPS开关','2019-06-24 13:53:33','2019-06-24 13:53:33'),(59,'APP_CONFIG_SPOTCOIN_PAIR','{0:[8]}','现货交易币种选择（json串）','2019-06-24 13:53:40','2019-06-24 13:53:40'),(60,'APP_CONFIG_SPOTQUERY_COINPAIR','{0:[8]}','现货委托查询币种选择（json串）','2019-06-24 13:53:49','2019-06-24 13:53:49'),(61,'APP_CONFIG_ORDERCOUNT','[5]','现货交易档位（json串）','2019-06-24 13:53:56','2019-06-24 13:53:56'),(62,'APP_CONFIG_C2CCOIN','[0]','c2c交易币种（json串）','2019-06-24 13:54:04','2019-06-24 13:54:04'),(63,'APP_CONFIG_RECHANDWITH_COIN','[0]','充值提现币种（json串）','2019-06-24 13:54:12','2019-06-24 13:54:12'),(66,'ORDER_C2C_MAKER_MINTOTAL','100','C2C商家最低交易额','2019-06-24 13:54:33','2019-06-24 13:54:33'),(67,'COMMISSION_LOGIN_ONOFF','0','登录奖励开关','2019-06-24 13:54:40','2019-06-24 13:54:40'),(68,'COMMISSION_LOGIN_AMOUNT','0','登录奖励金额','2019-06-24 13:54:49','2019-06-24 13:54:49'),(69,'COMMISSION_REALNAME_ONOFF','0','实名奖励开关','2019-06-24 13:54:56','2019-06-24 13:54:56'),(70,'COMMISSION_REALNAME_AMOUNT_USER','[25]','实名奖励金额-用户','2019-06-24 13:55:09','2019-06-24 13:55:09'),(71,'COMMISSION_REALNAME_AMOUNT_REFER','[25]','实名奖励金额-推荐人','2019-06-24 13:55:19','2019-06-24 13:55:19'),(74,'CALCULATE_FORCE_ONE','0','每日登录奖励','2019-06-24 13:55:40','2019-06-24 13:55:40'),(91,'APP_DOWNLAOD_URL','1','app下载地址','2019-06-24 17:57:25','2019-06-24 17:57:25'),(92,'APP_SHARE_URL','1','app分享地址','2019-06-24 17:57:32','2019-06-24 17:57:32'),(94,'DEFAULT_HEAD_IMG_URL','http://47.104.142.76:8080/file/showImg?imgUrl=/20190804/1564905074157_637.png','默认头像地址','2019-06-24 17:57:47','2019-06-24 17:57:47'),(95,'TRANS_TO_MAIN_COIN','[1]','需把余额转账至主账户的币种对','2019-06-24 17:57:57','2019-06-24 17:57:57'),(96,'NET_INDEX_URL','http://odin.xin/','官网','2019-06-24 17:58:04','2019-06-24 17:58:04'),(107,'RECHARGE_ONOFF_TOTAL','1','钱包充值开关','2019-06-24 17:59:18','2019-06-24 17:59:18'),(108,'RECHARGE_COINTYPES','1','后台可以充值的币种','2019-06-24 17:59:24','2019-06-24 17:59:24'),(109,'ORDER_PASSWORD_DEFAULT','123456','默认交易密码','2019-06-24 17:59:32','2019-06-24 17:59:32'),(110,'ROBOT_USDT_PRICE','1','机器人usdt价格','2019-06-24 17:59:39','2019-06-24 17:59:39'),(111,'USER_DEFAULT_PASSWORD','a12345678','用户默认密码','2019-06-24 17:59:45','2019-06-24 17:59:45'),(115,'AUTO_WITHDRAW_ONOFF','0','自动提现开关','2019-06-24 18:00:45','2019-06-24 18:00:45'),(132,'SYSTEM_RECHARGE_URL','暂未开放','系统充值地址','2019-07-19 14:38:15','2019-07-19 14:38:15'),(135,'APP_CONFIG_DEALDIGDOC_URL','http://47.104.142.76:8081/orderapi/web/doc/7.action','交易挖矿帮助文档','2019-07-28 23:08:28','2019-07-28 23:08:28'),(136,'COMMISSION_REALNAME_COIN','[1]','奖励币种 json','2019-07-29 15:45:31','2019-07-29 15:45:31'),(149,'HOMEPAGE_MARKET_COIN_LIST','1,8','首页行情展示币种 默认','2019-09-19 17:56:15','2019-09-19 17:56:15'),(150,'HOMEPAGE_MARKET_COIN_LIST_TOP','1,8','首页行情展示币种 涨幅榜','2019-09-19 17:56:37','2019-09-19 17:56:37'),(151,'ORDER_SPECIAL_COIN_PRICE_CHANGE_ONOFF','0','特殊币种价格变动开关','2019-09-19 17:56:56','2019-09-19 17:56:56'),(152,'ORDER_SPECIAL_COIN','8','特殊币种列表','2019-09-19 17:57:29','2019-09-19 17:57:29'),(153,'ORDER_SPECIAL_COIN_BASE_PRICE','1','基础价格','2019-09-19 17:57:38','2019-09-19 17:57:38'),(154,'ORDER_SPECIAL_COIN_NEW_PRICE','1','当前价格','2019-09-19 17:57:48','2019-09-19 17:57:48'),(155,'ORDER_SPECIAL_COIN_PRICE_MAX','0.005','最大溢价','2019-09-19 17:57:58','2019-09-19 17:57:58'),(156,'WITHDRAW_QUOTA_AUTH_1','2000','认证等级对应24小时提币额度','2019-09-19 17:58:30','2019-09-19 17:58:30'),(157,'WITHDRAW_QUOTA_AUTH_2','4000','认证等级对应24小时提币额度','2019-09-19 17:58:42','2019-09-19 17:58:42'),(158,'WITHDRAW_QUOTA_AUTH_3','8000','认证等级对应24小时提币额度','2019-09-19 17:58:59','2019-09-19 17:58:59'),(159,'C2C_QUOTA_AUTH_1','1000','认证等级对应法币交易单笔额度','2019-09-19 17:59:13','2019-09-19 17:59:13'),(160,'C2C_QUOTA_AUTH_2','2000','认证等级对应法币交易单笔额度','2019-09-19 17:59:22','2019-09-19 17:59:22'),(161,'C2C_QUOTA_AUTH_3','4000','认证等级对应法币交易单笔额度','2019-09-19 17:59:29','2019-09-19 17:59:29'),(162,'MARKET_COIN_LIST','eos,btc,eth,etc,ltc','全球行情币种列表','2019-09-24 10:09:49','2019-09-24 10:09:49'),(163,'REFER_STATUS_NUMBER_1','1','推荐奖金等级需要邀请人数-1级','2019-09-24 17:47:28','2019-09-24 17:47:28'),(164,'REFER_STATUS_NUMBER_2','10','推荐奖金等级需要邀请人数-2级','2019-09-24 17:47:43','2019-09-24 17:47:43'),(165,'REFER_STATUS_NUMBER_3','2','推荐奖金等级需要邀请人数-3级','2019-09-24 17:47:51','2019-09-24 17:47:51'),(166,'REFER_STATUS_NUMBER_AMOUNT_1','0.2','推荐人邀请等级对应奖励数量-1级','2019-09-25 10:21:43','2019-09-25 10:21:43'),(167,'REFER_STATUS_NUMBER_AMOUNT_2','0.1','推荐人邀请等级对应奖励数量-2级','2019-09-25 10:26:28','2019-09-25 10:26:28'),(168,'REFER_STATUS_NUMBER_AMOUNT_3','0.05','推荐人邀请等级对应奖励数量-3级','2019-09-25 10:26:48','2019-09-25 10:26:48'),(169,'DEAL_ORDER_REFER_REWARD_BUY_ONOFF','1','推荐人交易挖矿开关 买方','2019-09-25 11:46:11','2019-09-25 11:46:11'),(170,'DEAL_ORDER_REFER_REWARD_SALE_ONOFF','0','推荐人交易挖矿开关 卖方','2019-09-25 11:46:26','2019-09-25 11:46:26'),(171,'REFER_STATUS_NUMBER_AMOUNT_4','0.03','推荐人邀请等级对应奖励数量-4级','2019-10-06 11:39:02','2019-10-06 11:39:02'),(172,'REFER_STATUS_NUMBER_AMOUNT_5','0.02','推荐人邀请等级对应奖励数量-5级','2019-10-06 11:39:17','2019-10-06 11:39:17'),(173,'REFER_STATUS_NUMBER_4','2','推荐奖金等级需要邀请人数-4级','2019-10-06 11:39:42','2019-10-06 11:39:42'),(174,'REFER_STATUS_NUMBER_5','2','推荐奖金等级需要邀请人数-5级','2019-10-06 11:39:55','2019-10-06 11:39:55');

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `aaa` int(11) NOT NULL AUTO_INCREMENT,
  `bbb` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`aaa`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `test` */

insert  into `test`(`aaa`,`bbb`) values (1,'gunsTest'),(2,'bizTest'),(3,'gunsTest'),(4,'bizTest'),(5,'gunsTest'),(6,'bizTest');

/*Table structure for table `token_api` */

DROP TABLE IF EXISTS `token_api`;

CREATE TABLE `token_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `cur_block_index` int(11) NOT NULL COMMENT '起始块',
  `rpcurl` varchar(255) NOT NULL COMMENT '区块链接',
  `rpcport` varchar(10) NOT NULL COMMENT '端口',
  `tokenaddress` varchar(255) NOT NULL COMMENT '获取token地址',
  `transmethodid` varchar(255) NOT NULL COMMENT '获取交易地址',
  `balancemethodid` varchar(255) NOT NULL COMMENT '获取余额地址',
  `wei` decimal(16,0) NOT NULL COMMENT 'wei',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `token_api` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `phone` varchar(15) NOT NULL COMMENT '手机号',
  `userpassword` varchar(255) NOT NULL COMMENT '密码',
  `username` varchar(50) NOT NULL COMMENT '姓名',
  `uuid` int(20) NOT NULL DEFAULT '0' COMMENT 'uuid',
  `referenceid` int(20) DEFAULT NULL COMMENT '邀请人id',
  `reference_status` int(2) NOT NULL DEFAULT '0' COMMENT '邀请等级',
  `idcard` varchar(20) NOT NULL COMMENT '身份证号',
  `idstatus` int(2) NOT NULL COMMENT '实名状态 0未认证 1一级 2二级 3三级',
  `headpath` varchar(255) NOT NULL COMMENT '头像地址',
  `openid` varchar(255) DEFAULT NULL COMMENT 'openid',
  `secretkey` varchar(255) NOT NULL COMMENT '密钥',
  `token` varchar(255) NOT NULL COMMENT 'token',
  `tokencreatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'token创建时间',
  `orderpwd` varchar(50) NOT NULL COMMENT '交易密码',
  `logintime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `devicenum` varchar(255) NOT NULL COMMENT '设备',
  `state` int(11) NOT NULL COMMENT '状态',
  `role` int(11) NOT NULL COMMENT '权限',
  `nickname` varchar(11) NOT NULL COMMENT '昵称',
  `partnerflag` int(11) NOT NULL COMMENT '合伙人身份',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `phonePk` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`phone`,`userpassword`,`username`,`uuid`,`referenceid`,`reference_status`,`idcard`,`idstatus`,`headpath`,`openid`,`secretkey`,`token`,`tokencreatetime`,`orderpwd`,`logintime`,`devicenum`,`state`,`role`,`nickname`,`partnerflag`,`createtime`,`updatetime`) values (2,'13322222222','553e83ca69693b33ef73958c04b7a315','13322222222',37903824,NULL,0,'',2,'http://47.104.142.76:8080/file/showImg?imgUrl=/20190804/1564905074157_637.png',NULL,'61j2XdNIRTsnBRyi','1a388eec0aa94fc09d11e4813e32eaef','2019-09-20 11:26:18','','2019-09-20 11:26:18','1',1,0,'2222',2,'2019-09-20 11:24:42','2019-09-20 11:24:42');

/*Table structure for table `user_auth_record` */

DROP TABLE IF EXISTS `user_auth_record`;

CREATE TABLE `user_auth_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '认证类型 1一级 2二级 3三级',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '结果 0失败 1成功 2待验证',
  `video_url` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '视频地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_auth_record` */

insert  into `user_auth_record`(`id`,`user_id`,`type`,`state`,`video_url`,`create_time`,`update_time`) values (1,2,1,0,'','2019-09-26 11:14:22','2019-09-26 11:14:22'),(2,2,1,1,'','2019-09-26 11:32:49','2019-09-26 11:32:49'),(3,2,3,2,'370883199409167412','2019-09-26 11:49:24','2019-09-26 11:49:24');

/*Table structure for table `user_bind_account` */

DROP TABLE IF EXISTS `user_bind_account`;

CREATE TABLE `user_bind_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `type` int(2) NOT NULL COMMENT '类型',
  `account` varchar(50) NOT NULL COMMENT '账户',
  `token` varchar(255) NOT NULL COMMENT 'token',
  `expfield1` varchar(255) NOT NULL COMMENT 'expfield',
  `expfield2` varchar(255) NOT NULL COMMENT 'expfield',
  `expfield3` varchar(255) NOT NULL COMMENT 'expfield',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_bind_account` */

/*Table structure for table `user_diginfo` */

DROP TABLE IF EXISTS `user_diginfo`;

CREATE TABLE `user_diginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `digflag` tinyint(2) NOT NULL COMMENT '挖矿状态',
  `starttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `digcalcul` int(15) NOT NULL COMMENT '算力',
  `logindays` int(15) NOT NULL COMMENT '登录时间',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `dayrewardstate` tinyint(2) NOT NULL COMMENT '每天领取',
  `tenrewardstate` tinyint(2) NOT NULL COMMENT '每周领取',
  `monthrewardstate` tinyint(2) NOT NULL COMMENT '每月领取',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_diginfo` */

/*Table structure for table `withdraw` */

DROP TABLE IF EXISTS `withdraw`;

CREATE TABLE `withdraw` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `type` int(11) NOT NULL COMMENT '类型',
  `accounttype` int(11) NOT NULL COMMENT '账户类型',
  `payaddress` varchar(11) NOT NULL COMMENT '支付地址',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `fee` decimal(16,8) NOT NULL COMMENT '手续费',
  `remain` decimal(16,8) NOT NULL COMMENT '剩余',
  `cointype` int(11) NOT NULL COMMENT '币种',
  `state` int(11) NOT NULL COMMENT '状态',
  `ordernum` varchar(255) NOT NULL COMMENT '交易数量',
  `operid` int(11) NOT NULL COMMENT '操作id',
  `remark` varchar(11) NOT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `withdraw` */

/*Table structure for table `yubi_profit` */

DROP TABLE IF EXISTS `yubi_profit`;

CREATE TABLE `yubi_profit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `cointype` int(2) NOT NULL COMMENT '币种',
  `principle` decimal(16,8) NOT NULL COMMENT '数量',
  `rate` decimal(16,8) NOT NULL COMMENT '费率',
  `interest` decimal(16,8) NOT NULL COMMENT '盈利',
  `yubitotalprofit` decimal(16,8) NOT NULL COMMENT '总额',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`),
  KEY `cointypePk` (`cointype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yubi_profit` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
