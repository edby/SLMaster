package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.common.model.PageModel;
import com.yibi.common.utils.StrUtils;
import com.yibi.core.constants.CoinType;
import com.yibi.core.entity.DealDigRecord;
import com.yibi.core.entity.User;
import com.yibi.core.service.DealDigRecordService;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.biz.TeamBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:17
 */
@Service
public class TeamBizImpl implements TeamBiz {
    @Autowired
    private UserService userService;
    @Autowired
    private DealDigRecordService dealDigRecordService;
    @Override
    public String init(User user, PageModel pageModel) {
        Integer uuid = user.getUuid();
        Integer userId = user.getId();
        Map<Object, Object> result = new HashMap<>();
        Map<Object, Object> params = new HashMap<>();
        params.put("referenceid", uuid);
        int directCount = userService.selectCount(params);
        List<User> lists = userService.selectIdPhoneByAll();
        Map<Integer,User> userMap = new HashedMap();
        for(User user1 : lists){
            userMap.put(user1.getUuid(), user1);
        }
        int level = 0;
        JSONArray jsonArray = getUserJson(uuid, userMap, level);
        //团队
        result.put("team", jsonArray);
        //团队人数
        result.put("childCount", getKeyStringCount(jsonArray.toJSONString(), "phone"));
        //直推人数
        result.put("directCount", directCount);
        String personDigProfit = dealDigRecordService.getPersonDigProfit(userId, CoinType.PGY, "个人挖矿奖励");
        String teamDigProfit = dealDigRecordService.getPersonDigProfit(userId, CoinType.PGY, "团队挖矿奖励");
        //统计个人挖矿奖励
        result.put("personDigProfit", StrUtils.isBlank(personDigProfit) ? "0" : new BigDecimal(personDigProfit).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
        //统计团队挖矿奖励
        result.put("teamDigProfit",  StrUtils.isBlank(teamDigProfit) ? "0" : new BigDecimal(teamDigProfit).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
        params = new HashMap<Object, Object>();
        params.put("userid", userId);
        params.put("cointype", CoinType.PGY);
        params.put("firstResult", pageModel.getFirstResult());
        params.put("maxResult", pageModel.getMaxResult());
        List<Map<String, Object>> records = dealDigRecordService.selectTeamPaging(params);
        result.put("records", records);
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    /**
     * 递归处理   数据库树结构数据->树形json
     * @param uuid
     * @param users
     * @param level 推荐等级
     * @return
     */
    public static JSONArray getUserJson(Integer uuid, Map<Integer, User> users, int level){

        JSONArray childTree = new JSONArray();
        //当前层级当前点下的所有子节点
        Map<String, Object> resultMap = getChildUsers(uuid, users, level);
        if(!resultMap.isEmpty()) {
            List<User> childList = (List<User>) resultMap.get("list");
            level = (Integer) resultMap.get("level");
            for (User user : childList) {
                JSONObject o = new JSONObject();
                o.put("phone", user.getPhone());
                    o.put("size", childList.size());
                //递归调用该方法
                JSONArray childs = getUserJson(user.getUuid(), users, level);
                if (!childs.isEmpty()) {
                    o.put("children", childs);
                }
                childTree.add(o);
            }
        }
        return childTree;
    }

    /**
     * 获取当前节点的所有子节点
     * @param uuid
     * @param users
     * @param level 推荐等级
     * @return
     */
    private static Map<String, Object> getChildUsers(Integer uuid, Map<Integer, User> users, int level){
        Map<String, Object> resultMap = new HashMap<>();
        List<User> list = new ArrayList<>();
        //推荐等级 5层
        if(level >= 5){
            return resultMap;
        }
        for (Integer key : users.keySet() ) {
            if(users.get(key).getReferenceid().equals(uuid)){
                list.add(users.get(key));
            }
        }
        level ++;
        resultMap.put("level", level);
        resultMap.put("list", list);
        return resultMap;
    }

    /**
     * 记录一个字符串出现的次数
     * @param str
     * @param key
     * @return
     */
    private static int getKeyStringCount(String str, String key) {
        int count = 0;
        if (!str.contains(key)) {
            return count;
        }
        int index = 0;
        while((index = str.indexOf(key))!=-1){
            str = str.substring(index+key.length());
            count ++;
        }
        return count;
    }
}
