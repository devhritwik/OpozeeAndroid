package com.opozeeApp.pojo.getmyfollowers;

import java.util.List;

public class Response
{
    private String Status;

    private String Code;

    private List<GetMyFollowers> GetMyFollowers;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public String getCode ()
    {
        return Code;
    }

    public void setCode (String Code)
    {
        this.Code = Code;
    }

    public List<com.opozeeApp.pojo.getmyfollowers.GetMyFollowers> getGetMyFollowers() {
        return GetMyFollowers;
    }

    public void setGetMyFollowers(List<com.opozeeApp.pojo.getmyfollowers.GetMyFollowers> getMyFollowers) {
        GetMyFollowers = getMyFollowers;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", Code = "+Code+", GetMyFollowers = "+GetMyFollowers+"]";
    }
}
