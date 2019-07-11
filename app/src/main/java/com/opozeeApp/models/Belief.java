package com.opozeeApp.models;

public class Belief {


    private int Id;
    private int questionId;
    private String beliefText;
    private int userId;
    private String userName;
    private String UserFullName;
    private String UserImage;
    private int LikesCount;
    private int DislikesCount;

    public String getLongForm() {
        return LongForm;
    }

    public void setLongForm(String longForm) {
        LongForm = longForm;
    }

    private String CreationDate;
    private boolean IsAgree;
    private String questionText;
    private String LongForm;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getBeliefText() {
        return beliefText;
    }

    public void setBeliefText(String beliefText) {
        this.beliefText = beliefText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public int getLikesCount() {
        return LikesCount;
    }

    public void setLikesCount(int likesCount) {
        LikesCount = likesCount;
    }

    public int getDislikesCount() {
        return DislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        DislikesCount = dislikesCount;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public boolean isAgree() {
        return IsAgree;
    }

    public void setAgree(boolean agree) {
        IsAgree = agree;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
