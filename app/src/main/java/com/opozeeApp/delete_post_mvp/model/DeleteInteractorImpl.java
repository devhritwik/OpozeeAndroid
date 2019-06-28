package com.opozeeApp.delete_post_mvp.model;

import com.opozeeApp.params.DeletePostParams;
import com.opozeeApp.pojo.DeletePostResponse;
import com.opozeeApp.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteInteractorImpl implements DeleteInteractor{
    @Override
    public void deletePost(DeletePostParams params, final OnDeleteFinishedListener mListener) {
        Call<DeletePostResponse> call = WebServiceFactory.getInstance().deleteQuestion(String.valueOf(params.getQuestId()));

        call.enqueue(new Callback<DeletePostResponse>() {
            @Override
            public void onResponse(Call<DeletePostResponse> call, Response<DeletePostResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        DeletePostResponse LoginSignupResponse = response.body();
                        if (LoginSignupResponse != null) {

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
            public void onFailure(Call<DeletePostResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again 10");
            }
        });

    }



}
