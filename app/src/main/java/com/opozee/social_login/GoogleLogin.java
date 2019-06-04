package com.opozee.social_login;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.tasks.Task;
import com.opozee.activities.LoginActivity;

public class GoogleLogin implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1000;
    private static final String TAG = "GOOGLE LOGIN";
    private GoogleSignInOptions gso;
    private FragmentActivity activity;
   // private GoogleApiClient mGoogleApiClient;
    private String personName;
    private String personPhotoUrl;
    private String email;
    private String id;

    GoogleSignInClient mGoogleSignInClient;
    public GoogleLogin(AppCompatActivity activity) {
        this.activity = activity;
    }

    //setting GoogleAPIClient
    public void setUpGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
//        mGoogleApiClient = new Builder(activity).enableAutoManage(activity, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
    }

    //click action of button
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
//        activity.startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient), RC_SIGN_IN);
    }

    //handling the Activity onActivityResult here
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RC_SIGN_IN && resultCode == activity.RESULT_OK) {
            Log.d("GOOGLE LOGIN", "rest code is ok");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            Log.d("GOOGLE LOGIN", "rest is success");

            GoogleSignInAccount acct = result.getSignInAccount();
            personName = acct.getDisplayName();
            String givenName = acct.getGivenName();
            String familyName = acct.getFamilyName();
            try {
                if (acct.getPhotoUrl() != null)
                    personPhotoUrl = acct.getPhotoUrl().toString();
                else
                    personPhotoUrl = null;
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.e("EXCEPTION>> ", "" + e.toString());
                personPhotoUrl = "";
            }
            email = acct.getEmail();
            id = acct.getId();
            Log.e(TAG, "Name: " + personName + ", email: " + email + ", Image: " + personPhotoUrl + ",  givenName :  " + givenName + ",  id :  " + id + ",  familyName :  " + familyName);
            ((LoginActivity)activity).receiveGoogleData(email, id, personPhotoUrl, personName);
//            mGoogleApiClient.stopAutoManage((FragmentActivity) activity);
//            mGoogleApiClient.disconnect();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//
//    public void stopAutoManage() {
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.stopAutoManage(activity);
//            mGoogleApiClient.disconnect();
//        }
//    }



}
