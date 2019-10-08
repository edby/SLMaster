package com.yibi.core.dao;

import com.yibi.core.entrty.OrderAppeal;
import java.util.List;
import java.util.Map;

public interface OrderAppealMapper {
    int insert(OrderAppeal record);

    int insertSelective(OrderAppeal record);

    int updateByPrimaryKey(OrderAppeal record);

    int updateByPrimaryKeySelective(OrderAppeal record);

    int deleteByPrimaryKey(Integer id);

    OrderAppeal selectByPrimaryKey(Integer id);

    List<OrderAppeal> selectAll(Map<Object, Object> param);

    List<OrderAppeal> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}