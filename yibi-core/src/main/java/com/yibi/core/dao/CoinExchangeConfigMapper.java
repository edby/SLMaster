package com.yibi.core.dao;

import com.yibi.core.entity.CoinExchangeConfig;
import java.util.List;
import java.util.Map;

public interface CoinExchangeConfigMapper {
    int insert(CoinExchangeConfig record);

    int insertSelective(CoinExchangeConfig record);

    int updateByPrimaryKey(CoinExchangeConfig record);

    int updateByPrimaryKeySelective(CoinExchangeConfig record);

    int deleteByPrimaryKey(Integer id);

    CoinExchangeConfig selectByPrimaryKey(Integer id);

    List<CoinExchangeConfig> selectAll(Map<Object, Object> param);

    List<CoinExchangeConfig> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}