package com.bean;

import java.time.LocalDate;

public class UserInfo {
   private String useruid;
   private String nickname;
   private String gender;
   private LocalDate birthday;
   private String location;
   private String bio;

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserInfo() {
    }

    public UserInfo(String useruid, String nickname) {
        this.useruid = useruid;
        this.nickname = nickname;
    }

    public UserInfo(String useruid, String nickname, String gender, LocalDate birthday, String location, String bio) {
        this.useruid = useruid;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.location = location;
        this.bio = bio;
    }
}
