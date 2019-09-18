package com.yibi.core.dao;

import com.yibi.core.entity.AboutInfo;
import com.yibi.core.entity.UserAuthRecord;

import java.util.List;
import java.util.Map;

public interface UserAuthRecordMapper {
    int insert(UserAuthRecord record);

    int insertSelective(UserAuthRecord record);

    int updateByPrimaryKey(UserAuthRecord record);

    int updateByPrimaryKeySelective(UserAuthRecord record);

    int deleteByPrimaryKey(Integer id);

    UserAuthRecord selectByPrimaryKey(Integer id);

    List<UserAuthRecord> selectAll(Map<Object, Object> param);

    List<UserAuthRecord> selectPaging(Map<Object, Object> param);

    int selectCount(Map<Object, Object> param);
}