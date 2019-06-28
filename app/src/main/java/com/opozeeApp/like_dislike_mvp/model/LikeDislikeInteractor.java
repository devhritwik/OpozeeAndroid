package com.opozeeApp.like_dislike_mvp.model;

import com.opozeeApp.params.LikeDislikeParams;
import com.opozeeApp.pojo.LikeDislikeResponse;

public interface LikeDislikeInteractor {
    void dislike(LikeDislikeParams params, OnDislikeFinishedListener mListener);

    interface OnDislikeFinishedListener{
        void onSuccess(LikeDislikeResponse response);
        void onFailure(String error);
    }

}
