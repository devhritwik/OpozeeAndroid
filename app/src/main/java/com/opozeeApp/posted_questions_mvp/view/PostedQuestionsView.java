package com.opozeeApp.posted_questions_mvp.view;

import com.opozeeApp.pojo.PostedQuestionsResponse;

public interface PostedQuestionsView {
    void showProgress();
    void hideProgress();
    void onSuccess(PostedQuestionsResponse response);
    void onFailure(String error);
}
