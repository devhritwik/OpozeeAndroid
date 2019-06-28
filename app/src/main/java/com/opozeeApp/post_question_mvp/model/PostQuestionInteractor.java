package com.opozeeApp.post_question_mvp.model;

import com.opozeeApp.params.PostQuestionParams;
import com.opozeeApp.pojo.PostQuestionResponse;

public interface PostQuestionInteractor {
    void postQuestion(PostQuestionParams params, OnPostFinishedListener mListener);

    interface OnPostFinishedListener {
        void onSuccess(PostQuestionResponse response);
        void onQuestionError(String error);
        void onHashTagError(String error);
        void onFailure(String error);
    }
}
