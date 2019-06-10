package com.opozee.fragments;


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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opozee.R;
import com.opozee.activities.EditProfileActivity;
import com.opozee.adapters.UserBeliefAdapter;
import com.opozee.adapters.UserPostsAdapter;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.models.Belief;
import com.opozee.params.PostedQuestionsParams;
import com.opozee.params.ProfileParams;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.pojo.ProfileResponse;
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

import static android.view.View.GONE;


public class ProfileFragment extends Fragment implements ProfileView, PostedQuestionsView, UserBeliefView {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    public static final String PROFILE_FRAGMENG_ARGUEMENT_USER_ID = "ProfileFragmentArgumentUserId";
    private String mUserName = "";

    @BindView(R.id.profile_fragment_parent_relative_layout)
    RelativeLayout mParentRelativeLayout ;

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


    private enum TabMode{
        Questions, Beliefs
    }

    TabMode mCurrTabMode;

    private List< PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private List<Belief> mBeliefList = new ArrayList<>();
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
        return rootView;
    }

    public void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new UserPostsAdapter(getContext(), questionsList);
        mBeliefsAdapter = new UserBeliefAdapter(getContext(), mBeliefList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        //set presenter to attach view with interactor to get the data from API
        if (getArguments().containsKey(PROFILE_FRAGMENG_ARGUEMENT_USER_ID))
            mUserId = getArguments().getInt(PROFILE_FRAGMENG_ARGUEMENT_USER_ID);

        if (mUserId != Integer.valueOf(Utils.getLoggedInUserId(getContext()))){
            iv_edit.setVisibility(GONE);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            params.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen._70sdp));
            mParentRelativeLayout.setLayoutParams(params);
            mParentRelativeLayout.requestLayout();
            
        }
        setPresenters();
        setAdapter();
        //call API to populate data
        getProfile();
        getQuestions();
        getBeliefs();
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
                QuestionnaireApplication.getMixpanelApi().track("Questions clicked Profile Fragment" , jsonObject);
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
                QuestionnaireApplication.getMixpanelApi().track("Beliefs clicked Profile Fragment" , jsonObject);
                setTab(TabMode.Questions);
                setTab(TabMode.Beliefs);
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
        params.setUser_id(Integer.toString(mUserId));
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
    public void onEditClick()
    {
        QuestionnaireApplication.getMixpanelApi().track("Edit Profile Picture Clicked");
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        if(!isRefreshed && !isLastPage)
            if(Utils.mProgressDialog == null)
                Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if(!isRefreshed && !isLastPage)
            if(Utils.mProgressDialog != null)
                Utils.dismissProgress();
    }

    @Override
    public void onSuccess(PostedQuestionsResponse response) {
        if(response.getResponse().getAllUserQuestions().getPostQuestionDetail() != null)
            if(response.getResponse().getAllUserQuestions().getPostQuestionDetail().size() > 0) {
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
        if(response.getResponse().getType() == AppGlobal.TYPE_GET_PROFILE) {
            //after getting data update the UI
            updateUI(response);
        }
        else
        {
            Utils.showCustomToast(getActivity(),"Profile Updated Successfully");
            //updateUI after the updation of the profile
            getProfile();
        }

    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(),error);
    }

    private void updateUI(ProfileResponse response) {
//        tv_user_name.setText(Utils.capitalize(response.getResponse().getUserProfile().getFirstName() + " " +  response.getResponse().getUserProfile().getLastName()));
        tv_user_name.setText(Utils.capitalize(response.getResponse().getUserProfile().getUserName()));


        mUserName = response.getResponse().getUserProfile().getUserName();
        String imageURL = response.getResponse().getUserProfile().getImageURL();
        String defaultURL = "http://23.111.138.246:8021/Content/Upload/ProfileImage/oposee-profile.png";

        String url = imageURL == null || imageURL.length() == 0 || imageURL.equals("")? defaultURL : imageURL;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(iv_user);

        int likes = response.getResponse().getUserProfile().getTotalLikes() != null ? response.getResponse().getUserProfile().getTotalLikes() : 0;
        int dislikes = response.getResponse().getUserProfile().getTotalDislikes() != null ? response.getResponse().getUserProfile().getTotalDislikes() : 0;

        tv_dislikes.setText( dislikes+ "");
        tv_likes.setText( likes + "");
        tv_total_question.setText(String.format("Questions Posted : %d", response.getResponse().getUserProfile().getTotalPostedQuestion()));


    }


    @Override
    public void onSuccess(List<Belief> beliefList) {
        Log.d(TAG, "onSuccess Belief list");
        mBeliefList.clear();
        mBeliefList.addAll(beliefList);
        mBeliefCountView.setText(String.format("Beliefs Posted : %d", mBeliefList.size()));
        mBeliefsAdapter.notifyDataSetChanged();
    }

    public void setTab(TabMode tabMode){
        if (tabMode == TabMode.Questions){
            if (mCurrTabMode == TabMode.Questions)
                    return; // do nothing
            else {
                mQuestionTabView.setBackground(getResources().getDrawable(R.drawable.user_profile_tabs_selected_bg));
                mBeliefTabView.setBackground(getResources().getDrawable(R.drawable.user_profile_tabs_unselected_bg));
                recyclerView.setAdapter(mAdapter);

            }

        } else {
            if (mCurrTabMode == TabMode.Beliefs)
                return; // do nothing
            else {

                mBeliefTabView.setBackground(getResources().getDrawable(R.drawable.user_profile_tabs_selected_bg));
                mQuestionTabView.setBackground(getResources().getDrawable(R.drawable.user_profile_tabs_unselected_bg));
                recyclerView.setAdapter(mBeliefsAdapter);
                for (Belief b : mBeliefList){
                    Log.d(TAG, b.getQuestionText());
                }
            }
        }
    }


}
