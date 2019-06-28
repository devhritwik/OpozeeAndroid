package com.opozeeApp.pojo;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pchmn.materialchips.model.ChipInterface;
import com.opozeeApp.utils.Utils;


public class TagUsersResponse {

    @SerializedName("Response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class GetAllUser implements ChipInterface {

        @SerializedName("UserID")
        @Expose
        private Integer userID;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("FirstName")
        @Expose
        private Object firstName;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("LastName")
        @Expose
        private Object lastName;
        @SerializedName("Email")
        @Expose
        private Object email;
        @SerializedName("Password")
        @Expose
        private Object password;
        @SerializedName("SocialID")
        @Expose
        private Object socialID;
        @SerializedName("SocialType")
        @Expose
        private Object socialType;
        @SerializedName("ImageURL")
        @Expose
        private String imageURL;
        @SerializedName("ImageURL_data")
        @Expose
        private Object imageURLData;
        @SerializedName("DeviceType")
        @Expose
        private Object deviceType;
        @SerializedName("DeviceToken")
        @Expose
        private Object deviceToken;
        @SerializedName("RecordStatus")
        @Expose
        private Object recordStatus;
        @SerializedName("ModifiedDate")
        @Expose
        private Object modifiedDate;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;

        private boolean isChecked = false;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getFirstName() {
            return firstName;
        }

        public void setFirstName(Object firstName) {
            this.firstName = firstName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getLastName() {
            return lastName;
        }

        public void setLastName(Object lastName) {
            this.lastName = lastName;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getSocialID() {
            return socialID;
        }

        public void setSocialID(Object socialID) {
            this.socialID = socialID;
        }

        public Object getSocialType() {
            return socialType;
        }

        public void setSocialType(Object socialType) {
            this.socialType = socialType;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public Object getImageURLData() {
            return imageURLData;
        }

        public void setImageURLData(Object imageURLData) {
            this.imageURLData = imageURLData;
        }

        public Object getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Object deviceType) {
            this.deviceType = deviceType;
        }

        public Object getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(Object deviceToken) {
            this.deviceToken = deviceToken;
        }

        public Object getRecordStatus() {
            return recordStatus;
        }

        public void setRecordStatus(Object recordStatus) {
            this.recordStatus = recordStatus;
        }

        public Object getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(Object modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        @Override
        public Object getId() {
            return userID;
        }

        @Override
        public Uri getAvatarUri() {
            return Uri.parse(imageURL);
        }

        @Override
        public Drawable getAvatarDrawable() {
            return null;
        }

        @Override
        public String getLabel() {
            return Utils.capitalize(name);
        }

        @Override
        public String getInfo() {
            return "@" + userName.replace(" ", "").toLowerCase();
        }
    }

    public class Response {

        @SerializedName("Code")
        @Expose
        private Integer code;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("GetAllUsers")
        @Expose
        private List<GetAllUser> getAllUsers = null;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<GetAllUser> getGetAllUsers() {
            return getAllUsers;
        }

        public void setGetAllUsers(List<GetAllUser> getAllUsers) {
            this.getAllUsers = getAllUsers;
        }

    }

}

