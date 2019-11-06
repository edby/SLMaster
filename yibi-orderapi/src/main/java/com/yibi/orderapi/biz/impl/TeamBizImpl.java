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
        params.put("referenceId", uuid);
        //直推
        int directCount = userService.selectCount(params);
        params = new HashMap<>();
        List<User> lists = userService.selectAll(params);
        Map<Integer,User> userMap = new HashedMap();
        for(User user1 : lists){
            userMap.put(user1.getUuid(), user1);
        }
        JSONArray jsonArray = getUserJson(uuid, userMap);
        result.put("tree", jsonArray);
        result.put("countChild", getKeyStringCount(jsonArray.toJSONString(), "phone"));
        result.put("directCount", directCount);
        return Result.toResult(ResultCode.SUCCESS, result);
    }

    /**
     * 递归处理   数据库树结构数据->树形json
     * @param uuid
     * @param users
     * @return
     */
    public static JSONArray getUserJson(Integer uuid, Map<Integer, User> users){

        //当前层级当前node对象
        User cur = users.get(uuid);
        //当前层级当前点下的所有子节点（实战中不要慢慢去查,一次加载到集合然后慢慢处理）
        List<User> childList = getChildUsers(uuid,users);

        JSONArray childTree = new JSONArray();
        for (User node : childList) {
            JSONObject o = new JSONObject();
            o.put("phone", node.getPhone());
            //递归调用该方法
            JSONArray childs = getUserJson(node.getUuid(),users);
            if(!childs.isEmpty()) {
                o.put("children",childs);
            }
            childTree.add(o);
        }
        return childTree;
    }

    /**
     * 获取当前节点的所有子节点
     * @param uuid
     * @param users
     * @return
     */
    public static List<User> getChildUsers(Integer uuid, Map<Integer,User> users){
        List<User> list = new ArrayList<>();
        for (Integer key : users.keySet() ) {
            if(users.get(key).getReferenceid().equals(uuid)){
                list.add(users.get(key));
            }
        }
        return list;
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
