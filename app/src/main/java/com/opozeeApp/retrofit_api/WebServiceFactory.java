package com.opozeeApp.retrofit_api;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.opozeeApp.utils.WebBinder;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceFactory {

    private static WebService api;

    public static WebService getInstance() {
        if(api == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1));

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
//            if (BuildConfig.DEBUG) {
//                builder.interceptors().add(new LoggingInterceptor());
//            }

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Basic " + WebBinder.addAuthHeader()).build();
                    return chain.proceed(request);
                }
            });

            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);


            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(WebUrl.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create());

            OkHttpClient client = httpClient.build();


            Retrofit retrofit = builder.client(client).build();

            api =  retrofit.create(WebService.class);
        }
        return api;
    }


    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            Logger logger = Logger
            long t1 = System.nanoTime();
            Log.d("", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d("", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

//
//    public class LongTypeAdapter extends TypeAdapter<Long> {
//        @Override
//        public Long read(JsonReader reader) throws IOException {
//            if (reader.peek() == JsonToken.NULL) {
//                reader.nextNull();
//                return null;
//            }
//            String stringValue = reader.nextString();
//            try {
//                Long value = Long.valueOf(stringValue);
//                return value;
//            } catch (NumberFormatException e) {
//                return null;
//            }
//        }
//
//        @Override
//        public void write(JsonWriter writer, Long value) throws IOException {
//            if (value == null) {
//                writer.nullValue();
//                return;
//            }
//            writer.value(value);
//        }
//
//    }

}