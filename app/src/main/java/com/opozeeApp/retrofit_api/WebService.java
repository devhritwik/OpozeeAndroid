package com.opozeeApp.retrofit_api;



import com.opozeeApp.models.Belief;
import com.opozeeApp.params.BookMarkParams;
import com.opozeeApp.params.LikeDislikeParams;
import com.opozeeApp.params.LoginParams;
import com.opozeeApp.params.OpinionParams;
import com.opozeeApp.params.PostQuestionParams;
import com.opozeeApp.pojo.BookMarkResponse;
import com.opozeeApp.pojo.DeletePostResponse;
import com.opozeeApp.pojo.FavouriteResponse;
import com.opozeeApp.pojo.LikeDislikeResponse;
import com.opozeeApp.pojo.LoginResponse;
import com.opozeeApp.pojo.NotificationsResponse;
import com.opozeeApp.pojo.OpinionResponse;
import com.opozeeApp.pojo.PostQuestionResponse;
import com.opozeeApp.pojo.PostedQuestionsResponse;
import com.opozeeApp.pojo.ProfileResponse;
import com.opozeeApp.pojo.QuestionDetailResponse;
import com.opozeeApp.pojo.SearchQuestionResponse;
import com.opozeeApp.pojo.TagUsersResponse;
import com.opozeeApp.pojo.loginemail.LoginEmail;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebService {


//    @FormUrlEncoded
//    @POST(WebUrl.DISTANCE_INAPP_URL)
//    @Headers({WebUrl.CONTENT_HEADER, WebUrl.AUTHORIZATION_HEADER})
//    Call<StatusResponse> distanceInApp(@FieldMap Map<String, String> map);

//
//    @Multipart
//    @POST(WebUrl.USER_LOGIN_URL)
//    Call<LoginSignupResponse> loginUser(@Part("UserName") RequestBody UserName, @Part("Email") RequestBody Email,
//                                        @Part("Password") RequestBody Password, @Part("DeviceToken") RequestBody deviceToken,
//                                        @Part("DeviceType") RequestBody DeviceType);
//
//    @FormUrlEncoded
//    @Headers({WebUrl.AUTHORIZATION_HEADER})
//    @POST(WebUrl.USER_LOGIN_URL)
//    Call<LoginSignupResponse> loginUser(@FieldMap Map<String, String> params);


    @POST(WebUrl.USER_LOGIN_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<LoginResponse> loginUser(@Body LoginParams params);

    //LoginAPI//
    @POST("Login")
    Call<List<LoginEmail>> loginemailuser(@Header("Content-Type") String Content, @Body String data );

    @POST(WebUrl.POST_QUESTION_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<PostQuestionResponse> postQuestion(@Body PostQuestionParams params);

    @POST(WebUrl.POST_QUESTION_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<PostQuestionResponse> editPost(@Body PostQuestionParams params);

    @GET(WebUrl.GET_ALL_QUESTION_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<PostedQuestionsResponse> getAllQuestions(@Query("UserID") String userID, @Query("Pageindex") int Pageindex, @Query("Pagesize") int Pagesize,@Query("Sort") int sort,@Query("Search") String search);

    @GET(WebUrl.GET_ALL_NOTIFICATIONS_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<NotificationsResponse> getAllNotifications(@Query("userId") String userID, @Query("Pageindex") int Pageindex, @Query("Pagesize") int Pagesize);

    @GET(WebUrl.GET_ALL_POSTS_BY_USER_ID_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<PostedQuestionsResponse> getAllPostsByUserId(@Query("UserID") String userID, @Query("Pageindex") int Pageindex, @Query("Pagesize") int Pagesize);

    //user profile
    @GET(WebUrl.GET_PROFILE_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<ProfileResponse> getUserProfile(@Query("userid") String owneruserID,@Query("viewUserId") String viewuserid );

    @Multipart
    @POST(WebUrl.UPDATE_PROFILE_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
//    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);
    Call<ProfileResponse> editProfile(@Part MultipartBody.Part part, @Part("UserID") RequestBody userIdBody, @Part("FirstName") RequestBody firstNameBody, @Part("LastName") RequestBody lastNameBody,@Part("UserName") RequestBody username,@Part("UserInfo") RequestBody userinfo);

//    Image

    @GET(WebUrl.GET_QUESTION_DETAIL_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<QuestionDetailResponse> getQuestionDetail(@Query("questId") String questId, @Query("userid") String user_id, @Query("Pageindex") int Pageindex, @Query("Pagesize") int Pagesize);

    @GET(WebUrl.GET_ALL_FAVOURITES_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<FavouriteResponse> getAllFavourites(@Query("userId") String user_id, @Query("Pageindex") int Pageindex, @Query("Pagesize") int Pagesize);

    @POST(WebUrl.DELETE_QUESTION_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<DeletePostResponse> deleteQuestion(@Query("questId") String questId);

    @GET(WebUrl.SEARCH_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<SearchQuestionResponse> searchQuestion(@Query("search") String search, @Query("Pageindex") int Pageindex, @Query("Pagesize") int Pagesize);

    @GET(WebUrl.GET_ALL_USERS_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<TagUsersResponse> getAllUsers(@Query("Pageindex") int Pageindex, @Query("Pagesize") int Pagesize);

    @GET(WebUrl.GET_ALL_TAGGED_USERS_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<TagUsersResponse> getAllTaggedUsers(@Query("questId") String questId);

    @POST(WebUrl.BOOK_MARK_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<BookMarkResponse> bookMarkQuestion(@Body BookMarkParams params);

    @POST(WebUrl.POST_OPINION_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<OpinionResponse> addOpinion(@Body OpinionParams params);

    @POST(WebUrl.LIKE_DISLIKE_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<LikeDislikeResponse> likeDislikeOpinion(@Body LikeDislikeParams params);

    @GET(WebUrl.GET_ALL_USER_BELIEFS_URL)
    @Headers({WebUrl.AUTHORIZATION_HEADER})
    Call<List<Belief>> getUserBeliefs(@Query("userId") int userID);


}
