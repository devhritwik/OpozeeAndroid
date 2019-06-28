package com.opozeeApp.like_dislike_mvp.presenter;

import com.opozeeApp.like_dislike_mvp.model.LikeDislikeInteractorImpl;
import com.opozeeApp.like_dislike_mvp.view.LikeDislikeView;
import com.opozeeApp.params.LikeDislikeParams;

public interface LikeDislikePresenter {
    void attachView(LikeDislikeView dView, LikeDislikeInteractorImpl dInteractor);
    void dettachView();
    void dislike(LikeDislikeParams params);
}
