package com.opozeeApp.pojo.deletebelief;

public class Response
{
    private String Status;

    private DeleteMyBelief DeleteMyBelief;

    private String Code;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public DeleteMyBelief getDeleteMyBelief ()
    {
        return DeleteMyBelief;
    }

    public void setDeleteMyBelief (DeleteMyBelief DeleteMyBelief)
    {
        this.DeleteMyBelief = DeleteMyBelief;
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
        return "ClassPojo [Status = "+Status+", DeleteMyBelief = "+DeleteMyBelief+", Code = "+Code+"]";
    }
}


