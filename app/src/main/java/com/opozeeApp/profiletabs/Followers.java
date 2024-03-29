package com.opozeeApp.profiletabs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opozeeApp.LoadTabData;
import com.opozeeApp.R;
import com.opozeeApp.TabClicked;
import com.opozeeApp.WebRequest;
import com.opozeeApp.adapters.FollowerUsers;
import com.opozeeApp.model.FollowesUsers;
import com.opozeeApp.pojo.follower_pojo.Following;
import com.opozeeApp.pojo.getmyfollowers.GetFollower;
import com.opozeeApp.pojo.unfollow_pojo.UnFollow;
import com.opozeeApp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Followers extends Fragment implements LoadTabData, TabClicked {

    private static int pageIndex = 1;
    private static int pageSize = 1000;
    private int mUserId = Integer.valueOf(Utils.getLoggedInUserId(getContext()));
    private static RecyclerView rv_followers;
    public static FollowerUsers followerUsersadapter;
    private static ArrayList<FollowesUsers> followerslist = new ArrayList<>();
    private static boolean isRefreshed = false;
    private static boolean isLastPage = false;
    public static com.opozeeApp.acprogressflower.ACProgressFlower progressDialog;
    public Button btn_followersProfile;

    private static WebRequest webRequest;
    private static WebRequest.APIInterface apiInterface;
    private static retrofit2.Call<GetFollower> getFollowerCall;
    private static retrofit2.Call<Following> followingCall;
    private static retrofit2.Call<UnFollow> unFollowCall;
    public static Activity context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_followers, null);
        context=getActivity();
        rv_followers = view.findViewById(R.id.rv_followers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_followers.setLayoutManager(linearLayoutManager);
        webRequest = WebRequest.getSingleton(context);
       try {
           progressDialog = new com.opozeeApp.acprogressflower.ACProgressFlower.Builder(context)
                   .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                   .themeColor(Color.WHITE)
                   .fadeColor(Color.DKGRAY).build();
       }catch (Exception e){

       }
        if (Utils.isNetworkAvail(context)) {
            getfollowers();
        } else {
            Utils.showCustomToast(context, getString(R.string.internet_alert));
        }

        return view;
    }

    private void getfollowers() {
        try {
            if(!getActivity().isFinishing()) {
                if (progressDialog != null) {
                    progressDialog.show();
                }
            }
        }catch (Exception e){

        }
        String data = getjsonstring(Utils.idprofileget(getContext()));

        getFollowerCall = WebRequest.apiInterface.getallfollowers("application/json", data);
        getFollowerCall.enqueue(new Callback<GetFollower>() {
            @Override
            public void onResponse(Call<GetFollower> call, Response<GetFollower> response) {
                if (response != null) {
                    GetFollower getFollower = response.body();
                    int code = Integer.parseInt(getFollower.getResponse().getCode());
                    switch (code) {
                        case 0:
                            followerslist.clear();
                            for (int i = 0; i < getFollower.getResponse().getGetMyFollowers().size(); i++) {
                                FollowesUsers followesUsers = new FollowesUsers();
                                followesUsers.setImageurl(getFollower.getResponse().getGetMyFollowers().get(i).getImageURL());
                                followesUsers.setUsername(getFollower.getResponse().getGetMyFollowers().get(i).getUserName());
                                followesUsers.setUserid(getFollower.getResponse().getGetMyFollowers().get(i).getFollowerId());
                                followesUsers.setOwneruserid(getFollower.getResponse().getGetMyFollowers().get(i).getUserID());
                                followesUsers.setIsfollowing(getFollower.getResponse().getGetMyFollowers().get(i).getIsFollowing());
                                followesUsers.setIsfollowback(getFollower.getResponse().getGetMyFollowers().get(i).getHasFollowBack());
                                followerslist.add(followesUsers);
                            }
//                            followerUsersadapter.notifyDataSetChanged();
                            followerUsersadapter = new FollowerUsers(context, followerslist, Utils.getLoggedInUserId(getContext()));
                            rv_followers.setAdapter(followerUsersadapter);
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                            break;
                        case 1:
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                            break;
                        default:
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }

                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFollower> call, Throwable t) {
                if(progressDialog!=null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }


    public static void updatelist() {
        TabClicked tabClicked = new Followers();
        tabClicked.updatedata();
    }

    private String getjsonstring(String userid) {
        JSONObject obj = new JSONObject();
        String userdata = "";
        try {

            obj.put("UserId", userid);
            obj.put("PageNumber", pageIndex);
            obj.put("PageSize", pageSize);

//            obj.put("",)


            userdata = obj.toString();
//            Log.d("Jason", userdata);

        } catch (JSONException e1) {
            Log.e("ERROR :", "" + e1);
            e1.printStackTrace();

        }
        return userdata;


    }

    public void unfollowuser(String userid, String status, String followingId) {
      try {
          progressDialog.show();
      }catch (Exception e){

      }
        String data = followjsonString(userid, status, followingId);
        unFollowCall = WebRequest.apiInterface.unfollowuser("application/json", data);
        unFollowCall.enqueue(new Callback<UnFollow>() {
            @Override
            public void onResponse(Call<UnFollow> call, Response<UnFollow> response) {
                if (response != null) {
                    UnFollow unFollow = response.body();
                    int code = Integer.parseInt(unFollow.getResponse().getCode());
                    switch (code) {
                        case 0:
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
//                            getProfile();
//                            getFollowing();
//                            getFollowers();
                            if (Utils.isNetworkAvail(context)) {
                                getfollowers();
                            } else {
                                Utils.showCustomToast(context, getString(R.string.internet_alert));
                            }
                            Followings.updatelist();
                            break;
                        default:
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                            break;
                    }

                } else {
                    if(progressDialog!=null){
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UnFollow> call, Throwable t) {
                if(progressDialog!=null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });

    }

    public static void unfollowcall(String owneruserid, String aFalse, String userid) {
        LoadTabData loadData = new Followers();
        loadData.LoadData(owneruserid, aFalse, userid);
    }

    @Override
    public void LoadData(String owneruserid, String aFalse, String userid) {
        if (Utils.isNetworkAvail(context)) {
            unfollowuser(owneruserid, aFalse, userid);
        } else {
            Utils.showCustomToast(context, getString(R.string.internet_alert));
        }

    }

    @Override
    public void followcall(String owneruserid, String aFalse, String userid) {
        if (Utils.isNetworkAvail(context)) {
            followuser(owneruserid, aFalse, userid);
        } else {
            Utils.showCustomToast(context, getString(R.string.internet_alert));
        }

    }

    @Override
    public void updatedata() {
        if (Utils.isNetworkAvail(context)) {
            getfollowers();
        } else {
            Utils.showCustomToast(context, getString(R.string.internet_alert));
        }
    }

    public static void followfacecall(String owneruserid, String aTrue, String userid) {
        LoadTabData loadData = new Followers();
        loadData.followcall(owneruserid, aTrue, userid);
    }

    public void followuser(String owneruserid, String aTrue, String userid) {
       try {
           if (progressDialog != null) {
               progressDialog.show();
           }
       }catch (Exception e){

       }
        String data = followjsonString(owneruserid, aTrue, userid);
        followingCall = WebRequest.apiInterface.followuser("application/json", data);
        followingCall.enqueue(new Callback<Following>() {
            @Override
            public void onResponse(Call<Following> call, Response<Following> response) {
                if (response != null) {
                    Following following = response.body();
                    int code = Integer.parseInt(following.getResponse().getCode());
                    switch (code) {
                        case 0:
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
//                            getProfile();
//                            getFollowing();
                            if (Utils.isNetworkAvail(context)) {
                                getfollowers();
                            } else {
                                Utils.showCustomToast(context, getString(R.string.internet_alert));
                            }
                            Followings.updatelist();
                            break;
                        default:
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                            break;
                    }
                    if(progressDialog!=null){
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }

                } else {
                    if(progressDialog!=null){
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<Following> call, Throwable t) {
                if(progressDialog!=null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }

    private String followjsonString(String userid, String data, String following) {
        JSONObject obj = new JSONObject();
        String userdata = "";
        try {

            obj.put("UserId", userid);
            obj.put("IsFollowing", data);
            obj.put("Following", following);

//            obj.put("",)


            userdata = obj.toString();
//            Log.d("Jason", userdata);

        } catch (JSONException e1) {
//            Log.e("ERROR :", "" + e1);
            e1.printStackTrace();

        }
        return userdata;

    }


}
