package com.yibi.orderapi.biz.impl;

import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.entity.Banner;
import com.yibi.core.service.BannerService;
import com.yibi.orderapi.biz.BannerBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
public class BannerBizImpl implements BannerBiz {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private RedisTemplate<String, String> redis;


    @Override
    public List<Banner> queryAllInfo(Map<Object, Object> map) {
        return bannerService.selectAllInfo(map);
    }

    @Override
    public String getBannerByType(Integer bannerType) {
        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("bannertype", 0);
        map.put("state", 1);
        List<Banner> list = queryAllInfo(map);
        data.put("list", list);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String changeMood(Integer moodState) {
        String modd = RedisUtil.searchString(redis, String.format(RedisKey.MARKET_MOOD, 0));
        int mood = Integer.valueOf(modd) + moodState;
        //看空
        if(moodState < 0){
            RedisUtil.addString(redis, String.format(RedisKey.MARKET_MOOD, 0), Integer.toString(mood));
            RedisUtil.addString(redis, String.format(RedisKey.MARKET_MOOD, 1), String.valueOf(100 - mood));
        }else{
            RedisUtil.addString(redis, String.format(RedisKey.MARKET_MOOD, 1), Integer.toString(mood));
            RedisUtil.addString(redis, String.format(RedisKey.MARKET_MOOD, 0), String.valueOf(100 - mood));
        }
        //今日情绪 0看空 1看涨
        String moodBottom = RedisUtil.searchString(redis, String.format(RedisKey.MARKET_MOOD, 0));
        String moodTop = RedisUtil.searchString(redis, String.format(RedisKey.MARKET_MOOD, 1));
        Map<String, Object> moodMap = new HashMap<>();
        moodMap.put("moodBottom", moodBottom);
        moodMap.put("moodTop", moodTop);
        return Result.toResult(ResultCode.SUCCESS, moodMap);
    }
}
