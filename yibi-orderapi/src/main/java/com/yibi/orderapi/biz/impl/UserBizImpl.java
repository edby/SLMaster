package com.yibi.orderapi.biz.impl;

import com.google.common.eventbus.EventBus;
import com.yibi.common.encrypt.MD5;
import com.yibi.common.utils.*;
import com.yibi.core.constants.*;
import com.yibi.core.entity.*;
import com.yibi.core.service.*;
import com.yibi.extern.api.aliyun.cloudauth.AliyunRPBasicAuthenticate;
import com.yibi.extern.api.aliyun.cloudauth.MaterialModel;
import com.yibi.extern.api.rongcloud.request.RongCloudUserRequest;
import com.yibi.orderapi.biz.IdCardValidateBiz;
import com.yibi.orderapi.biz.UserBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/7/10 0010.
 */
@Slf4j
@Service
@Transactional
public class UserBizImpl extends BaseBizImpl implements UserBiz{

    @Resource
    private UserService  userService;
    @Resource
    private SmsRecordService smsRecordService;
    @Resource
    private AccountService accountService;
    @Resource
    private DigcalRecordService digcalRecordService;
    @Resource
    private UserAuthRecordService userAuthRecordService;
    @Resource
    private SysparamsService sysparamsService;
    @Resource
    private UserDiginfoService userDiginfoService;
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private RongCloudUserRequest userRequest;
    @Autowired
    private IdCardValidateBiz idcardValidateBiz;
    @Autowired
    private IdcardValidateService idcardValidateService;
    @Autowired
    private BindInfoService bindInfoService;
    @Autowired
    private DigHonorsService digHonorsService;
    @Autowired
    private CommissionInviteService commissionInviteService;
    @Autowired
    private CoinManageService coinManageService;
    @Resource
    private EventBus orderEventBus;
    @Resource
    private AliyunRPBasicAuthenticate aliyunRPBasicAuthenticate;


    @Override
    public User queryUser() {
        return userService.selectByPrimaryKey(2);
    }

    @Override
    public String register(String phone, String code, Integer codeId, String password, String referPhone, String deviceNum, Integer syetemType) throws Exception {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REGIST_ONOFF);
        if(systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }

		/*校验验证码是否正确*/

        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, phone);
        if(sms==null||!code.equals(sms.getCode())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }

		/*校验验证码有效期*/
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis()-sms.getCreatetime().getTime())/(1000*60));
        if(timeLimit==null || sms.getTimes()!= GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }

		/*校验手机号是否存在*/
        User oldUser = userService.selectByPhone(phone);
        if(oldUser!=null){
            return Result.toResult(ResultCode.USER_HAS_EXISTED);
        }

		/*校验推荐人是否有效*/
        User referUser = null;
        if(!StrUtils.isBlank(referPhone)){
            referUser = userService.selectByUUID(Integer.valueOf(referPhone));
            if(referUser==null){
                return Result.toResult(ResultCode.REFER_USER_NOT_EXIST);
            }

            if(referUser.getState()==GlobalParams.FORBIDDEN){
                return Result.toResult(ResultCode.REFER_USER_ACCOUNT_FORBIDDEN);
            }
            if(referUser.getState()==GlobalParams.LOGOFF){
                return Result.toResult(ResultCode.REFER_USER_ACCOUNT_LOGOFF);
            }
        }

        Integer uuid = getUUID();
		/*保存用户信息*/
        User user = new User();
        user.setPhone(phone);
        user.setUserpassword(MD5.getMd5(password));
        user.setUsername(phone);
        user.setUuid(uuid);
        user.setState(GlobalParams.ACTIVE);//有效
        user.setReferenceid(StrUtils.isBlank(referPhone)?null: referUser.getUuid());
        user.setIdstatus(0);//未认证
        user.setDevicenum(deviceNum);
        user.setRole(GlobalParams.ROLE_TYPE_COMMON);//普通
        user.setIdcard("");
        user.setSecretkey("");
        user.setToken("");
        user.setOrderpwd("");
        user.setNickname(phone.substring(phone.length()-4));
        user.setHeadpath(sysparamsService.getValByKey(SystemParams.DEFAULT_HEAD_IMG_URL).getKeyval());
