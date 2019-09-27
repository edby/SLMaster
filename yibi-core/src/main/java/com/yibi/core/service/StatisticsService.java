package com.yibi.core.service;

import com.yibi.core.entrty.Statistics;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-09-27 17:54:33
 **/ 
public interface StatisticsService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    int insert(Statistics record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    int insertSelective(Statistics record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    int updateByPrimaryKey(Statistics record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    int updateByPrimaryKeySelective(Statistics record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    Statistics selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    List<Statistics> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    List<Statistics> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-09-27 17:54:33
     **/ 
    int selectCount(Map<Object, Object> param);
}