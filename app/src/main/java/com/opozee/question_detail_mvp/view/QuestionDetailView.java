package com.opozee.question_detail_mvp.view;

import com.opozee.pojo.QuestionDetailResponse;

public interface QuestionDetailView {
    void showProgress();
    void hideProgress();
    void onSuccess(QuestionDetailResponse response);
    void onFailure(String error);
}
