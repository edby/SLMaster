package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.*;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.AccountType;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.YubiBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

@Service
@Transactional
public class YubiBizImpl extends BaseBizImpl implements YubiBiz {
    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Autowired
    private AccountTransferService accountTransferService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private YubiProfitService yubiProfitService;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private OdinReleaseRecordService odinReleaseRecordService;
    @Autowired
    private OdinBuyingRecordService odinBuyingRecordService;
    @Autowired
    private RedisTemplate<String, String> redis;

    @Override
    public String transfer(User user, String password, BigDecimal amount, Integer type, Integer coinType) {

        CoinScale coinScale = coinScaleService.queryByCoin(coinType, CoinType.NONE);
        if (coinScale != null) {
            amount = BigDecimalUtils.roundDown(amount, coinScale.getYubiscale());
        }
        //不能为0
        if(BigDecimal.ZERO.compareTo(amount) == 0){
            return Result.toResult(ResultCode.PARAM_IS_INVALID);
        }
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        /*功能开关*/
        if (type == GlobalParams.TRANSFER_TYPE_IN) {
            if (coinManage.getSpottoyubionoff() == GlobalParams.INACTIVE) {
                return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
            }
        } else {
            if (coinManage.getYubitospotonoff() == GlobalParams.INACTIVE) {
                return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
            }
        }

        /*实名认证判断*/
        if (user.getIdstatus() == 0) {
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

        /*校验交易密码*/
        if (!StrUtils.isBlank(password)) {
            String valiStr = validateOrderPassword(user, password);
            if (valiStr != null) {
                return valiStr;
            }
        }


        int fromType = type == GlobalParams.TRANSFER_TYPE_OUT ? GlobalParams.ACCOUNT_TYPE_YUBI : GlobalParams.ACCOUNT_TYPE_SPOT;
        int toType = type == GlobalParams.TRANSFER_TYPE_OUT ? GlobalParams.ACCOUNT_TYPE_SPOT : GlobalParams.ACCOUNT_TYPE_YUBI;

        //节点钱包转入转出 币种
        String transferCointype = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_TRANSFER_COINTYPE);
        transferCointype = transferCointype.replace("[","").replace("]","");
        String[] coinTypeList = transferCointype.split(",");
        for (String cointypes : coinTypeList) {
            if (cointypes.equals(coinType.toString())) {
                if (type == GlobalParams.TRANSFER_TYPE_IN) {
                    //节点钱包转入转出基数
                    String transferAmount = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_TRANSFER_AMOUNT);
                    if (amount.longValue() % Long.valueOf(transferAmount) != 0) {
                        //操作数量必须为基数的倍数
                        return Result.toResult(ResultCode.ODIN_WALLET_TRANS_AMOUNT_ERROR);
                    }
                    //增加到缓存中 倒计时结束
                    String time = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_TRANSFER_TIME);
                    String redisKey = String.format(RedisKey.ODIN_WALLET_ACCOUNT, user.getId());
                    RedisUtil.addString(redis, redisKey, Long.valueOf(time), amount.toString());
                }
                /*保存划转记录*/
                AccountTransfer trans = new AccountTransfer();
                trans.setFromaccount(fromType);
                trans.setToaccount(toType);
                trans.setUserid(user.getId());
                trans.setCointype(coinType);
                trans.setAmount(amount);
                trans.setRelatedid(0);
                accountTransferService.insert(trans);

                /*减少转出账户并保存流水*/
                accountService.updateAccountAndInsertFlow(user.getId(), fromType, coinType, BigDecimalUtils.plusMinus(amount), BigDecimal.ZERO, user.getId(), "奥丁钱包转出", trans.getId());
                /*增加转入账户并保存流水*/
                if(toType == AccountType.ACCOUNT_YUBI) {
                    accountService.updateAccountAndInsertFlow(user.getId(), toType, coinType, BigDecimal.ZERO, amount, user.getId(), "奥丁钱包转入", trans.getId());
                }else {
                    accountService.updateAccountAndInsertFlow(user.getId(), toType, coinType, amount, BigDecimal.ZERO, user.getId(), "奥丁钱包转入", trans.getId());
                }
            }
        }

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String queryFlows(User user, Integer coinType, Integer page, Integer rows) {
        Map<String, Object> data = new HashMap();
        if (coinType == CoinType.SL) {
            Account acc = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, GlobalParams.ACCOUNT_TYPE_YUBI);

            Integer userId = user.getId();
            //昨日
            OdinReleaseRecord odinReleaseRecord = odinReleaseRecordService.selectLastRecordByUser(userId);
            BigDecimal amount = odinReleaseRecord == null ? BigDecimal.ZERO : odinReleaseRecord.getAmount();
            data.put("lastProfit", amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //累计金额
            String totalProfit = odinReleaseRecordService.getTotalByUser(userId);
            totalProfit = "".equals(totalProfit) || totalProfit == null ? "0" : totalProfit;
            data.put("totalProfit", new BigDecimal(totalProfit).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //总金额
            String totalAmount = odinBuyingRecordService.getOdinTotalBuyingByUser(userId);
            totalAmount = "".equals(totalAmount) || totalAmount == null ? "0" : totalAmount;
            data.put("availBalance", new BigDecimal(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //折合人民币
            BigDecimal totalOfCny = BigDecimalUtils.multiply(new BigDecimal(totalAmount), getPriceOfCNY(coinType));
            data.put("availBalanceOfCny", totalOfCny.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            //锁仓额度
            data.put("forecastProfit", acc.getFrozenblance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //可用额度
            data.put("annualRate", acc.getAvailbalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            Integer pageInt = page == null ? 0 : page;
            Integer rowsInt = rows == null ? 10 : rows;

            List<Flow> list = flowService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, GlobalParams.ACCOUNT_TYPE_YUBI, pageInt, rowsInt);
            for (Flow flow : list) {
                flow.setAmount(flow.getAmount().setScale(4, BigDecimal.ROUND_HALF_UP));
                flow.setTime(TimeStampUtils.toTimeString(flow.getCreatetime(), "MM-dd HH:mm"));
                flow.setResultAmount(BigDecimalUtils.toString(flow.getAmount(), 4));
            }
            data.put("list", list);
        } else{
            Integer userId = user.getId();
            Account acc = accountService.queryByUserIdAndCoinTypeAndAccountType(userId, coinType, GlobalParams.ACCOUNT_TYPE_YUBI);
            //昨日
            String yesDate = DateUtils.getSomeDay(-1);
            String lastProfit = yubiProfitService.selectYestdayProfit(userId, yesDate, CoinType.getCoinName(coinType), coinType);
            lastProfit = StrUtils.isBlank(lastProfit) ? "0" : lastProfit;
            data.put("lastProfit", new BigDecimal(lastProfit).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //累计金额
            String totalProfit = yubiProfitService.selectTotalProfit(userId, CoinType.getCoinName(coinType), coinType);
            totalProfit = StrUtils.isBlank(totalProfit) ? "0" : totalProfit;
            data.put("totalProfit", new BigDecimal(totalProfit).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //总金额
            BigDecimal totalAmount = acc.getAvailbalance().add(acc.getFrozenblance());
            totalAmount = totalAmount.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : totalAmount;
            data.put("availBalance", totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //折合人民币
            BigDecimal totalOfCny = BigDecimalUtils.multiply(totalAmount, getPriceOfCNY(coinType));
            data.put("availBalanceOfCny", totalOfCny.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            //锁仓额度
            data.put("forecastProfit", acc.getFrozenblance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //可用额度
            data.put("annualRate", acc.getAvailbalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

            Integer pageInt = page == null ? 0 : page;
            Integer rowsInt = rows == null ? 10 : rows;

            List<Flow> list = flowService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, GlobalParams.ACCOUNT_TYPE_YUBI, pageInt, rowsInt);
            for (Flow flow : list) {
                flow.setAmount(flow.getAmount().setScale(4, BigDecimal.ROUND_HALF_UP));
                flow.setTime(TimeStampUtils.toTimeString(flow.getCreatetime(), "MM-dd HH:mm"));
                flow.setResultAmount(BigDecimalUtils.toString(flow.getAmount(), 4));
            }
            data.put("list", list);
        }
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String queryBalance(User user, Integer coinType, Integer accountType) {
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        Map<String, Object> map = new HashMap<>();
        Account account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, accountType);
        map.put("availBalance", account == null ? "0" : BigDecimalUtils.toString(account.getAvailbalance()));
        map.put("minTransAmount", BigDecimalUtils.toString(coinManage.getYubitransmin()));


        /*预计到账时间*/
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour > 15) {
            cal.add(Calendar.DATE, 2);
        } else {
            cal.add(Calendar.DATE, 1);
        }

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        SimpleDateFormat dateFm = new SimpleDateFormat("MM-dd");
        map.put("profitReturnDate", dateFm.format(cal.getTime()) + "(" + weekDays[w] + ")");
        map.put("profitDay", "T+1");
        map.put("minProfitAmount", BigDecimalUtils.toString(coinManage.getYubiprofitminamt()));
        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String withdrawFrozen(User user, String password, BigDecimal amount, Integer accountType, Integer coinType) {
        CoinScale coinScale = coinScaleService.queryByCoin(coinType, CoinType.NONE);
        if (coinScale != null) {
            amount = BigDecimalUtils.roundDown(amount, coinScale.getYubiscale());
        }
        //不能为0
        if(BigDecimal.ZERO.compareTo(amount) == 0){
            return Result.toResult(ResultCode.PARAM_IS_INVALID);
        }
        //节点钱包转入转出基数
        String transferAmount = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_TRANSFER_AMOUNT);
        if (amount.longValue() % Long.valueOf(transferAmount) != 0) {
            //操作数量必须为基数的倍数
            return Result.toResult(ResultCode.ODIN_WALLET_TRANS_AMOUNT_ERROR);
        }
        /*实名认证判断*/
        if (user.getIdstatus() == 0) {
            return Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

        /*校验交易密码*/
        if (!StrUtils.isBlank(password)) {
            String valiStr = validateOrderPassword(user, password);
            if (valiStr != null) {
                return valiStr;
            }
        }

        //查询开放提取的币种
        String cointypes = sysparamsService.getValStringByKey(SystemParams.ODIN_WALLET_TRANSFER_COINTYPE);
        if(!StrUtils.isBlank(cointypes)){
            cointypes = cointypes.replace("[","").replace("]","");
            String[] coinTypeList = cointypes.split(",");
            for(String cointype : coinTypeList){
                if(cointype.equals(coinType.toString())){
                    //比较余额
                    Account account = accountService.getAccountByUserAndCoinTypeAndAccount(user.getId(), coinType, accountType);
                    if(amount.compareTo(account.getFrozenblance()) > 0){
                        return Result.toResult(ResultCode.AMOUNT_NOT_ENOUGH);
                    }
                    //保存资金划转记录
                    AccountTransfer trans = new AccountTransfer();
                    trans.setFromaccount(accountType);
                    trans.setToaccount(AccountType.ACCOUNT_SPOT);
                    trans.setUserid(user.getId());
                    trans.setCointype(coinType);
                    trans.setAmount(amount);
                    trans.setRelatedid(0);
                    accountTransferService.insert(trans);
                    //插入流水
                    accountService.updateAccountAndInsertFlow(user.getId(), accountType, coinType, BigDecimal.ZERO, BigDecimalUtils.plusMinus(amount), user.getId(), "奥丁钱包转出", trans.getId());
                    accountService.updateAccountAndInsertFlow(user.getId(), AccountType.ACCOUNT_SPOT, coinType, amount, BigDecimal.ZERO, user.getId(), "奥丁钱包转入", trans.getId());
                    return Result.toResult(ResultCode.SUCCESS);
                }
            }
        }

        return Result.toResult(ResultCode.PERMISSION_NO_OPEN);
    }
}
