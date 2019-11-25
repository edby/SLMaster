package com.yibi.core.dao;

import com.yibi.core.entity.MortgageProfitRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MortgageProfitRecordMapper {
    int insert(MortgageProfitRecord record);

    int insertSelective(MortgageProfitRecord record);

    int updateByPrimaryKey(MortgageProfitRecord record);

    int updateByPrimaryKeySelective(MortgageProfitRecord record);

    int deleteByPrimaryKey(Integer id);

    MortgageProfitRecord selectByPrimaryKey(Integer id);

    List<MortgageProfitRecord> selectAll(Map<Object, Object> param);

    List<MortgageProfitRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    String getYestodayProfit(Map<Object, Object> param);
}