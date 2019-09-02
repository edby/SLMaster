package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSON;
import com.yibi.common.utils.BigDecimalUtils;
import com.yibi.common.utils.RedisUtil;
import com.yibi.common.variables.RedisKey;
import com.yibi.core.constants.CoinType;
import com.yibi.core.constants.GlobalParams;
import com.yibi.core.constants.SystemParams;
import com.yibi.core.entity.Banner;
import com.yibi.core.entity.Doc;
import com.yibi.core.entity.Notice;
import com.yibi.core.entity.User;
import com.yibi.core.service.CoinScaleService;
import com.yibi.core.service.DocService;
import com.yibi.core.service.SysparamsService;
import com.yibi.orderapi.biz.*;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by ZhaoHe on 2018/7/17 0017.
 */
@Service
@Transactional
public class HomePageBizImpl implements HomePageBiz {

    @Autowired
    private BannerBiz bannerBiz;
    @Autowired
    private NoticeBiz noticeBiz;
    @Autowired
    private AccountBiz accountBiz;
    @Autowired
    private RedisTemplate<String, String> redis;
    @Autowired
    private SysparamsService sysparamsService;
    @Autowired
    private CoinScaleService coinScaleService;
    @Override
    public String initOut() {
        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("bannertype", 0);
        map.put("state", 1);
        List<Banner> bannerList = bannerBiz.queryAllInfo(map);
        data.put("banner", bannerList);

        Notice notice = noticeBiz.queryInfoByIndex();
        data.put("notice", notice);
        data.put("noticeUrl", sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL) + "/web/notice/1.action");

        //买币指南
        data.put("buyCoinGuide", sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL) + "/web/doc/" + GlobalParams.NOTICE_BUY_USING + ".action");
        //账户指南
        data.put("accountGuide", sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL) + "/web/doc/" + GlobalParams.NOTICE_ACCOUNT_USING + ".action");
        //首页行情 默认
        String coinList = sysparamsService.getValStringByKey(SystemParams.HOMEPAGE_MARKET_COIN_LIST);
        List<Map<String, Object>> coinListsMain = new LinkedList<>();
        //涨幅度列表
        List<BigDecimal> decimals = new LinkedList<>();
        Map<String, Map<String, Object>> coinMap = new HashMap<>();
        for(String orderCoinType : Collections.singletonList(coinList)){
            Map<String, Object> coinInfoMap = new HashMap<>();
            String redisKey = String.format(RedisKey.MARKET, 1, CoinType.USDT, orderCoinType);
            String market = RedisUtil.searchString(redis, redisKey);
            if (market != null && !"".equals(market)) {
                Map<String, Object> jsonMap = JSON.parseObject(market, Map.class);
                String chgPrice = jsonMap.get("chgPrice").toString();
                String newPriceCny = jsonMap.get("newPriceCNY").toString();
                //币种
                coinInfoMap.put("coinType", orderCoinType);
                //涨幅度
                coinInfoMap.put("chgPrice", chgPrice);
                //人民币价格
                coinInfoMap.put("newPriceCNY", newPriceCny);
                coinListsMain.add(coinInfoMap);

                decimals.add(new BigDecimal(chgPrice));
                coinMap.put(chgPrice, jsonMap);
            }
        }
        //涨幅榜
        List<Map<String, Object>> coinLists24h = new LinkedList<>();
        decimals.sort(Comparator.reverseOrder());
        for(BigDecimal chgPrice : decimals){
            Map<String, Object> coinInfoMap = new HashMap<>();
            Map<String, Object> jsonMap = coinMap.get(chgPrice.toString());
            //币种
            coinInfoMap.put("orderCoinType", jsonMap.get("orderCoinType"));
            coinInfoMap.put("unitCoinType", jsonMap.get("unitCoinType"));
            //涨幅度
            coinInfoMap.put("chgPrice", chgPrice);
            //人民币价格
            coinInfoMap.put("newPriceCNY", jsonMap.get("newPriceCNY"));
            //最新价格
            coinInfoMap.put("newPrice", jsonMap.get("newPrice"));
            coinLists24h.add(coinInfoMap);
        }
        data.put("marketMain",coinListsMain);
        data.put("market24h",coinLists24h);

        //今日情绪 0看空 1看涨
        String moodBottom = RedisUtil.searchString(redis, String.format(RedisKey.MARKET_MOOD, 0));
        String moodTop = RedisUtil.searchString(redis, String.format(RedisKey.MARKET_MOOD, 1));
        Map<String, Object> moodMap = new HashMap<>();
        moodMap.put("moodBottom", moodBottom);
        moodMap.put("moodTop", moodTop);
        data.put("mood", moodMap);
        return Result.toResult(ResultCode.SUCCESS, data);
    }

    @Override
    public String initIn(User user) {
        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> map = new HashMap<>();
        map.put("bannertype", 0);
        map.put("state", 1);
        List<Banner> bannerList = bannerBiz.queryAllInfo(map);
        data.put("banner", bannerList);

        Notice notice = noticeBiz.queryInfoByIndex();
        data.put("notice", notice);
        data.put("noticeUrl", sysparamsService.getValStringByKey(SystemParams.SYSTEM_URL) + "/web/article/" +  notice.getId() + ".action");

        BigDecimal soptAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_SPOT);
        BigDecimal c2cAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_C2C);
        BigDecimal leverAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_LEVERAGE);
        BigDecimal yubiAccount = accountBiz.queryByUser(user.getId(), GlobalParams.ACCOUNT_TYPE_YUBI);
        data.put("soptAccount", BigDecimalUtils.toStringInZERO(soptAccount, 2));
        data.put("c2cAccount", BigDecimalUtils.toStringInZERO(c2cAccount, 2));
        data.put("leverAccount", BigDecimalUtils.toStringInZERO(leverAccount, 2));
        data.put("yubiAccount", BigDecimalUtils.toStringInZERO(yubiAccount, 2));
        data.put("total", BigDecimalUtils.toStringInZERO(soptAccount.add(c2cAccount).add(leverAccount).add(yubiAccount), 2));

        return Result.toResult(ResultCode.SUCCESS, data);
    }
}
