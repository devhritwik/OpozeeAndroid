package com.opozee.post_question_mvp.model;

import com.opozee.params.PostQuestionParams;
import com.opozee.pojo.PostQuestionResponse;

public interface PostQuestionInteractor {
    void postQuestion(PostQuestionParams params, OnPostFinishedListener mListener);

    interface OnPostFinishedListener {
        void onSuccess(PostQuestionResponse response);
        void onQuestionError(String error);
        void onHashTagError(String error);
        void onFailure(String error);
    }
}
