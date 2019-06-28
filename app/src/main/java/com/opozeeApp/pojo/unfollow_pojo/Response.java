package com.opozeeApp.pojo.unfollow_pojo;

public class Response {
    private String Status;

    private String Code;

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

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", Code = "+Code+"]";
    }
}
