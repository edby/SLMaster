package com.yibi.core.dao;

import com.yibi.core.entity.MortgageConfig;
import java.util.List;
import java.util.Map;

public interface MortgageConfigMapper {
    int insert(MortgageConfig record);

    int insertSelective(MortgageConfig record);

    int updateByPrimaryKey(MortgageConfig record);

    int updateByPrimaryKeySelective(MortgageConfig record);

    int deleteByPrimaryKey(Integer id);

    MortgageConfig selectByPrimaryKey(Integer id);

    List<MortgageConfig> selectAll(Map<Object, Object> param);

    List<MortgageConfig> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}