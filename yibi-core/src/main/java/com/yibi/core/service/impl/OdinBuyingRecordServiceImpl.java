package com.yibi.core.service.impl;

import com.yibi.core.dao.OdinBuyingRecordMapper;
import com.yibi.core.entity.OdinBuyingRecord;
import com.yibi.core.service.OdinBuyingRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-16 13:38:31
 **/ 
@Service("odinBuyingRecordService")
public class OdinBuyingRecordServiceImpl implements OdinBuyingRecordService {
    @Resource
    private OdinBuyingRecordMapper odinBuyingRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(OdinBuyingRecordServiceImpl.class);

    @Override
    public int insert(OdinBuyingRecord record) {
        return this.odinBuyingRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(OdinBuyingRecord record) {
        return this.odinBuyingRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OdinBuyingRecord record) {
        return this.odinBuyingRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OdinBuyingRecord record) {
        return this.odinBuyingRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.odinBuyingRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OdinBuyingRecord selectByPrimaryKey(Integer id) {
        return this.odinBuyingRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OdinBuyingRecord> selectAll(Map<Object, Object> param) {
        return this.odinBuyingRecordMapper.selectAll(param);
    }

    @Override
    public List<OdinBuyingRecord> selectPaging(Map<Object, Object> param) {
        return this.odinBuyingRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.odinBuyingRecordMapper.selectCount(param);
    }

    @Override
    public String countUserOnceDayAmount(Integer userId, Integer number) {
        return odinBuyingRecordMapper.countUserOnceDayAmount(userId, number);
    }

    @Override
    public String countPlatFormOnceDayAmount(Integer number) {
        return odinBuyingRecordMapper.countPlatFormOnceDayAmount(number);
    }

    @Override
    public List<Map<String, Object>> selectAmountAndPhoneAndTimeByReferId(Map<Object, Object> params, Integer uuid) {
        return odinBuyingRecordMapper.selectAmountAndPhoneAndTimeByReferId(params, uuid);
    }
}