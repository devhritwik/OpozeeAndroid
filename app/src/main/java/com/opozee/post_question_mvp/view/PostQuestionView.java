package com.opozee.post_question_mvp.view;

import com.opozee.pojo.PostQuestionResponse;

public interface PostQuestionView {
    void showProgress();
    void hideProgress();
    void onSuccess(PostQuestionResponse response);
    void onQuestionError(String error);
    void onHashTagError(String error);
    void onFailure(String error);
}
