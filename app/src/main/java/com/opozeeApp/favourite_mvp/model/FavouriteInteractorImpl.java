package com.opozeeApp.favourite_mvp.model;

import com.opozeeApp.params.FavouriteParams;
import com.opozeeApp.pojo.FavouriteResponse;
import com.opozeeApp.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteInteractorImpl implements FavouriteInteractor {
    @Override
    public void getFavourites(FavouriteParams params, final OnFavouriteFinishedListener mListener) {
        Call<FavouriteResponse> call = WebServiceFactory.getInstance().getAllFavourites(params.getUserId(), params.getPageIndex(), params.getPageSize());

        call.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        FavouriteResponse FavouriteResponse = response.body();
                        if (FavouriteResponse != null) {
                            mListener.onSuccess(response.body());
                        }
                    } else {
                        mListener.onFailure(response.body().getResponse().getStatus());

                    }
                } else {
                    mListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again ");
            }
        });
    }

}
