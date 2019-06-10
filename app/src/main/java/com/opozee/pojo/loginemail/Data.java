package com.opozee.pojo.loginemail;

public class Data
{
    private String ReferralCode;

    private String Email;

    private String UserName;

    private String BalanceToken;

    private String LastLoginDate;

    private String ImageURL;

    private String Token;

    private String Followings;

    private String IsVerificationLogin;

    private String Followers;

    private String DeviceType;

    private String TotalReferred;

    private String DeviceToken;

    private String IsSocialLogin;

    private AuthToken AuthToken;

    private String Id;

    private String Password;

    public String getReferralCode ()
    {
        return ReferralCode;
    }

    public void setReferralCode (String ReferralCode)
    {
        this.ReferralCode = ReferralCode;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getUserName ()
    {
        return UserName;
    }

    public void setUserName (String UserName)
    {
        this.UserName = UserName;
    }

    public String getBalanceToken ()
    {
        return BalanceToken;
    }

    public void setBalanceToken (String BalanceToken)
    {
        this.BalanceToken = BalanceToken;
    }

    public String getLastLoginDate ()
    {
        return LastLoginDate;
    }

    public void setLastLoginDate (String LastLoginDate)
    {
        this.LastLoginDate = LastLoginDate;
    }

    public String getImageURL ()
    {
        return ImageURL;
    }

    public void setImageURL (String ImageURL)
    {
        this.ImageURL = ImageURL;
    }

    public String getToken ()
    {
        return Token;
    }

    public void setToken (String Token)
    {
        this.Token = Token;
    }

    public String getFollowings ()
    {
        return Followings;
    }

    public void setFollowings (String Followings)
    {
        this.Followings = Followings;
    }

    public String getIsVerificationLogin ()
    {
        return IsVerificationLogin;
    }

    public void setIsVerificationLogin (String IsVerificationLogin)
    {
        this.IsVerificationLogin = IsVerificationLogin;
    }

    public String getFollowers ()
    {
        return Followers;
    }

    public void setFollowers (String Followers)
    {
        this.Followers = Followers;
    }

    public String getDeviceType ()
    {
        return DeviceType;
    }

    public void setDeviceType (String DeviceType)
    {
        this.DeviceType = DeviceType;
    }

    public String getTotalReferred ()
    {
        return TotalReferred;
    }

    public void setTotalReferred (String TotalReferred)
    {
        this.TotalReferred = TotalReferred;
    }

    public String getDeviceToken ()
    {
        return DeviceToken;
    }

    public void setDeviceToken (String DeviceToken)
    {
        this.DeviceToken = DeviceToken;
    }

    public String getIsSocialLogin ()
    {
        return IsSocialLogin;
    }

    public void setIsSocialLogin (String IsSocialLogin)
    {
        this.IsSocialLogin = IsSocialLogin;
    }

    public AuthToken getAuthToken ()
    {
        return AuthToken;
    }

    public void setAuthToken (AuthToken AuthToken)
    {
        this.AuthToken = AuthToken;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [ReferralCode = "+ReferralCode+", Email = "+Email+", UserName = "+UserName+", BalanceToken = "+BalanceToken+", LastLoginDate = "+LastLoginDate+", ImageURL = "+ImageURL+", Token = "+Token+", Followings = "+Followings+", IsVerificationLogin = "+IsVerificationLogin+", Followers = "+Followers+", DeviceType = "+DeviceType+", TotalReferred = "+TotalReferred+", DeviceToken = "+DeviceToken+", IsSocialLogin = "+IsSocialLogin+", AuthToken = "+AuthToken+", Id = "+Id+", Password = "+Password+"]";
    }
}