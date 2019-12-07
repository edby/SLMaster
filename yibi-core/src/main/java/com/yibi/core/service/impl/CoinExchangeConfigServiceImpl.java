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
 * 
 * @author: autogeneration
 * @date: 2019-12-07 23:25:31
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
    public CoinExchangeConfig selectByCoin(int c1, int c2) {
        Map<Object, Object> param = new HashMap<>();
        param.put("union_coin", c1);
        param.put("order_coin", c2);
        List<CoinExchangeConfig> list = coinExchangeConfigMapper.selectAll(param);
        return list.size() == 0 ? null : list.get(0);
    }
}