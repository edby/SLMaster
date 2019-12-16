package com.yibi.core.service.impl;

import com.yibi.core.dao.OrderC2cConfigMapper;
import com.yibi.core.entity.OrderC2cConfig;
import com.yibi.core.service.OrderC2cConfigService;

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
 * @date: 2019-12-16 17:09:48
 **/ 
@Service("orderC2cConfigService")
public class OrderC2cConfigServiceImpl implements OrderC2cConfigService {
    @Resource
    private OrderC2cConfigMapper orderC2cConfigMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderC2cConfigServiceImpl.class);

    @Override
    public int insert(OrderC2cConfig record) {
        return this.orderC2cConfigMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderC2cConfig record) {
        return this.orderC2cConfigMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderC2cConfig record) {
        return this.orderC2cConfigMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderC2cConfig record) {
        return this.orderC2cConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.orderC2cConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderC2cConfig selectByPrimaryKey(Integer id) {
        return this.orderC2cConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderC2cConfig> selectAll(Map<Object, Object> param) {
        return this.orderC2cConfigMapper.selectAll(param);
    }

    @Override
    public List<OrderC2cConfig> selectPaging(Map<Object, Object> param) {
        return this.orderC2cConfigMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.orderC2cConfigMapper.selectCount(param);
    }
    @Override
    public OrderC2cConfig selectByCoinType(Integer coinType) {
        Map<Object, Object> param = new HashMap<>();
        param.put("coinType", coinType);
        List<OrderC2cConfig> list = orderC2cConfigMapper.selectAll(param);
        return list.size() == 0 ? null : list.get(0);
    }
}