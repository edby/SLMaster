package com.yibi.core.service;

import com.yibi.core.entity.Doc;
import com.yibi.core.entity.News;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:文档 doc
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface NewsService {

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/
    News selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<News> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<News> selectPaging(Map<Object, Object> param);

    News getByType(Integer type);
}