package com.yibi.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.yibi.common.model.Grid;
import com.yibi.common.model.Json;
import com.yibi.common.model.PageModel;
import com.yibi.common.model.SessionInfo;
import com.yibi.common.utils.DateUtils;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SmsTemplateCode;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Account;
import com.yibi.core.entity.CoinManage;
import com.yibi.core.entity.User;
import com.yibi.core.entity.Withdraw;
import com.yibi.core.service.AccountService;
import com.yibi.core.service.CoinManageService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserService;
import com.yibi.extern.api.aliyun.smscode.SMSCodeUtil;
import com.yibi.web.service.WithdrawService;
import com.yibi.web.wallet.wallet;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@Log4j
public class WithdrawServiceImpl implements WithdrawService {
    @Autowired
    private com.yibi.core.service.WithdrawService withdrawService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SMSCodeUtil smsCodeUtil;

    @Autowired
    private CoinManageService coinManageService;
    @Autowired
    private SysparamsService sysparamsService;


    @Override
    public Object getWithdrawList(Integer accountType, Integer rows, Integer page, Integer coinType, Integer state, String phone, String username, String orderNum) {
        PageModel pageModel = new PageModel(page,rows);
        Map<Object,Object> map = new HashMap<>();
        map.put("maxResult",pageModel.getMaxResult());
        map.put("firstResult",pageModel.getFirstResult());
        if(coinType != null){
            map.put("coinType",coinType);
        }
        if(accountType != null){
            map.put("accountType",accountType);
        }
        if(state != null){
            map.put("state",state);
        }
        if(!StrUtils.isBlank(phone)){
            map.put("phone",phone);
        }
        if(!StrUtils.isBlank(username)){
            map.put("username",username);
        }
        if(!StrUtils.isBlank(orderNum)){
            map.put("orderNum",orderNum);
        }
        List<Map<String,Object>> list = this.withdrawService.selectWithdrawOrPhone(map);
        int total = this.withdrawService.selectCountWithdrawOrPhone(map);
        Grid grid = new Grid();
        grid.setRows(list);
        grid.setTotal(total);
        return grid;
    }

