package com.opozee.pojo.getmyfollowing_pojo;

public class GetMyFollowing
{
    private String CreationDate;

    private String UserName;

    private String UserID;

    private String ImageURL;

    private String IsFollowing;

    private String FollowerId;

    private String HasFollowBack;

    public String getCreationDate ()
    {
        return CreationDate;
    }

    public void setCreationDate (String CreationDate)
    {
        this.CreationDate = CreationDate;
    }

    public String getUserName ()
    {
        return UserName;
    }

    public void setUserName (String UserName)
    {
        this.UserName = UserName;
    }

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    public String getImageURL ()
    {
        return ImageURL;
    }

    public void setImageURL (String ImageURL)
    {
        this.ImageURL = ImageURL;
    }

    public String getIsFollowing ()
    {
        return IsFollowing;
    }

    public void setIsFollowing (String IsFollowing)
    {
        this.IsFollowing = IsFollowing;
    }

    public String getFollowerId ()
    {
        return FollowerId;
    }

    public void setFollowerId (String FollowerId)
    {
        this.FollowerId = FollowerId;
    }

    public String getHasFollowBack ()
    {
        return HasFollowBack;
    }

    public void setHasFollowBack (String HasFollowBack)
    {
        this.HasFollowBack = HasFollowBack;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CreationDate = "+CreationDate+", UserName = "+UserName+", UserID = "+UserID+", ImageURL = "+ImageURL+", IsFollowing = "+IsFollowing+", FollowerId = "+FollowerId+", HasFollowBack = "+HasFollowBack+"]";
    }
}
