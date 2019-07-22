package com.yibi.core.service.impl;

import com.yibi.core.dao.OdinRewardRecoedMapper;
import com.yibi.core.entity.OdinRewardRecoed;
import com.yibi.core.service.OdinRewardRecoedService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-19 16:55:31
 **/ 
@Service("odinRewardRecoedService")
public class OdinRewardRecoedServiceImpl implements OdinRewardRecoedService {
    @Resource
    private OdinRewardRecoedMapper odinRewardRecoedMapper;

    private static final Logger logger = LoggerFactory.getLogger(OdinRewardRecoedServiceImpl.class);

    @Override
    public int insert(OdinRewardRecoed record) {
        return this.odinRewardRecoedMapper.insert(record);
    }

    @Override
    public int insertSelective(OdinRewardRecoed record) {
        return this.odinRewardRecoedMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OdinRewardRecoed record) {
        return this.odinRewardRecoedMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OdinRewardRecoed record) {
        return this.odinRewardRecoedMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.odinRewardRecoedMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OdinRewardRecoed selectByPrimaryKey(Integer id) {
        return this.odinRewardRecoedMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OdinRewardRecoed> selectAll(Map<Object, Object> param) {
        return this.odinRewardRecoedMapper.selectAll(param);
    }

    @Override
    public List<OdinRewardRecoed> selectPaging(Map<Object, Object> param) {
        return this.odinRewardRecoedMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.odinRewardRecoedMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> countThisNumberInfo(Integer userId, Integer number) {
        return this.odinRewardRecoedMapper.countThisNumberInfo(userId, number);
    }

    @Override
    public List<Map<String, Object>> getRankByNumber(Integer number) {
        return this.odinRewardRecoedMapper.getRankByNumber(number);
    }

    @Override
    public List<Map<String, Object>> countAllNumberInfo(Integer userId) {
        return this.odinRewardRecoedMapper.countAllNumberInfo(userId);
    }
}