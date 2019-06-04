package com.opozee.params;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OpinionParams implements Serializable {
    @SerializedName("Id")
    String id;
    @SerializedName("QuestId")
    String question_id;
    @SerializedName("Comment")
    String comment;
    @SerializedName("CommentedUserId")
    String commentedUserId;
    @SerializedName("OpinionAgreeStatus")
    int opinionAgreeStatus;

    public int getOpinionAgreeStatus() {
        return opinionAgreeStatus;
    }

    public void setOpinionAgreeStatus(int opinionAgreeStatus) {
        this.opinionAgreeStatus = opinionAgreeStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(String commentedUserId) {
        this.commentedUserId = commentedUserId;
    }
}
