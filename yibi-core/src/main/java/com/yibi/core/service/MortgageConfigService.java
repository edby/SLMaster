package com.yibi.core.service;

import com.yibi.core.entity.MortgageConfig;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-11-23 15:05:36
 **/ 
public interface MortgageConfigService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    int insert(MortgageConfig record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    int insertSelective(MortgageConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    int updateByPrimaryKey(MortgageConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    int updateByPrimaryKeySelective(MortgageConfig record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    MortgageConfig selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    List<MortgageConfig> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    List<MortgageConfig> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-11-23 15:05:36
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 根据币种查询
     * @param coinType
     * @return
     */
    MortgageConfig selectByCoin(Integer coinType);
}