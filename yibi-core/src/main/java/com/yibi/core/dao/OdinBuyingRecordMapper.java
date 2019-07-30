package com.yibi.core.dao;

import com.yibi.core.entity.OdinBuyingRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OdinBuyingRecordMapper {
    int insert(OdinBuyingRecord record);

    int insertSelective(OdinBuyingRecord record);

    int updateByPrimaryKey(OdinBuyingRecord record);

    int updateByPrimaryKeySelective(OdinBuyingRecord record);

    int deleteByPrimaryKey(Integer id);

    OdinBuyingRecord selectByPrimaryKey(Integer id);

    List<OdinBuyingRecord> selectAll(Map<Object, Object> param);

    List<OdinBuyingRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    String countUserOnceDayAmount(@Param("userId") Integer userId, @Param("number") Integer number);

    String countPlatFormOnceDayAmount(@Param("number") Integer number);

    List<Map<String, Object>> selectAmountAndPhoneAndTimeByReferId(@Param("uuid") Integer uuid, @Param("firstResult") Integer firstResult, @Param("maxResult") Integer maxResult);

    String getOdinTotalBuyingByUser(@Param("userId") Integer userId);
}