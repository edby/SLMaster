package com.yibi.core.service;

import com.yibi.common.model.PageModel;
import com.yibi.core.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 业务逻辑接口:用户表 user
 * 
 * @author: autogeneration
 * @date: 2018-07-09 18:26:10
 **/ 
public interface UserService {
    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insert(User record);

    /**
     * 添加
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int insertSelective(User record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKey(User record);

    /**
     * 更新
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int updateByPrimaryKeySelective(User record);

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
    User selectByPrimaryKey(Integer id);

    /**
     * 条件查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<User> selectAll(Map<Object, Object> param);

    /**
     * 分页查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    List<User> selectPaging(Map<Object, Object> param);

    /**
     * 统计查询
     * 
     * @author: autogeneration
     * @date: 2018-07-09 18:26:10
     **/ 
    int selectCount(Map<Object, Object> param);

    /**
     * 根据手机号查询
     * @param phone
     * @return
     */
    User selectByPhone(String phone);

    User getByRole(Integer role);

    User getByidcard(String identificationnumber);

    /**
     * 查询已经实名且状态有效的用户
     * @param page
     * @param rows
     * @return
     */
    List<User> queryActiveUserList(Integer page,Integer rows);

    /**
     * 根据邀请人id查询
     * @param id
     * @return
     */
    User selectByReferId(Integer id);

    /**
     * 根据uuid查询
     * @param referPhone
     * @return
     */
    User selectByUUID(Integer referPhone);

    /**
     * 查询推荐人列表
     * @param uuid
     * @param firstResult
     * @param maxResult
     * @return
     */
    List<User> queryReferUserList(Integer uuid, Integer firstResult, Integer maxResult);

    /**
     * 获取直推列表
     * @param uuid 个人uuid
     * @param pageModel
     * @return
     */
    List<User> getDirectList(Integer uuid, PageModel pageModel);

    /**
     * 查询所有用户的id和phone
     * @return
     */
    List<User> selectIdPhoneByAll();
}