package com.yibi.core.service;

import com.yibi.core.entity.CoinExchangeConfig;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:幅度计算  1000*1.1=1100 coin_exchange_config
 * 业务逻辑接口:1000*0.8=800 coin_exchange_config
 * 
 * @author: autogeneration
 * @date: 2019-12-08 11:10:08
 **/ 
public interface CoinExchangeConfigService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    int insert(CoinExchangeConfig record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    int insertSelective(CoinExchangeConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    int updateByPrimaryKey(CoinExchangeConfig record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    int updateByPrimaryKeySelective(CoinExchangeConfig record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    CoinExchangeConfig selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    List<CoinExchangeConfig> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    List<CoinExchangeConfig> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2019-12-08 11:10:08
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 根据C1C2查询
     * @param c1
     * @param c2
     * @return
     */
    CoinExchangeConfig selectByCoin(Integer c1, Integer c2);

    /**
     * 根据依赖币种查询
     * @param c2
     * @return
     */
    List<CoinExchangeConfig> selectByRelyCoin(int c2);
}