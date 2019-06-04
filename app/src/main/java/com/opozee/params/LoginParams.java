package com.opozee.params;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginParams implements Serializable {

     @SerializedName("ThirdPartyType")
     String ThirdPartyType;
    @SerializedName("ThirdPartyId")
     String ThirdPartyId;
    @SerializedName("Email")
     String Email;
    @SerializedName("UserName")
     String UserName;
    @SerializedName("FirstName")
     String FirstName;
    @SerializedName("LastName")
     String LastName;
    @SerializedName("DeviceType")
     String DeviceType;
    @SerializedName("DeviceToken")
     String DeviceToken;
    @SerializedName("ImageURL")
     String ImageURL;

    public String getThirdPartyType() {
        return ThirdPartyType;
    }

    public void setThirdPartyType(String thirdPartyType) {
        ThirdPartyType = thirdPartyType;
    }

    public String getThirdPartyId() {
        return ThirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        ThirdPartyId = thirdPartyId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
