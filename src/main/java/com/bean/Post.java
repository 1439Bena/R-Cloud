package com.bean;

import java.util.Date;
import java.util.List;

public class Post {
    private String pid;
    private String pcontent;
    private String pimage;
    private String pvideo;
    private AccountInfo publishaccountInfo;
    private UserInfo userinfo;
    private String parentid;
    private Date ptime;
    private String ptags;
    private String pstatus;
    private List<Post> comments;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPcontent() {
        return pcontent;
    }

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getPvideo() {
        return pvideo;
    }

    public void setPvideo(String pvideo) {
        this.pvideo = pvideo;
    }

    public AccountInfo getPublishaccountInfo() {
        return publishaccountInfo;
    }

    public void setPublishaccountInfo(AccountInfo publishaccountInfo) {
        this.publishaccountInfo = publishaccountInfo;
    }

    public UserInfo getUserInfo() {
        return userinfo;
    }

    public void setUserInfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    public String getPtags() {
        return ptags;
    }

    public void setPtags(String ptags) {
        this.ptags = ptags;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public List<Post> getComments() {
        return comments;
    }

    public void setComments(List<Post> comments) {
        this.comments = comments;
    }

    public Post() {
    }

    public Post(String pid, String pcontent, String pimage, String pvideo, AccountInfo publishaccountInfo, UserInfo userinfo, String parentid, Date ptime, String ptags, String pstatus, List<Post> comments) {
        this.pid = pid;
        this.pcontent = pcontent;
        this.pimage = pimage;
        this.pvideo = pvideo;
        this.publishaccountInfo = publishaccountInfo;
        this.userinfo = userinfo;
        this.parentid = parentid;
        this.ptime = ptime;
        this.ptags = ptags;
        this.pstatus = pstatus;
        this.comments = comments;
    }
}
