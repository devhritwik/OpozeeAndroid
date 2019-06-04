package com.opozee.posted_questions_mvp.model;

import com.opozee.params.PostedQuestionsParams;
import com.opozee.pojo.PostedQuestionsResponse;

public interface PostedQuestionsInteractor {
    void getQuestions(PostedQuestionsParams params, OnPostedQuestionsFinishListener mListener);

    interface OnPostedQuestionsFinishListener
    {
        void onSuccess(PostedQuestionsResponse response);
        void onFailure(String error);
    }

}
