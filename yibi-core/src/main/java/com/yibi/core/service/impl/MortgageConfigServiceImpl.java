package com.yibi.core.service.impl;

import com.yibi.core.dao.MortgageConfigMapper;
import com.yibi.core.entity.MortgageConfig;
import com.yibi.core.service.MortgageConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-11-23 15:05:36
 **/ 
@Service("mortgageConfigService")
public class MortgageConfigServiceImpl implements MortgageConfigService {
    @Resource
    private MortgageConfigMapper mortgageConfigMapper;

    private static final Logger logger = LoggerFactory.getLogger(MortgageConfigServiceImpl.class);

    @Override
    public int insert(MortgageConfig record) {
        return this.mortgageConfigMapper.insert(record);
    }

    @Override
    public int insertSelective(MortgageConfig record) {
        return this.mortgageConfigMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(MortgageConfig record) {
        return this.mortgageConfigMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(MortgageConfig record) {
        return this.mortgageConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.mortgageConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public MortgageConfig selectByPrimaryKey(Integer id) {
        return this.mortgageConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MortgageConfig> selectAll(Map<Object, Object> param) {
        return this.mortgageConfigMapper.selectAll(param);
    }

    @Override
    public List<MortgageConfig> selectPaging(Map<Object, Object> param) {
        return this.mortgageConfigMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.mortgageConfigMapper.selectCount(param);
    }

    @Override
    public MortgageConfig selectByCoin(Integer coinType) {
        Map<Object, Object> param = new HashMap<>();
        param.put("cointype", coinType);
        List<MortgageConfig> list = mortgageConfigMapper.selectAll(param);
        return list.size() == 0 ? null : list.get(0);
    }
}