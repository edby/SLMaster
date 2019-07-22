package com.yibi.core.dao;

import com.yibi.core.entity.OdinRewardRecoed;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OdinRewardRecoedMapper {
    int insert(OdinRewardRecoed record);

    int insertSelective(OdinRewardRecoed record);

    int updateByPrimaryKey(OdinRewardRecoed record);

    int updateByPrimaryKeySelective(OdinRewardRecoed record);

    int deleteByPrimaryKey(Integer id);

    OdinRewardRecoed selectByPrimaryKey(Integer id);

    List<OdinRewardRecoed> selectAll(Map<Object, Object> param);

    List<OdinRewardRecoed> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String, Object>> countThisNumberInfo(@Param("userId") Integer userId, @Param("number") Integer number);

    List<Map<String, Object>> getRankByNumber(@Param("number") Integer number);

    List<Map<String, Object>> countAllNumberInfo(@Param("userId") Integer userId);
}