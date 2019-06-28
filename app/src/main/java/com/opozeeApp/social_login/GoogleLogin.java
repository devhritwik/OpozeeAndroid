package com.opozeeApp.social_login;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.opozeeApp.activities.LoginActivity;

public class GoogleLogin implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 1000;
    private static final String TAG = "GOOGLE LOGIN";
    private GoogleSignInOptions gso;
    private FragmentActivity activity;
//    private GoogleApiClient mGoogleApiClient;
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
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
////        mGoogleApiClient = new Builder(activity).enableAutoManage(activity, this)
////                .addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

    }

    //click action of button
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
//        activity.startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient), RC_SIGN_IN);
    }

//    //handling the Activity onActivityResult here
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
////@Override
////public void onActivityResult(int requestCode, int resultCode, Intent data) {
////    super.onActivityResult(requestCode, resultCode, data);
////
////    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
////    if (requestCode == RC_SIGN_IN) {
////        // The Task returned from this call is always completed, no need to attach
////        // a listener.
////        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
////        handleSignInResult(task);
////    }
////}
//
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(activity);
            if (acct != null) {
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((LoginActivity)activity).receiveGoogleData(email, id, personPhotoUrl, personName);
                    mGoogleSignInClient.signOut().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            revokeAccess();
                        }
                    });

//                    mGoogleApiClient.stopAutoManage((FragmentActivity) activity);
//                    mGoogleApiClient.disconnect();
                }

            }
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void handleSignInResult(GoogleSignInResult result) {
//        if (result.isSuccess()) {
//            Log.d("GOOGLE LOGIN", "rest is success");
//
//            GoogleSignInAccount acct = result.getSignInAccount();
//            personName = acct.getDisplayName();
//            String givenName = acct.getGivenName();
//            String familyName = acct.getFamilyName();
//            try {
//                if (acct.getPhotoUrl() != null)
//                    personPhotoUrl = acct.getPhotoUrl().toString();
//                else
//                    personPhotoUrl = null;
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//                Log.e("EXCEPTION>> ", "" + e.toString());
//                personPhotoUrl = "";
//            }
//            email = acct.getEmail();
//            id = acct.getId();
//            Log.e(TAG, "Name: " + personName + ", email: " + email + ", Image: " + personPhotoUrl + ",  givenName :  " + givenName + ",  id :  " + id + ",  familyName :  " + familyName);
//            ((LoginActivity)activity).receiveGoogleData(email, id, personPhotoUrl, personName);
////            mGoogleApiClient.stopAutoManage((FragmentActivity) activity);
////            mGoogleApiClient.disconnect();
//        }
//
//    }

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
