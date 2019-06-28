package com.opozeeApp.question_detail_mvp.presenter;

import com.opozeeApp.params.QuestionDetailParams;
import com.opozeeApp.question_detail_mvp.model.QuestionDetailInteractorImpl;
import com.opozeeApp.question_detail_mvp.view.QuestionDetailView;

public interface QuestionDetailPresenter {
    void attachView(QuestionDetailView qView, QuestionDetailInteractorImpl qInterator);
    void dettachView();
    void getQuestionDetail(QuestionDetailParams params);
}
