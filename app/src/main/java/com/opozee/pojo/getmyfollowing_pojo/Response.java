package com.opozee.pojo.getmyfollowing_pojo;

import java.util.List;

public class Response
{
    private String Status;

    private List<GetMyFollowing> GetMyFollowing;

    private String Code;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public List<GetMyFollowing> getGetMyFollowing() {
        return GetMyFollowing;
    }

    public void setGetMyFollowing(List<GetMyFollowing> getMyFollowing) {
        GetMyFollowing = getMyFollowing;
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
        return "ClassPojo [Status = "+Status+", GetMyFollowing = "+GetMyFollowing+", Code = "+Code+"]";
    }
}
