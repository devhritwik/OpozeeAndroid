package com.opozeeApp.like_dislike_mvp.presenter;

import android.util.Log;

import com.opozeeApp.like_dislike_mvp.model.LikeDislikeInteractor;
import com.opozeeApp.like_dislike_mvp.model.LikeDislikeInteractorImpl;
import com.opozeeApp.like_dislike_mvp.view.LikeDislikeView;
import com.opozeeApp.params.LikeDislikeParams;
import com.opozeeApp.pojo.LikeDislikeResponse;

public class LikeDislikePresenterImpl implements LikeDislikePresenter, LikeDislikeInteractor.OnDislikeFinishedListener {
    private LikeDislikeView dView;
    private LikeDislikeInteractorImpl dInteractor;
    private static final String TAG = LikeDislikePresenterImpl.class.getSimpleName();

    @Override
    public void onSuccess(LikeDislikeResponse response) {
//        dView.hideProgress();
//        Log.d(TAG, "onSuccess()");
        dView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
//        Log.d(TAG, "inFailure()");
//        dView.hideProgress();
        dView.onFailure(error);
    }

    @Override
    public void attachView(LikeDislikeView dView, LikeDislikeInteractorImpl dInteractor) {
//        Log.d(TAG, "attachView()");
        this.dView = dView;
        this.dInteractor = dInteractor;
    }

    @Override
    public void dettachView() {
//        Log.d(TAG, "detachView()");
        this.dView = null;
        this.dInteractor = null;
    }

    @Override
    public void dislike(LikeDislikeParams params) {
//        Log.d(TAG, "dislike()");
//        dView.showProgress();
        dInteractor.dislike(params, this);
    }
}
