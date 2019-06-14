package com.opozee.pojo.unfollow_pojo;

public class UnFollow {

    private Response Response;

    public Response getResponse ()
    {
        return Response;
    }

    public void setResponse (Response Response)
    {
        this.Response = Response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Response = "+Response+"]";
    }
}
