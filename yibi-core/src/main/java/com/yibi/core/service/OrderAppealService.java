package com.yibi.core.service;

import com.yibi.core.entrty.OrderAppeal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-10-08 16:05:24
 **/ 
public interface OrderAppealService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    int insert(OrderAppeal record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    int insertSelective(OrderAppeal record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    int updateByPrimaryKey(OrderAppeal record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    int updateByPrimaryKeySelective(OrderAppeal record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    OrderAppeal selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    List<OrderAppeal> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    List<OrderAppeal> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-10-08 16:05:24
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 获取申诉信息
     * @param orderId
     * @return
     */
    OrderAppeal selectByOrderId(Integer orderId);
}