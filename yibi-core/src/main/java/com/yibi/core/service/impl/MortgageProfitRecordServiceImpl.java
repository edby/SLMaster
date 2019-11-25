package com.yibi.core.service.impl;

import com.yibi.core.dao.MortgageProfitRecordMapper;
import com.yibi.core.entity.MortgageProfitRecord;
import com.yibi.core.entity.User;
import com.yibi.core.service.MortgageProfitRecordService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-11-23 15:06:36
 **/ 
@Service("mortgageProfitRecordService")
public class MortgageProfitRecordServiceImpl implements MortgageProfitRecordService {
    @Resource
    private MortgageProfitRecordMapper mortgageProfitRecordMapper;

    private static final Logger logger = LoggerFactory.getLogger(MortgageProfitRecordServiceImpl.class);

    @Override
    public int insert(MortgageProfitRecord record) {
        return this.mortgageProfitRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(MortgageProfitRecord record) {
        return this.mortgageProfitRecordMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(MortgageProfitRecord record) {
        return this.mortgageProfitRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(MortgageProfitRecord record) {
        return this.mortgageProfitRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.mortgageProfitRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public MortgageProfitRecord selectByPrimaryKey(Integer id) {
        return this.mortgageProfitRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MortgageProfitRecord> selectAll(Map<Object, Object> param) {
        return this.mortgageProfitRecordMapper.selectAll(param);
    }

    @Override
    public List<MortgageProfitRecord> selectPaging(Map<Object, Object> param) {
        return this.mortgageProfitRecordMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.mortgageProfitRecordMapper.selectCount(param);
    }

    @Override
    public String getYestodayProfit(Map<Object, Object> param) {
        return this.mortgageProfitRecordMapper.getYestodayProfit(param);
    }
}