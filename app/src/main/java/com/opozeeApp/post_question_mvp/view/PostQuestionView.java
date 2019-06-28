package com.opozeeApp.post_question_mvp.view;

import com.opozeeApp.pojo.PostQuestionResponse;

public interface PostQuestionView {
    void showProgress();
    void hideProgress();
    void onSuccess(PostQuestionResponse response);
    void onQuestionError(String error);
    void onHashTagError(String error);
    void onFailure(String error);
}
