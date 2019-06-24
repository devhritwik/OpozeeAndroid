package com.opozee.activities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.opozee.OpozeeActivity;
import com.opozee.R;
import com.opozee.WebRequest;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.login_mvp.model.LoginInteractorImpl;
import com.opozee.login_mvp.presenter.LoginPresenterImpl;
import com.opozee.login_mvp.view.LoginView;
import com.opozee.params.LoginParams;
import com.opozee.pojo.LoginResponse;
import com.opozee.pojo.loginemail.LoginEmail;
import com.opozee.retrofit_api.WebUrl;
import com.opozee.social_login.FBLogin;
import com.opozee.social_login.GoogleLogin;
import com.opozee.social_login.TwitterLogin;
import com.opozee.utils.AppGlobal;
import com.opozee.utils.AppSP;
import com.opozee.utils.AppUtils;
import com.opozee.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.List;

public class LoginActivity extends OpozeeActivity implements GoogleApiClient.OnConnectionFailedListener, LoginView, View.OnClickListener {

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
    private EditText et_email, et_password;
    private Button btn_login;
    private WebRequest webRequest;
    private WebRequest.APIInterface apiInterface;
    LoginEmail loginEmail;
    retrofit2.Call<LoginEmail> loginEmailCall;
    private TextView tv_signUp, tv_forgot;

    private static final int RC_SIGN_IN = 1000;

