package com.opozeeApp.params;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookMarkParams implements Serializable {

    @SerializedName("Id")
    String id;
    @SerializedName("QuestionId")
    String question_id;
    @SerializedName("IsBookmark")
    boolean isBookmark;
    @SerializedName("UserId")
    String userId;

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

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
