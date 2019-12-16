package com.yibi.core.service;

import com.yibi.core.entity.OrderC2cConfig;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-16 17:09:48
 **/ 
public interface OrderC2cConfigService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    int insert(OrderC2cConfig record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    int insertSelective(OrderC2cConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    int updateByPrimaryKey(OrderC2cConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    int updateByPrimaryKeySelective(OrderC2cConfig record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    OrderC2cConfig selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    List<OrderC2cConfig> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    List<OrderC2cConfig> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-16 17:09:48
     **/ 
    int selectCount(Map<Object, Object> param);

    OrderC2cConfig selectByCoinType(Integer coinType);
}