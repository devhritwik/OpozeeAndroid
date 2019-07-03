package com.opozeeApp.profiletabs;

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

import com.opozeeApp.R;
import com.opozeeApp.adapters.UserPostsAdapter;
import com.opozeeApp.params.PostedQuestionsParams;
import com.opozeeApp.pojo.PostedQuestionsResponse;
import com.opozeeApp.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozeeApp.posted_questions_mvp.presenter.PostedQuestionsPresenter;
import com.opozeeApp.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
import com.opozeeApp.posted_questions_mvp.view.PostedQuestionsView;
import com.opozeeApp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Questions extends Fragment implements PostedQuestionsView {
    private PostedQuestionsPresenter mPostedQuestionPresenter;
    private int pageIndex = 1;
    private int pageSize = 1000;
    private int mUserId = Integer.valueOf(Utils.idprofileget(getContext()));
    private RecyclerView rv_questions;
    private UserPostsAdapter mAdapter;
    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private boolean isRefreshed = false;
    private boolean isLastPage = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_questions,null);
        rv_questions=view.findViewById(R.id.rv_questions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
      rv_questions.setLayoutManager(linearLayoutManager);
        setPresenters();
        getQuestions();
        return view;

    }



    private void setPresenters() {

        mPostedQuestionPresenter = new PostedQuestionsPresenterImpl();
        mPostedQuestionPresenter.attachView(this, new PostedQuestionsInteractorImpl());


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
//                mAdapter.notifyDataSetChanged();
                Log.d("Size=data",""+questionsList.size());
                mAdapter = new UserPostsAdapter(getContext(), questionsList);
                rv_questions.setAdapter(mAdapter);
                isRefreshed = false;
                isLastPage = false;


            }
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }
}
