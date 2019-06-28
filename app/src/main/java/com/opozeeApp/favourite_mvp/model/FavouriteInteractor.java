package com.opozeeApp.favourite_mvp.model;

import com.opozeeApp.params.FavouriteParams;
import com.opozeeApp.pojo.FavouriteResponse;

public interface FavouriteInteractor {
    void getFavourites(FavouriteParams params, OnFavouriteFinishedListener mListener);

    interface OnFavouriteFinishedListener
    {
        void onSuccess(FavouriteResponse response);
        void onFailure(String error);
    }
}
