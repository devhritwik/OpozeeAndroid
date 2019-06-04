package com.opozee.favourite_mvp.model;

import com.opozee.params.FavouriteParams;
import com.opozee.pojo.FavouriteResponse;

public interface FavouriteInteractor {
    void getFavourites(FavouriteParams params, OnFavouriteFinishedListener mListener);

    interface OnFavouriteFinishedListener
    {
        void onSuccess(FavouriteResponse response);
        void onFailure(String error);
    }
}
