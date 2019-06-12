package com.opozee.pojo;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;
        import com.opozee.interfaces.TopBeliefs;


public class PostedQuestionsResponse {

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
        @SerializedName("AllUserQuestions")
        @Expose
        private AllUserQuestions allUserQuestions;

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

        public AllUserQuestions getAllUserQuestions() {
            return allUserQuestions;
        }

        public void setAllUserQuestions(AllUserQuestions allUserQuestions) {
            this.allUserQuestions = allUserQuestions;
        }

    }

    public class PostQuestionDetail {

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
        @SerializedName("MostYesLiked")
        @Expose
        private MostLiked mostLiked;
        @SerializedName("MostNoLiked")
        @Expose
        private MostDisliked mostDisliked;

        @SerializedName("YesCount")
        @Expose
        private Integer yesCount;

        @SerializedName("NoCount")
        @Expose
        private Integer noCount;


        @SerializedName("ReactionSum")
        @Expose
        private String reactionsum;

        @SerializedName("LastActivityTime")
        @Expose
        private String activitytime;

        public String getActivitytime() {
            return activitytime;
        }

        public void setActivitytime(String activitytime) {
            this.activitytime = activitytime;
        }

        public String getReactionsum() {
            return reactionsum;
        }

        public void setReactionsum(String reactionsum) {
            this.reactionsum = reactionsum;
        }

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

        public MostLiked getMostLiked() {
            return mostLiked;
        }

        public void setMostLiked(MostLiked mostLiked) {
            this.mostLiked = mostLiked;
        }

        public MostDisliked getMostDisliked() {
            return mostDisliked;
        }

        public void setMostDisliked(MostDisliked mostDisliked) {
            this.mostDisliked = mostDisliked;
        }

    }

    public class MostLiked implements TopBeliefs {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("CommentedUserId")
        @Expose
        private Integer commentedUserId;
        @SerializedName("CommentedUserName")
        @Expose
        private String commentedUserName;
        @SerializedName("LikesCount")
        @Expose
        private Integer likesCount;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("UserImage")
        @Expose
        private String userImage;
        @SerializedName("DislikesCount")
        @Expose
        private Integer dislikesCount;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("Likes")
        @Expose
        private Boolean likes;
        @SerializedName("DisLikes")
        @Expose
        private Boolean disLikes;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getCommentedUserName() {
            return commentedUserName;
        }

        public void setCommentedUserName(String commentedUserName) {
            this.commentedUserName = commentedUserName;
        }

        public Integer getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(Integer likesCount) {
            this.likesCount = likesCount;
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

        public Integer getDislikesCount() {
            return dislikesCount;
        }

        public void setDislikesCount(Integer dislikesCount) {
            this.dislikesCount = dislikesCount;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public Boolean getLikes() {
            return likes;
        }

        public void setLikes(Boolean likes) {
            this.likes = likes;
        }

        public Boolean getDisLikes() {
            return disLikes;
        }

        public void setDisLikes(Boolean disLikes) {
            this.disLikes = disLikes;
        }

    }

    public class MostDisliked implements TopBeliefs {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Comment")
        @Expose
        private String comment;
        @SerializedName("CommentedUserId")
        @Expose
        private Integer commentedUserId;
        @SerializedName("CommentedUserName")
        @Expose
        private String commentedUserName;
        @SerializedName("LikesCount")
        @Expose
        private Integer likesCount;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("UserImage")
        @Expose
        private String userImage;
        @SerializedName("DislikesCount")
        @Expose
        private Integer dislikesCount;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("Likes")
        @Expose
        private Boolean likes;
        @SerializedName("DisLikes")
        @Expose
        private Boolean disLikes;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getCommentedUserName() {
            return commentedUserName;
        }

        public void setCommentedUserName(String commentedUserName) {
            this.commentedUserName = commentedUserName;
        }

        public Integer getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(Integer likesCount) {
            this.likesCount = likesCount;
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

        public Integer getDislikesCount() {
            return dislikesCount;
        }

        public void setDislikesCount(Integer dislikesCount) {
            this.dislikesCount = dislikesCount;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public Boolean getLikes() {
            return likes;
        }

        public void setLikes(Boolean likes) {
            this.likes = likes;
        }

        public Boolean getDisLikes() {
            return disLikes;
        }

        public void setDisLikes(Boolean disLikes) {
            this.disLikes = disLikes;
        }

    }

    public class AllUserQuestions {

        @SerializedName("PostQuestionDetail")
        @Expose
        private List<PostQuestionDetail> postQuestionDetail = null;

        public List<PostQuestionDetail> getPostQuestionDetail() {
            return postQuestionDetail;
        }

        public void setPostQuestionDetail(List<PostQuestionDetail> postQuestionDetail) {
            this.postQuestionDetail = postQuestionDetail;
        }

    }

}


