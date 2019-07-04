package com.opozeeApp.retrofit_api;

public interface WebUrl {

    String AUTHORIZATION_HEADER = "Authorization:Basic b3Bvc2VlOm9wb3NlZTk5IQ==";
    String CONTENT_HEADER = "Content-Type:application/x-www-form-urlencoded";

//    public static final String BASE_URL = "http://23.111.138.246:8021/oposee/api/MobileApi/";
   //public static final String BASE_URL = "https://4e422dd2.ngrok.io/opozee/api/MobileApi/";
//    public static final String BASE_URL = "https://opozee.com:81/opozee/api/MobileApi/";
//    public static final String BASE_URL = "http://test.opozee.com:81/opozee/api/MobileApi/";


    //SandboxUrl
    String BASE_URL = "https://opozee.com:81/opozee/api/MobileApi/";
    //String BASE_URL = "http://localhost:61545/opozee/api/MobileApi/";
//    public static final String BASE_URL = "http://test.opozee.com:81/opozee/api/MobileApi/";
    String REGISTRER_LOGIN = "https://opozee.com/register";
    String FORGOT_PASSWORD = "https://opozee.com/login";


    String IMAGE_BASE_URL = "https://opozee.com/Content/EventImages/";
    String GET_ALL_POSTS_BY_USER_ID_URL = "GetAllPostsByUserId";
    String LIKE_DISLIKE_URL = "LikeDislikeOpinion";
    String UPDATE_PROFILE_URL = "UpdateUserProfile";
    String GET_PROFILE_URL = "GetUserProfile";
    String POST_OPINION_URL = "PostAnswer";
    String SEARCH_URL = "SearchByHashTagOrString";
    String BOOK_MARK_URL = "BookMarkQuestion";
    String GET_QUESTION_DETAIL_URL = "GetAllOpinion";
    String USER_LOGIN_URL = "signinthirdparty";
    String POST_QUESTION_URL = "PostQuestion";
//    public static final String GET_ALL_QUESTION_URL = "GetAllPosts";
String GET_ALL_QUESTION_URL = "GetAllPostsMobile";
    String DELETE_QUESTION_URL = "DeleteQuestion";
    String GET_ALL_USERS_URL = "GetAllUsers";
    String GET_ALL_TAGGED_USERS_URL = "GetAllTaggedUsers";
    String GET_ALL_NOTIFICATIONS_URL = "GetAllNotificationByUser";
    String GET_ALL_FAVOURITES_URL = "GetAllBookMarkById";
    String GET_ALL_USER_BELIEFS_URL = "GetUserBeliefs";
    String USER_LOGIN_API = "Login";





//    public static final String DELETE_QUESTION_URL = "GetAllNotificationByUser?userId=3";

//    http://23.111.138.246:8021/oposee/api/MobileApi/?=1
}
