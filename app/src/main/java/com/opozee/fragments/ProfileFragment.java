package com.opozee.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opozee.R;
import com.opozee.WebRequest;
import com.opozee.activities.EditProfileActivity;
import com.opozee.adapters.FollowerUsers;
import com.opozee.adapters.Followingadapter;
import com.opozee.adapters.UserBeliefAdapter;
import com.opozee.adapters.UserPostsAdapter;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.model.FollowesUsers;
import com.opozee.model.FollowingUser;
import com.opozee.models.Belief;
import com.opozee.params.PostedQuestionsParams;
import com.opozee.params.ProfileParams;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.pojo.ProfileResponse;
import com.opozee.pojo.follower_pojo.Following;
import com.opozee.pojo.getmyfollowers.GetFollower;
import com.opozee.pojo.getmyfollowing_pojo.GetFollowing;
import com.opozee.pojo.unfollow_pojo.UnFollow;
import com.opozee.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozee.posted_questions_mvp.presenter.PostedQuestionsPresenter;
import com.opozee.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
import com.opozee.posted_questions_mvp.view.PostedQuestionsView;
import com.opozee.profile_mvp.model.ProfileInteractorImpl;
import com.opozee.profile_mvp.presenter.ProfilePresenterImpl;
import com.opozee.profile_mvp.view.ProfileView;
import com.opozee.user_belief_mvp.model.UserBeliefInteractorImpl;
import com.opozee.user_belief_mvp.presenter.UserBeliefPresenter;
import com.opozee.user_belief_mvp.presenter.UserBeliefPresenterImpl;
import com.opozee.user_belief_mvp.view.UserBeliefView;
import com.opozee.utils.AppGlobal;
import com.opozee.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;


public class ProfileFragment extends Fragment implements ProfileView, PostedQuestionsView, UserBeliefView {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public static final String PROFILE_FRAGMENG_ARGUEMENT_USER_ID = "ProfileFragmentArgumentUserId";
    private String mUserName = "";

    @BindView(R.id.profile_fragment_parent_relative_layout)
    RelativeLayout mParentRelativeLayout;

    @BindView(R.id.iv_user)
    CircleImageView iv_user;