    private GoogleSignInOptions gso;
    private FragmentActivity activity;
    // private GoogleApiClient mGoogleApiClient;
    private String personName;
    private String personPhotoUrl;
    private String email;
    private String id;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStatusBarHeight();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        setContentView(R.layout.activity_login_new);
        tv_signUp = findViewById(R.id.tv_signUp);
        tv_forgot = findViewById(R.id.tv_forgot);
        tv_signUp.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
        webRequest = WebRequest.getSingleton(this);

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        ButterKnife.bind(this);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);


        //initialize FBLogin
        fbLogin = new FBLogin(this, callbackManager);

        //initialize GoogleLogin
        googleLogin = new GoogleLogin(this);

        //initialize TwitterLogin
        twitterLogin = new TwitterLogin(this);

        getKeyHash();
        //set Presenter to interact with Model to login user
        setPresenter();

        if (AppUtils.isLoggedIn(LoginActivity.this)) {
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

        if (deviceId.equals("")) {
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
    @OnEditorAction(R.id.et_password)
    boolean onEditorAction()
    {
        if (et_email != null && et_password != null) {
            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            if (email.length() > 0) {
                if (isEmailValid(email)) {
                    if (password.length() > 0) {
                        loginUseremail(email, password);
                    } else {
                        et_password.requestFocus();
                        et_password.setError("Password is required");
                    }
                } else {
                    et_email.requestFocus();
                    et_email.setError("Invalid Email");
                }
            } else {
                et_email.requestFocus();
                et_email.setError("Email is required");
            }
        }

        return false;
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
    public void onFBClick(View v) {
        QuestionnaireApplication.getMixpanelApi().track("onFacebookLoginClicked()");
        twitterLogin = null;
        googleLogin = null;
        if (fbLogin == null)
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
    public void onGoogleClick(View v) {
        QuestionnaireApplication.getMixpanelApi().track("onGoogleLoginClicked()");
        twitterLogin = null;
        fbLogin = null;
        if (googleLogin == null)
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
        if (fbLogin != null)
            fbLogin.onActivityResult(requestCode, resultCode, data);
        else if (googleLogin != null)
            googleLogin.onActivityResult(requestCode, resultCode, data);
        else if (twitterLogin != null)
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

    private void loginUseremail(String email, String password) {

        if (Utils.isNetworkAvail(LoginActivity.this)) {
            String params = getjsonstring(email, password);
            hitloginapi(params);
//            mPresenter.loginUserEmail(params);
        } else {
            Utils.showCustomToast(LoginActivity.this, getString(R.string.internet_alert));
        }
    }

    private void hitloginapi(String params) {
        Log.d("Json=","Params="+params);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        loginEmailCall = webRequest.apiInterface.loginemailuser("application/json", params);
        loginEmailCall.enqueue(new Callback<LoginEmail>() {
            @Override
            public void onResponse(Call<LoginEmail> call, Response<LoginEmail> response) {
                if (response != null) {
                    LoginEmail loginEmail = response.body();
                    int code = Integer.parseInt(loginEmail.getSuccess());
                    switch (code) {
                        case 200:
                            et_email.setText("");
                            et_password.setText("");
                            Toast.makeText(LoginActivity.this, loginEmail.getMessage(), Toast.LENGTH_SHORT).show();
                            setMixpanelSuperProperties(loginEmail);
                            Utils.saveemailuserdata(LoginActivity.this, loginEmail);
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                            break;
                        case 400:
//                            et_email.setText("");
//                            et_password.setText("");
                            if (progressDialog != null) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                            Toast.makeText(LoginActivity.this, loginEmail.getMessage(), Toast.LENGTH_LONG).show();
                            break;
                        default:
//                            et_email.setText("");
//                            et_password.setText("");

                            if (progressDialog != null) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                            Toast.makeText(activity, "Error Occured", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    if (progressDialog != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                    Toast.makeText(activity, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginEmail> call, Throwable t) {

//                et_email.setText("");
//                et_password.setText("");

                Log.d("Response=", t.toString());
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setMixpanelSuperProperties(LoginEmail response) {
        JSONObject jsonObject = new JSONObject();
//        LoginResponse.UserData userData = response.getResponse().getUserData();

        try {
            if (response.getData().getId() != null)
                jsonObject.putOpt("UserId", response.getData().getId());
            jsonObject.put("ThirdPartyType", "email");
            jsonObject.put("SocialId", "3");
            jsonObject.put("Name", "");
            if (response.getData().getEmail() != null)
                jsonObject.put("Email", response.getData());
            if (response.getData().getUserName() != null)
                jsonObject.put("Username", response.getData().getUserName());
            if (response.getData().getDeviceToken() != null)
            jsonObject.put("DeviceToken", response.getData().getDeviceToken());
            if (response.getData().getDeviceType() != null)
            jsonObject.put("DeviceType", response.getData().getDeviceToken());

        } catch (JSONException e) {
            // do nothing for now
        }

        QuestionnaireApplication.getMixpanelApi().registerSuperProperties(jsonObject);
        QuestionnaireApplication.getMixpanelApi().identify(response.getData().getUserName());
        QuestionnaireApplication.getMixpanelApi().getPeople().identify(response.getData().getUserName());
        QuestionnaireApplication.getMixpanelApi().alias(response.getData().getUserName(), null);
        QuestionnaireApplication.getMixpanelApi().flush();

    }

    private String getjsonstring(String email, String password) {
        JSONObject obj = new JSONObject();
        String userdata = "";
        try {

            obj.put("email", email);
            obj.put("password", password);
            obj.put("DeviceType","Android");
            obj.put("DeviceToken",deviceId);
//            obj.put("",)


            userdata = obj.toString();
            Log.d("Jason", userdata);

        } catch (JSONException e1) {
            Log.e("ERROR :", "" + e1);
            e1.printStackTrace();

        }
        return userdata;


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
        String defaultURL = "https://opozee.com:81/Content/Upload/ProfileImage/oposee-profile.png";
        String url = (imageURL == null || imageURL.length() == 0 || imageURL.equals("")) ? defaultURL : imageURL;

        params.setImageURL(url);
        params.setDeviceToken(deviceId);
        params.setDeviceType("Android");
        if (email == null)
            params.setEmail("");
        else
            params.setEmail(email);
        params.setThirdPartyType(type);
        if (name.contains(" ")) {
            String[] nameArr = name.split(" ");
            params.setFirstName(nameArr[0]);
            params.setLastName(nameArr[1]);
        } else {
            params.setFirstName(name);
            params.setLastName("");
        }
        if (screenName.equals(""))
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
        if (Utils.mProgressDialog == null)
            Utils.showProgress(LoginActivity.this);
    }

    @Override
    public void hideProgress() {
        if (Utils.mProgressDialog != null)
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (et_email != null && et_password != null) {
                    String email = et_email.getText().toString();
                    String password = et_password.getText().toString();

                    if (email.length() > 0) {
                        if (isEmailValid(email)) {
                            if (password.length() > 0) {
                                loginUseremail(email, password);
                            } else {
                                et_password.requestFocus();
                                et_password.setError("Password is required");
                            }
                        } else {
                            et_email.requestFocus();
                            et_email.setError("Invalid Email");
                        }
                    } else {
                        et_email.requestFocus();
                        et_email.setError("Email is required");
                    }
                }
                break;

            case R.id.tv_signUp:
//                http://test.opozee.com/login
                String url = WebUrl.REGISTRER_LOGIN;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
//               Intent intent=new Intent(LoginActivity.this,RegisterUserActivity.class);
//               startActivity(intent);
                break;

            case R.id.tv_forgot:
                String url1 = WebUrl.FORGOT_PASSWORD;
                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(url1));
                startActivity(i1);
                break;
        }
    }

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }


}

//Client ID:
//825803054523-d6ui7qa4rp0iu2hclufehnf1ajb0vuu7.apps.googleusercontent.com

//Client Secret
//cAUXg5rYqrd6s-iiWd_oxobR