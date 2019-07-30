package com.yibi.core.dao;

import com.yibi.core.entity.OdinBuyingRecord;
import com.yibi.core.entity.OdinReleaseRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OdinReleaseRecordMapper {
    int insert(OdinReleaseRecord record);

    int insertSelective(OdinReleaseRecord record);

    int updateByPrimaryKey(OdinReleaseRecord record);

    int updateByPrimaryKeySelective(OdinReleaseRecord record);

    int deleteByPrimaryKey(Integer id);

    OdinReleaseRecord selectByPrimaryKey(Integer id);

    List<OdinReleaseRecord> selectAll(Map<Object, Object> param);

    List<OdinReleaseRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Integer> getNumberList();

    List<Map<String, Object>> getRankList(List<Integer> numbers);

    OdinReleaseRecord selectLastRecordByUser(@Param("userId") Integer userId);

    String getTotalByUser(@Param("userId") Integer userId);
}