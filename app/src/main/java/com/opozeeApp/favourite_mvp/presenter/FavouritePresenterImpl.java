package com.opozeeApp.favourite_mvp.presenter;

import com.opozeeApp.favourite_mvp.model.FavouriteInteractor;
import com.opozeeApp.favourite_mvp.model.FavouriteInteractorImpl;
import com.opozeeApp.favourite_mvp.view.FavouriteView;
import com.opozeeApp.params.FavouriteParams;
import com.opozeeApp.pojo.FavouriteResponse;

public class FavouritePresenterImpl implements FavouritePresenter, FavouriteInteractor.OnFavouriteFinishedListener {

    private FavouriteView fView;
    private FavouriteInteractorImpl fInteractor;

    @Override
    public void onSuccess(FavouriteResponse response) {
        fView.hideProgress();
        fView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        fView.hideProgress();
        fView.onFailure(error);
    }

    @Override
    public void attachView(FavouriteView fView, FavouriteInteractorImpl fInteractor) {

        this.fView = fView;
        this.fInteractor = fInteractor;
    }

    @Override
    public void dettachView() {
        fView = null;
        fInteractor = null;
    }

    @Override
    public void getFavourites(FavouriteParams params) {
        fView.showProgress();
        fInteractor.getFavourites(params, this);
    }
}
