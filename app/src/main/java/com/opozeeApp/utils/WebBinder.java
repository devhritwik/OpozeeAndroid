package com.opozeeApp.utils;

/**
 * @author Nauman Zubair
 */

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


public class WebBinder {

    public static String AUTH_USERNAME = "oposee";
    public static String AUTH_PASSWORD = "oposee99!";

    private static String TAG = "ERROR";
    public Context context;

    public WebBinder(Context context) {
        this.context = context;
    }

    public static String addAuthHeader() {
        String authStr = AUTH_USERNAME + ":" + AUTH_PASSWORD;
        // Encode authentication values, and add to header
        String authStrEncoded = "";
        authStrEncoded = new String(Base64.encode(
                authStr.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP));
        Log.d("AuthString", authStrEncoded);
        return authStrEncoded;
    }

}
