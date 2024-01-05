package com.service;

import com.bean.Post;
import com.dao.PostDao;
import com.dao.impl.PostDaoImpl;

import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-05
 */
public class PostService {
    final PostDao dao = new PostDaoImpl();

    public List<Post> GetPost(Integer page, Integer limit) {
        return dao.selectByPage(page, limit);
    }
    public List<Post> GetComments(String cparentid){
        return dao.selectComments(cparentid);
    }
    public long GetPostCount(){
        return dao.selectByPageCount();
    }
    public Post PostInfo(String pid){
        return dao.selectPost(pid);
    }
    public long GetCommentsCount(String pid){return dao.selectCommentsCount(pid); }
    public int PubPost(Post post){
        return dao.add(post);
    }
}
