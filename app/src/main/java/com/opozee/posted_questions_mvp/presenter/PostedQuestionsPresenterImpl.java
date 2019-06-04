package com.opozee.posted_questions_mvp.presenter;

import com.opozee.params.PostedQuestionsParams;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.posted_questions_mvp.model.PostedQuestionsInteractor;
import com.opozee.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozee.posted_questions_mvp.view.PostedQuestionsView;

public class PostedQuestionsPresenterImpl implements PostedQuestionsPresenter, PostedQuestionsInteractor.OnPostedQuestionsFinishListener {
    private PostedQuestionsView pView;
    private PostedQuestionsInteractorImpl pInteractor;

    @Override
    public void onSuccess(PostedQuestionsResponse response) {
        pView.hideProgress();
        pView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        pView.hideProgress();
        pView.onFailure(error);
    }

    @Override
    public void attachView(PostedQuestionsView pView, PostedQuestionsInteractorImpl pInteractor) {

        this.pView = pView;
        this.pInteractor = pInteractor;
    }

    @Override
    public void dettachView() {
        pView = null;
        pInteractor = null;
    }

    @Override
    public void getQuestions(PostedQuestionsParams params) {
        pView.showProgress();
        pInteractor.getQuestions(params, this);
    }
}
