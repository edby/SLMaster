package com.yibi.orderapi.biz.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yibi.core.entity.User;
import com.yibi.core.service.UserService;
import com.yibi.orderapi.biz.TeamBiz;
import com.yibi.orderapi.dto.Result;
import com.yibi.orderapi.enums.ResultCode;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zhaohe
 * @create: 2019-04-24 14:17
 */
@Service
public class TeamBizImpl implements TeamBiz {
    @Autowired
    private UserService userService;
    @Override
    public String init(User user) {
        Integer uuid = user.getUuid();
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
        result.put("children", jsonArray);
        //团队人数
        result.put("countChild", getKeyStringCount(jsonArray.toJSONString(), "phone"));
        //直推人数
        result.put("directCount", directCount);
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
        Map<String, Object> resultMap = new HashMap<>();
        //当前层级当前点下的所有子节点
        resultMap = getChildUsers(uuid, users, level);
        if(!resultMap.isEmpty()) {
            List<User> childList = (List<User>) resultMap.get("list");
            level = (Integer) resultMap.get("level");
            for (User user : childList) {
                JSONObject o = new JSONObject();
                o.put("phone", user.getPhone());
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
