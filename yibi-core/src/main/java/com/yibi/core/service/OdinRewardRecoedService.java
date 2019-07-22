package com.yibi.core.service;

import com.yibi.core.entity.OdinRewardRecoed;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-19 16:55:31
 **/ 
public interface OdinRewardRecoedService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    int insert(OdinRewardRecoed record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    int insertSelective(OdinRewardRecoed record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    int updateByPrimaryKey(OdinRewardRecoed record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    int updateByPrimaryKeySelective(OdinRewardRecoed record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    OdinRewardRecoed selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    List<OdinRewardRecoed> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    List<OdinRewardRecoed> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-07-19 16:55:31
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 获取该期的数据
     *
     * @param userId
     * @param number
     * @return
     */
    List<Map<String, Object>> countThisNumberInfo(Integer userId, Integer number);

    /**
     * 获取本期排名
     * @param number
     * @return
     */
    List<Map<String, Object>> getRankByNumber(Integer number);

    /**
     * 获取累计数据
     * @param userId
     * @return
     */
    List<Map<String, Object>> countAllNumberInfo(Integer userId);
}