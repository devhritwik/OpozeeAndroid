package com.opozeeApp.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("Response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        int type;

        @SerializedName("Code")
        @Expose
        private Integer code;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("UserProfile")
        @Expose
        private UserProfile userProfile;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

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

        public UserProfile getUserProfile() {
            return userProfile;
        }

        public void setUserProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
        }

    }

    public class UserProfile {

        @SerializedName("UserID")
        @Expose
        private Integer userID;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Password")
        @Expose
        private Object password;
        @SerializedName("ImageURL")
        @Expose
        private String imageURL;
        @SerializedName("BalanceToken")
        @Expose
        private Integer balanceToken;
        @SerializedName("TotalPostedQuestion")
        @Expose
        private Integer totalPostedQuestion;
        @SerializedName("TotalLikes")
        @Expose
        private Integer totalLikes;
        @SerializedName("TotalDislikes")
        @Expose
        private Integer totalDislikes;



        @SerializedName("HasFollowed")
        @Expose
        private String hasfollowed;
        @SerializedName("TotalPostedBeliefs")
        @Expose
        private int totalbeliefs;

        public int getTotalbeliefs() {
            return totalbeliefs;
        }

        public void setTotalbeliefs(int totalbeliefs) {
            this.totalbeliefs = totalbeliefs;
        }

        public String getFollowers() {
            return followers;
        }

        public void setFollowers(String followers) {
            this.followers = followers;
        }

        public String getFollowings() {
            return followings;
        }

        public void setFollowings(String followings) {
            this.followings = followings;
        }

        @SerializedName("Followers")
        @Expose
        private String followers;

        @SerializedName("Following")
        @Expose
        private String followings;


        public String getHasfollowed() {
            return hasfollowed;
        }

        public void setHasfollowed(String hasfollowed) {
            this.hasfollowed = hasfollowed;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public Integer getBalanceToken() {
            return balanceToken;
        }

        public void setBalanceToken(Integer balanceToken) {
            this.balanceToken = balanceToken;
        }

        public Integer getTotalPostedQuestion() {
            return totalPostedQuestion;
        }

        public void setTotalPostedQuestion(Integer totalPostedQuestion) {
            this.totalPostedQuestion = totalPostedQuestion;
        }

        public Integer getTotalLikes() {
            return totalLikes;
        }

        public void setTotalLikes(Integer totalLikes) {
            this.totalLikes = totalLikes;
        }

        public Integer getTotalDislikes() {
            return totalDislikes;
        }

        public void setTotalDislikes(Integer totalDislikes) {
            this.totalDislikes = totalDislikes;
        }

    }


}
