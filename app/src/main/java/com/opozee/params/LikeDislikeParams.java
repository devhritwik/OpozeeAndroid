package com.opozee.params;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LikeDislikeParams implements Serializable {
    @SerializedName("Id")
    int id;
    @SerializedName("CommentedUserId")
    int commentedUserId;
    @SerializedName("CommentId")
    int commentedId;
    @SerializedName("CommentStatus")
    int commentStatus;
    @SerializedName("questId")
    int questId;

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(int commentedUserId) {
        this.commentedUserId = commentedUserId;
    }

    public int getCommentedId() {
        return commentedId;
    }

    public void setCommentedId(int commentedId) {
        this.commentedId = commentedId;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }
}
