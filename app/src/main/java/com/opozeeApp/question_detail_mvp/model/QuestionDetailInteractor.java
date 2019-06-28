package com.opozeeApp.question_detail_mvp.model;

import com.opozeeApp.params.QuestionDetailParams;
import com.opozeeApp.pojo.QuestionDetailResponse;

public interface QuestionDetailInteractor {
    void getQuestionDetail(QuestionDetailParams params, OnDetailFinishListener mListener);

     interface OnDetailFinishListener
     {
         void onSuccess(QuestionDetailResponse response);
         void onFailure(String error);
     }

}
