package com.opozeeApp.social_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.opozeeApp.R;
import com.opozeeApp.activities.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FBLogin {

    private static final String TAG = "FBLOGIN";
    private Activity activity;
    private CallbackManager callbackManager;
    private String facebookID;

    public FBLogin(Activity activity, CallbackManager callbackManager)
    {
        this.activity = activity;
//        this.callbackManager = callbackManager;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginUser() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d(TAG, "Facebook Login onSuccess()");
                        AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                        Profile.fetchProfileForCurrentAccessToken();

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());
                                        Profile fbProfile = Profile.getCurrentProfile();
                                        String name = "";
                                        String picture = null;
                                        String email = "";
                                        Log.d(TAG, "Friends Response :" + response);
                                        JSONObject json = null;
                                        try {

                                            if (response != null) {
                                                json = response.getJSONObject();
                                                if (fbProfile != null) {
                                                    facebookID = fbProfile.getId();
                                                    name = fbProfile.getName();
                                                    picture = String.valueOf(fbProfile.getProfilePictureUri(200, 200));
                                                } else {
//                                                    "picture":{"data":{"height":50,"is_silhouette":true,"url":"https:\/\/platform-lookaside.fbsbx.com\/platform\/profilepic\/?asid=128478534747436&height=50&width=50&ext=1547189128&hash=AeRWW0-OXTRksbOO","width":50}}}
                                                    facebookID = json.getString("id");
                                                    name = json.getString("name");
                                                    picture = json.getString("picture");
                                                    Log.e("Picture>>", ">>>> " + picture);
                                                }
                                                if(json.has("email"))
                                                    email = json.getString("email");
                                                else
                                                    email = "";

//                                                Utils.saveFacebookId(facebookID, activity);
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                    ((LoginActivity)activity).receiveFBData(email, facebookID, picture, name);
                                                }

                                            } else {
                                                Toast.makeText(activity, activity.getString(R.string.message_fb_error), Toast.LENGTH_SHORT).show();
                                                return;

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Facebook Login onCancel()");
                        Toast.makeText(activity, "onCancel Called ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(activity, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Facebook Login Error :" + exception.toString());
                    }
                });
    }



}
