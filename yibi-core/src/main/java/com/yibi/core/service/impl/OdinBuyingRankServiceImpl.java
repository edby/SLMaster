package com.yibi.core.service.impl;

import com.yibi.core.dao.OdinBuyingRankMapper;
import com.yibi.core.entity.OdinBuyingRank;
import com.yibi.core.service.OdinBuyingRankService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-18 17:51:12
 **/ 
@Service("odinBuyingRankService")
public class OdinBuyingRankServiceImpl implements OdinBuyingRankService {
    @Resource
    private OdinBuyingRankMapper odinBuyingRankMapper;

    private static final Logger logger = LoggerFactory.getLogger(OdinBuyingRankServiceImpl.class);

    @Override
    public int insert(OdinBuyingRank record) {
        return this.odinBuyingRankMapper.insert(record);
    }

    @Override
    public int insertSelective(OdinBuyingRank record) {
        return this.odinBuyingRankMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OdinBuyingRank record) {
        return this.odinBuyingRankMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OdinBuyingRank record) {
        return this.odinBuyingRankMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.odinBuyingRankMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OdinBuyingRank selectByPrimaryKey(Integer id) {
        return this.odinBuyingRankMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OdinBuyingRank> selectAll(Map<Object, Object> param) {
        return this.odinBuyingRankMapper.selectAll(param);
    }

    @Override
    public List<OdinBuyingRank> selectPaging(Map<Object, Object> param) {
        return this.odinBuyingRankMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.odinBuyingRankMapper.selectCount(param);
    }

    @Override
    public List<Map<String, Object>> getMoreRank(Integer firstResult, Integer maxResult) {
        return this.odinBuyingRankMapper.getMoreRank(firstResult, maxResult);
    }
}