package com.opozee.social_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.opozee.activities.LoginActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;

public class TwitterLogin {

    private static final String TAG = "TwitterLogin";
    private Activity activity;
    private TwitterAuthClient client;

    public TwitterLogin(Activity activity)
    {
        this.activity = activity;
    }

    public void initTwitter()
    {
        //initialize twitter auth client
        client = new TwitterAuthClient();
    }

    public void twitterLogin()
    {
        //check if user is already authenticated or not
        if (getTwitterSession() == null) {

            //if user is not authenticated start authenticating
            client.authorize(activity, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {

                    // Do something with result, which provides a TwitterSession for making API calls
                    TwitterSession twitterSession = result.data;

                    //call fetch data
                    fetchTwitterData();
                }

                @Override
                public void failure(TwitterException e) {
                    // Do something on failure
                    Toast.makeText(activity, "Failed to authenticate. Please try again1.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is already authenticated direct call fetch twitter email api
            Toast.makeText(activity, "User already authenticated1", Toast.LENGTH_SHORT).show();
            //call fetch data
            fetchTwitterData();
        }
    }

    /**
     * Before using this feature, ensure that “Request email addresses from users” is checked for your Twitter app.
     *
     * @param twitterSession user logged in twitter session
     * @param user
     */
    public void fetchTwitterEmail(final TwitterSession twitterSession, final User user) {

        client.requestEmail(twitterSession, new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void success(Result<String> result) {
                ((LoginActivity)activity).receiveTwitterData(result.data, user.idStr, user.profileImageUrl, user.name, user.screenName);
                //here it will give u only email and rest of other information u can get from TwitterSession
//                ((LoginActivity)activity).receiveTwitterData(result.data, twitterSession.getUserId(), twitterSession.profileImageUrl, user.name);
//                userDetailsLabel.setText("User Id : " + twitterSession.getUserId() + "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(activity, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * call Verify Credentials API when Twitter Auth is successful else it will go in exception block
     * this metod will provide you User model which contain all user information
     */
    public void fetchTwitterData() {
        //check if user is already authenticated or not
        if (getTwitterSession() != null) {

            //fetch twitter image with other information if user is already authenticated

            //initialize twitter api client
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

            //Link for Help : https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/get-account-verify_credentials

            //pass includeEmail : true if you want to fetch Email as well
            Call<User> call = twitterApiClient.getAccountService().verifyCredentials(true, false, true);
            call.enqueue(new Callback<User>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void success(Result<User> result) {
                    User user = result.data;
//                    userDetailsLabel.setText("User Id : " + user.id + "\nUser Name : " + user.name + "\nEmail Id : " + user.email + "\nScreen Name : " + user.screenName);

                    String imageProfileUrl = user.profileImageUrl;
                    Log.e(TAG, "Data : " + imageProfileUrl);
                    Log.e(TAG, "User Id : " + user.id + "\nUser Name : " + user.name + "\nEmail Id : " + user.email + "\nScreen Name : " + user.screenName);
                    //NOTE : User profile provided by twitter is very small in size i.e 48*48
                    //Link : https://developer.twitter.com/en/docs/accounts-and-users/user-profile-images-and-banners
                    //so if you want to get bigger size image then do the following:
                    imageProfileUrl = imageProfileUrl.replace("_normal", "");

                    fetchTwitterEmail(getTwitterSession(), user);

                    ///load image using Picasso
//                    Picasso.with(MainActivity.this)
//                            .load(imageProfileUrl)
//                            .placeholder(R.mipmap.ic_launcher_round)
//                            .into(userProfileImageView);
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(activity, "Failed to authenticate. Please try again2.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //if user is not authenticated first ask user to do authentication
            Toast.makeText(activity, "First to Twitter auth to Verify Credentials gfcbfgd.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * get authenticates user session
     *
     * @return twitter session
     */
    private TwitterSession getTwitterSession() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        //NOTE : if you want to get token and secret too use uncomment the below code
        /*TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;*/

        return session;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Pass the activity result to the twitterAuthClient.
        if (client != null)
            client.onActivityResult(requestCode, resultCode, data);

    }

}
