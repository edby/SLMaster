package com.yibi.core.dao;

import com.yibi.core.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);

    int deleteByPrimaryKey(Integer id);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll(Map<Object, Object> param);

    List<User> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);

    List<User> selectUserByPhoneOrName(Map<Object,Object> map);

    List<User> queryReferUserList(@Param("id")Integer id, @Param("firstResult")Integer firstResult, @Param("maxResult")Integer maxResult);

    List<User> selectIdPhoneByAll();

    List<User> getDirectList(Map<Object, Object> map);
}