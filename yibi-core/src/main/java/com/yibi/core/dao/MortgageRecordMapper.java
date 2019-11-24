package com.yibi.core.dao;

import com.yibi.core.entity.MortgageRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MortgageRecordMapper {
    int insert(MortgageRecord record);

    int insertSelective(MortgageRecord record);

    int updateByPrimaryKey(MortgageRecord record);

    int updateByPrimaryKeySelective(MortgageRecord record);

    int deleteByPrimaryKey(Integer id);

    MortgageRecord selectByPrimaryKey(Integer id);

    List<MortgageRecord> selectAll(Map<Object, Object> param);

    List<MortgageRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    String selectTotalByUser(@Param("userId") Integer id, @Param("coinType") Integer coinType);
}