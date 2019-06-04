package com.opozee.like_dislike_mvp.presenter;

import com.opozee.like_dislike_mvp.model.LikeDislikeInteractorImpl;
import com.opozee.like_dislike_mvp.view.LikeDislikeView;
import com.opozee.params.LikeDislikeParams;

public interface LikeDislikePresenter {
    void attachView(LikeDislikeView dView, LikeDislikeInteractorImpl dInteractor);
    void dettachView();
    void dislike(LikeDislikeParams params);
}
