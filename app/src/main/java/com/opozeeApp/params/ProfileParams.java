package com.opozeeApp.params;

import java.io.File;

public class ProfileParams {

    String firstName;
    String lastName;
    String user_id;
    String userName;
    String viewuserid;

    public String getViewuserid() {
        return viewuserid;
    }

    public void setViewuserid(String viewuserid) {
        this.viewuserid = viewuserid;
    }

    File profilePic;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public File getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(File profilePic) {
        this.profilePic = profilePic;
    }



}

