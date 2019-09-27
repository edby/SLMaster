package com.yibi.core.dao;

import com.yibi.core.entrty.Statistics;
import java.util.List;
import java.util.Map;

public interface StatisticsMapper {
    int insert(Statistics record);

    int insertSelective(Statistics record);

    int updateByPrimaryKey(Statistics record);

    int updateByPrimaryKeySelective(Statistics record);

    int deleteByPrimaryKey(Integer id);

    Statistics selectByPrimaryKey(Integer id);

    List<Statistics> selectAll(Map<Object, Object> param);

    List<Statistics> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}