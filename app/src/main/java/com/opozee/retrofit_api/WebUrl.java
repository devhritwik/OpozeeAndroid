package com.opozee.retrofit_api;

public interface WebUrl {

    public static final String AUTHORIZATION_HEADER = "Authorization:Basic b3Bvc2VlOm9wb3NlZTk5IQ==";
    public static final String CONTENT_HEADER = "Content-Type:application/x-www-form-urlencoded";

//    public static final String BASE_URL = "http://23.111.138.246:8021/oposee/api/MobileApi/";
   //public static final String BASE_URL = "https://4e422dd2.ngrok.io/opozee/api/MobileApi/";
//    public static final String BASE_URL = "https://opozee.com:81/opozee/api/MobileApi/";
//    public static final String BASE_URL = "http://test.opozee.com:81/opozee/api/MobileApi/";


    //SandboxUrl
    public static final String BASE_URL = "https://opozee.com:81/opozee/api/MobileApi/";
//    public static final String BASE_URL = "http://test.opozee.com:81/opozee/api/MobileApi/";
    public static final String REGISTRER_LOGIN = "http://test.opozee.com/register";
    public static final String FORGOT_PASSWORD = "http://test.opozee.com/login";


    public static final String IMAGE_BASE_URL = "https://opozee.com/Content/EventImages/";
    public static final String GET_ALL_POSTS_BY_USER_ID_URL = "GetAllPostsByUserId";
    public static final String LIKE_DISLIKE_URL = "LikeDislikeOpinion";
    public static final String UPDATE_PROFILE_URL = "UpdateUserProfile";
    public static final String GET_PROFILE_URL = "GetUserProfile";
    public static final String POST_OPINION_URL = "PostAnswer";
    public static final String SEARCH_URL = "SearchByHashTagOrString";
    public static final String BOOK_MARK_URL = "BookMarkQuestion";
    public static final String GET_QUESTION_DETAIL_URL = "GetAllOpinion";
    public static final String USER_LOGIN_URL = "signinthirdparty";
    public static final String POST_QUESTION_URL = "PostQuestion";
//    public static final String GET_ALL_QUESTION_URL = "GetAllPosts";
    public static final String GET_ALL_QUESTION_URL = "GetAllPostsMobile";
    public static final String DELETE_QUESTION_URL = "DeleteQuestion";
    public static final String GET_ALL_USERS_URL = "GetAllUsers";
    public static final String GET_ALL_TAGGED_USERS_URL = "GetAllTaggedUsers";
    public static final String GET_ALL_NOTIFICATIONS_URL = "GetAllNotificationByUser";
    public static final String GET_ALL_FAVOURITES_URL = "GetAllBookMarkById";
    public static final String GET_ALL_USER_BELIEFS_URL = "GetUserBeliefs";
    public static final String USER_LOGIN_API = "Login";





//    public static final String DELETE_QUESTION_URL = "GetAllNotificationByUser?userId=3";

//    http://23.111.138.246:8021/oposee/api/MobileApi/?=1
}
