package com.opozeeApp.add_opinion_mvp.model;

import com.opozeeApp.params.OpinionParams;
import com.opozeeApp.pojo.OpinionResponse;

public interface AddOpinionInteractor {
    void addOpinion(OpinionParams params, OnOpinionFinishedListener mListener);

    interface OnOpinionFinishedListener{
        void onSuccess(OpinionResponse response);
        void onFailure(String error);
    }
}
