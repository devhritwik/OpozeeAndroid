package com.opozeeApp.profiletabs;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.opozeeApp.LoadTabData;
import com.opozeeApp.R;
import com.opozeeApp.TabClicked;
import com.opozeeApp.WebRequest;
import com.opozeeApp.adapters.FollowerUsers;
import com.opozeeApp.adapters.Followingadapter;
import com.opozeeApp.model.FollowingUser;
import com.opozeeApp.pojo.follower_pojo.Following;
import com.opozeeApp.pojo.getmyfollowers.GetFollower;
import com.opozeeApp.pojo.getmyfollowing_pojo.GetFollowing;
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

public class Followings extends Fragment implements TabClicked, LoadTabData {
    private static int pageIndex = 1;
    private static int pageSize = 1000;
    private int mUserId = Integer.valueOf(Utils.idprofileget(getContext()));
    private static RecyclerView rv_followings;
    public static FollowerUsers followerUsersadapter;
    private static ArrayList<FollowingUser> followingUserList = new ArrayList<>();
    private static boolean isRefreshed = false;
    private static boolean isLastPage = false;
    public static com.opozeeApp.acprogressflower.ACProgressFlower progressDialog;


    private static WebRequest webRequest;
    private static WebRequest.APIInterface apiInterface;
    private static retrofit2.Call<GetFollower> getFollowerCall;
    private static retrofit2.Call<Following> followingCall;
    private static retrofit2.Call<UnFollow> unFollowCall;

    public static retrofit2.Call<GetFollowing> getFollowingCall;
    public static Followingadapter followingadapter;
    public static Activity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_followings, null);
        activity=getActivity();
        rv_followings = view.findViewById(R.id.rv_followings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_followings.setLayoutManager(linearLayoutManager);
        webRequest = WebRequest.getSingleton(activity);
        try {
            progressDialog = new com.opozeeApp.acprogressflower.ACProgressFlower.Builder(activity)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
        }catch (Exception e){

        }
        if (Utils.isNetworkAvail(activity)) {
            getfollowings();
        } else {
            Utils.showCustomToast(activity, getString(R.string.internet_alert));
        }

        return view;

    }

    private void getfollowings() {

        try {
            if (progressDialog != null) {
                progressDialog.show();
            }
        }catch (Exception e){

        }
        String data = getjsonstring(Utils.idprofileget(getContext()));
        getFollowingCall = WebRequest.apiInterface.getallfollowing("application/json", data);
        getFollowingCall.enqueue(new Callback<GetFollowing>() {
            @Override
            public void onResponse(Call<GetFollowing> call, Response<GetFollowing> response) {
                if (response != null) {
                    GetFollowing getFollowing = response.body();
                    int code = Integer.parseInt(getFollowing.getResponse().getCode());
                    switch (code) {
                        case 0:
                            followingUserList.clear();
                            for (int i = 0; i < getFollowing.getResponse().getGetMyFollowing().size(); i++) {
                                FollowingUser followingUser = new FollowingUser();
                                followingUser.setImageurl(getFollowing.getResponse().getGetMyFollowing().get(i).getImageURL());
                                followingUser.setUsername(getFollowing.getResponse().getGetMyFollowing().get(i).getUserName());
                                followingUser.setUserid(getFollowing.getResponse().getGetMyFollowing().get(i).getFollowerId());
                                followingUser.setOwneruserid(getFollowing.getResponse().getGetMyFollowing().get(i).getUserID());
                                followingUser.setIsfollowing(getFollowing.getResponse().getGetMyFollowing().get(i).getIsFollowing());
                                followingUser.setIsfollowback(getFollowing.getResponse().getGetMyFollowing().get(i).getHasFollowBack());
                                followingUserList.add(followingUser);
                            }
                            followingadapter = new Followingadapter(activity, followingUserList, Utils.getLoggedInUserId(getContext()));
                            rv_followings.setAdapter(followingadapter);
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
//                            followingadapter.notifyDataSetChanged();
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
            public void onFailure(Call<GetFollowing> call, Throwable t) {
                if(progressDialog!=null){
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }



    public static void updatelist() {
        TabClicked tabClicked = new Followings();
        tabClicked.updatedata();
    }
    @Override
    public void updatedata() {
        if (Utils.isNetworkAvail(activity)) {
            getfollowings();
        } else {
            Utils.showCustomToast(activity, getString(R.string.internet_alert));
        }
    }

    @Override
    public void LoadData(String owneruserid, String aFalse, String userid) {
        if (Utils.isNetworkAvail(activity)) {
            unfollowuser(owneruserid, aFalse, userid);
        } else {
            Utils.showCustomToast(activity, getString(R.string.internet_alert));
        }

    }

    @Override
    public void followcall(String owneruserid, String aFalse, String userid) {

    }

    public static void unfollowcall(String owneruserid, String aFalse, String userid) {
        LoadTabData loadData = new Followings();
        loadData.LoadData(owneruserid, aFalse, userid);
    }

    public  void unfollowuser(String owneruserid, String aFalse, String userid) {
     try {
         if(!getActivity().isFinishing()){
         if(progressDialog!=null) {
             progressDialog.show();
         }else{
             progressDialog = new com.opozeeApp.acprogressflower.ACProgressFlower.Builder(activity)
                     .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                     .themeColor(Color.WHITE)
                     .fadeColor(Color.DKGRAY).build();
             progressDialog.show();
         }
         }

     }catch (Exception e){

     }
        String data = followjsonString(owneruserid, aFalse, userid);
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
                            Followers.updatelist();
                            if (Utils.isNetworkAvail(activity)) {
                                getfollowings();
                            } else {
                                Utils.showCustomToast(activity, getString(R.string.internet_alert));
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

    private String getjsonstring(String userid) {
        JSONObject obj = new JSONObject();
        String userdata = "";
        try {

            obj.put("UserId", userid);
            obj.put("PageNumber", pageIndex);
            obj.put("PageSize", pageSize);

//            obj.put("",)


            userdata = obj.toString();
            Log.d("Jason", userdata);

        } catch (JSONException e1) {
            Log.e("ERROR :", "" + e1);
            e1.printStackTrace();

        }
        return userdata;


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
            Log.d("Jason", userdata);

        } catch (JSONException e1) {
            Log.e("ERROR :", "" + e1);
            e1.printStackTrace();

        }
        return userdata;

    }


}
