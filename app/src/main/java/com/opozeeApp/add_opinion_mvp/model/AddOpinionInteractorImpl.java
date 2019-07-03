package com.opozeeApp.add_opinion_mvp.model;

import com.opozeeApp.params.OpinionParams;
import com.opozeeApp.pojo.OpinionResponse;
import com.opozeeApp.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOpinionInteractorImpl implements AddOpinionInteractor {
    @Override
    public void addOpinion(OpinionParams params, final OnOpinionFinishedListener mListener) {
        Call<OpinionResponse> call = WebServiceFactory.getInstance().addOpinion(params);

        call.enqueue(new Callback<OpinionResponse>() {
            @Override
            public void onResponse(Call<OpinionResponse> call, Response<OpinionResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        OpinionResponse LoginSignupResponse = response.body();
                        if (LoginSignupResponse != null) {

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
            public void onFailure(Call<OpinionResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again ");
            }
        });
    }
}
