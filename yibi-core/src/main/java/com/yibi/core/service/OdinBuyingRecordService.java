package com.yibi.core.service;

import com.yibi.core.entity.OdinBuyingRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-16 13:38:31
 **/ 
public interface OdinBuyingRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    int insert(OdinBuyingRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    int insertSelective(OdinBuyingRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    int updateByPrimaryKey(OdinBuyingRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    int updateByPrimaryKeySelective(OdinBuyingRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    OdinBuyingRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    List<OdinBuyingRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    List<OdinBuyingRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-07-16 13:38:31
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 统计用户单日认购金额
     * @param userId
     * @param number
     * @return
     */
    String countUserOnceDayAmount(Integer userId, Integer number);
}