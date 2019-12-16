package com.yibi.core.dao;

import com.yibi.core.entity.OrderC2cConfig;
import java.util.List;
import java.util.Map;

public interface OrderC2cConfigMapper {
    int insert(OrderC2cConfig record);

    int insertSelective(OrderC2cConfig record);

    int updateByPrimaryKey(OrderC2cConfig record);

    int updateByPrimaryKeySelective(OrderC2cConfig record);

    int deleteByPrimaryKey(Integer id);

    OrderC2cConfig selectByPrimaryKey(Integer id);

    List<OrderC2cConfig> selectAll(Map<Object, Object> param);

    List<OrderC2cConfig> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}