package com.opozeeApp.favourite_mvp.presenter;

import com.opozeeApp.favourite_mvp.model.FavouriteInteractorImpl;
import com.opozeeApp.favourite_mvp.view.FavouriteView;
import com.opozeeApp.params.FavouriteParams;

public interface FavouritePresenter {
    void attachView(FavouriteView fView, FavouriteInteractorImpl fInteractor);
    void dettachView();
    void getFavourites(FavouriteParams params);

}
