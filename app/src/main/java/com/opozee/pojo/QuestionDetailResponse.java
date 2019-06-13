package com.opozee.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class QuestionDetailResponse {

    @SerializedName("Response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class AllOpinion {

        @SerializedName("PostQuestionDetail")
        @Expose
        private PostQuestionDetail postQuestionDetail;
        @SerializedName("Comments")
        @Expose
        private List<Comment> comments = null;

        public PostQuestionDetail getPostQuestionDetail() {
            return postQuestionDetail;
        }

        public void setPostQuestionDetail(PostQuestionDetail postQuestionDetail) {
            this.postQuestionDetail = postQuestionDetail;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

    }

    public class Comment {

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

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @SerializedName("code")
        @Expose
        private int code;


        public Boolean getIschecked() {
            return ischecked;
        }

        public void setIschecked(Boolean ischecked) {
            this.ischecked = ischecked;
        }

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

        public Boolean getIsAgree() {
            return isAgree;
        }

        public void setIsAgree(Boolean isAgree) {
            this.isAgree = isAgree;
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
        @SerializedName("UserImage")
        @Expose
        private String userImage;
        @SerializedName("HashTags")
        @Expose
        private String hashTags;
        @SerializedName("CreationDate")
        @Expose
        private String creationDate;
        @SerializedName("IsBookmark")
        @Expose
        private Boolean isBookmark;
        @SerializedName("BookmarkId")
        @Expose
        private Integer bookmarkId;
        @SerializedName("IsUserPosted")
        @Expose
        private Boolean isUserPosted;
        @SerializedName("TotalLikes")
        @Expose
        private Integer totalLikes;
        @SerializedName("TotalDisLikes")
        @Expose
        private Integer totalDisLikes;

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("TaggedUsers")
        @Expose
        private List<Object> taggedUsers = null;

        @SerializedName("YesCount")
        @Expose
        private Integer yesCount;
        @SerializedName("NoCount")
        @Expose
        private Integer noCount;

        @SerializedName("ReactionSum")
        @Expose
        private String reactiontime;
        @SerializedName("LastActivityTime")
        @Expose

        private String lastactivitytime;
        public String getReactiontime() {
            return reactiontime;
        }

        public void setReactiontime(String reactiontime) {
            this.reactiontime = reactiontime;
        }

        public String getLastactivitytime() {
            return lastactivitytime;
        }

        public void setLastactivitytime(String lastactivitytime) {
            this.lastactivitytime = lastactivitytime;
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

        public Boolean getBookmark() {
            return isBookmark;
        }

        public void setBookmark(Boolean bookmark) {
            isBookmark = bookmark;
        }

        public Boolean getUserPosted() {
            return isUserPosted;
        }

        public void setUserPosted(Boolean userPosted) {
            isUserPosted = userPosted;
        }

        public List<Object> getTaggedUsers() {
            return taggedUsers;
        }

        public void setTaggedUsers(List<Object> taggedUsers) {
            this.taggedUsers = taggedUsers;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public Boolean getIsBookmark() {
            return isBookmark;
        }

        public void setIsBookmark(Boolean isBookmark) {
            this.isBookmark = isBookmark;
        }

        public Integer getBookmarkId() {
            return bookmarkId;
        }

        public void setBookmarkId(Integer bookmarkId) {
            this.bookmarkId = bookmarkId;
        }

        public Boolean getIsUserPosted() {
            return isUserPosted;
        }

        public void setIsUserPosted(Boolean isUserPosted) {
            this.isUserPosted = isUserPosted;
        }

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
        private AllOpinion allOpinion;

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

        public AllOpinion getAllOpinion() {
            return allOpinion;
        }

        public void setAllOpinion(AllOpinion allOpinion) {
            this.allOpinion = allOpinion;
        }

    }

}


