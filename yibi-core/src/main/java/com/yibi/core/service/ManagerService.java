package com.yibi.core.service;

import com.yibi.core.entity.Manager;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:管理员用户表 manager
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface ManagerService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(Manager record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(Manager record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(Manager record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(Manager record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    Manager selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Manager> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<Manager> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);
}