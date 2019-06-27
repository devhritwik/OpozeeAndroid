package com.opozee.profiletabs;

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

import com.opozee.R;
import com.opozee.adapters.UserPostsAdapter;
import com.opozee.params.PostedQuestionsParams;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozee.posted_questions_mvp.presenter.PostedQuestionsPresenter;
import com.opozee.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
import com.opozee.posted_questions_mvp.view.PostedQuestionsView;
import com.opozee.profile_mvp.model.ProfileInteractorImpl;
import com.opozee.profile_mvp.presenter.ProfilePresenterImpl;
import com.opozee.user_belief_mvp.model.UserBeliefInteractorImpl;
import com.opozee.user_belief_mvp.presenter.UserBeliefPresenterImpl;
import com.opozee.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.opozee.fragments.Profile_New_Fragment.scrollView;

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
        mPostedQuestionPresenter.getQuestions(params);
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
