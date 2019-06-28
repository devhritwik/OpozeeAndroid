package com.opozeeApp.posted_questions_mvp.model;

import com.opozeeApp.params.PostedQuestionsParams;
import com.opozeeApp.pojo.PostedQuestionsResponse;

public interface PostedQuestionsInteractor {
    void getQuestions(PostedQuestionsParams params, OnPostedQuestionsFinishListener mListener);

    interface OnPostedQuestionsFinishListener
    {
        void onSuccess(PostedQuestionsResponse response);
        void onFailure(String error);
    }

}
