package com.opozee.utils;

/**
 * @author Nauman Zubair
 */

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;


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
        try {
            authStrEncoded = new String(Base64.encode(
                    authStr.getBytes("UTF-8"), Base64.NO_WRAP));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Log.d("AuthString", authStrEncoded);
        return authStrEncoded;
    }

}
