package com.opozeeApp.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;
import com.opozeeApp.R;
import com.opozeeApp.activities.HomeActivity;
import com.opozeeApp.application.QuestionnaireApplication;
import com.opozeeApp.params.PostQuestionParams;
import com.opozeeApp.params.ProfileParams;
import com.opozeeApp.pojo.PostQuestionResponse;
import com.opozeeApp.pojo.ProfileResponse;
import com.opozeeApp.pojo.TagUsersResponse;
import com.opozeeApp.post_question_mvp.model.PostQuestionInteractorImpl;
import com.opozeeApp.post_question_mvp.presenter.PostQuestionPresenterImpl;
import com.opozeeApp.post_question_mvp.view.PostQuestionView;
import com.opozeeApp.profile_mvp.model.ProfileInteractorImpl;
import com.opozeeApp.profile_mvp.presenter.ProfilePresenterImpl;
import com.opozeeApp.profile_mvp.view.ProfileView;
import com.opozeeApp.utils.AppGlobal;
import com.opozeeApp.utils.Utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostQuestionFragment extends Fragment implements PostQuestionView, ProfileView  {

    @BindView(R.id.btn_post)
    Button btn_post;
    @BindView(R.id.iv_add_users)
    ImageView iv_add_users;
    @BindView(R.id.edit_hash_tags)
    EditText edit_hash_tags;
    @BindView(R.id.edit_question)
    EditText edit_questions;

    private PostQuestionPresenterImpl mPresenter;
    ProfilePresenterImpl mProfilePresenter;
    private String tagIds = "";
    @BindView(R.id.chips_input)
    ChipsInput chips_input;
    List<TagUsersResponse.GetAllUser> usersList = new ArrayList<>();
public static Activity activity;

    public PostQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post_question, container, false);
activity=getActivity();
        ButterKnife.bind(PostQuestionFragment.this, rootView);
        //set Presenter to attach the view with presenter
        setPresenter();
        //set presenter to attach view with interactor to get the data from API
        setProfile();

        //call API to populate data
        getProfile();


        return rootView;
    }

    private void getProfile() {
        if (Utils.isNetworkAvail(activity)) {
            mProfilePresenter.profile(getProfileParams());
        } else {
            Utils.showCustomToast(activity, getString(R.string.internet_alert));
        }

    }

    private ProfileParams getProfileParams() {
        ProfileParams params = new ProfileParams();
        params.setType(AppGlobal.TYPE_GET_PROFILE);
        params.setUser_id(Utils.getLoggedInUserId(activity));
        params.setViewuserid(Utils.getLoggedInUserId(activity));
        return params;
    }

    private void setProfile() {
        mProfilePresenter = new ProfilePresenterImpl();
        mProfilePresenter.attachView(this, new ProfileInteractorImpl());
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
            Utils.showCustomToast(activity,"Profile Updated Successfully");
            //updateUI after the updation of the profile
            getProfile();
        }

    }


    private void updateUI(ProfileResponse response) {

        //Do nothing for now
    }


    private void setPresenter(){
        mPresenter = new PostQuestionPresenterImpl();
        mPresenter.attachView(this, new PostQuestionInteractorImpl());
    }


    @OnClick(R.id.btn_post)
    void onPostClick()
    {
    if (Utils.isNetworkAvail(activity)) {
        mPresenter.postQuestion(getParams());
        trackQuestionPosting();
    } else {
        Utils.showCustomToast(activity, getString(R.string.internet_alert));
    }

    }



    private void sendEmail() {
        Intent i = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        if (i != null) {


            String to = "info@opozee.com";
            String subject = "Regarding Insufficient token balance";
            String body = "I have no tokens balance in my account. Please refill my account.";
            String mailTo = "mailto:" + to +
                    "?&subject=" + Uri.encode(subject) +
                    "&body=" + Uri.encode(body);
            Intent emailIntent = new Intent(Intent.ACTION_VIEW);
            emailIntent.setData(Uri.parse(mailTo));
            startActivity(emailIntent);
        } else {
            Toast.makeText(activity, "Gmail App Is Not Installed In Your Phone", Toast.LENGTH_SHORT).show();
        }
    }


    private PostQuestionParams getParams() {
        PostQuestionParams params = new PostQuestionParams();
//        id = 0 for posting & id != 0 for editing
        params.setId("0");
        params.setHashTags(edit_hash_tags.getText().toString().trim().replace("#","").replace(" ",","));
        params.setQuestion(StringEscapeUtils.escapeJava(edit_questions.getText().toString().trim().replace("\\n", "<br/>")));
        params.setUserId(Utils.getLoggedInUserId(activity));
        params.setTaggedUsers(getTagIds());

        return params;
    }

    private String getTagIds() {
        ArrayList<Integer> idsList = new ArrayList<>();
        for (ChipInterface chipInterface: chips_input.getSelectedChipList()) {
            idsList.add(Integer.parseInt(String.valueOf(chipInterface.getId())));
        }

        return TextUtils.join(",", idsList);
    }

    @Override
    public void showProgress() {
        if(Utils.mProgressDialog == null)
            Utils.showProgress(activity);
    }

    @Override
    public void hideProgress() {
        if(Utils.mProgressDialog != null)
            Utils.dismissProgress();
    }

    @Override
    public void onSuccess(PostQuestionResponse response) {
        Utils.showCustomToast(activity, "Question posted successfully");
        if(getActivity() instanceof HomeActivity)
        {
//            ((HomeActivity)getActivity()).getLastFragment(AppGlobal.HOMEFRAG);
            ((HomeActivity)activity).refresh();
        }
        else
        {
            Intent i = new Intent(getActivity(), HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            getActivity().finish();
        }

    }



    @Override
    public void onQuestionError(String error) {
        edit_questions.requestFocus();
        edit_questions.setError(error);
    }

    @Override
    public void onHashTagError(String error) {
        edit_hash_tags.requestFocus();
        edit_hash_tags.setError(error);
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(activity,error);
    }

    private void trackQuestionPosting(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Question", edit_questions.getText().toString());
            jsonObject.put("HashTags", edit_hash_tags.getText().toString());
            jsonObject.put("HashTags", edit_hash_tags.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        QuestionnaireApplication.getMixpanelApi().track("Post question", jsonObject) ;
    }

}
