package com.yibi.core.service;

import com.yibi.core.entity.OdinBuyingRank;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-18 17:51:12
 **/ 
public interface OdinBuyingRankService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    int insert(OdinBuyingRank record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    int insertSelective(OdinBuyingRank record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    int updateByPrimaryKey(OdinBuyingRank record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    int updateByPrimaryKeySelective(OdinBuyingRank record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    OdinBuyingRank selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    List<OdinBuyingRank> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    List<OdinBuyingRank> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-07-18 17:51:12
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 查询更多排行
     * @return
     * @param params
     */
    List<Map<String, Object>> getMoreRank(Map<Object, Object> params);
}