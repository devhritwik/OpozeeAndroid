package com.opozeeApp.pojo;



        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.List;


public class NotificationsResponse {

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
        @SerializedName("AllOpinion")
        @Expose
        private List<AllOpinion> allOpinion = null;

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

        public List<AllOpinion> getAllOpinion() {
            return allOpinion;
        }

        public void setAllOpinion(List<AllOpinion> allOpinion) {
            this.allOpinion = allOpinion;
        }

    }

    public class AllOpinion {

        @SerializedName("Image")
        @Expose
        private String userImage;

        @SerializedName("QuestionId")
        @Expose
        private Integer questionId;
        @SerializedName("Question")
        @Expose
        private String question;
        @SerializedName("HashTags")
        @Expose
        private String hashTags;
        @SerializedName("OpinionId")
        @Expose
        private Integer opinionId;
        @SerializedName("Opinion")
        @Expose
        private String opinion;
        @SerializedName("CommentedUserId")
        @Expose
        private Integer commentedUserId;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("Like")
        @Expose
        private Boolean like;
        @SerializedName("Dislike")
        @Expose
        private Boolean dislike;
        @SerializedName("Comment")
        @Expose
        private Boolean comment;
        @SerializedName("Tag")
        @Expose
        private String tag;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("ModifiedDate")
        @Expose
        private String modifiedDate;

        @SerializedName("Name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getHashTags() {
            return hashTags;
        }

        public void setHashTags(String hashTags) {
            this.hashTags = hashTags;
        }

        public Integer getOpinionId() {
            return opinionId;
        }

        public void setOpinionId(Integer opinionId) {
            this.opinionId = opinionId;
        }

        public String getOpinion() {
            return opinion;
        }

        public void setOpinion(String opinion) {
            this.opinion = opinion;
        }

        public Integer getCommentedUserId() {
            return commentedUserId;
        }

        public void setCommentedUserId(Integer commentedUserId) {
            this.commentedUserId = commentedUserId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public Boolean getComment() {
            return comment;
        }

        public void setComment(Boolean comment) {
            this.comment = comment;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

    }
}
