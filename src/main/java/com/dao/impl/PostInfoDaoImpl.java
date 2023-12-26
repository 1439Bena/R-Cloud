package com.dao.impl;

import com.bean.PostInfo;
import com.dao.PostInfoDao;
import com.service.PostService;
import com.utils.Dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Cc
 * @Date 2023-12-19
 */
public class PostInfoDaoImpl implements PostInfoDao {
    final Dbutils util=new Dbutils();
    @Override
    public PostInfo selectPostInfo(String pid) {
        long likes =0L;
        String sql = "select count(Tid) from Thumbsup where Tpostid = ? and Tstatus = 1";
        Object[] params = {
                pid
        };
        ResultSet rs;
        try {
            rs = util.executeQuery(sql,params);
            if (rs.next()){
                likes= rs.getLong(1);
            }
            long commentscount=new PostService().GetCommentsCount(pid);

            PostInfo postInfo=new PostInfo(likes,commentscount);

            return postInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