    @Nullable
    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.profile_fragment_belief_view)
    TextView mBeliefCountView;

    @BindView(R.id.tv_total_question)
    TextView tv_total_question;

    @BindView(R.id.tv_likes)
    TextView tv_likes;

    @BindView(R.id.tv_dislikes)
    TextView tv_dislikes;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.profile_tab_questions)
    TextView mQuestionTabView;

    @BindView(R.id.profile_tab_beliefs)
    TextView mBeliefTabView;

    @BindView(R.id.profile_tab_followers)
    TextView mFollowersView;

    @BindView(R.id.profile_tab_following)
    TextView mFollowingView;


    private ProfilePresenterImpl mProfilePresenter;
    private int pageIndex = 1;
    private int pageSize = 1000;
    private boolean isLastPage = false;
    private PostedQuestionsPresenter mPostedQuestionPresenter;
    private UserBeliefPresenter mUserBeliefPresenter;
    private UserPostsAdapter mAdapter;
    private UserBeliefAdapter mBeliefsAdapter;
    private boolean isRefreshed = false;
    private int mUserId = Integer.valueOf(Utils.getLoggedInUserId(getContext()));

    private static WebRequest webRequest;
    private static WebRequest.APIInterface apiInterface;
    retrofit2.Call<GetFollower> getFollowerCall;
    retrofit2.Call<Following> followingCall;
    retrofit2.Call<UnFollow> unFollowCall;
    retrofit2.Call<GetFollowing> getFollowingCall;

    public static FollowerUsers followerUsersadapter;
    public static Followingadapter followingadapter;
    public static ProgressDialog progressDialog;
    public Button btn_followersProfile;
    public boolean followrequest = false;


    private enum TabMode {
        Questions, Beliefs, Followers, Following
    }

    TabMode mCurrTabMode;

    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private List<Belief> mBeliefList = new ArrayList<>();
    private ArrayList<FollowesUsers> followerslist = new ArrayList<>();
    private ArrayList<FollowingUser> followingUserList = new ArrayList<>();

    public ProfileFragment() {
        // Required empty public constructor
        Log.d(TAG, "CONSTRUCTOR");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        webRequest = WebRequest.getSingleton(getActivity());
        btn_followersProfile = rootView.findViewById(R.id.btn_followersProfile);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        btn_followersProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followrequest == false) {
                    followuser(Utils.getLoggedInUserId(getContext()), "true", String.valueOf(mUserId));
                } else if (followrequest == true) {
                    unfollowuser(Utils.getLoggedInUserId(getContext()), "false", String.valueOf(mUserId));
                }
            }
        });
        return rootView;


    }

    public void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new UserPostsAdapter(getContext(), questionsList);
        mBeliefsAdapter = new UserBeliefAdapter(getContext(), mBeliefList);
        followerUsersadapter = new FollowerUsers(ProfileFragment.this, followerslist,Utils.getLoggedInUserId(getContext()));
        followingadapter = new Followingadapter(ProfileFragment.this, followingUserList,Utils.getLoggedInUserId(getContext()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //set presenter to attach view with interactor to get the data from API
        if (getArguments().containsKey(PROFILE_FRAGMENG_ARGUEMENT_USER_ID))
            mUserId = getArguments().getInt(PROFILE_FRAGMENG_ARGUEMENT_USER_ID);

        if (mUserId != Integer.valueOf(Utils.getLoggedInUserId(getContext()))) {
            iv_edit.setVisibility(GONE);


            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            params.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen._70sdp));
            mParentRelativeLayout.setLayoutParams(params);
            mParentRelativeLayout.requestLayout();

        } else {
            btn_followersProfile.setVisibility(GONE);
        }


        setPresenters();
        setAdapter();
        //call API to populate data
        getProfile();
        getQuestions();
        getBeliefs();
        getFollowers();
        getFollowing();
        setTab(TabMode.Questions);
        mQuestionTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("profile", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Questions clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
            }
        });
        mBeliefTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("profile", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Beliefs clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
                setTab(TabMode.Beliefs);
            }
        });

        mFollowersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("Followers", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Followers clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
                setTab(TabMode.Beliefs);
                setTab(TabMode.Followers);

            }
        });

        mFollowingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("Following", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Following clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
                setTab(TabMode.Beliefs);
                setTab(TabMode.Followers);
                setTab(TabMode.Following);
            }
        });
    }

    private void getFollowers() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        String data = getjsonstring(String.valueOf(mUserId));

        getFollowerCall = webRequest.apiInterface.getallfollowers("application/json", data);
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
                            followerUsersadapter.notifyDataSetChanged();

                            progressDialog.dismiss();
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
            public void onFailure(Call<GetFollower> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void getFollowing() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        String data = getjsonstring(String.valueOf(mUserId));
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
                            progressDialog.dismiss();
                            followingadapter.notifyDataSetChanged();
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

    private void getProfile() {
        if (Utils.isNetworkAvail(getActivity())) {
            mProfilePresenter.profile(getParams());
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }

    }

    private ProfileParams getParams() {
        ProfileParams params = new ProfileParams();
        params.setType(AppGlobal.TYPE_GET_PROFILE);
        // params.setUser_id(Utils.getLoggedInUserId(getActivity()));
        params.setUser_id((Utils.getLoggedInUserId(getContext())));
        params.setViewuserid(Integer.toString(mUserId));
        return params;
    }

    private void setPresenters() {
        mProfilePresenter = new ProfilePresenterImpl();
        mProfilePresenter.attachView(this, new ProfileInteractorImpl());

        mPostedQuestionPresenter = new PostedQuestionsPresenterImpl();
        mPostedQuestionPresenter.attachView(this, new PostedQuestionsInteractorImpl());

        mUserBeliefPresenter = new UserBeliefPresenterImpl();
        mUserBeliefPresenter.attachView(this, new UserBeliefInteractorImpl());
    }

    @OnClick(R.id.iv_edit)
    public void onEditClick() {
        QuestionnaireApplication.getMixpanelApi().track("Edit Profile Picture Clicked");
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog == null)
                Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog != null)
                Utils.dismissProgress();
    }

    @Override
    public void onSuccess(PostedQuestionsResponse response) {
        if (response.getResponse().getAllUserQuestions().getPostQuestionDetail() != null)
            if (response.getResponse().getAllUserQuestions().getPostQuestionDetail().size() > 0) {
                questionsList.clear();
                questionsList.addAll(response.getResponse().getAllUserQuestions().getPostQuestionDetail());

                mAdapter.notifyDataSetChanged();
                isRefreshed = false;
                isLastPage = false;
            }
    }


    private void getQuestions() {
        PostedQuestionsParams params = new PostedQuestionsParams();
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setUser_id(String.valueOf(mUserId));
        mPostedQuestionPresenter.getQuestions(params);
    }

    private void getBeliefs() {
        mUserBeliefPresenter.getUserBeliefs(mUserId);
    }


    @Override
    public void onFirstNameError(String error) {

    }

    @Override
    public void onLastNameError(String error) {
    }

    @Override
    public void onSuccess(ProfileResponse response) {
        if (response.getResponse().getType() == AppGlobal.TYPE_GET_PROFILE) {
            //after getting data update the UI
            updateUI(response);
        } else {
            Utils.showCustomToast(getActivity(), "Profile Updated Successfully");
            //updateUI after the updation of the profile
            getProfile();
        }

    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }

    private void updateUI(ProfileResponse response) {
//        tv_user_name.setText(Utils.capitalize(response.getResponse().getUserProfile().getFirstName() + " " +  response.getResponse().getUserProfile().getLastName()));
        tv_user_name.setText(Utils.capitalize(response.getResponse().getUserProfile().getUserName()));

        if (response.getResponse().getUserProfile().getHasfollowed() != null) {
            if (response.getResponse().getUserProfile().getHasfollowed().equals("true")) {
                btn_followersProfile.setText("Unfollow");
                followrequest = true;
            } else if (response.getResponse().getUserProfile().getHasfollowed().equals("false")) {
                btn_followersProfile.setText("Follow");
                followrequest = false;
            }
        }
        mUserName = response.getResponse().getUserProfile().getUserName();
        String imageURL = response.getResponse().getUserProfile().getImageURL();
        String defaultURL = "https://opozee.com:81/Content/Upload/ProfileImage/oposee-profile.png";

        String url = imageURL == null || imageURL.length() == 0 || imageURL.equals("") ? defaultURL : imageURL;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(iv_user);

        int likes = response.getResponse().getUserProfile().getTotalLikes() != null ? response.getResponse().getUserProfile().getTotalLikes() : 0;
        int dislikes = response.getResponse().getUserProfile().getTotalDislikes() != null ? response.getResponse().getUserProfile().getTotalDislikes() : 0;

        tv_dislikes.setText(dislikes + "");
        tv_likes.setText(likes + "");
        tv_total_question.setText(String.format("Questions Posted : %d", response.getResponse().getUserProfile().getTotalPostedQuestion()));

mFollowersView.setText("Followers("+response.getResponse().getUserProfile().getFollowers()+")");
mFollowingView.setText("Followings("+response.getResponse().getUserProfile().getFollowings()+")");

    }


    @Override
    public void onSuccess(List<Belief> beliefList) {
        Log.d(TAG, "onSuccess Belief list");
        mBeliefList.clear();
        mBeliefList.addAll(beliefList);
        mBeliefCountView.setText(String.format("Beliefs Posted : %d", mBeliefList.size()));
        mBeliefsAdapter.notifyDataSetChanged();
    }

    public void setTab(TabMode tabMode) {
        if (tabMode == TabMode.Questions) {
            if (mCurrTabMode == TabMode.Questions)
                return; // do nothing
            else {
                mQuestionTabView.setBackground(getResources().getDrawable(R.drawable.textview_question_background));
                mBeliefTabView.setBackground(getResources().getDrawable(R.drawable.textview_belief_un));
                mFollowersView.setBackground(getResources().getDrawable(R.drawable.textview_followers_un));
                mFollowingView.setBackground(getResources().getDrawable(R.drawable.textview_following_un));
                recyclerView.setAdapter(mAdapter);

            }

        } else if (tabMode == TabMode.Beliefs) {
            if (mCurrTabMode == TabMode.Beliefs)
                return; // do nothing
            else {

                mBeliefTabView.setBackground(getResources().getDrawable(R.drawable.textview_belief_background));
                mQuestionTabView.setBackground(getResources().getDrawable(R.drawable.textview_question_un));
                mFollowersView.setBackground(getResources().getDrawable(R.drawable.textview_followers_un));
                mFollowingView.setBackground(getResources().getDrawable(R.drawable.textview_following_un));
                recyclerView.setAdapter(mBeliefsAdapter);
                for (Belief b : mBeliefList) {
                    Log.d(TAG, b.getQuestionText());
                }
            }
        } else if (tabMode == TabMode.Followers) {
            if (mCurrTabMode == TabMode.Followers) {
                return;
            } else {
                mFollowersView.setBackground(getResources().getDrawable(R.drawable.textview_followers_background));
                mQuestionTabView.setBackground(getResources().getDrawable(R.drawable.textview_question_un));
                mBeliefTabView.setBackground(getResources().getDrawable(R.drawable.textview_belief_un));
                mFollowingView.setBackground(getResources().getDrawable(R.drawable.textview_following_un));
                recyclerView.setAdapter(followerUsersadapter);
            }
        } else if (tabMode == TabMode.Following) {
            if (mCurrTabMode == TabMode.Following) {
                return;
            } else {
                mFollowingView.setBackground(getResources().getDrawable(R.drawable.textview_following_background));
                mQuestionTabView.setBackground(getResources().getDrawable(R.drawable.textview_question_un));
                mBeliefTabView.setBackground(getResources().getDrawable(R.drawable.textview_belief_un));
                mFollowersView.setBackground(getResources().getDrawable(R.drawable.textview_followers_un));
                recyclerView.setAdapter(followingadapter);
            }
        }
    }

    public void followuser(String userid, String status, String followingId) {
        progressDialog.show();
        String data = followjsonString(userid, status, followingId);
        followingCall = webRequest.apiInterface.followuser("application/json", data);
        followingCall.enqueue(new Callback<Following>() {
            @Override
            public void onResponse(Call<Following> call, Response<Following> response) {
                if (response != null) {
                    Following following = response.body();
                    int code = Integer.parseInt(following.getResponse().getCode());
                    switch (code) {
                        case 0:
                            progressDialog.dismiss();
                            getProfile();
                            getFollowing();
                            getFollowers();
                            break;
                        default:
                            progressDialog.dismiss();
                            break;
                    }
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Following> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void unfollowuser(String userid, String status, String followingId) {
        progressDialog.show();
        String data = followjsonString(userid, status, followingId);
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
                            getProfile();
                            getFollowing();
                            getFollowers();
                            break;
                        default:
                            progressDialog.dismiss();
                            break;
                    }

                }else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UnFollow> call, Throwable t) {
progressDialog.dismiss();
            }
        });

    }

    public void relodfragment(int userid){
        //set presenter to attach view with interactor to get the data from API

            mUserId =userid;

        if (mUserId != Integer.valueOf(Utils.getLoggedInUserId(getContext()))) {
            iv_edit.setVisibility(GONE);
            btn_followersProfile.setVisibility(View.VISIBLE);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            params.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen._70sdp));
            mParentRelativeLayout.setLayoutParams(params);
            mParentRelativeLayout.requestLayout();

        } else {
            btn_followersProfile.setVisibility(GONE);
        }


        setPresenters();
        setAdapter();
        //call API to populate data
        getProfile();
        getQuestions();
        getBeliefs();
        getFollowers();
        getFollowing();
        setTab(TabMode.Questions);
        mQuestionTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("profile", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Questions clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
            }
        });
        mBeliefTabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("profile", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Beliefs clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
                setTab(TabMode.Beliefs);
            }
        });

        mFollowersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("Followers", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Followers clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
                setTab(TabMode.Beliefs);
                setTab(TabMode.Followers);

            }
        });

        mFollowingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt("Following", mUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                QuestionnaireApplication.getMixpanelApi().track("Following clicked Profile Fragment", jsonObject);
                setTab(TabMode.Questions);
                setTab(TabMode.Beliefs);
                setTab(TabMode.Followers);
                setTab(TabMode.Following);
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
