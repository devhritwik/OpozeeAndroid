package com.opozee.question_detail_mvp.presenter;

import com.opozee.params.QuestionDetailParams;
import com.opozee.question_detail_mvp.model.QuestionDetailInteractorImpl;
import com.opozee.question_detail_mvp.view.QuestionDetailView;

public interface QuestionDetailPresenter {
    void attachView(QuestionDetailView qView, QuestionDetailInteractorImpl qInterator);
    void dettachView();
    void getQuestionDetail(QuestionDetailParams params);
}
