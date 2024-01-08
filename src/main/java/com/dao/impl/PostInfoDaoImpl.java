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
    public PostInfo selectPostInfo(String pid, String uid) {
        long likes = 0L;
        boolean isLiked = false;
        String sql = "SELECT COUNT(Tid) FROM Thumbsup WHERE Tpostid = ? AND Tstatus = 1";
        Object[] params = {
                pid
        };
        ResultSet rs;
        try {
            rs = util.executeQuery(sql, params);
            if (rs.next()) {
                likes = rs.getLong(1);
            }

            // 判断是否存在点赞记录
            sql = "SELECT COUNT(Tid) FROM Thumbsup WHERE Tpostid = ? AND Tuserid = ? AND Tstatus = 1";
            params = new Object[]{pid, uid};
            rs = util.executeQuery(sql, params);
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    isLiked = true;
                }
            }

            long commentsCount = new PostService().GetCommentsCount(pid);

            PostInfo postInfo = new PostInfo(likes, commentsCount, isLiked);

            return postInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
