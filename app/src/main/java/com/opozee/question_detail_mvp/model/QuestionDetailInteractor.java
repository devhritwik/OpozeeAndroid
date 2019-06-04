package com.opozee.question_detail_mvp.model;

import com.opozee.params.QuestionDetailParams;
import com.opozee.pojo.QuestionDetailResponse;

public interface QuestionDetailInteractor {
    void getQuestionDetail(QuestionDetailParams params, OnDetailFinishListener mListener);

     interface OnDetailFinishListener
     {
         void onSuccess(QuestionDetailResponse response);
         void onFailure(String error);
     }

}
