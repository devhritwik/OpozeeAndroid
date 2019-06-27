package com.opozee.profiletabs;

import android.app.ProgressDialog;
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
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.opozee.LoadTabData;
import com.opozee.R;
import com.opozee.TabClicked;
import com.opozee.WebRequest;
import com.opozee.adapters.FollowerUsers;
import com.opozee.adapters.Followingadapter;
import com.opozee.model.FollowesUsers;
import com.opozee.model.FollowingUser;
import com.opozee.pojo.follower_pojo.Following;
import com.opozee.pojo.getmyfollowers.GetFollower;
import com.opozee.pojo.getmyfollowing_pojo.GetFollowing;
import com.opozee.pojo.unfollow_pojo.UnFollow;
import com.opozee.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.opozee.fragments.Profile_New_Fragment.scrollView;

public class Followings extends Fragment implements TabClicked, LoadTabData {
    private static int pageIndex = 1;
    private static int pageSize = 1000;
    private int mUserId = Integer.valueOf(Utils.idprofileget(getContext()));
    private static RecyclerView rv_followings;
    public static FollowerUsers followerUsersadapter;
    private static ArrayList<FollowingUser> followingUserList = new ArrayList<>();
    private static boolean isRefreshed = false;
    private static boolean isLastPage = false;
    public static ProgressDialog progressDialog;


    private static WebRequest webRequest;
    private static WebRequest.APIInterface apiInterface;
    private static retrofit2.Call<GetFollower> getFollowerCall;
    private static retrofit2.Call<Following> followingCall;
    private static retrofit2.Call<UnFollow> unFollowCall;

    retrofit2.Call<GetFollowing> getFollowingCall;
    public static Followingadapter followingadapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_followings, null);
        rv_followings = view.findViewById(R.id.rv_followings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_followings.setLayoutManager(linearLayoutManager);
        webRequest = WebRequest.getSingleton(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        getfollowings();
        return view;

    }

    private void getfollowings() {

        if (progressDialog != null) {
            progressDialog.show();
        }
        String data = getjsonstring(String.valueOf(Utils.idprofileget(getContext())));
        getFollowingCall = webRequest.apiInterface.getallfollowing("application/json", data);
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
                            followingadapter = new Followingadapter(getActivity(), followingUserList, Utils.getLoggedInUserId(getContext()));
                            rv_followings.setAdapter(followingadapter);
                            progressDialog.dismiss();
//                            followingadapter.notifyDataSetChanged();
                            break;
                        case 1:
                            progressDialog.dismiss();
                            break;
                        default:
                            progressDialog.dismiss();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<GetFollowing> call, Throwable t) {

            }
        });
    }



    public static void updatelist() {
        TabClicked tabClicked = new Followings();
        tabClicked.updatedata();
    }
    @Override
    public void updatedata() {
getfollowings();
    }

    @Override
    public void LoadData(String owneruserid, String aFalse, String userid) {
        unfollowuser(owneruserid, aFalse, userid);
    }

    @Override
    public void followcall(String owneruserid, String aFalse, String userid) {

    }

    public static void unfollowcall(String owneruserid, String aFalse, String userid) {
        LoadTabData loadData = new Followings();
        loadData.LoadData(owneruserid, aFalse, userid);
    }

    public  void unfollowuser(String owneruserid, String aFalse, String userid) {
        progressDialog.show();
        String data = followjsonString(owneruserid, aFalse, userid);
        unFollowCall = webRequest.apiInterface.unfollowuser("application/json", data);
        unFollowCall.enqueue(new Callback<UnFollow>() {
            @Override
            public void onResponse(Call<UnFollow> call, Response<UnFollow> response) {
                if (response != null) {
                    UnFollow unFollow = response.body();
                    int code = Integer.parseInt(unFollow.getResponse().getCode());
                    switch (code) {
                        case 0:
                            progressDialog.dismiss();
                            Followers.updatelist();
                            getfollowings();
                            break;
                        default:
                            progressDialog.dismiss();
                            break;
                    }

                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UnFollow> call, Throwable t) {
                progressDialog.dismiss();
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
