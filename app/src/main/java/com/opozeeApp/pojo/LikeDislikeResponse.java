package com.opozeeApp.pojo;


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class LikeDislikeResponse {

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
        @SerializedName("LikeDislikeOpinion")
        @Expose
        private LikeDislikeOpinion likeDislikeOpinion;

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

        public LikeDislikeOpinion getLikeDislikeOpinion() {
            return likeDislikeOpinion;
        }

        public void setLikeDislikeOpinion(LikeDislikeOpinion likeDislikeOpinion) {
            this.likeDislikeOpinion = likeDislikeOpinion;
        }

    }

    public class LikeDislikeOpinion {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("CommentedUserId")
        @Expose
        private Integer commentedUserId;
        @SerializedName("CommentId")
        @Expose
        private Integer commentId;
        @SerializedName("Like")
        @Expose
        private Boolean like;
        @SerializedName("Dislike")
        @Expose
        private Boolean dislike;
        @SerializedName("Comment")
        @Expose
        private Object comment;
        @SerializedName("SendNotification")
        @Expose
        private Object sendNotification;
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

        public Integer getCommentedUserId() {
            return commentedUserId;
        }

        public void setCommentedUserId(Integer commentedUserId) {
            this.commentedUserId = commentedUserId;
        }

        public Integer getCommentId() {
            return commentId;
        }

        public void setCommentId(Integer commentId) {
            this.commentId = commentId;
        }

        public Boolean getLike() {
            return like;
        }

        public void setLike(Boolean like) {
            this.like = like;
        }

        public Boolean getDislike() {
            return dislike;
        }

        public void setDislike(Boolean dislike) {
            this.dislike = dislike;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public Object getSendNotification() {
            return sendNotification;
        }

        public void setSendNotification(Object sendNotification) {
            this.sendNotification = sendNotification;
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


