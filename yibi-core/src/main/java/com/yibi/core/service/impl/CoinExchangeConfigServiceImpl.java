package com.yibi.core.service.impl;

import com.yibi.core.dao.CoinExchangeConfigMapper;
import com.yibi.core.entity.CoinExchangeConfig;
import com.yibi.core.service.CoinExchangeConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑实现类:幅度计算  1000*1.1=1100 coin_exchange_config
 * 业务逻辑实现类:1000*0.8=800 coin_exchange_config
 * 
 * @author: autogeneration
 * @date: 2019-12-08 11:10:08
 **/ 
@Service("coinExchangeConfigService")
public class CoinExchangeConfigServiceImpl implements CoinExchangeConfigService {
    @Resource
    private CoinExchangeConfigMapper coinExchangeConfigMapper;

    private static final Logger logger = LoggerFactory.getLogger(CoinExchangeConfigServiceImpl.class);

    @Override
    public int insert(CoinExchangeConfig record) {
        return this.coinExchangeConfigMapper.insert(record);
    }

    @Override
    public int insertSelective(CoinExchangeConfig record) {
        return this.coinExchangeConfigMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKey(CoinExchangeConfig record) {
        return this.coinExchangeConfigMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(CoinExchangeConfig record) {
        return this.coinExchangeConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return this.coinExchangeConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CoinExchangeConfig selectByPrimaryKey(Integer id) {
        return this.coinExchangeConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CoinExchangeConfig> selectAll(Map<Object, Object> param) {
        return this.coinExchangeConfigMapper.selectAll(param);
    }

    @Override
    public List<CoinExchangeConfig> selectPaging(Map<Object, Object> param) {
        return this.coinExchangeConfigMapper.selectPaging(param);
    }

    @Override
    public int selectCount(Map<Object, Object> param) {
        return this.coinExchangeConfigMapper.selectCount(param);
    }
    @Override
    public CoinExchangeConfig selectByCoin(Integer c1, Integer c2) {
        Map<Object, Object> param = new HashMap<>();
        param.put("unionCoin", c1);
        param.put("orderCoin", c2);
        List<CoinExchangeConfig> list = coinExchangeConfigMapper.selectAll(param);
        return list.size() == 0 ? null : list.get(0);
    }
    @Override
    public List<CoinExchangeConfig> selectByRelyCoin(int c2) {
        Map<Object, Object> param = new HashMap<>();
        param.put("relyCoin", c2);
        return this.coinExchangeConfigMapper.selectAll(param);
    }
}