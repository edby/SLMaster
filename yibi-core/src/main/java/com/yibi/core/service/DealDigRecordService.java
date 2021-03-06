package com.yibi.core.service;

import com.yibi.core.entity.DealDigRecord;
import com.yibi.core.entity.DealDigRecordModel;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:交易挖矿记录 deal_dig_record
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface DealDigRecordService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(DealDigRecord record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(DealDigRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(DealDigRecord record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(DealDigRecord record);

    /**
     * 删除
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int deleteByPrimaryKey(Integer id);

    /**
     * 按主键查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    DealDigRecord selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<DealDigRecord> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<DealDigRecord> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);


    DealDigRecordModel queryProfit(Integer userid, Integer coinType);

    /**
     * 获取每日挖矿总量
     *
     * @param coinType
     * @param yestday
     * @param today
     * @return
     */
    String getDayTotalDealDig(Integer coinType, String yestday, String today);

    /**
     * 每天推荐人挖矿总量
     * @param coinType
     * @param yestday
     * @param today
     * @return
     */
    String getDayTotalReferDealDig(Integer coinType, String yestday, String today);

    /**
     * 每天个人挖矿总量
     * @param coinType
     * @param yestday
     * @param today
     * @return
     */
    String getDayTotalPersonDealDig(Integer coinType, String yestday, String today);

    /**
     * 总挖矿量
     * @param coinType
     * @return
     */
    String getTotalDealDig(Integer coinType);

    /**
     * 统计个人挖矿奖励
     * @param userId
     * @param cointype
     * @param remark operType 操作类型
     * @return
     */
    String getPersonDigProfit(Integer userId, int cointype, String remark);

    /**
     * 团队页流水详情
     * @param params
     * @return
     */
    List<Map<String, Object>> selectTeamPaging(Map<Object, Object> params);

    int getCountPersonDealDig(Map<Object, Object> params);

    List<Map<String, Object>> selectDataCount(Integer userid, String today, Integer coinType);
}