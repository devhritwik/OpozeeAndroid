package com.opozee.posted_questions_mvp.view;

import com.opozee.pojo.PostedQuestionsResponse;

public interface PostedQuestionsView {
    void showProgress();
    void hideProgress();
    void onSuccess(PostedQuestionsResponse response);
    void onFailure(String error);
}
