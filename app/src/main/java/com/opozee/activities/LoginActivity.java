package com.opozee.activities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.opozee.R;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.login_mvp.model.LoginInteractorImpl;
import com.opozee.login_mvp.presenter.LoginPresenterImpl;
import com.opozee.login_mvp.view.LoginView;
import com.opozee.params.LoginParams;
import com.opozee.pojo.LoginResponse;
import com.opozee.social_login.FBLogin;
import com.opozee.social_login.GoogleLogin;
import com.opozee.social_login.TwitterLogin;
import com.opozee.utils.AppGlobal;
import com.opozee.utils.AppSP;
import com.opozee.utils.AppUtils;
import com.opozee.utils.Utils;

import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LoginView {

    @BindView(R.id.btn_fb)
    Button btn_fb;

    @BindView(R.id.btn_google)
    Button btn_google;

    @BindView(R.id.fb_progressBar)
    ProgressBar fbBar;

    @BindView(R.id.google_progressBar)
    ProgressBar gBar;


    @Override
    protected void onPause() {
        super.onPause();
//        if (googleLogin != null) {
//            googleLogin.stopAutoManage();
//        }
    }

    private CallbackManager callbackManager;
    private FBLogin fbLogin;
    private Object mGoogleApiClient;
    private GoogleLogin googleLogin;
    private TwitterLogin twitterLogin;
    private LoginPresenterImpl mPresenter;
    private AppSP appSP;
    private String TAG = "LoginActivity";
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStatusBarHeight();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_login);


        ButterKnife.bind(this);


        //initialize FBLogin
        fbLogin = new FBLogin(this, callbackManager);

        //initialize GoogleLogin
        googleLogin = new GoogleLogin(this);

        //initialize TwitterLogin
        twitterLogin = new TwitterLogin(this);

        getKeyHash();
        //set Presenter to interact with Model to login user
        setPresenter();

        if(AppUtils.isLoggedIn(LoginActivity.this))
        {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        //get device id for notification
        checkDeviceID();

    }

    private void checkDeviceID() {
        appSP = AppSP.getInstance(LoginActivity.this);

        deviceId = appSP.readString(AppGlobal.REGISTRATION_ID, "");

        if(deviceId.equals("")) {
            getDeviceToken();
        }
    }

    private void getDeviceToken() {


        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        appSP.savePreferences(AppGlobal.REGISTRATION_ID, token);

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
//                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]
    }

    private void setPresenter() {
        mPresenter = new LoginPresenterImpl();
        mPresenter.attachView(this, new LoginInteractorImpl());
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.btn_fb)
    public void onFBClick(View v)
    {
        QuestionnaireApplication.getMixpanelApi().track("onFacebookLoginClicked()");
        twitterLogin = null;
        googleLogin = null;
        if(fbLogin == null)
            //initialize FBLogin
            fbLogin = new FBLogin(this, callbackManager);
//        //start animation and disable all the clicks of the other buttons
//        Utils.circularReveal(btn_fb, fbBar);
//        btn_google.setEnabled(false);
//        btn_twitter.setEnabled(false);

        fbLogin.loginUser();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.btn_google)
    public void onGoogleClick(View v)
    {
        QuestionnaireApplication.getMixpanelApi().track("onGoogleLoginClicked()");
        twitterLogin = null;
        fbLogin = null;
        if(googleLogin == null)
            //initialize GoogleLogin
            googleLogin = new GoogleLogin(this);
//        //start animation and disable all the clicks of the other buttons
//        Utils.circularReveal(btn_google, gBar);
//        btn_fb.setEnabled(false);
//        btn_twitter.setEnabled(false);

        googleLogin.setUpGoogle();
        googleLogin.signIn();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(fbLogin != null)
            fbLogin.onActivityResult(requestCode, resultCode, data);
        else if(googleLogin != null)
            googleLogin.onActivityResult(requestCode, resultCode, data);
        else if(twitterLogin != null)
            twitterLogin.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getKeyHash() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(getString(R.string.app_package), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("KeyHash", "KeyHash  -->>" + something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("KeyHash", "name not found" + e1.toString());

        } catch (Exception e) {
            Log.e("KeyHash", "exception" + e.toString());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void receiveFBData(String email, String facebookID, String imageURL, String name) {
//        Toast.makeText(LoginActivity.this, "Successfully SignedIn as " + name, Toast.LENGTH_LONG).show();
        Log.e("DAta>> ", "Fb IN ACTIVITY >>> " + email);

        //start animation and enable all the clicks of the other buttons
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {

//            }
//        }, 3000);
//        btn_fb.setVisibility(View.VISIBLE);
//        Utils.unReveal(btn_fb, fbBar);
//        btn_google.setEnabled(true);
//        btn_twitter.setEnabled(true);
//        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//        startActivity(i);
        loginUser(getParams(email, facebookID, imageURL, name, AppGlobal.FACEBOOK, ""));

    }

    private void loginUser(LoginParams params) {
        if (Utils.isNetworkAvail(LoginActivity.this)) {
            mPresenter.loginUser(params);
        } else {
            Utils.showCustomToast(LoginActivity.this, getString(R.string.internet_alert));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void receiveGoogleData(String email, String googleID, String imageURL, String name) {
//        Toast.makeText(LoginActivity.this, "Successfully SignedIn as " + name, Toast.LENGTH_LONG).show();
//        btn_google.setVisibility(View.VISIBLE);
        //start animation and enable all the clicks of the other buttons
//        Utils.unReveal(btn_google, gBar);
//        btn_fb.setEnabled(true);
//        btn_twitter.setEnabled(true);
//        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//        startActivity(i);
        Log.e("DAta>> ", " Google IN ACTIVITY >>> " + email);

        loginUser(getParams(email, googleID, imageURL, name, AppGlobal.GOOGLE, ""));
    }

//    http://23.111.138.246:8021/Content/Upload/ProfileImage/oposee-profile.png

    //getLogin params
    private LoginParams getParams(String email, String socialId, String imageURL, String name, String type, String screenName) {
        LoginParams params = new LoginParams();
        params.setThirdPartyId(socialId);
        String defaultURL = "http://23.111.138.246:8021/Content/Upload/ProfileImage/oposee-profile.png";
        String url = (imageURL == null || imageURL.length() == 0 || imageURL.equals(""))? defaultURL : imageURL;

        params.setImageURL(url);
        params.setDeviceToken(deviceId);
        params.setDeviceType("Android");
        if(email == null)
            params.setEmail("");
        else
            params.setEmail(email);
        params.setThirdPartyType(type);
        if(name.contains(" "))
        {
            String[] nameArr = name.split(" ");
            params.setFirstName(nameArr[0]);
            params.setLastName(nameArr[1]);
        }
        else{
            params.setFirstName(name);
            params.setLastName("");
        }
        if(screenName.equals(""))
            params.setUserName(name.replace(" ", "").toLowerCase());
        else
            params.setUserName(screenName);
        return params;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void receiveTwitterData(String email, String twitterID, String imageURL, String name, String screenName) {
//        Toast.makeText(LoginActivity.this, "Successfully SignedIn as " + name, Toast.LENGTH_LONG).show();
        Log.e("DAta>> ", " Twitter IN ACTIVITY >>> " + email);
//        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
//        startActivity(i);
        loginUser(getParams(email, twitterID, imageURL, name, AppGlobal.TWITTER, screenName));
//        btn_twitter.setVisibility(View.VISIBLE);
//        //start animation and enable all the clicks  of the other buttons
//        Utils.unReveal(btn_twitter, tBar);
//        btn_fb.setEnabled(true);
//        btn_google.setEnabled(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void showProgress() {
        if(Utils.mProgressDialog == null)
            Utils.showProgress(LoginActivity.this);
    }

    @Override
    public void hideProgress() {
        if(Utils.mProgressDialog != null)
            Utils.dismissProgress();
    }

    @Override
    public void onSuccess(LoginResponse response) {
        Utils.saveUserDataInSharePreferences(LoginActivity.this, response.getResponse().getUserData());
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(LoginActivity.this, error);
    }
}
