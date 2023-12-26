package com.service;

import com.bean.UserInfo;
import com.dao.impl.UserInfoDaoImpl;

import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-06
 */
public class UserInfoService {
    final UserInfoDaoImpl dao=new UserInfoDaoImpl();

    public int addUser(UserInfo userInfo){
        return dao.addUserInfo(userInfo);
    }
    public List<UserInfo> getUserInfo(UserInfo userInfo, Integer page, Integer limit){
        return dao.selectUserInfoByPage(userInfo,page,limit);
    }
    public Long getUserCount(UserInfo userInfo){
        return dao.selectByPageCount(userInfo);
    }
}
