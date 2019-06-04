package com.opozee.posted_questions_mvp.presenter;

import com.opozee.params.PostedQuestionsParams;
import com.opozee.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozee.posted_questions_mvp.view.PostedQuestionsView;

public interface PostedQuestionsPresenter {
    void attachView(PostedQuestionsView pView, PostedQuestionsInteractorImpl pInteractor);
    void dettachView();
    void getQuestions(PostedQuestionsParams params);
}
