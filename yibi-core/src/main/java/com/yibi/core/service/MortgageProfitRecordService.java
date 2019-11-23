package com.yibi.core.service;

import com.yibi.core.entity.MortgageProfitRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-11-23 15:06:36
 **/ 
public interface MortgageProfitRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    int insert(MortgageProfitRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    int insertSelective(MortgageProfitRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    int updateByPrimaryKey(MortgageProfitRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    int updateByPrimaryKeySelective(MortgageProfitRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    MortgageProfitRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    List<MortgageProfitRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    List<MortgageProfitRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:36
     **/ 
    int selectCount(Map<Object, Object> param);
}