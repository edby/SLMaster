package com.yibi.core.service.impl;

import com.yibi.core.dao.MortgageRecordMapper;
import com.yibi.core.entity.MortgageRecord;
import com.yibi.core.service.MortgageRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-11-23 15:06:48
 **/ 
@Service("mortgageRecordService")
public class MortgageRecordServiceImpl implements MortgageRecordService {
    @Resource
    private MortgageRecordMapper mortgageRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(MortgageRecordServiceImpl.class);

    @Override
    public int insert(MortgageRecord record) {
        return this.mortgageRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(MortgageRecord record) {
        return this.mortgageRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(MortgageRecord record) {
        return this.mortgageRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(MortgageRecord record) {
        return this.mortgageRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.mortgageRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public MortgageRecord selectByPrimaryKey(Integer id) {
        return this.mortgageRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MortgageRecord> selectAll(Map<Object, Object> param) {
        return this.mortgageRecordMapper.selectAll(param);
    }

    @Override
    public List<MortgageRecord> selectPaging(Map<Object, Object> param) {
        return this.mortgageRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.mortgageRecordMapper.selectCount(param);
    }

    @Override
    public String selectTotalByUserAndCoinType(Integer id, Integer coinType) {
        return this.mortgageRecordMapper.selectTotalByUser(id, coinType);
    }
}