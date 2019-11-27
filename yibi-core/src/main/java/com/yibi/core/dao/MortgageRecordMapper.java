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

    String selectTotalByUserAndCoinType(@Param("userId") Integer userId, @Param("coinType") Integer coinType);
}