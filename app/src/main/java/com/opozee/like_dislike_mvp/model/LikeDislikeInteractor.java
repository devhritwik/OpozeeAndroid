package com.opozee.like_dislike_mvp.model;

import com.opozee.params.LikeDislikeParams;
import com.opozee.pojo.LikeDislikeResponse;

public interface LikeDislikeInteractor {
    void dislike(LikeDislikeParams params, OnDislikeFinishedListener mListener);

    interface OnDislikeFinishedListener{
        void onSuccess(LikeDislikeResponse response);
        void onFailure(String error);
    }

}
