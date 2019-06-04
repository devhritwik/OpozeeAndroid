package com.opozee.like_dislike_mvp.view;

import com.opozee.pojo.LikeDislikeResponse;

public interface LikeDislikeView {
    void showProgress();
    void hideProgress();
    void onSuccess(LikeDislikeResponse response);
    void onFailure(String error);
}
