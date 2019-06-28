package com.opozeeApp.post_question_mvp.presenter;

import com.opozeeApp.params.PostQuestionParams;
import com.opozeeApp.pojo.PostQuestionResponse;
import com.opozeeApp.post_question_mvp.model.PostQuestionInteractor;
import com.opozeeApp.post_question_mvp.model.PostQuestionInteractorImpl;
import com.opozeeApp.post_question_mvp.view.PostQuestionView;

public class PostQuestionPresenterImpl implements PostQuestionPresenter, PostQuestionInteractor.OnPostFinishedListener {
    private PostQuestionView pView;
    private PostQuestionInteractorImpl pInteractor;

    @Override
    public void onSuccess(PostQuestionResponse response) {
        pView.hideProgress();
        pView.onSuccess(response);
    }

    @Override
    public void onQuestionError(String error) {
        pView.hideProgress();
        pView.onQuestionError(error);
    }

    @Override
    public void onHashTagError(String error) {
        pView.hideProgress();
        pView.onHashTagError(error);
    }

    @Override
    public void onFailure(String error) {
        pView.hideProgress();
        pView.onFailure(error);
    }

    @Override
    public void attachView(PostQuestionView pView, PostQuestionInteractorImpl pInteractor) {

        this.pView = pView;
        this.pInteractor = pInteractor;
    }

    @Override
    public void dettachView() {
        pView = null;
        pInteractor = null;
    }

    @Override
    public void postQuestion(PostQuestionParams params) {
        pView.hideProgress();
        pInteractor.postQuestion(params, this);
    }
}
