package com.opozeeApp.profiletabs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opozeeApp.DeleteQuestions;
import com.opozeeApp.R;
import com.opozeeApp.adapters.UserPostsAdapter;
import com.opozeeApp.delete_post_mvp.model.DeleteInteractorImpl;
import com.opozeeApp.delete_post_mvp.presenter.DeletePresenterImpl;
import com.opozeeApp.delete_post_mvp.view.DeleteView;
import com.opozeeApp.params.DeletePostParams;
import com.opozeeApp.params.PostedQuestionsParams;
import com.opozeeApp.pojo.DeletePostResponse;
import com.opozeeApp.pojo.PostedQuestionsResponse;
import com.opozeeApp.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozeeApp.posted_questions_mvp.presenter.PostedQuestionsPresenter;
import com.opozeeApp.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
import com.opozeeApp.posted_questions_mvp.view.PostedQuestionsView;
import com.opozeeApp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Questions extends Fragment implements PostedQuestionsView, DeleteView,DeleteQuestions {
    private PostedQuestionsPresenter mPostedQuestionPresenter;
    private int pageIndex = 1;
    private int pageSize = 1000;
    private int mUserId = Integer.valueOf(Utils.idprofileget(getContext()));
    private RecyclerView rv_questions;
    private UserPostsAdapter mAdapter;
    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private boolean isRefreshed = false;
    private boolean isLastPage = false;
    private static DeletePresenterImpl mDeletePresenter;
    public static Context  context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_questions,null);
        rv_questions=view.findViewById(R.id.rv_questions);
        context=getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
      rv_questions.setLayoutManager(linearLayoutManager);
        setPresenters();
        getQuestions();
        return view;

    }



    private void setPresenters() {

        mPostedQuestionPresenter = new PostedQuestionsPresenterImpl();
        mPostedQuestionPresenter.attachView(this, new PostedQuestionsInteractorImpl());

        mDeletePresenter = new DeletePresenterImpl();
        mDeletePresenter.attachView(this, new DeleteInteractorImpl());


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
    public void onSuccess(DeletePostResponse response) {
getQuestions();
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
                if (Utils.mProgressDialog != null)
                    Utils.dismissProgress();

            }else{
                if (Utils.mProgressDialog != null)
                    Utils.dismissProgress();
            }
    }


    public static void deleterequest(int id){



        DeleteQuestions deleteQuestions=new Questions();
        deleteQuestions.deletequestions(id);
    }



    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }

    @Override
    public void deletequestions(int id) {

       AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);
        alert.setMessage("Are you sure you want to delete");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeletePostParams params = new DeletePostParams();
                params.setQuestId(id);
                mDeletePresenter.deletePost(params);


            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();

    }
}
