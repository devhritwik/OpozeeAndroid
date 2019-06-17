package com.opozee.pojo.getallhashtags;

public class Data
{
    private String HashTag;

    private String Count;

    public String getHashTag ()
    {
        return HashTag;
    }

    public void setHashTag (String HashTag)
    {
        this.HashTag = HashTag;
    }

    public String getCount ()
    {
        return Count;
    }

    public void setCount (String Count)
    {
        this.Count = Count;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [HashTag = "+HashTag+", Count = "+Count+"]";
    }
}
