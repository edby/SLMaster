package com.yibi.orderapi.biz.impl;

import com.yibi.common.model.PageModel;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.exception.BanlanceNotEnoughException;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.OdinBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class OdinBizImpl extends BaseBizImpl implements OdinBiz {

    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private OdinRewardRecoedService odinRewardRecoedService;
    @Autowired
    private OdinBuyingRankService odinBuyingRankService;
    @Autowired
    private OdinBuyingRecordService odinBuyingRecordService;

    @Override
    public String buy(User user, String amount, String ecnAmount, String password) {
        //验证功能开关
        String onoff = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_ONOFF);
        if(GlobalParams.INACTIVE == Integer.valueOf(onoff)){
            return Result.toResult(ResultCode.ODIN_NO_ACCESS);
        }
        Integer userId = user.getId();
        BigDecimal amountBig = new BigDecimal(amount);
        BigDecimal ecnAmountBig = new BigDecimal(ecnAmount);
        //验证交易密码
        String validateOrderPassword = validateOrderPassword(user, password);
        if (validateOrderPassword != null) {
            return validateOrderPassword;
        }
        //验证余额
        Account account = accountService.getAccountByUserAndCoinTypeAndAccount(userId, CoinType.ENC, AccountType.ACCOUNT_SPOT);
        if(account.getAvailbalance().compareTo(ecnAmountBig) < 0){
            return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
        }

        /*--------------------验证购买条件是否满足-----------------*/
        //期数
        Integer number = sysparamsService.getValIntByKey(SystemParams.ODIN_BUYING_NUMBER);
        //个人单日限额标准
        String personQuota = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_PERSON_QUOTA);
        //个人单日限额
        String quota = odinBuyingRecordService.countUserOnceDayAmount(userId, number);
        if(quota != null && new BigDecimal(personQuota).compareTo(new BigDecimal(quota)) < 0){
            return Result.toResult(ResultCode.ODIN_BUY_PERSON_MORE);
        }
        //统计平台单日已认购金额
        quota = odinBuyingRecordService.countPlatFormOnceDayAmount(number);
        //平台单日限额
        String platformQuota = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_PLATFORM_QUOTA);
        if(quota != null && new BigDecimal(platformQuota).compareTo(new BigDecimal(quota)) < 0){
            //修改认购功能开关
            Sysparams buyOnOff = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_ONOFF);
            buyOnOff.setKeyval(String.valueOf(GlobalParams.INACTIVE));
            sysparamsService.updateByPrimaryKeySelective(buyOnOff);
            return Result.toResult(ResultCode.ODIN_BUY_PLATFORM_MORE);
        }
        /*--------------------保存认购记录------------------------*/
        OdinBuyingRecord odinBuyingRecord = new OdinBuyingRecord();
        odinBuyingRecord.setUserId(userId);
        odinBuyingRecord.setAmount(ecnAmountBig);
        //本期购买金额
        BigDecimal buyPrice = new BigDecimal(sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_THIS_PRICE));
        odinBuyingRecord.setBuyPrice(buyPrice);
        odinBuyingRecord.setNumber(number);
        //可购买奥丁数量
        odinBuyingRecord.setGetOdinAmount(amountBig);
        //保存认购记录
        odinBuyingRecordService.insertSelective(odinBuyingRecord);

        /*----------------------推荐人人奖励------------------------*/
        User referUser = userService.selectByUUID(user.getReferenceid());
        String referOdinRate = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_REFERENCE_ODIN_RATE);
        String referECNRate = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_REFERENCE_ECN_RATE);
        accountService.updateAccountAndInsertFlow(referUser.getId(), AccountType.ACCOUNT_YUBI, CoinType.YEZI, BigDecimal.ZERO, amountBig.multiply(new BigDecimal(referOdinRate)), referUser.getId(), "ODIN认购推荐奖励", odinBuyingRecord.getId());
        accountService.updateAccountAndInsertFlow(referUser.getId(), AccountType.ACCOUNT_SPOT, CoinType.ENC, ecnAmountBig.multiply(new BigDecimal(referECNRate)), BigDecimal.ZERO, referUser.getId(), "ODIN认购推荐奖励", odinBuyingRecord.getId());

        //插入奖励记录
        OdinRewardRecoed odinRewardRecoed = new OdinRewardRecoed();
        odinRewardRecoed.setNumber(sysparamsService.getValIntByKey(SystemParams.ODIN_BUYING_RANK_NUMBER));
        odinRewardRecoed.setUnionAmount(ecnAmountBig.multiply(new BigDecimal(referECNRate)));
        odinRewardRecoed.setOrderAmount(amountBig.multiply(new BigDecimal(referOdinRate)));
        odinRewardRecoed.setUserId(referUser.getId());
        odinRewardRecoedService.insertSelective(odinRewardRecoed);

        /*-------------------更新账户和记录流水-------------------*/
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_YUBI, CoinType.YEZI, BigDecimal.ZERO, amountBig, userId, "ODIN认购", odinBuyingRecord.getId());
        accountService.updateAccountAndInsertFlow(userId, AccountType.ACCOUNT_SPOT, CoinType.ENC, BigDecimalUtils.plusMinus(ecnAmountBig), BigDecimal.ZERO, userId, "ODIN认购", odinBuyingRecord.getId());

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String init() {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new LinkedList<>();
        list.add(SystemParams.ODIN_BUYING_NUMBER);
        list.add(SystemParams.ODIN_BUYING_TIME);
        list.add(SystemParams.ODIN_BUYING_THIS_PRICE);
        list.add(SystemParams.ODIN_BUYING_NEXT_PRICE);
        list.add(SystemParams.ODIN_BUYING_NOW_ORDER_PRICE);
        list.add(SystemParams.ODIN_BUYING_BAR);
        list.add(SystemParams.ODIN_BUYING_AMOUNT_LIST);
        for(String sysKey : list){
            Sysparams sysparams = sysparamsService.getValByKey(sysKey);
            if(SystemParams.ODIN_BUYING_AMOUNT_LIST.equals(sysKey)){
                String c = sysparams.getKeyval().replace("[","").replace("]","");
                String[] arrays = c.split(",");
                List<String> lists = new LinkedList<>(Arrays.asList(arrays));
                map.put(sysparams.getKeyname(), lists);
            }else {
                map.put(sysparams.getKeyname(), sysparams.getKeyval());
            }
        }
        String thisPrice = map.get(SystemParams.ODIN_BUYING_THIS_PRICE).toString();
        List<String> amountList = (List<String>) map.get(SystemParams.ODIN_BUYING_AMOUNT_LIST);
        List<Map<String, Object>> canAmount = new ArrayList<>();
        for(String amount : amountList){
            Map<String, Object> maps = new HashMap<>();
            maps.put(amount, new BigDecimal(amount).divide(new BigDecimal(thisPrice), 2, BigDecimal.ROUND_HALF_UP).toPlainString());
            canAmount.add(maps);
        }
        map.put("cannGetAmount", canAmount);
        String sysPath = sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL);
        map.put("docUrl", sysPath + "/web/doc/1.action");
        String state = sysparamsService.getValStringByKey(SystemParams.ODIN_BUYING_ONOFF);
        map.put("state", state);
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public boolean checkAmount(String amount) {
        Sysparams sysparams = sysparamsService.getValByKey(SystemParams.ODIN_BUYING_AMOUNT_LIST);
        String c = sysparams.getKeyval().replace("[","").replace("]","");
        String[] arrays = c.split(",");
        List<String> lists = new LinkedList<>(Arrays.asList(arrays));
        for(String val : lists){
            if(val.equals(amount)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String reward(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        Integer userId = user.getId();
        Integer number = sysparamsService.getValIntByKey(SystemParams.ODIN_BUYING_RANK_NUMBER);
        //本期奖励
        List<Map<String, Object>> resultList = odinRewardRecoedService.countThisNumberInfo(userId, number);
        resultList.removeAll(Collections.singleton(null));
        if(resultList.size() == 0){
            resultMap.put("thisUnion", 0);
            resultMap.put("thisOrder", 0);
        }else{
            resultMap.put("thisUnion", resultList.get(0).get("unionAmount"));
            resultMap.put("thisOrder", resultList.get(0).get("orderAmount"));
        }
        //累计奖励
        resultList = odinRewardRecoedService.countAllNumberInfo(userId);
        resultList.removeAll(Collections.singleton(null));
        if(resultList.size() == 0){
            resultMap.put("allUnion", 0);
            resultMap.put("allOrder", 0);
        }else{
            resultMap.put("allUnion", resultList.get(0).get("unionAmount"));
            resultMap.put("allOrder", resultList.get(0).get("orderAmount"));
        }
        //获取前10排名
        resultList = odinRewardRecoedService.getRankByNumber(number);
        String rank = "";
        List<Map<String, Object>> topList = new LinkedList<>();
        int i = 1;
        for(Map<String, Object> map : resultList){
            if(i < 4) {
                Map<String, Object> newMap = new HashMap<>();
                String phone = map.get("phone").toString();
                phone = phone.substring(0, 3) + "****" + phone.substring(7);
                newMap.put("phone", phone);
                newMap.put("rank", i);
                newMap.put("amount", map.get("unionAmount"));
                topList.add(newMap);
                i++;
            }
            String uId = map.get("user_id").toString();
            if(userId.toString().equals(uId)){
                rank =map.get("rank").toString();
            }
        }
        resultMap.put("rank", "".equals(rank) ? "暂无排名" : rank);
        resultMap.put("topList", topList);
        resultMap.put("referCode", user.getUuid());
        String sysPath = sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL);
        resultMap.put("docUrl", sysPath + "/web/doc/2.action");
        //推荐人信息
        User referUser = userService.selectByUUID(user.getUuid());
        String referPhone = referUser.getPhone();
        resultMap.put("referPhone", referPhone.substring(0, 3) + "****" + referPhone.substring(7));
        resultMap.put("qrCode", "http://qr.topscan.com/api.php?text=" + sysPath + "/web/register.action?phone=" + referUser.getUuid());
        return Result.toResult(ResultCode.SUCCESS, resultMap);
    }

    @Override
    public String inviteList(User user, PageModel pageModel) {
        Integer firstResult = pageModel.getFirstResult();
        Integer maxResult = pageModel.getMaxResult() == null ? 10 : pageModel.getMaxResult();
        List<User> referList = userService.queryReferUserList(user.getId(), firstResult, maxResult);
        List<Map<String, Object>> resultList = new LinkedList<>();
        referList.removeAll(Collections.singleton(null));
        if(referList.size() != 0){
            for(User referUser : referList){
                Map<String, Object> map = new HashMap<>();
                String totalEcn = odinBuyingRecordService.getEcnTotalBuyingByUser(referUser.getId());
                map.put("amount", totalEcn == null || StrUtils.isBlank(totalEcn) ? "0" : totalEcn);
                String phone = referUser.getPhone();
                map.put("phone", phone.substring(0, 3) + "****" + phone.substring(7));
                map.put("create_time", referUser.getCreatetime());
                resultList.add(map);
            }
        }
        /*List<Map<String, Object>> lists = odinBuyingRecordService.selectAmountAndPhoneAndTimeByReferId(user.getUuid(), firstResult, maxResult);
        for(Map<String, Object> map : lists){
            String phone = map.get("phone").toString();
            phone = phone
            map.put("phone", phone);
        }*/
        return Result.toResult(ResultCode.SUCCESS, resultList);
    }

    @Override
    public String moreRank(User user, PageModel pageModel) {
        Integer firstResult = pageModel.getFirstResult();
        Integer maxResult = pageModel.getMaxResult() == null ? 10 : pageModel.getMaxResult();
        List<Map<String, Object>> lists = odinBuyingRankService.getMoreRank(firstResult, maxResult);
        List<Map<String, Object>> list = new LinkedList<>();
        List<Map<String, Object>> rankList = new LinkedList<>();
        List<Map<String, Object>> resultList = new LinkedList<>();
        Integer userId = user.getId();
        for(Map<String, Object> map : lists){
            Map<String, Object> maps = new HashMap<>();
            maps.put("onePhone", map.get("onePhone"));
            maps.put("oneAmount", map.get("oneAmount"));
            maps.put("twoPhone", map.get("twoPhone"));
            maps.put("twoAmount", map.get("twoAmount"));
            maps.put("threePhone", map.get("threePhone"));
            maps.put("threeAmount", map.get("threeAmount"));
            Integer number = Integer.valueOf(map.get("number").toString());
            maps.put("number", number);
            maps.put("time", map.get("create_time"));
            rankList = odinRewardRecoedService.getRankByNumber(number);
            String rank = "";
            for(Map<String, Object> rankMap : rankList){
                String uId = rankMap.get("user_id").toString();
                if(userId.toString().equals(uId)){
                    rank = map.get("rank") == null ? "" : map.get("rank").toString();
                }
            }
            maps.put("rank", "".equals(rank) ? "暂无排名" : rank);
            list.add(maps);
        }
        return Result.toResult(ResultCode.SUCCESS, list);
    }
}
