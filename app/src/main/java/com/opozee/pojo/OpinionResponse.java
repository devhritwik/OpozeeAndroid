package com.opozee.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpinionResponse {

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

        @SerializedName("Code")
        @Expose
        private Integer code;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Opinion")
        @Expose
        private Opinion opinion;

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

        public Opinion getOpinion() {
            return opinion;
        }

        public void setOpinion(Opinion opinion) {
            this.opinion = opinion;
        }

    }

    public class Opinion {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("QuestId")
        @Expose
        private Integer questId;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("CommentedUserId")
        @Expose
        private Integer commentedUserId;
        @SerializedName("Likes")
        @Expose
        private Object likes;
        @SerializedName("Dislikes")
        @Expose
        private Object dislikes;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("ModifiedDate")
        @Expose
        private Object modifiedDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getQuestId() {
            return questId;
        }

        public void setQuestId(Integer questId) {
            this.questId = questId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Integer getCommentedUserId() {
            return commentedUserId;
        }

        public void setCommentedUserId(Integer commentedUserId) {
            this.commentedUserId = commentedUserId;
        }

        public Object getLikes() {
            return likes;
        }

        public void setLikes(Object likes) {
            this.likes = likes;
        }

        public Object getDislikes() {
            return dislikes;
        }

        public void setDislikes(Object dislikes) {
            this.dislikes = dislikes;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public Object getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(Object modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

    }

}


