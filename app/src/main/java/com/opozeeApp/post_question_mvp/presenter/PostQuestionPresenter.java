package com.opozeeApp.post_question_mvp.presenter;

import com.opozeeApp.params.PostQuestionParams;
import com.opozeeApp.post_question_mvp.model.PostQuestionInteractorImpl;
import com.opozeeApp.post_question_mvp.view.PostQuestionView;

public interface PostQuestionPresenter {
    void attachView(PostQuestionView pView, PostQuestionInteractorImpl pInteractor);
    void dettachView();
    void postQuestion(PostQuestionParams params);
}
