package com.opozee.pojo;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;



public class SearchQuestionResponse {

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
        @SerializedName("SearchQuestion")
        @Expose
        private List<SearchQuestion> searchQuestion = null;

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

        public List<SearchQuestion> getSearchQuestion() {
            return searchQuestion;
        }

        public void setSearchQuestion(List<SearchQuestion> searchQuestion) {
            this.searchQuestion = searchQuestion;
        }

    }
    public class SearchQuestion {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Question")
        @Expose
        private String question;
        @SerializedName("OwnerUserID")
        @Expose
        private Integer ownerUserID;
        @SerializedName("OwnerUserName")
        @Expose
        private String ownerUserName;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("UserImage")
        @Expose
        private String userImage;
        @SerializedName("HashTags")
        @Expose
        private String hashTags;
        @SerializedName("TotalLikes")
        @Expose
        private Integer totalLikes;
        @SerializedName("TotalDisLikes")
        @Expose
        private Integer totalDisLikes;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("MostLiked")
        @Expose
        private Object mostLiked;
        @SerializedName("MostDisliked")
        @Expose
        private Object mostDisliked;

        @SerializedName("YesCount")
        @Expose
        private Integer yesCount;
        @SerializedName("NoCount")
        @Expose
        private Integer noCount;

        public Integer getYesCount() {
            return yesCount;
        }

        public void setYesCount(Integer yesCount) {
            this.yesCount = yesCount;
        }

        public Integer getNoCount() {
            return noCount;
        }

        public void setNoCount(Integer noCount) {
            this.noCount = noCount;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Integer getOwnerUserID() {
            return ownerUserID;
        }

        public void setOwnerUserID(Integer ownerUserID) {
            this.ownerUserID = ownerUserID;
        }

        public String getOwnerUserName() {
            return ownerUserName;
        }

        public void setOwnerUserName(String ownerUserName) {
            this.ownerUserName = ownerUserName;
        }

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

        public String getHashTags() {
            return hashTags;
        }

        public void setHashTags(String hashTags) {
            this.hashTags = hashTags;
        }

        public Integer getTotalLikes() {
            return totalLikes;
        }

        public void setTotalLikes(Integer totalLikes) {
            this.totalLikes = totalLikes;
        }

        public Integer getTotalDisLikes() {
            return totalDisLikes;
        }

        public void setTotalDisLikes(Integer totalDisLikes) {
            this.totalDisLikes = totalDisLikes;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public Object getMostLiked() {
            return mostLiked;
        }

        public void setMostLiked(Object mostLiked) {
            this.mostLiked = mostLiked;
        }

        public Object getMostDisliked() {
            return mostDisliked;
        }

        public void setMostDisliked(Object mostDisliked) {
            this.mostDisliked = mostDisliked;
        }

    }

}
