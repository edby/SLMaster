package com.yibi.core.dao;

import com.yibi.core.entity.News;

import java.util.List;
import java.util.Map;

public interface NewsMapper {
    News selectByPrimaryKey(Integer id);

    List<News> selectAll(Map<Object, Object> param);

    List<News> selectPaging(Map<Object, Object> param);
}