    @Override
    public Object updateWithdrawState(Integer id, Integer type, HttpSession session) {
        Json json = new Json();
        Withdraw withdraw = this.withdrawService.selectByPrimaryKey(id);
        //查询提现地址
        if (StrUtils.isBlank(withdraw.getPayaddress())){
            json.setMsg("提现地址不能为空！");
            return json;
        }
        //提现订单的状态验证
        if (withdraw.getState() != 0){
            json.setMsg("此订单已经处理过了！");
            return json;
        }
        User user = this.userService.selectByPrimaryKey(withdraw.getUserid());
        User profitUser = userService.getByRole(GlobalParams.ROLE_TYPE_PLATFORM);
        int result = 0;
        if (profitUser == null){
            json.setMsg("收益账户不存在！");
            return json;
        }
        if(type == 1){ //确认提现
            log.info("订单："+withdraw.getOrdernum()+"正在确认");
            //修改订单状态
            withdraw.setState(GlobalParams.WITHDRAW_STATE_FINISH);
            withdraw.setOperid(((SessionInfo)session.getAttribute("loginUser")).getUserid());
            result = this.withdrawService.updateByPrimaryKeySelective(withdraw);
            if (result == 0){
                throw new RuntimeException("更新订单失败状态");
            }
            //更新收益钱包
            Account profitAccount = getAccountByUserAndCoinType(profitUser,withdraw.getCointype());
            if (profitAccount == null){//如果钱包不存在则创建
                profitAccount = new Account();
                profitAccount.setAvailbalance(BigDecimal.ZERO);
                profitAccount.setAccounttype(Integer.valueOf(GlobalParams.ACCOUNT_TYPE_SPOT));
                profitAccount.setFrozenblance(BigDecimal.ZERO);
                profitAccount.setUserid(profitUser.getId());
                profitAccount.setCointype(withdraw.getCointype());
                result = accountService.insertSelective(profitAccount);
                if (result ==0){
                    throw new RuntimeException("新增收益钱包失败");
                }
            }
            //更新收益钱包、添加流水
            accountService.updateAccountAndInsertFlow(profitUser.getId(),GlobalParams.ACCOUNT_TYPE_SPOT,withdraw.getCointype()
            ,withdraw.getFee(),BigDecimal.ZERO,((SessionInfo)session.getAttribute("loginUser")).getUserid(),"提现手续费",withdraw.getId());
            int withdrawOnoff =  sysparamsService.getValIntByKey(SystemParams.AUTO_WITHDRAW_ONOFF);
            if (withdrawOnoff == 1){//判断自动打币开关是否开启
               // 打币操作 。。。。。
               if (withdraw.getCointype() == 1 || withdraw.getCointype() == 3 || withdraw.getCointype() == 12 || withdraw.getCointype() == 13
                       || withdraw.getCointype() == 4 || withdraw.getCointype() == 8){
                   json.setMsg("打币操作暂时不支持此币种！！");
                   return json;
               }
               Map<Object,Object> map = new HashMap<>();
               map.put("cointype",withdraw.getCointype());
               List<CoinManage> list= coinManageService.selectAll(map);
               if (list == null || list.size() == 0){
                   throw  new RuntimeException("提现币种不存在！");
               }
               CoinManage coinManage = list.get(0);
               String resluts = null;
               Map<String, Object> maps = null;
               if (withdraw.getCointype() == 4){//eth 提现
                   resluts = wallet.transferETH(withdraw.getPayaddress(),withdraw.getRemain().toString(),4);
                   maps = (Map<String, Object>)JSON.parse(resluts);

                   if ("false".equals((String) maps.get("result"))){
                       throw  new RuntimeException("打币失败！请核对提现信息");
                   }
               }else if (withdraw.getCointype() == 8){//YT
                   resluts = wallet.transferYT(withdraw.getPayaddress(),withdraw.getRemain().toString());//返回的是交易Id
                   if (StrUtils.isBlank(resluts)){
                       throw  new RuntimeException("打币失败！请核对提现信息");
                   }
               } else if (withdraw.getCointype() == 1 || withdraw.getCointype() == 3 || withdraw.getCointype() == 12 || withdraw.getCointype() == 13){
                   resluts = wallet.transferCommon(coinManage.getGetAddress(),withdraw.getPayaddress(),withdraw.getRemain().toString());
                   maps = (Map<String, Object>)JSON.parse(resluts);
                   if (StrUtils.isBlank((String) maps.get("result"))){
                       throw  new RuntimeException("打币失败！请核对提现信息");
                   }
               }
           }
            //短信通知用户
            Map<String,String> params = new HashMap<>();
            params.put("createTime",DateUtils.getDateFormate(withdraw.getCreatetime()));
            params.put("remain",withdraw.getRemain().toString());
            params.put("orderNum",withdraw.getOrdernum());
            smsCodeUtil.sendSms(user.getPhone(),SmsTemplateCode.SMS_WITHDRAW_COMFIRM,params);
            json.setMsg("确认成功");
            json.setSuccess(true);
        }else {  //撤销 只有现货提现
            log.info("订单："+withdraw.getOrdernum()+"正在撤销");
            //修改订单状态
            withdraw.setState(GlobalParams.WITHDRAW_STATE_CANCEL);
            withdraw.setOperid(((SessionInfo)session.getAttribute("loginUser")).getUserid());
            result = this.withdrawService.updateByPrimaryKeySelective(withdraw);
            if (result ==0){
                throw new RuntimeException("更新订单转态失败");
            }
            //返给用户钱,添加流水
            accountService.updateAccountAndInsertFlow(withdraw.getUserid(),withdraw.getAccounttype(),withdraw.getCointype(),withdraw.getAmount(),
                    BigDecimal.ZERO,((SessionInfo)session.getAttribute("loginUser")).getUserid(),"提现撤销",withdraw.getId());

            json.setMsg("撤销成功");
            //短信通知用户
            Map<String,String> params = new HashMap<>();
            params.put("createTime",DateUtils.getDateFormate(withdraw.getCreatetime()));
            params.put("orderNum",withdraw.getOrdernum());
            smsCodeUtil.sendSms(user.getPhone(),SmsTemplateCode.SMS_WITHDRAW_CANCEL,params);
            json.setSuccess(true);
        }
        return  json;
    }
    public Account getAccountByUserAndCoinType(User user,Integer coinType){
        Map<Object, Object> param = new HashMap<>();
        param.put("userid", user.getId());
        param.put("cointype", coinType);
        param.put("accounttype", GlobalParams.ACCOUNT_TYPE_SPOT);
        List<Account> list = accountService.selectAll(param);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}
