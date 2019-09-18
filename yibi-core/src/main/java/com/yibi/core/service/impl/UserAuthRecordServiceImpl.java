package com.yibi.core.service.impl;

import com.yibi.core.dao.AboutInfoMapper;
import com.yibi.core.dao.UserAuthRecordMapper;
import com.yibi.core.entity.AboutInfo;
import com.yibi.core.entity.UserAuthRecord;
import com.yibi.core.service.AboutInfoService;
import com.yibi.core.service.UserAuthRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-18 16:54:11
 **/ 
@Service("userAuthRecordService")
public class UserAuthRecordServiceImpl implements UserAuthRecordService {
    @Resource
    private UserAuthRecordMapper userAuthRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserAuthRecordServiceImpl.class);

    @Override
    public int insert(UserAuthRecord record) {
        return this.userAuthRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(UserAuthRecord record) {
        return this.userAuthRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserAuthRecord record) {
        return this.userAuthRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(UserAuthRecord record) {
        return this.userAuthRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.userAuthRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UserAuthRecord selectByPrimaryKey(Integer id) {
        return this.userAuthRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserAuthRecord> selectAll(Map<Object, Object> param) {
        return this.userAuthRecordMapper.selectAll(param);
    }

    @Override
    public List<UserAuthRecord> selectPaging(Map<Object, Object> param) {
        return this.userAuthRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.userAuthRecordMapper.selectCount(param);
    }
}