package com.dao;

import com.bean.UserInfo;

import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-06
 */
public interface UserInfoDao {
    /**
     * 随账号创建生成默认用户信息
     * @param userInfo
     * @return
     */
    int addUserInfo(UserInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 动态条件 + 分页查询账号信息
     * @param userInfo
     * @param page
     * @param limit
     * @return
     */
    List<UserInfo> selectUserInfoByPage(UserInfo userInfo, Integer page, Integer limit);

    /**
     * 动态条件 + 分页查询 - 总记录数
     * @param userInfo
     * @return
     */
    Long selectByPageCount(UserInfo userInfo);
}
