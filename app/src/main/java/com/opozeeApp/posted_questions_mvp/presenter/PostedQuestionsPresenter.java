package com.opozeeApp.posted_questions_mvp.presenter;

import com.opozeeApp.params.PostedQuestionsParams;
import com.opozeeApp.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozeeApp.posted_questions_mvp.view.PostedQuestionsView;

public interface PostedQuestionsPresenter {
    void attachView(PostedQuestionsView pView, PostedQuestionsInteractorImpl pInteractor);
    void dettachView();
    void getQuestions(PostedQuestionsParams params);
}
