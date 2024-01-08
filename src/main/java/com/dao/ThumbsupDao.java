package com.dao;

/**
 * @Author Cc
 * @Date 2024-01-06
 */
public interface ThumbsupDao {
    /**
     * 检查是否有点赞记录
     * @param pid
     * @param uid
     * @return
     */
     boolean checkLiked(String pid, String uid);

    /**
     * 添加点赞记录
     * @param pid
     * @param uid
     * @return
     */
     boolean addLike(String pid, String uid);

    /**
     * 取消点赞
     * @param pid
     * @param uid
     * @return
     */
    boolean cancelLike(String pid, String uid);
}
