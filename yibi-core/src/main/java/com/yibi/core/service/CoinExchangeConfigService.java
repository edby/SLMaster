package com.yibi.core.service;

import com.yibi.core.entity.CoinExchangeConfig;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author: autogeneration
 * @date: 2019-12-07 23:25:31
 **/ 
public interface CoinExchangeConfigService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    int insert(CoinExchangeConfig record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    int insertSelective(CoinExchangeConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    int updateByPrimaryKey(CoinExchangeConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    int updateByPrimaryKeySelective(CoinExchangeConfig record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    CoinExchangeConfig selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    List<CoinExchangeConfig> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    List<CoinExchangeConfig> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-07 23:25:31
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 根据c1c2查询
     * @param c1
     * @param c2
     * @return
     */
    CoinExchangeConfig selectByCoin(int c1, int c2);
}