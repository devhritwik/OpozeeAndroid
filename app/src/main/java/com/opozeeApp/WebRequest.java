package com.opozeeApp;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opozeeApp.pojo.deletebelief.DeleteBelief;
import com.opozeeApp.pojo.deletebelief.DeleteMyBelief;
import com.opozeeApp.pojo.follower_pojo.Following;
import com.opozeeApp.pojo.getallhashtags.GetAllTags;
import com.opozeeApp.pojo.getmyfollowers.GetFollower;
import com.opozeeApp.pojo.getmyfollowing_pojo.GetFollowing;
import com.opozeeApp.pojo.loginemail.LoginEmail;
import com.opozeeApp.pojo.unfollow_pojo.UnFollow;
import com.opozeeApp.retrofit_api.WebUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;



public class WebRequest {
    private static Context mContext;
    private static WebRequest mWebrequest;
    public static APIInterface apiInterface;

    public static WebRequest getSingleton(Context context) {
        if (mWebrequest == null) {
            synchronized ((WebRequest.class)) {
                if (mWebrequest == null) {
                    mWebrequest = new WebRequest(context);
                }
            }
        }
        return mWebrequest;
    }

    public WebRequest(Context context) {
        apiInterface = RestClient.getClient();
        mContext = context;
    }

    private static class RestClient {
        private static APIInterface apiInterface;

        public static APIInterface getClient() {
            if (apiInterface == null) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                OkHttpClient client = new OkHttpClient.Builder().
                        connectTimeout(30, TimeUnit.SECONDS).
                        readTimeout(30, TimeUnit.SECONDS).
                        writeTimeout(30, TimeUnit.SECONDS).
                        build();

                Retrofit retrofit = new Retrofit.Builder()

                        .baseUrl(WebUrl.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                apiInterface = retrofit.create(APIInterface.class);
            }
            return apiInterface;
        }

    }

    public interface APIInterface {

        @POST("Login")
        Call<LoginEmail> loginemailuser(@Header("Content-Type") String Content, @Body String data );

        @POST("Following")
        Call<Following> followuser(@Header("Content-Type") String Content, @Body String data );

        @POST("GetMyFollowers")
        Call<GetFollower> getallfollowers(@Header("Content-Type") String Content, @Body String data );

        @POST("GetMyFollowing")
        Call<GetFollowing> getallfollowing(@Header("Content-Type") String Content, @Body String data );

        @POST("UnfollowUser")
        Call<UnFollow> unfollowuser(@Header("Content-Type") String Content, @Body String data );

        @GET("GetPopularHashTags")
        Call<GetAllTags> getalltags();

        @POST("DeleteMyBelief")
        Call<DeleteBelief> deletebelief(@Header("Content-Type") String content,@Body String data);

    }
}

