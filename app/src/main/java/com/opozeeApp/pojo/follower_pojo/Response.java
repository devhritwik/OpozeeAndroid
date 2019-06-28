package com.opozeeApp.pojo.follower_pojo;

public class Response
{
    private String Status;

    private FollowerData followerData;

    private String Code;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public FollowerData getFollowerData ()
    {
        return followerData;
    }

    public void setFollowerData (FollowerData followerData)
    {
        this.followerData = followerData;
    }

    public String getCode ()
    {
        return Code;
    }

    public void setCode (String Code)
    {
        this.Code = Code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", followerData = "+followerData+", Code = "+Code+"]";
    }
}


