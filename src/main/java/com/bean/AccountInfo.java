package com.bean;

import java.time.LocalDateTime;

public class AccountInfo {
    private String uid;
    private String username;
    private String password;
    private String avatar;
    private String email;
    private String aphone;
    private UserInfo userinfo;
    private LocalDateTime registrationtime;
    private int astatus;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAphone() {
        return aphone;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    public LocalDateTime getRegistrationtime() {
        return registrationtime;
    }

    public void setRegistrationtime(LocalDateTime registrationtime) {
        this.registrationtime = registrationtime;
    }

    public int getAstatus() {
        return astatus;
    }

    public void setAstatus(int astatus) {
        this.astatus = astatus;
    }

    public AccountInfo() {
    }
    public AccountInfo(String username, String avatar) {
        this.username=username;
        this.avatar=avatar;
    }
    public AccountInfo(String uid, String username, String password, String avatar, String email, String aphone, LocalDateTime registrationtime, int astatus, UserInfo userinfo) {
        this.uid = uid;
        this.username=username;
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.aphone = aphone;
        this.userinfo=userinfo;
        this.registrationtime = registrationtime;
        this.astatus = astatus;
    }
}
