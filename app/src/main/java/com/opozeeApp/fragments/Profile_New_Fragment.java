package com.opozeeApp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opozeeApp.R;
import com.opozeeApp.WebRequest;
import com.opozeeApp.activities.EditProfileActivity;
import com.opozeeApp.activities.RefferealCodeActivity;
import com.opozeeApp.adapters.FollowerUsers;
import com.opozeeApp.adapters.Followingadapter;
import com.opozeeApp.adapters.UserBeliefAdapter;
import com.opozeeApp.adapters.UserPostsAdapter;
import com.opozeeApp.application.QuestionnaireApplication;
import com.opozeeApp.model.FollowesUsers;
import com.opozeeApp.model.FollowingUser;
import com.opozeeApp.models.Belief;
import com.opozeeApp.params.PostedQuestionsParams;
import com.opozeeApp.params.ProfileParams;
import com.opozeeApp.pojo.PostedQuestionsResponse;
import com.opozeeApp.pojo.ProfileResponse;
import com.opozeeApp.pojo.follower_pojo.Following;
import com.opozeeApp.pojo.getmyfollowers.GetFollower;
import com.opozeeApp.pojo.getmyfollowing_pojo.GetFollowing;
import com.opozeeApp.pojo.unfollow_pojo.UnFollow;
import com.opozeeApp.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozeeApp.posted_questions_mvp.presenter.PostedQuestionsPresenter;
import com.opozeeApp.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
import com.opozeeApp.posted_questions_mvp.view.PostedQuestionsView;
import com.opozeeApp.profile_mvp.model.ProfileInteractorImpl;
import com.opozeeApp.profile_mvp.presenter.ProfilePresenterImpl;
import com.opozeeApp.profile_mvp.view.ProfileView;
import com.opozeeApp.profiletabs.Beliefs;
import com.opozeeApp.profiletabs.Followers;
import com.opozeeApp.profiletabs.Followings;
import com.opozeeApp.profiletabs.Questions;

import com.opozeeApp.user_belief_mvp.model.UserBeliefInteractorImpl;
import com.opozeeApp.user_belief_mvp.presenter.UserBeliefPresenter;
import com.opozeeApp.user_belief_mvp.presenter.UserBeliefPresenterImpl;
import com.opozeeApp.user_belief_mvp.view.UserBeliefView;
import com.opozeeApp.utils.AppGlobal;
import com.opozeeApp.utils.AppSP;
import com.opozeeApp.utils.Utils;
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

public class Profile_New_Fragment extends Fragment implements ProfileView, PostedQuestionsView, UserBeliefView {

    private static final String TAG = Profile_New_Fragment.class.getSimpleName();

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

    @BindView(R.id.ll_referlinks)
    LinearLayout ll_referlinks;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;

//    @BindView(R.id.profile_tab_questions)
//    TextView mQuestionTabView;

//    @BindView(R.id.profile_tab_beliefs)
//    TextView mBeliefTabView;
//
//    @BindView(R.id.profile_tab_followers)
//    TextView mFollowersView;
//
//    @BindView(R.id.profile_tab_following)
//    TextView mFollowingView;


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
    public  String follow="";
    public String followings="";



    private enum TabMode {
        Questions, Beliefs, Followers, Following
    }

    Profile_New_Fragment.TabMode mCurrTabMode;

    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private List<Belief> mBeliefList = new ArrayList<>();
    private ArrayList<FollowesUsers> followerslist = new ArrayList<>();
    private ArrayList<FollowingUser> followingUserList = new ArrayList<>();
    public TabLayout tab_layout;
    ViewPager viewPager;
    public static NestedScrollView scrollView;


    public Profile_New_Fragment() {
        // Required empty public constructor
        Log.d(TAG, "CONSTRUCTOR");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_profile, container, false);
        ButterKnife.bind(this, rootView);
        scrollView= rootView.findViewById(R.id.profile_fragment_nested_scrollview);
        webRequest = WebRequest.getSingleton(getActivity());
        btn_followersProfile = rootView.findViewById(R.id.btn_followersProfile);
        tab_layout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.viewPager);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels-dpToPx(120);
        int width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
        lp.height =height;

        btn_followersProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followrequest == false) {
                    if (Utils.isNetworkAvail(getActivity())) {
                        followuser(Utils.getLoggedInUserId(getContext()), "true", String.valueOf(mUserId));
                    } else {
                        Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
                    }

                } else if (followrequest == true) {
                    if (Utils.isNetworkAvail(getActivity())) {
                        unfollowuser(Utils.getLoggedInUserId(getContext()), "false", String.valueOf(mUserId));
                    } else {
                        Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
                    }

                }
            }
        });

        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.scrollTo(0,0);
                    }
                });
            }
        });


        return rootView;


    }



    public void setAdapter() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //set presenter to attach view with interactor to get the data from API
        Log.d("UserId=",Utils.getLoggedInUserId(getContext()));
        if (getArguments().containsKey(PROFILE_FRAGMENG_ARGUEMENT_USER_ID)) {
            mUserId = getArguments().getInt(PROFILE_FRAGMENG_ARGUEMENT_USER_ID);
            AppSP appSP = AppSP.getInstance(getActivity());
            appSP.savePreferences(AppGlobal.IDPROFILEGET, mUserId);
        }else{
            AppSP appSP = AppSP.getInstance(getActivity());
            appSP.savePreferences(AppGlobal.IDPROFILEGET, mUserId);
        }
        if (mUserId != Integer.valueOf(Utils.getLoggedInUserId(getContext()))) {
            iv_edit.setVisibility(GONE);


//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT));
//            params.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen._70sdp));
//            mParentRelativeLayout.setLayoutParams(params);
//            mParentRelativeLayout.requestLayout();
//            scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    scrollView.post(new Runnable() {
//                        public void run() {
//                            scrollView.scrollTo(0,0);
//                        }
//                    });
//                }
//            });

        } else {
            btn_followersProfile.setVisibility(GONE);

        }



