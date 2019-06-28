package com.opozeeApp.favourite_mvp.view;

import com.opozeeApp.pojo.FavouriteResponse;

public interface FavouriteView {

    void showProgress();
    void hideProgress();
    void onSuccess(FavouriteResponse response);
    void onFailure(String error);
}
