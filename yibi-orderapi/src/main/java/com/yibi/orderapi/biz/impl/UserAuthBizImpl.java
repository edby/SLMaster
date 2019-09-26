package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.UserAuthUtils;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Bank;
import com.yibi.core.entity.Sysparams;
import com.yibi.core.entity.User;
import com.yibi.core.entity.UserAuthRecord;
import com.yibi.core.service.BankService;
import com.yibi.core.service.SysparamsService;
import com.yibi.core.service.UserAuthRecordService;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.biz.BankBiz;
import com.yibi.orderapi.biz.UserAuthBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
public class UserAuthBizImpl implements UserAuthBiz {

    @Autowired
    private SysparamsService sysparamsService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthRecordService userAuthRecordService;
    @Override
    public String level1(String userName, String idCardNumber, User user) {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REAL_NAME_ONOFF);
        if(systemParam==null||systemParam.getKeyval().equals(GlobalParams.OFF)){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        /*用户是否已经实名*/
        if(user.getIdstatus() != GlobalParams.REALNAME_NEW_STATE_NO){
            return Result.toResult(ResultCode.USER_REALNAME_ERROR);
        }
        /*验证身份证是否被使用*/
        Map<Object, Object> param = new HashMap<>();
        param.put("idcard", idCardNumber);
        int count = userService.selectCount(param);
        if(count != 0){
            return Result.toResult(ResultCode.REAL_NAME_IDCARD_EXIST);
        }

        String result = UserAuthUtils.idCardAuth(userName, idCardNumber);
        //认证记录
        UserAuthRecord userAuthRecord = new UserAuthRecord();
        userAuthRecord.setType(GlobalParams.REALNAME_NEW_STATE_ONE);
        userAuthRecord.setUserId(user.getId());
        userAuthRecord.setVideoUrl("");
        if("0000".equals(result)){
            userAuthRecord.setState(GlobalParams.REALNAME_STATE_SUCCESS);
            userAuthRecordService.insertSelective(userAuthRecord);
            user.setIdstatus(GlobalParams.REALNAME_NEW_STATE_ONE);
            userService.updateByPrimaryKeySelective(user);
            return Result.toResult(ResultCode.REAL_NAME_SUCCESS);
        }
        userAuthRecord.setState(GlobalParams.REALNAME_STATE_FALSE);
        userAuthRecordService.insertSelective(userAuthRecord);
        //修改用户信息
        user.setIdstatus(GlobalParams.REALNAME_NEW_STATE_NO);
        userService.updateByPrimaryKeySelective(user);
        return Result.toResult(ResultCode.REAL_NAME_FAIL);
    }

    @Override
    public String level3(String videoUrl, User user) {
        /*判断功能是否关闭*/
        Sysparams systemParam = sysparamsService.getValByKey(SystemParams.REAL_NAME_ONOFF);
        if(systemParam==null||systemParam.getKeyval().equals(GlobalParams.OFF)){
            return Result.toResult(ResultCode.PERMISSION_NO_ACCESS);
        }
        /*用户是否已经实名*/
        if(user.getIdstatus() != GlobalParams.REALNAME_NEW_STATE_TWO){
            return Result.toResult(ResultCode.USER_REALNAME_ERROR);
        }

        //认证记录
        UserAuthRecord userAuthRecord = new UserAuthRecord();
        userAuthRecord.setUserId(user.getId());
        userAuthRecord.setType(GlobalParams.REALNAME_NEW_STATE_THREE);
        userAuthRecord.setState(GlobalParams.REALNAME_STATE_WAIT);
        userAuthRecord.setVideoUrl(videoUrl);
        userAuthRecordService.insertSelective(userAuthRecord);

        //修改用户信息
        user.setIdstatus(GlobalParams.REALNAME_NEW_STATE_TWO);
        userService.updateByPrimaryKeySelective(user);
        return Result.toResult(ResultCode.SUCCESS);
    }
}
