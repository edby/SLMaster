package com.yibi.core.service;

import com.yibi.core.entity.OdinReleaseRecord;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-07-17 17:45:44
 **/ 
public interface OdinReleaseRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    int insert(OdinReleaseRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    int insertSelective(OdinReleaseRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    int updateByPrimaryKey(OdinReleaseRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    int updateByPrimaryKeySelective(OdinReleaseRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    OdinReleaseRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    List<OdinReleaseRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    List<OdinReleaseRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-07-17 17:45:44
     **/ 
    int selectCount(Map<Object, Object> param);
}