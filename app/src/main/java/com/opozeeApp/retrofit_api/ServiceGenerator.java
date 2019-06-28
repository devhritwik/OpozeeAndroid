package com.opozeeApp.retrofit_api;

import android.util.Log;
import com.opozeeApp.BuildConfig;
import com.opozeeApp.application.QuestionnaireApplication;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final int DISK_CACHE_SIZE = 10485760;
    private static OkHttpClient defaultOkHttpClient = new Builder().cache(getCache()).build();
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());

    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        return createService(serviceClass, baseUrl, null);
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, WebUrl.BASE_URL, null);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, Interceptor networkInterceptor) {
        Builder okHttpClientBuilder = defaultOkHttpClient.newBuilder();
        if (networkInterceptor != null) {
            okHttpClientBuilder.addNetworkInterceptor(networkInterceptor);
        }
        retrofitBuilder.client(okHttpClientBuilder.addInterceptor(getHttpLoggingInterceptor()).build());
        retrofitBuilder.baseUrl(baseUrl);
        return retrofitBuilder.build().create(serviceClass);
    }

    private static Cache getCache() {
        try {
            return new Cache(new File(QuestionnaireApplication.getCacheDirectory(), "http"), DISK_CACHE_SIZE);
        } catch (Exception e) {
            Log.e(e.toString(), "Unable to install disk cache.");
            return null;
        }
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(Level.NONE);
        }
        return httpLoggingInterceptor;
    }
}
