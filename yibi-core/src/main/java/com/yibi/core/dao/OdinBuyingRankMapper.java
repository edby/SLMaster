package com.yibi.core.dao;

import com.yibi.core.entity.OdinBuyingRank;
import java.util.List;
import java.util.Map;

public interface OdinBuyingRankMapper {
    int insert(OdinBuyingRank record);

    int insertSelective(OdinBuyingRank record);

    int updateByPrimaryKey(OdinBuyingRank record);

    int updateByPrimaryKeySelective(OdinBuyingRank record);

    int deleteByPrimaryKey(Integer id);

    OdinBuyingRank selectByPrimaryKey(Integer id);

    List<OdinBuyingRank> selectAll(Map<Object, Object> param);

    List<OdinBuyingRank> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<Map<String, Object>> getMoreRank();
}