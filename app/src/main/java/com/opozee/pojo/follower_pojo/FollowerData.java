package com.opozee.pojo.follower_pojo;

public class FollowerData
{
    private String CreationDate;

    private String UserId;

    private String IsFollowing;

    private String Id;

    private String FollowedId;

    public String getCreationDate ()
    {
        return CreationDate;
    }

    public void setCreationDate (String CreationDate)
    {
        this.CreationDate = CreationDate;
    }

    public String getUserId ()
    {
        return UserId;
    }

    public void setUserId (String UserId)
    {
        this.UserId = UserId;
    }

    public String getIsFollowing ()
    {
        return IsFollowing;
    }

    public void setIsFollowing (String IsFollowing)
    {
        this.IsFollowing = IsFollowing;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getFollowedId ()
    {
        return FollowedId;
    }

    public void setFollowedId (String FollowedId)
    {
        this.FollowedId = FollowedId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CreationDate = "+CreationDate+", UserId = "+UserId+", IsFollowing = "+IsFollowing+", Id = "+Id+", FollowedId = "+FollowedId+"]";
    }
}


