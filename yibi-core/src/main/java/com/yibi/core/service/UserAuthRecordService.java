package com.yibi.core.service;

import com.yibi.core.entity.AboutInfo;
import com.yibi.core.entity.UserAuthRecord;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2018-07-18 16:54:11
 **/ 
public interface UserAuthRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int insert(UserAuthRecord userAuthRecord);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int insertSelective(UserAuthRecord userAuthRecord);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int updateByPrimaryKey(UserAuthRecord userAuthRecord);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int updateByPrimaryKeySelective(UserAuthRecord userAuthRecord);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/
    UserAuthRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    List<UserAuthRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    List<UserAuthRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-18 16:54:11
     **/ 
    int selectCount(Map<Object, Object> param);
}