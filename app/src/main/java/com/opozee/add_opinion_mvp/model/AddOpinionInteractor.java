package com.opozee.add_opinion_mvp.model;

import com.opozee.params.OpinionParams;
import com.opozee.pojo.OpinionResponse;

public interface AddOpinionInteractor {
    void addOpinion(OpinionParams params, OnOpinionFinishedListener mListener);

    interface OnOpinionFinishedListener{
        void onSuccess(OpinionResponse response);
        void onFailure(String error);
    }
}
