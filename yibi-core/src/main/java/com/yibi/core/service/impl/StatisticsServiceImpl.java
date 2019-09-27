package com.yibi.core.service.impl;

import com.yibi.core.dao.StatisticsMapper;
import com.yibi.core.entrty.Statistics;
import com.yibi.core.service.StatisticsService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-09-27 17:54:33
 **/ 
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Override
    public int insert(Statistics record) {
        return this.statisticsMapper.insert(record);
    }

    @Override
    public int insertSelective(Statistics record) {
        return this.statisticsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(Statistics record) {
        return this.statisticsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Statistics record) {
        return this.statisticsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.statisticsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Statistics selectByPrimaryKey(Integer id) {
        return this.statisticsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Statistics> selectAll(Map<Object, Object> param) {
        return this.statisticsMapper.selectAll(param);
    }

    @Override
    public List<Statistics> selectPaging(Map<Object, Object> param) {
        return this.statisticsMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.statisticsMapper.selectCount(param);
    }
}