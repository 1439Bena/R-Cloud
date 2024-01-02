package com.dao.impl;

import com.bean.UserInfo;
import com.dao.UserInfoDao;
import com.utils.Dbutils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dao.impl.AccountDaoImpl.getaLong;

/**
 * @Author Cc
 * @Date 2023-12-06
 */
public class UserInfoDaoImpl implements UserInfoDao {
    final Dbutils util = new Dbutils();

    @Override
    public int addUserInfo(UserInfo userInfo) {
       String sql="insert into UserInfo" +
               " values(?,?,?,?,?,?)";
       Object[] params={
            userInfo.getUseruid(),
            userInfo.getNickname(),
            userInfo.getGender(),
            userInfo.getBirthday(),
            userInfo.getLocation(),
            userInfo.getBio()
       };
        try {
            return util.executeUpdate(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        String sql = "update UserInfo set Nickname = ?,gender = ?,birthday = ?,location = ?,bio = ? where UserUid = ?";
        Object[] params ={
            userInfo.getNickname(),
            userInfo.getGender(),
            userInfo.getBirthday(),
            userInfo.getLocation(),
            userInfo.getBio(),
            userInfo.getUseruid()
        };
        try {
            return util.executeUpdate(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserInfo> selectUserInfoByPage(UserInfo userInfo, Integer page, Integer limit) {
        List<UserInfo> users = new ArrayList<>();

        StringBuilder sql = new StringBuilder("select * from ( ");
        sql.append("select UserInfo.*,row_number() over(order by UserUid) as rownum from UserInfo");
        sql.append(" where 1=1");

        List<Object> params = new ArrayList<>();

        Params(userInfo, sql, params);

        sql.append(") as t");
        sql.append(" where rownum between ? and ?");

        params.add((page - 1) * limit + 1);
        params.add(page * limit);

        ResultSet rs;

        try {
            rs = util.executeQuery(sql, params);

            while (rs.next()) {
                String uid = rs.getString(1);
                String nickname = rs.getString(2);
                String gender = rs.getString(3);
                Date birthday = rs.getDate(4);
                String location = rs.getString(5);
                String bio = rs.getString(6);

                UserInfo user = new UserInfo();

                user.setUseruid(uid);
                user.setNickname(nickname);
                user.setGender(gender);
                if (birthday!=null)
                user.setBirthday(birthday.toLocalDate());
                user.setLocation(location);
                user.setBio(bio);

                users.add(user);
            }
            util.closeAll();
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long selectByPageCount(UserInfo userInfo) {
        Long count = 0L;

        // 第一：定义要操作数据库的SQL语句 - 动态多条件查询
        StringBuilder sql = new StringBuilder("select count(UserUid) from Userinfo where 1=1");

        List<Object> params = new ArrayList<Object>();
        Params(userInfo, sql, params);
        return getaLong(count, sql, params, util);
    }

    private void Params(UserInfo userInfo, StringBuilder sql, List<Object> params) {
        if (userInfo != null && userInfo.getNickname() != null && !"".equals(userInfo.getNickname().trim())) {
            sql.append(" and Nickname like ?");

            params.add("%" + userInfo.getNickname() + "%");
        }
        if (userInfo != null && userInfo.getGender() != null && !"".equals(userInfo.getGender().trim())) {
            sql.append(" and gender like ?");

            params.add("%" + userInfo.getGender() + "%");
        }
    }
}
