package com.bean;

/**
 * @Author Cc
 * @Date 2023-12-19
 */
public class PostInfo {
    private long likes;
    private long commentscount;
    private boolean islike;

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

    public boolean isIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public PostInfo() {
    }

    public PostInfo(long likes, long commentscount, boolean islike) {
        this.likes = likes;
        this.commentscount = commentscount;
        this.islike = islike;
    }
}
