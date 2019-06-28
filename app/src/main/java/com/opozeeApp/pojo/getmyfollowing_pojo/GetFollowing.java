package com.opozeeApp.pojo.getmyfollowing_pojo;

public class GetFollowing {
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
