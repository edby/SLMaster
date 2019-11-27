package com.yibi.core.service;

import com.yibi.core.entity.MortgageRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-11-26 17:48:53
 **/ 
public interface MortgageRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    int insert(MortgageRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    int insertSelective(MortgageRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    int updateByPrimaryKey(MortgageRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    int updateByPrimaryKeySelective(MortgageRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    MortgageRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    List<MortgageRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    List<MortgageRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-11-26 17:48:53
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 统计
     * @param userId
     * @param coinType
     * @return
     */
    String selectTotalByUserAndCoinType(Integer userId, Integer coinType);
}