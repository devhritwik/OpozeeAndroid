package com.opozee.like_dislike_mvp.model;

import com.opozee.params.LikeDislikeParams;
import com.opozee.pojo.LikeDislikeResponse;
import com.opozee.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeDislikeInteractorImpl implements LikeDislikeInteractor {
    @Override
    public void dislike(LikeDislikeParams params, final OnDislikeFinishedListener mListener) {
        Call<LikeDislikeResponse> call = WebServiceFactory.getInstance().likeDislikeOpinion(params);

        call.enqueue(new Callback<LikeDislikeResponse>() {
            @Override
            public void onResponse(Call<LikeDislikeResponse> call, Response<LikeDislikeResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        LikeDislikeResponse LikeDislikeResponse = response.body();
                        if (LikeDislikeResponse != null) {

//                            Utils.saveDataInSharePreferences(LoginSignupResponse, null);
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
            public void onFailure(Call<LikeDislikeResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again");
            }
        });
    }
}
