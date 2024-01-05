package com.dao.impl;

import com.bean.AccountInfo;
import com.bean.Post;
import com.bean.UserInfo;
import com.dao.PostDao;
import com.utils.Dbutils;
import com.utils.impl.ImageUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.dao.impl.AccountDaoImpl.getaLong;


/**
 * @Author Cc
 * @Date 2023-11-23
 */
public class PostDaoImpl implements PostDao {
    final Dbutils util = new Dbutils();

    @Override
    public int add(Post post) {
        String sql = "";
        Object[] params;
        if (post.getPimage() != null) {
            sql = "insert into Post " +
                    "values(?,?,?,?,?,?,?,?,?)";
            params = new Object[]{
                    post.getPid(),
                    post.getPcontent(),
                    new ImageUtils().stringTobyte(post.getPimage()),
                    post.getPvideo(),
                    post.getPublishaccountInfo().getUid(),
                    post.getParentid(),
                    post.getPtime(),
                    post.getPtags(),
                    post.getPstatus()
            };
        } else {
            sql = "insert into Post(Pid, Pcontent, Pvideo, Publisher, Parentid, Ptime, Ptags, Pstatus) " +
                    "values(?,?,?,?,?,?,?,?)";
            params = new Object[]{
                    post.getPid(),
                    post.getPcontent(),
                    post.getPvideo(),
                    post.getPublishaccountInfo().getUid(),
                    post.getParentid(),
                    post.getPtime(),
                    post.getPtags(),
                    post.getPstatus()
            };
        }

        try {
            return util.executeUpdate(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int del(String pid) {
        String sql = "update Post Pstatus='删除' where Pid=?";

        try {
            return util.executeUpdate(sql, pid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> selectByPage(Integer page, Integer limit) {
        List<Post> posts = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("WITH RandomizedPosts AS ( ");
        sql.append("    SELECT P.Pid, CAST(P.Pcontent AS NVARCHAR(MAX)) AS Pcontent, P.PImage, P.Pvideo, P.Publisher, ");
        sql.append("           P.Parentid, P.Ptime, P.Ptags, P.Pstatus, ");
        sql.append("           U.UserUid, U.Nickname, A.Username, A.Avatar, ");
        sql.append("           ROW_NUMBER() OVER (ORDER BY RAND(CHECKSUM(NEWID()))) AS rowNumber ");
        sql.append("    FROM Post P ");
        sql.append("    JOIN UserInfo U ON P.Publisher = U.UserUid ");
        sql.append("    JOIN Accountinfo A ON U.UserUid = A.Uid ");
        sql.append("    WHERE P.Parentid IS NULL ");
        sql.append("      AND P.Pstatus = '正常' ");
        sql.append("      AND P.Pid NOT IN (SELECT TOP 10 A.Pid FROM Post A ORDER BY NEWID()) ");
        sql.append("    GROUP BY P.Pid, CAST(P.Pcontent AS NVARCHAR(MAX)), P.PImage, P.Pvideo, P.Publisher, P.Parentid, P.Ptime, P.Ptags, P.Pstatus, U.UserUid, U.Nickname, A.Username, A.Avatar ");
        sql.append(") ");
        sql.append("SELECT * ");
        sql.append("FROM ( ");
        sql.append("    SELECT *, ROW_NUMBER() OVER (ORDER BY rowNumber) AS rowIndex ");
        sql.append("    FROM RandomizedPosts ");
        sql.append(") AS SubQuery ");
        sql.append("WHERE rowIndex BETWEEN ? AND ? ");
        sql.append("ORDER BY Ptime DESC");

        List<Object> params = new ArrayList<>();
// 添加分页参数
        params.add((page - 1) * limit + 1);
        params.add(page * limit);


        ResultSet rs;
        try {
            rs = util.executeQuery(sql, params);

            while (rs.next()) {
                Post p = new Post();

                String pid = rs.getString(1);
                String pcontent = rs.getString(2);
                byte[] pimage = rs.getBytes(3);
                String pvideo = rs.getString(4);
                String parentid = rs.getString(6);
                Timestamp ts = rs.getTimestamp(7);
                String ptags = rs.getString(8);
                String pstatus = rs.getString(9);
                String useruid = rs.getString(10);
                String nickname = rs.getString(11);
                String username = rs.getString(12);
                byte[] avatar = rs.getBytes(13);

                p.setPid(pid);
                p.setPcontent(pcontent);
                if (pimage != null)
                    p.setPimage(new ImageUtils().byteToString(pimage));
                p.setPvideo(pvideo);
                p.setParentid(parentid);
                p.setPublishaccountInfo(new AccountInfo(username, new ImageUtils().byteToString(avatar)));
                p.setUserInfo(new UserInfo(useruid, nickname));
                p.setPtime(ts);
                p.setPtags(ptags);
                p.setPstatus(pstatus);

                posts.add(p);
            }
            util.closeAll();
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Long selectByPageCount() {
        Long count = 0L;

        // 第一：定义要操作数据库的SQL语句 - 动态多条件查询
        StringBuilder sql = new StringBuilder("select count(Pid) from Post where Parentid is null and Pstatus='正常'");

        List<Object> params = new ArrayList<Object>();

        return getaLong(count, sql, params, util);
    }

    @Override
    public Long selectCommentsCount(String pid) {
        Long count = 0L;

        // 第一：定义要操作数据库的SQL语句 - 动态多条件查询
        StringBuilder sql = new StringBuilder("select count(Pid) from Post where Parentid = ? and Pstatus='正常'");

        List<Object> params = new ArrayList<Object>();
        params.add(pid);

        return getaLong(count, sql, params, util);
    }

    @Override
    public List<Post> selectComments(String cparentid) {
        List<Post> posts = new ArrayList<>();

        StringBuilder sql = new StringBuilder("select * from ( ");
        sql.append("select Post.*, UserInfo.UserUid, UserInfo.Nickname, Accountinfo.Username,Accountinfo.Avatar,row_number() over(order by Pid) as rownum from Post join UserInfo on Post.Publisher = UserInfo.UserUid join Accountinfo on Post.Publisher = Accountinfo.Uid");
        sql.append(" where Parentid = ? and Pstatus = '正常'");

        // 1.定义集合，用于存储 SQL 语句的 ？占位符的参数值
        List<Object> params = new ArrayList<Object>();
        params.add(cparentid);
        sql.append(") as t");


        return getPosts(posts, sql, params);
    }

    @Override
    public Post selectPost(String Pid) {
        Post p = new Post();

        StringBuilder sql = new StringBuilder("select * from ( ");
        sql.append("select Post.*, UserInfo.UserUid, UserInfo.Nickname, Accountinfo.Username,Accountinfo.Avatar,row_number() over(order by Pid) as rownum from Post join UserInfo on Post.Publisher = UserInfo.UserUid join Accountinfo on Post.Publisher = Accountinfo.Uid");
        sql.append(" where Parentid is null and Pstatus = '正常' and Pid = ?");

        List<Object> params = new ArrayList<Object>();
        params.add(Pid);
        sql.append(") as t");

        ResultSet rs;
        try {
            rs = util.executeQuery(sql, params);

            while (rs.next()) {
                String pid = rs.getString(1);
                String pcontent = rs.getString(2);
                byte[] pimage = rs.getBytes(3);
                String pvideo = rs.getString(4);
                String parentid = rs.getString(6);
                Timestamp ts = rs.getTimestamp(7);
                String ptags = rs.getString(8);
                String pstatus = rs.getString(9);
                String useruid = rs.getString(10);
                String nickname = rs.getString(11);
                String username = rs.getString(12);
                byte[] avatar = rs.getBytes(13);
                List<Post> comments = selectComments(pid);

                p.setPid(pid);
                p.setPcontent(pcontent);
                if (pimage != null)
                    p.setPimage(new ImageUtils().byteToString(pimage));
                p.setPvideo(pvideo);
                p.setParentid(parentid);
                p.setPublishaccountInfo(new AccountInfo(username, new ImageUtils().byteToString(avatar)));
                p.setUserInfo(new UserInfo(useruid, nickname));
                p.setPtime(ts);
                p.setPtags(ptags);
                p.setPstatus(pstatus);
                p.setComments(comments);
            }
            util.closeAll();
            return p;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Post> getPosts(List<Post> posts, StringBuilder sql, List<Object> params) {
        ResultSet rs;
        try {
            rs = util.executeQuery(sql, params);

            while (rs.next()) {
                String pid = rs.getString(1);
                String pcontent = rs.getString(2);
                byte[] pimage = rs.getBytes(3);
                String pvideo = rs.getString(4);
                String parentid = rs.getString(6);
                Timestamp ts = rs.getTimestamp(7);
                String ptags = rs.getString(8);
                String pstatus = rs.getString(9);
                String useruid = rs.getString(10);
                String nickname = rs.getString(11);
                String username = rs.getString(12);
                byte[] avatar = rs.getBytes(13);
                List<Post> comments = selectComments(pid);

                Post p = new Post();

                p.setPid(pid);
                p.setPcontent(pcontent);
                if (pimage != null)
                    p.setPimage(new ImageUtils().byteToString(pimage));
                p.setPvideo(pvideo);
                p.setParentid(parentid);
                p.setPublishaccountInfo(new AccountInfo(username, new ImageUtils().byteToString(avatar)));
                p.setUserInfo(new UserInfo(useruid, nickname));
                p.setPtime(ts);
                p.setPtags(ptags);
                p.setPstatus(pstatus);
                p.setComments(comments);

                posts.add(p);
            }
            util.closeAll();
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