//        getQuestions();
//        getBeliefs();
//        getFollowers();
//        getFollowing();


//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


//        params.height=height; //left, top, right, bottom

//        viewPager.setLayoutParams(params);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels-dpToPx(120);
        int width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
        lp.height =height;

        setPresenters();
        setAdapter();
        //call API to populate data
        getProfile();

//        createViewPager(viewPager);
//        tab_layout.setupWithViewPager(viewPager);
//        tab_layout.setOnTabSelectedListener(onTabSelectedListener(viewPager));




    }
    private int dpToPx(int i)
    {
        Resources r = getContext ().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics());
        return (int)px;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);

        }
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
//       for(int i=0;i<getTagsModelist.size();i++){
        adapter.addFragment(new Questions(), "Questions");
        adapter.addFragment(new Beliefs(),"Beliefs");
        adapter.addFragment(new Followers(), "Followers("+follow+")");
        adapter.addFragment(new Followings(), "Followings("+followings+")");

//       }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
//        onTabSelectedListener(viewPager);

    }

    private void getFollowers() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        String data = getjsonstring(String.valueOf(mUserId));

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
                            followerUsersadapter.notifyDataSetChanged();

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

    private void getFollowing() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        String data = getjsonstring(String.valueOf(mUserId));
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
                            if(progressDialog!=null){
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                            followingadapter.notifyDataSetChanged();
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
    @OnClick(R.id.ll_referlinks)
    public void referclicked(){
        Intent intent=new Intent(getActivity(), RefferealCodeActivity.class);
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
        if (Utils.isNetworkAvail(getActivity())) {
            mPostedQuestionPresenter.getQuestions(params);
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }

    }

    private void getBeliefs() {
        if (Utils.isNetworkAvail(getActivity())) {
            mUserBeliefPresenter.getUserBeliefs(mUserId);
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }

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
       if(response.getResponse().getUserProfile().getTotalbeliefs()>0) {
           mBeliefCountView.setText(String.format("Beliefs Posted : %d", response.getResponse().getUserProfile().getTotalbeliefs()));
       }else{
           mBeliefCountView.setText(String.format("Beliefs Posted : %d", 0));
       }
        follow=response.getResponse().getUserProfile().getFollowers();
        followings= response.getResponse().getUserProfile().getFollowings();

        createViewPager(viewPager);
        tab_layout.setupWithViewPager(viewPager);
        tab_layout.setOnTabSelectedListener(onTabSelectedListener(viewPager));
//        mFollowersView.setText("Followers(" + response.getResponse().getUserProfile().getFollowers() + ")");
//        mFollowingView.setText("Followings(" + response.getResponse().getUserProfile().getFollowings() + ")");

    }


    @Override
    public void onSuccess(List<Belief> beliefList) {
//        Log.d(TAG, "onSuccess Belief list");
//        mBeliefList.clear();
//        mBeliefList.addAll(beliefList);
//        mBeliefCountView.setText(String.format("Beliefs Posted : %d", mBeliefList.size()));
//        mBeliefsAdapter.notifyDataSetChanged();
    }


    public void followuser(String userid, String status, String followingId) {
        progressDialog.show();
        String data = followjsonString(userid, status, followingId);
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
                            getProfile();
//                            getFollowing();
//                            getFollowers();
//                            Followings.updatelist();
//                            Followers.updatelist();
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

    public void unfollowuser(String userid, String status, String followingId) {
        progressDialog.show();
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
                            getProfile();
//                            getFollowing();
//                            getFollowers();
//                            Followings.updatelist();
//                            Followers.updatelist();
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


    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager viewPager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                scrollView.post(new Runnable() {
                                    public void run() {
                                        scrollView.scrollTo(0,0);
                                    }
                                });
                            }
                        });
                        break;
                    case 1:
                        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                scrollView.post(new Runnable() {
                                    public void run() {
                                        scrollView.scrollTo(0,0);
                                    }
                                });
                            }
                        });
                        break;
                    case 2:
                        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                scrollView.post(new Runnable() {
                                    public void run() {
                                        scrollView.scrollTo(0,0);
                                    }
                                });
                            }
                        });
                        break;
                    case 3:
                        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                scrollView.post(new Runnable() {
                                    public void run() {
                                        scrollView.scrollTo(0,0);
                                    }
                                });
                            }
                        });
                        break;




                }
//tabName=String.valueOf(tab.getText());
////if(HomeFragment.loaddata==true) {
//    viewPager.setCurrentItem(tab.getPosition());
////    HomeFragment.tabseleted();
//}

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        };
    }

}
