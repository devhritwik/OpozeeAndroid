package com.opozee;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opozee.pojo.loginemail.LoginEmail;
import com.opozee.retrofit_api.WebUrl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

//        //Registeration User//
//        @POST("UpperLooksService.svc/RegisterUser")
//        Call<Registeration_user> registor(@Body String json, @Header("Content-Type") String Content);
//
//        //SignIn User//
//        @POST("UpperLooksService.svc/Logon")
//        Call<SignIn> login(@Header("Content-Type") String Content, @Body String d);
//
//        //################### GET CITY APT HIT #####################
//        @GET("/UpperLooksService.svc/GetCity")
//        Call<GetCity> getCity(@Header("Authorization") String token, @Header("Content-Type") String Content);
//
//        //##################### Business Type Get #################
//        @GET("/UpperLooksService.svc/BusinessTypesGet")
//        Call<BusinessResponse> getBusinesstype(@Header("Authorization") String token, @Header("Content-Type") String Content);
//
//        @POST("/UpperLooksService.svc/Searching ")
//        Call<ListingResponse> searchingresult(@Body String jsonObject, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //################### SUGESSTION<BUSINESS DETAIL ##############
//        @POST("/UpperLooksService.svc/Suggestions")
//        Call<ListingResponse> searchingresultSuggestion(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        @POST("UpperLooksService.svc/SendErrorReport")
//        Call<EmailCrash> emailcrash(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //GET offers//
//        @POST("UpperLooksService.svc/GetBusinessOffersDetails")
//        Call<Nearbyoffers> nearbyoffers(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //#################### Business Like Save  ##################
//        @POST("/UpperLooksService.svc/BusinessLikeSave")
//        Call<BusinessLikeResponse> likebusinesss(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //#################### Business DISLike Save  ##################
//        @POST("/UpperLooksService.svc/BusinessDislikeSave")
//        Call<BusinessLikeResponse> dislikebusinesss(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        @POST("UpperLooksService.svc/BusinessDetailsGet")
//        Call<BusinessService> getservices(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        @POST("/UpperLooksService.svc/AddBusinessReview")
//        Call<AddReview> addReview(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //Favourite Added//
//        @POST("UpperLooksService.svc/FavouriteBusinessSave")
//        Call<Favourite> addfavourite(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //Recently viewed//
//        @POST("UpperLooksService.svc/RecentlyViewedBusinessGet")
//        Call<Recent> addrecentviews(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //with device id recent views
//        @POST("UpperLooksService.svc/RecentlyViewedBusinessGet")
//        Call<Recent> addrecentviews(@Body String d, @Header("Content-Type") String Content);
//
//
//        //{"data":{"IPAddress":"192.168.1.1","PageNumber":1,"PageSize":10}}
//        @POST("UpperLooksService.svc/UpdateProfile")
//        Call<Update> updateprofile(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //imageupdate
//        @POST("UpperLooksService.svc/UpdateProfileImage")
//        Call<Imageupdate> updateimage(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
//
//        //Forgot Password//
//        @POST("UpperLooksService.svc/ForgotPassword")
//        Call<Forgot> forgotpassword(@Body String d, @Header("Content-Type") String Content);
//
//        //#################### Add Feedback  ##################
//        @POST("/UpperLooksService.svc/AddFeedBack")
//        Call<AddFeedback> addfeedback(@Body String d, @Header("Authorization") String owner, @Header("Content-Type") String Content);
////#####FavouriteBusines ########
//        @GET("/UpperLooksService.svc/GetFavouruteBusiness")
//        Call<FavouriteBusiness> favouritebusiness(@Header("Authorization") String token);

    }
}
//<android.support.design.widget.TextInputLayout
//        android:id="@+id/tl_number"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:theme="@style/TextLabelWhite"
//        app:layout_constraintStart_toStartOf="parent"
//        android:layout_marginLeft="30dp"
//        android:layout_marginRight="30dp"
//        app:layout_constraintEnd_toEndOf="parent"
//        android:paddingBottom="15dp"
//        android:paddingLeft="15dp"
//        app:layout_constraintTop_toBottomOf="@+id/tl_name"
//        android:layout_marginTop="20dp">
//
//<EditText
//        android:id="@+id/et_user_mobilenumber"
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                android:textSize="20dp"
//                android:maxLength="15"
//                android:inputType="number"
//                android:textColor="@android:color/white"
//                android:textColorHint="@android:color/darker_gray"
//                android:backgroundTint="@android:color/white"
//                android:hint="Mobile Number"
//                />
//
//</android.support.design.widget.TextInputLayout>