//        user.setHeadpath("1");
        user.setPartnerflag(2); //普通用户
        userService.insert(user);

        Map map = new HashMap();
        List<CoinManage> list = coinManageService.selectAll(map);
        /*初始化C2C账户*/
        for(int i = 0; i < list.size(); i++){
            Account account = new Account();
            account.setUserid(user.getId());
            account.setCointype(list.get(i).getCointype());
            account.setAvailbalance(new BigDecimal(0));
            account.setFrozenblance(new BigDecimal(0));
            account.setAccounttype(0);
            accountService.insert(account);
        }
        /*初始化现货账户*/
        for(int i = 0; i < list.size(); i++){
            Account account = new Account();
            account.setUserid(user.getId());
            account.setCointype(list.get(i).getCointype());
            account.setAvailbalance(new BigDecimal(0));
            account.setFrozenblance(new BigDecimal(0));
            account.setAccounttype(1);
            accountService.insert(account);
        }

        /*sms.setTimes(GlobalParams.INACTIVE);
        smsRecordService.updateByPrimaryKey(sms);*/

        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String login(String phone, String password, String deviceNum, Integer syetemType, String secretKey) throws Exception {
        User user = userService.selectByPhone(phone);
		
		/*用户状态校验*/
        if(user==null){
            return Result.toResult(ResultCode.USER_NOT_EXIST);
        }
        if(user.getState()==GlobalParams.FORBIDDEN){
            return Result.toResult(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        if(user.getState()==GlobalParams.LOGOFF){
            return Result.toResult(ResultCode.USER_ACCOUNT_LOGOFF);
        }
		
		/*密码校验*/
        String passwordEn = MD5.getMd5(password);
        if(!passwordEn.equals(user.getUserpassword())){
            return Result.toResult(ResultCode.USER_LOGIN_ERROR);
        }

        //登录时给用户添加现有所有账户钱包
        insertAccount(user);
        return getLoginInfo(user,deviceNum,syetemType,secretKey);
    }


    @Override
    public String loginByPhone(String phone, String code, Integer codeId, String deviceNum, Integer systemType, String secretKey) {
        User user = userService.selectByPhone(phone);

		/*用户状态校验*/
        if(user==null){
            return Result.toResult(ResultCode.USER_NOT_EXIST);
        }
        if(user.getState()==GlobalParams.FORBIDDEN){
            return Result.toResult(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        if(user.getState()==GlobalParams.LOGOFF){
            return Result.toResult(ResultCode.USER_ACCOUNT_LOGOFF);
        }

		/*校验验证码是否正确*/
        SmsRecord sms = smsRecordService.getByIdAndPhone(codeId, phone);
        if(sms==null||!code.equals(sms.getCode().toString())){
            if(validateErrorTimesOfSms(codeId)){
                return Result.toResult(ResultCode.SMS_CHECK_ERROR);
            }else{
                return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
            }
        }

		/*校验验证码有效期*/
        Sysparams timeLimit = sysparamsService.getValByKey(SystemParams.SMS_TIME_LIMIT);
        int interval = (int) ((System.currentTimeMillis()-sms.getCreatetime().getTime())/(1000*60));
        if(timeLimit==null || sms.getTimes()!=GlobalParams.ACTIVE|| interval>=Integer.parseInt(timeLimit.getKeyval()) || !validataStateOfSms(codeId)){
            return Result.toResult(ResultCode.SMS_TIME_LIMIT_ERROR);
        }

        //登录时给用户添加现有所有账户钱包
        insertAccount(user);
        return getLoginInfo(user,deviceNum,systemType,secretKey);
    }


    /**
     * 登录时给用户添加现有所有账户钱包
     * @param user
     */
    private void insertAccount(User user) {
        Map map = new HashMap();
        List<CoinManage> list = coinManageService.selectAll(map);
        List<Integer> accountTypeList = new ArrayList<>();
        accountTypeList.add(GlobalParams.ACCOUNT_TYPE_C2C);
        accountTypeList.add(GlobalParams.ACCOUNT_TYPE_SPOT);
        accountTypeList.add(GlobalParams.ACCOUNT_TYPE_YUBI);
        for(Integer accountType : accountTypeList) {
            for(CoinManage coinManage :  list){
                if((coinManage.getCointype() == CoinType.SL && accountType == AccountType.ACCOUNT_YUBI) || (coinManage.getCointype() == CoinType.SL && accountType == AccountType.ACCOUNT_C2C)){
                    continue;
                }
                Account account = accountService.queryByUserIdAndCoinTypeAndAccountType(user.getId(), coinManage.getCointype(), accountType);
                if(account == null){
                    account = new Account();
                    account.setUserid(user.getId());
                    account.setCointype(coinManage.getCointype());
                    account.setAvailbalance(new BigDecimal(0));
                    account.setFrozenblance(new BigDecimal(0));
                    account.setAccounttype(accountType);
                    accountService.insert(account);
                    log.info("用户：【" + user.getPhone() + "】增加了账户：【" + accountType + "】,币种：【" + coinManage.getCointype() + "】");
                }
            }
        }
    }

    @Override
    public String setHeadimg(User user, String imgPath) {
        user.setHeadpath(imgPath);
        userService.updateByPrimaryKey(user);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String setNickname(User user, String nickname) {
        user.setNickname(nickname);
        userService.updateByPrimaryKey(user);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public String bindInfo(User user,String password,String account,String name ,String imgUrl,String bankName,String branchName,Integer type) {
        Map<String, Object>map = new HashMap<String, Object>();
        /*校验交易密码*/
        if(!StrUtils.isBlank(password)){
            String valiStr = validateOrderPassword(user, password);
            if(valiStr!=null){
                return valiStr;
            }
        }

        BindInfo bind = new BindInfo();
        bind.setUserid(user.getId());
        bind.setType(type);
        bind.setAccount(account);
        bind.setName(name);
        bind.setImgurl(imgUrl);
        bind.setBankname(bankName);
        bind.setBranchname(branchName);
        bind.setState(GlobalParams.ACTIVE);
        bindInfoService.updateOrInsertBindInfo(bind);
        return Result.toResult(ResultCode.SUCCESS);
    }

    @Override
    public ResultCode exit(User user) {
        user.setSecretkey("");
        user.setToken("");
        user.setTokencreatetime(null);

        userService.updateByPrimaryKey(user);
        return ResultCode.SUCCESS;
    }

    @Override
    public String getToken(User user) {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REAL_NAME_ONOFF);
        if(systemParam==null||systemParam.getKeyval().equals("-1")){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        if(user.getIdstatus() != GlobalParams.REALNAME_NEW_STATE_ONE){
            return Result.toResult(ResultCode.USER_REALNAME_ERROR);
        }

		/*判断验证次数*/
        Map<String, Object> countMap = idcardValidateBiz.queryValidateTimes(user.getId(),3);
        Sysparams timesLimit = sysparamsService.getValByKey(SystemParams.IDCARD_VALIDATE_TIMES_LIMIT);
        if(timesLimit==null){
            return Result.toResult(ResultCode.SYSTEM_PARAM_ERROR);
        }
        int times = Integer.parseInt(timesLimit.getKeyval());
        if(countMap!=null&&times>0){
            //当日认证次数
            BigInteger dateCount = (BigInteger)countMap.get(DATE.getCurrentDateStr());
            if(dateCount!=null&&dateCount.intValue()>=times){
                return Result.toResult(ResultCode.REAL_NAME_LIMIT);
            }

            //连续两天次数限制
            BigInteger dateCount1 = (BigInteger)countMap.get(DateUtils.getSomeDay(-1));
            BigInteger dateCount2 = (BigInteger)countMap.get(DateUtils.getSomeDay(-2));
            BigInteger dateCount3 = (BigInteger)countMap.get(DateUtils.getSomeDay(-3));
            if((dateCount1!=null&&dateCount1.intValue()>=times&&dateCount2!=null&&dateCount2.intValue()>=times)
                    ||(dateCount2!=null&&dateCount2.intValue()>=times&&dateCount3!=null&&dateCount3.intValue()>=times)){
                return Result.toResult(ResultCode.REAL_NAME_LIMIT);
            }
        }
        String ticketId = UuidUtil.get32UUID();
        String token = aliyunRPBasicAuthenticate.getVerifyToken(ticketId);
        if(token == null){
            return Result.toResult(ResultCode.REAL_NAME_INIT_FAIL);
        }
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("taskId", ticketId);

        return Result.toResult(ResultCode.SUCCESS, map);
    }

    @Override
    public String getCalcul(User user) {
        Map<Object, Object> data = new HashMap<>();
        Map<Object, Object> param = new HashMap<>();
        List<IdcardValidate> u = idcardValidateService.selectAll(param);
        if(u != null && !u.isEmpty() && u.size() != 0){
            data.put("sex", u.get(0).getSex());
        }
        data.put("headUrl", user.getHeadpath());
        data.put("nickName", user.getNickname());
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String getStatus(User user, String taskId) {
        /*用户是否已经实名*/
        if(user.getIdstatus() != GlobalParams.REALNAME_NEW_STATE_ONE){
            return Result.toResult(ResultCode.USER_REALNAME_ERROR);
        }

        int status = aliyunRPBasicAuthenticate.getStatus(taskId);
        //认证记录不存在，直接返回
        if(status==GlobalParams.REALNAME_STATE_NOT_EXIST){
            return Result.toResult(ResultCode.REAL_NAME_TASK_NOT_EXIST);
        }
        //认证中，等待两秒继续请求一次
        if(status==GlobalParams.REALNAME_STATE_ING){
            try {
                TimeUnit.SECONDS.sleep(2);
                status = aliyunRPBasicAuthenticate.getStatus(taskId);
            } catch (InterruptedException e) {
                log.info("实人认证等待被打断---");
                e.printStackTrace();
            }
        }
        HashMap<String, Object> map = new HashMap<>();

        IdcardValidate iv = new IdcardValidate();
        ResultCode code = ResultCode.REAL_NAME_FAIL;
        if(status ==GlobalParams.REALNAME_STATE_SUCCESS ||status==GlobalParams.REALNAME_STATE_FAIL){
            MaterialModel mate = aliyunRPBasicAuthenticate.getMaterials(taskId,status);
            BeanUtils.copyProperties(mate, iv);
            iv.setState(status);
            iv.setTaskid(taskId);
            iv.setUserid(user.getId());
            iv.setIdentificationnumber(mate.getIdentificationNumber());
        }

        if(status==GlobalParams.REALNAME_STATE_SUCCESS){
            User old = userService.getByidcard(iv.getIdentificationnumber());
            //年龄判断
            int age = DateUtils.idCardToAge(iv.getIdentificationnumber().substring(6,14));
            if(age<18||age>70){
                iv.setState(GlobalParams.REALNAME_STATE_AGE_ILLEGAL);
                code = ResultCode.REAL_NAME_AGE_ILLEGAL;
            }
            //身份证是否已经认证过
            else if( old!=null && !old.getId().equals(user.getId())){
                iv.setState(GlobalParams.REALNAME_STATE_IDCARD_EXIST);
                code = ResultCode.REAL_NAME_IDCARD_EXIST;
            }else{
                user.setIdstatus(GlobalParams.REALNAME_NEW_STATE_THREE);
                user.setIdcard(iv.getIdentificationnumber());
                user.setUsername(iv.getName());
                userService.updateByPrimaryKeySelective(user);
                code = ResultCode.SUCCESS;
                map.put("name", iv.getName());
            }
        }
        //认证记录
        UserAuthRecord userAuthRecord = new UserAuthRecord();
        userAuthRecord.setType(GlobalParams.REALNAME_NEW_STATE_TWO);
        userAuthRecord.setUserId(user.getId());
        userAuthRecord.setVideoUrl("");
        if(code == ResultCode.SUCCESS){
            //实名奖励
            commission_realName(user);
            userAuthRecord.setState(GlobalParams.REALNAME_STATE_SUCCESS);
            /*推荐人奖励等级提升*/
            referLevelAward(user.getReferenceid());
        }else{
            userAuthRecord.setState(GlobalParams.REALNAME_STATE_FALSE);
        }
        userAuthRecordService.insertSelective(userAuthRecord);
        return Result.toResult(code,map);
    }

    /**
     * 推荐人奖励等级提升
     * @param uuid
     */
    private void referLevelAward(Integer uuid) {
        User referUser = userService.selectByUUID(uuid);
        /*推荐人奖励等级提升*/
        Integer referStatus = referUser.getReferenceStatus();
        Map<Object, Object> params = new HashMap<>();
        params.put("referenceid", uuid);
        int count = userService.selectCount(params);
        if(referStatus == GlobalParams.REFER_STATUS_0){
            String referNumber = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_1);
            if(count >= Integer.valueOf(referNumber)) {
                referUser.setReferenceStatus(GlobalParams.REFER_STATUS_1);
                userService.updateByPrimaryKeySelective(referUser);
            }
        }else if(referStatus == GlobalParams.REFER_STATUS_1){
            String referNumber = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_2);
            if(count >= Integer.valueOf(referNumber)) {
                referUser.setReferenceStatus(GlobalParams.REFER_STATUS_2);
                userService.updateByPrimaryKeySelective(referUser);
            }
        }else if(referStatus == GlobalParams.REFER_STATUS_2){
            params.put("referenceStatus", 2);
            count = userService.selectCount(params);
            String referNumber = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_3);
            if(count >= Integer.valueOf(referNumber)) {
                referUser.setReferenceStatus(GlobalParams.REFER_STATUS_3);
                userService.updateByPrimaryKeySelective(referUser);
            }
        }else if(referStatus == GlobalParams.REFER_STATUS_3){
            params.put("referenceStatus", 3);
            count = userService.selectCount(params);
            String referNumber = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_4);
            if(count >= Integer.valueOf(referNumber)) {
                referUser.setReferenceStatus(GlobalParams.REFER_STATUS_4);
                userService.updateByPrimaryKeySelective(referUser);
            }
        }else if(referStatus == GlobalParams.REFER_STATUS_4){
            params.put("referenceStatus", 4);
            count = userService.selectCount(params);
            String referNumber = sysparamsService.getValStringByKey(SystemParams.REFER_STATUS_NUMBER_5);
            if(count >= Integer.valueOf(referNumber)) {
                referUser.setReferenceStatus(GlobalParams.REFER_STATUS_5);
                userService.updateByPrimaryKeySelective(referUser);
            }
        }
    }


    /*实名奖励*/
    public void commission_realName(User user){
        Sysparams onoff = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_ONOFF);
        if(onoff == null ||"1".equals(onoff.getKeyval())){
            //奖励币种 json
            Sysparams realCoin = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_COIN);
            String realCoinStr = realCoin.getKeyval();
            String a = realCoinStr.replace("[","").replace("]","");
             if(!StrUtils.isBlank(a)){
                String[] array = a.split(",");
                 //奖励币种对应奖励数量--用户 json
                Sysparams realCoinAmountUser = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_COIN_AMOUNT_USER);
                 //奖励币种对应奖励数量--推荐人 json
                Sysparams realCoinAmountRefer = sysparamsService.getValByKey(SystemParams.COMMISSION_REALNAME_COIN_AMOUNT_REFER);
                Integer i = 0;
                String realCoinAmountStr = realCoinAmountUser.getKeyval();
                String b=realCoinAmountStr.replace("[","").replace("]","");
                String[] realCoinAmountStrArray = b.split(",");
                 //奖励币种对应奖励数量 list
                List<String> realCoinAmountList = new ArrayList<String>();
                for (String str : realCoinAmountStrArray){
                    realCoinAmountList.add(str);
                }
                String realCoinAmountReferStr = realCoinAmountRefer.getKeyval();
                String c=realCoinAmountReferStr.replace("[","").replace("]","");
                String[] realCoinAmountReferStrArray = c.split(",");
                 //奖励币种对应奖励数量 list
                List<String> realCoinAmountReferList = new ArrayList<String>();
                for (String str : realCoinAmountReferStrArray){
                    realCoinAmountReferList.add(str);
                }
                for (int j =0;j<array.length ;j++) {
                    Integer coinType = Integer.valueOf(array[j]);
                    String amountUser;
                    String amountRefer;
                    CommissionInvite comm = null;
                    try {
                        amountUser = realCoinAmountList.get(i);
                        amountRefer = realCoinAmountReferList.get(i);
                        comm = createCommission(user, coinType, amountUser, amountRefer);
                    } catch (Exception e1) {
                        throw new RuntimeException("系统参数格式配置错误");
                    }
                    try {
                        //用户奖励
                        accountService.updateAccountAndInsertFlow(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, coinType, comm.getAmount(), BigDecimal.ZERO, user.getId(), "实名奖励", comm.getId());
                        //推荐人奖励
                        if(user.getReferenceid()!=null && user.getReferenceid() >0) {
                            User referUser = userService.selectByUUID(user.getReferenceid());
                            if (!"".equals(referUser.getToken()) && referUser.getToken().length() > 5) {
                                accountService.updateAccountAndInsertFlow(referUser.getId(), GlobalParams.ACCOUNT_TYPE_SPOT, coinType, comm.getReferamount(), BigDecimal.ZERO, referUser.getId(), "实名推荐人奖励", comm.getId());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                    i++;
                    log.info("【实名认证奖励】【"+user.getPhone()+"】增加了币种编码【"+coinType+"】数量【"+amountUser+"】");
                }
            }
        }
    }

    public CommissionInvite createCommission(User user, Integer coinType, String amountUser, String amountRefer){
        CommissionInvite comm = new CommissionInvite();
        comm.setUserid(user.getId());
        comm.setCointype(coinType);
        comm.setAmount(new BigDecimal(amountUser));
        comm.setReferuserid(user.getReferenceid());
        comm.setReferamount(new BigDecimal(amountRefer));
        comm.setType(GlobalParams.COMMISSION_TYPE_REALNAME);
        commissionInviteService.insertSelective(comm);
        return comm;
    }


    /**
     * 从redis中获取错误次数并加1返回
     * @param key
     * @return int
     * @date 2018-1-26
     * @author lina
     */
    private int getNextErrorTimes(String key){
        String val = RedisUtil.searchString(redis, key);
        if(StrUtils.isBlank(val)){
            return 1;
        }
        return Integer.parseInt(val)+1;
    }
    /**
     * 获取登录信息
     * @param user
     * @param deviceNum
     * @param systemType
     * @param secretKey
     * @return String
     * @date 2018-4-19
     * @author lina
     */
    public String getLoginInfo(User user,String deviceNum, Integer systemType,String secretKey){


        user.setLogintime(new java.sql.Date(new Date().getTime()));
        user.setDevicenum(deviceNum==null?"":deviceNum);
        user.setToken(UUIDs.uuid());
        user.setTokencreatetime(new java.sql.Date(new Date().getTime()));
        user.setSecretkey(secretKey);
        userService.updateByPrimaryKey(user);


        Map map = new HashMap();
        map.put("userid", user.getId());
        List<UserDiginfo> list= userDiginfoService.selectAll(map);
        UserDiginfo diginfo = list == null || list.isEmpty() ? null :list.get(0);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", user.getNickname());
        data.put("phone", user.getPhone());
        data.put("token", user.getToken());
//        data.put("talkToken", bindAccount.getToken());
        data.put("headImg", user.getHeadpath());
        data.put("orderPwdFlag", !StrUtils.isBlank(user.getOrderpwd()));
        data.put("idCheckFlag", user.getIdstatus()==GlobalParams.ACTIVE);
        data.put("digFlag", diginfo==null?false:diginfo.getDigflag());

        Map<Object, Object> param = new HashMap<>();
        param.put("userId", user.getId());
        param.put("state", GlobalParams.REALNAME_STATE_SUCCESS);
        List<IdcardValidate> idCard = idcardValidateService.selectAll(param);
        if(idCard != null && !idCard.isEmpty() && idCard.size() != 0){
            data.put("sex", "m".equals(idCard.get(0).getSex())?GlobalParams.SEX_MALE:GlobalParams.SEX_FAMALE);
            data.put("birthday", StrUtils.getBirthdayFromIdCard(idCard.get(0).getIdentificationnumber()));
        }

        Map<Integer, Object> payInfo = new HashMap<Integer, Object>();

        Map<Object, Object> bindsParam = new HashMap<>();
        bindsParam.put("userid", user.getId());
        bindsParam.put("state", 1);
        List<BindInfo> binds = bindInfoService.selectAll(bindsParam);

        for(BindInfo bind:binds){
             BindInfoModel infoM  = new BindInfoModel();
            infoM.setBankName(bind.getBankname());
            infoM.setBranchName(bind.getBranchname());
            BeanUtils.copyProperties(bind, infoM);
            payInfo.put(bind.getType(), infoM);
        }
        data.put("bindInfo", payInfo);
        data.put("secretkey", user.getSecretkey());
        data.put("uuid", user.getUuid());

        return Result.toResult(ResultCode.SUCCESS, data);
    }

    private Integer getUUID(){
        Integer uuid = UUIDs.getUUID8();
        if(uuid < 10000000){
            return getUUID();
        }
        User uuser = userService.selectByUUID(uuid);
        if(uuser == null){
            return uuid;
        }else{
            return getUUID();
        }
    }

}
