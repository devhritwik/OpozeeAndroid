package com.opozee.model;

public class FollowingUser {
    String imageurl,userid,isfollowback,isfollowing,username, owneruserid;

    public String getImageurl() {
        return imageurl;
    }

    public String getOwneruserid() {
        return owneruserid;
    }

    public void setOwneruserid(String owneruserid) {
        this.owneruserid = owneruserid;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIsfollowback() {
        return isfollowback;
    }

    public void setIsfollowback(String isfollowback) {
        this.isfollowback = isfollowback;
    }

    public String getIsfollowing() {
        return isfollowing;
    }

    public void setIsfollowing(String isfollowing) {
        this.isfollowing = isfollowing;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
