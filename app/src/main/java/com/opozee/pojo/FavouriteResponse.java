package com.opozee.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavouriteResponse {

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
        @SerializedName("GetBookmarkQuestion")
        @Expose
        private GetBookmarkQuestion getBookmarkQuestion;

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

        public GetBookmarkQuestion getGetBookmarkQuestion() {
            return getBookmarkQuestion;
        }

        public void setGetBookmarkQuestion(GetBookmarkQuestion getBookmarkQuestion) {
            this.getBookmarkQuestion = getBookmarkQuestion;
        }

    }

    public class GetBookmarkQuestion {

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

        @SerializedName("Comments")
        @Expose
        private List<Comments> commentsList;


        public List<Comments> getCommentsList() {
            return commentsList;
        }

        public void setCommentsList(List<Comments> commentsList) {
            this.commentsList = commentsList;
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

    public class Comments{
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
        @SerializedName("IsAgree")
        @Expose
        private Boolean isAgree;

        @SerializedName("CheckVisibility")
        @Expose
        private Boolean ischecked;

        @SerializedName("LikesThoughtfullCount")
        @Expose
        private String likesthought;

        @SerializedName("LikesFactualCount")
        @Expose
        private String likesfactual;

        @SerializedName("LikesFunnyCount")
        @Expose
        private String likesfunny;

        @SerializedName("DislikesNoMaterialCount")
        @Expose
        private String dislikenomaterial;

        @SerializedName("DislikesFakeNewsCount")
        @Expose
        private String dislikefakenewscount;

        @SerializedName("DislikesBiasedCount")
        @Expose
        private String dislikebiasedcount;

        public int getSubreation() {
            return subreation;
        }

        public void setSubreation(int subreation) {
            this.subreation = subreation;
        }

        @SerializedName("SubReaction")
        @Expose
        private int subreation;

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

        public Boolean getAgree() {
            return isAgree;
        }

        public void setAgree(Boolean agree) {
            isAgree = agree;
        }

        public Boolean getIschecked() {
            return ischecked;
        }

        public void setIschecked(Boolean ischecked) {
            this.ischecked = ischecked;
        }

        public String getLikesthought() {
            return likesthought;
        }

        public void setLikesthought(String likesthought) {
            this.likesthought = likesthought;
        }

        public String getLikesfactual() {
            return likesfactual;
        }

        public void setLikesfactual(String likesfactual) {
            this.likesfactual = likesfactual;
        }

        public String getLikesfunny() {
            return likesfunny;
        }

        public void setLikesfunny(String likesfunny) {
            this.likesfunny = likesfunny;
        }

        public String getDislikenomaterial() {
            return dislikenomaterial;
        }

        public void setDislikenomaterial(String dislikenomaterial) {
            this.dislikenomaterial = dislikenomaterial;
        }

        public String getDislikefakenewscount() {
            return dislikefakenewscount;
        }

        public void setDislikefakenewscount(String dislikefakenewscount) {
            this.dislikefakenewscount = dislikefakenewscount;
        }

        public String getDislikebiasedcount() {
            return dislikebiasedcount;
        }

        public void setDislikebiasedcount(String dislikebiasedcount) {
            this.dislikebiasedcount = dislikebiasedcount;
        }
    }

}

