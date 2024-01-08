package com.dao;

import com.bean.PostInfo;

/**
 * @Author Cc
 * @Date 2023-12-19
 */
public interface PostInfoDao {
    /**
     * 根据帖子id查询帖子的评论数和点赞数
     *
     * @param pid
     * @return
     */
    PostInfo selectPostInfo(String pid,String uid);
}
