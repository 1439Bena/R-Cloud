package com.service;

import com.bean.PostInfo;
import com.dao.impl.PostInfoDaoImpl;

/**
 * @Author Cc
 * @Date 2023-12-19
 */
public class PostInfoService {
    final PostInfoDaoImpl dao = new PostInfoDaoImpl();

    public PostInfo selectLikesAndCommentsCount(String pid, String uid) {
        return dao.selectPostInfo(pid,uid);
    }
}
