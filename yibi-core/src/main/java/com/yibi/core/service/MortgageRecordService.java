package com.yibi.core.service;

import com.yibi.core.entity.MortgageRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-11-23 15:06:48
 **/ 
public interface MortgageRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    int insert(MortgageRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    int insertSelective(MortgageRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    int updateByPrimaryKey(MortgageRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    int updateByPrimaryKeySelective(MortgageRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    MortgageRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    List<MortgageRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    List<MortgageRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:06:48
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 统计当前已抵押金额
     * @param id
     * @return
     */
    String selectTotalByUserAndCoinType(Integer id, Integer coinType);
}