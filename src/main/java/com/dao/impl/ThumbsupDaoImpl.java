package com.dao.impl;

import com.dao.ThumbsupDao;
import com.utils.Dbutils;
import com.utils.RandomUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Cc
 * @Date 2024-01-06
 */
public class ThumbsupDaoImpl implements ThumbsupDao {
    final Dbutils util = new Dbutils();
    @Override
    public boolean checkLiked(String pid, String uid) {
        String sql = "SELECT COUNT(Tid) FROM Thumbsup WHERE Tpostid = ? AND Tuserid = ?";
        Object[] params = {pid, uid};
        try {
            ResultSet rs = util.executeQuery(sql, params);
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean addLike(String pid, String uid) {
        String sql = "INSERT INTO Thumbsup (Tid,Tpostid, Tuserid, Tstatus) VALUES (?, ?, ?, 1)";
        String tid;
        try {
            tid = "Like-" + new RandomUtils().GetRandomNumber(8, 0, 9);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Object[] params = {tid, pid, uid};
        int result;
        try {
            result = util.executeUpdate(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result > 0;
    }

    @Override
    public boolean cancelLike(String pid, String uid) {
        String sql = "UPDATE Thumbsup SET Tstatus = 0 WHERE Tpostid = ? AND Tuserid = ?";
        Object[] params = {pid, uid};
        int result;
        try {
            result = util.executeUpdate(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result > 0;
    }
}
