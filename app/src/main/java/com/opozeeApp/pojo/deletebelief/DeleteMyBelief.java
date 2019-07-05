package com.opozeeApp.pojo.deletebelief;

public class DeleteMyBelief
{
    private String Dislikes;

    private String Comment;

    private String CommentedUserId;

    private String CreationDate;

    private String ReactionType;

    private String QuestId;

    private String IsAgree;

    private String Id;

    private String Likes;

    private String ModifiedDate;

    private String LongForm;

    public String getDislikes ()
    {
        return Dislikes;
    }

    public void setDislikes (String Dislikes)
    {
        this.Dislikes = Dislikes;
    }

    public String getComment ()
    {
        return Comment;
    }

    public void setComment (String Comment)
    {
        this.Comment = Comment;
    }

    public String getCommentedUserId ()
    {
        return CommentedUserId;
    }

    public void setCommentedUserId (String CommentedUserId)
    {
        this.CommentedUserId = CommentedUserId;
    }

    public String getCreationDate ()
    {
        return CreationDate;
    }

    public void setCreationDate (String CreationDate)
    {
        this.CreationDate = CreationDate;
    }



    public String getQuestId ()
    {
        return QuestId;
    }

    public void setQuestId (String QuestId)
    {
        this.QuestId = QuestId;
    }

    public String getIsAgree ()
    {
        return IsAgree;
    }

    public void setIsAgree (String IsAgree)
    {
        this.IsAgree = IsAgree;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getLikes ()
    {
        return Likes;
    }

    public void setLikes (String Likes)
    {
        this.Likes = Likes;
    }


    public String getReactionType() {
        return ReactionType;
    }

    public void setReactionType(String reactionType) {
        ReactionType = reactionType;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getLongForm() {
        return LongForm;
    }

    public void setLongForm(String longForm) {
        LongForm = longForm;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Dislikes = "+Dislikes+", Comment = "+Comment+", CommentedUserId = "+CommentedUserId+", CreationDate = "+CreationDate+", ReactionType = "+ReactionType+", QuestId = "+QuestId+", IsAgree = "+IsAgree+", Id = "+Id+", Likes = "+Likes+", ModifiedDate = "+ModifiedDate+", LongForm = "+LongForm+"]";
    }
}

