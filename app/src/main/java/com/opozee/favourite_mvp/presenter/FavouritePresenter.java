package com.opozee.favourite_mvp.presenter;

import com.opozee.favourite_mvp.model.FavouriteInteractorImpl;
import com.opozee.favourite_mvp.view.FavouriteView;
import com.opozee.params.FavouriteParams;

public interface FavouritePresenter {
    void attachView(FavouriteView fView, FavouriteInteractorImpl fInteractor);
    void dettachView();
    void getFavourites(FavouriteParams params);

}
