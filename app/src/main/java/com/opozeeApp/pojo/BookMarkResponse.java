package com.opozeeApp.pojo;


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class BookMarkResponse {

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
        @SerializedName("Question")
        @Expose
        private Question question;

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

        public Question getQuestion() {
            return question;
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

    }

    public class Question {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("QuestionId")
        @Expose
        private Integer questionId;
        @SerializedName("IsBookmark")
        @Expose
        private Boolean isBookmark;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
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

        public Integer getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Integer questionId) {
            this.questionId = questionId;
        }

        public Boolean getIsBookmark() {
            return isBookmark;
        }

        public void setIsBookmark(Boolean isBookmark) {
            this.isBookmark = isBookmark;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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


