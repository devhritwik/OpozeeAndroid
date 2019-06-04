package com.opozee.params;

import java.io.File;

public class ProfileParams {

    String firstName;
    String lastName;
    String user_id;
    File profilePic;
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

