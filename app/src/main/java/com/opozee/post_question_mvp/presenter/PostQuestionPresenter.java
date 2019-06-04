package com.opozee.post_question_mvp.presenter;

import com.opozee.params.PostQuestionParams;
import com.opozee.post_question_mvp.model.PostQuestionInteractorImpl;
import com.opozee.post_question_mvp.view.PostQuestionView;

public interface PostQuestionPresenter {
    void attachView(PostQuestionView pView, PostQuestionInteractorImpl pInteractor);
    void dettachView();
    void postQuestion(PostQuestionParams params);
}
