package com.dao;

import com.bean.Post;

import java.util.List;

public interface PostDao {
    /**
     * 发布帖子
     * @param post
     * @return
     */
    int add(Post post);

    /**
     * 删除帖子
     * @param pid
     * @return
     */
    int del(String pid);

    /**
     * 动态条件 + 分页查询
     * @param key 查询条件（帖子内容、帖子发布者用户名）
     * @param page 当前页
     * @param limit 每页显示记录数
     * @return
     */
    List<Post> selectByPage(String key, Integer page, Integer limit);

    /**
     * 动态条件 + 分页查询 - 总记录数
     * @param key
     * @return
     */
    Long selectByPageCount(String key);

    /**
     * 查询帖子评论的总数
     * @param pid
     * @return
     */
    Long selectCommentsCount(String pid);

    /**
     * 查询帖子下的评论
     * @param parentid
     * @return
     */
    List<Post> selectComments(String parentid);

    Post selectPost(String pid);
}