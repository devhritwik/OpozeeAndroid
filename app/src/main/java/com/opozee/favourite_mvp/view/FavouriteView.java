package com.opozee.favourite_mvp.view;

import com.opozee.pojo.FavouriteResponse;

public interface FavouriteView {

    void showProgress();
    void hideProgress();
    void onSuccess(FavouriteResponse response);
    void onFailure(String error);
}
