package com.opozee.question_detail_mvp.presenter;

import com.opozee.params.QuestionDetailParams;
import com.opozee.pojo.QuestionDetailResponse;
import com.opozee.question_detail_mvp.model.QuestionDetailInteractor;
import com.opozee.question_detail_mvp.model.QuestionDetailInteractorImpl;
import com.opozee.question_detail_mvp.view.QuestionDetailView;

public class QuestionDetailPresenterImpl implements QuestionDetailPresenter, QuestionDetailInteractor.OnDetailFinishListener {
    private QuestionDetailView qView;
    private QuestionDetailInteractorImpl qInterator;

    @Override
    public void onSuccess(QuestionDetailResponse response) {
        qView.hideProgress();
        qView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        qView.hideProgress();
        qView.onFailure(error);
    }

    @Override
    public void attachView(QuestionDetailView qView, QuestionDetailInteractorImpl qInterator) {

        this.qView = qView;
        this.qInterator = qInterator;
    }

    @Override
    public void dettachView() {
        this.qView = null;
        this.qInterator = null;
    }

    @Override
    public void getQuestionDetail(QuestionDetailParams params) {
        qView.showProgress();
        qInterator.getQuestionDetail(params, this);
    }

    public void getQuestionDetailWithoutShowingLoading(QuestionDetailParams params) {
        qInterator.getQuestionDetail(params, this);
    }
}
