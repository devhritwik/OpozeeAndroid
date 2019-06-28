package com.opozeeApp.params;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostQuestionParams implements Serializable {
    @SerializedName("Id")
    String id;
    @SerializedName("PostQuestion")
    String question;
    @SerializedName("OwnerUserID")
    String userId;
    @SerializedName("HashTags")
    String hashTags;
    @SerializedName("TaggedUser")
    String taggedUsers;

    public String getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(String taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashTags() {
        return hashTags;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }
}
