package com.yibi.core.service.impl;

import com.yibi.core.dao.OrderAppealMapper;
import com.yibi.core.entrty.OrderAppeal;
import com.yibi.core.service.OrderAppealService;

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
 * @date: 2019-10-08 16:05:24
 **/ 
@Service("orderAppealService")
public class OrderAppealServiceImpl implements OrderAppealService {
    @Resource
    private OrderAppealMapper orderAppealMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderAppealServiceImpl.class);

    @Override
    public int insert(OrderAppeal record) {
        return this.orderAppealMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderAppeal record) {
        return this.orderAppealMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderAppeal record) {
        return this.orderAppealMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderAppeal record) {
        return this.orderAppealMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.orderAppealMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderAppeal selectByPrimaryKey(Integer id) {
        return this.orderAppealMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrderAppeal> selectAll(Map<Object, Object> param) {
        return this.orderAppealMapper.selectAll(param);
    }

    @Override
    public List<OrderAppeal> selectPaging(Map<Object, Object> param) {
        return this.orderAppealMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.orderAppealMapper.selectCount(param);
    }

    @Override
    public OrderAppeal selectByOrderId(Integer orderId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<OrderAppeal> list = orderAppealMapper.selectAll(map);
        return list.size() == 0 ? null : list.get(0);
    }
}