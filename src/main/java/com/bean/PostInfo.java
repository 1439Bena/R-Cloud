package com.bean;

/**
 * @Author Cc
 * @Date 2023-12-19
 */
public class PostInfo {
    private long likes;
    private long commentscount;

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getCommentscount() {
        return commentscount;
    }

    public void setCommentscount(long commentscount) {
        this.commentscount = commentscount;
    }

    public PostInfo() {
    }

    public PostInfo(long likes, long commentscount) {
        this.likes = likes;
        this.commentscount = commentscount;
    }
}
