package com.opozee.pojo;


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class DeletePostResponse {

    @SerializedName("Response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public class DeleteQuestion {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("PostQuestion")
        @Expose
        private String postQuestion;
        @SerializedName("OwnerUserID")
        @Expose
        private Integer ownerUserID;
        @SerializedName("HashTags")
        @Expose
        private String hashTags;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("ModifiedDate")
        @Expose
        private Object modifiedDate;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPostQuestion() {
            return postQuestion;
        }

        public void setPostQuestion(String postQuestion) {
            this.postQuestion = postQuestion;
        }

        public Integer getOwnerUserID() {
            return ownerUserID;
        }

        public void setOwnerUserID(Integer ownerUserID) {
            this.ownerUserID = ownerUserID;
        }

        public String getHashTags() {
            return hashTags;
        }

        public void setHashTags(String hashTags) {
            this.hashTags = hashTags;
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

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

    }

    public class Response {

        @SerializedName("Code")
        @Expose
        private Integer code;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("DeleteQuestion")
        @Expose
        private DeleteQuestion deleteQuestion;

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

        public DeleteQuestion getDeleteQuestion() {
            return deleteQuestion;
        }

        public void setDeleteQuestion(DeleteQuestion deleteQuestion) {
            this.deleteQuestion = deleteQuestion;
        }

    }
}
