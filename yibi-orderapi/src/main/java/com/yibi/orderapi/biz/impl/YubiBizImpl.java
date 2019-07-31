package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.common.utils.TimeStampUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.orderapi.biz.YubiBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FlowService flowService;
    @Autowired
    private OdinReleaseRecordService odinReleaseRecordService;
    @Autowired
    private OdinBuyingRecordService odinBuyingRecordService;
    @Override
    public String transfer(User user, String password, BigDecimal amount, Integer type, Integer coinType) {

        CoinScale coinScale = coinScaleService.queryByCoin(coinType,CoinType.NONE);
        if(coinScale!=null){
            amount = BigDecimalUtils.roundDown(amount,coinScale.getYubiscale());
        }
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        /*功能开关*/
        if(type==GlobalParams.TRANSFER_TYPE_IN){
            if(coinManage.getSpottoyubionoff() == GlobalParams.INACTIVE){
                return  Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
            }
        }else{
            if(coinManage.getYubitospotonoff() == GlobalParams.INACTIVE){
                return  Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
            }
        }

		/*实名认证判断*/
        if(user.getIdstatus()==0){
            return  Result.toResult(ResultCode.USER_NOT_REALNAME);
        }

		/*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }

        int fromType = type==GlobalParams.TRANSFER_TYPE_OUT?GlobalParams.ACCOUNT_TYPE_YUBI:GlobalParams.ACCOUNT_TYPE_SPOT;
        int toType = type==GlobalParams.TRANSFER_TYPE_OUT?GlobalParams.ACCOUNT_TYPE_SPOT:GlobalParams.ACCOUNT_TYPE_YUBI;


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
        accountService.updateAccountAndInsertFlow(user.getId(),fromType,coinType,BigDecimalUtils.plusMinus(amount),BigDecimal.ZERO,user.getId(),"ODIN转出",trans.getId());
        /*增加转入账户并保存流水*/
        accountService.updateAccountAndInsertFlow(user.getId(),toType,coinType,amount,BigDecimal.ZERO,user.getId(),"ODIN转入",trans.getId());

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String queryFlows(User user, Integer coinType, Integer page, Integer rows) {
        Map<String, Object> data = new HashMap();
        CoinScale scale = coinScaleService.queryByCoin(coinType, CoinType.NONE);
        CoinManage manage = coinManageService.queryByCoinType(coinType);
        Account acc = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(),coinType,GlobalParams.ACCOUNT_TYPE_YUBI);

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
        data.put("annualRate",acc.getAvailbalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        Integer pageInt = page==null?0:page;
        Integer rowsInt = rows==null?10:rows;

        List<Flow> list = flowService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinType, GlobalParams.ACCOUNT_TYPE_YUBI, pageInt, rowsInt);
        for (Flow flow : list) {
            flow.setAmount(flow.getAmount().setScale(4, BigDecimal.ROUND_HALF_UP));
            flow.setTime(TimeStampUtils.toTimeString(flow.getCreatetime(),"MM-dd HH:mm"));
            flow.setResultAmount(BigDecimalUtils.toString(flow.getAmount(),4));
        }
        data.put("list",list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String queryBalance(User user, Integer coinType, Integer accountType) {
        CoinManage coinManage = coinManageService.queryByCoinType(coinType);
        Map<String, Object> map = new HashMap<>();
        Account account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(),coinType,accountType);
        map.put("availBalance", account ==null?"0":BigDecimalUtils.toString(account.getAvailbalance()));
        map.put("minTransAmount",BigDecimalUtils.toString(coinManage.getYubitransmin()));


		/*预计到账时间*/
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if(hour>15){
            cal.add(Calendar.DATE, 2);
        }else {
            cal.add(Calendar.DATE, 1);
        }

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        SimpleDateFormat dateFm = new SimpleDateFormat("MM-dd");
        map.put("profitReturnDate", dateFm.format(cal.getTime())+"("+weekDays[w]+")");
        map.put("profitDay", "T+1");
        map.put("minProfitAmount",BigDecimalUtils.toString(coinManage.getYubiprofitminamt()));
        return Result.toResult(ResultCode.SUCCESS, map);
    }



}
