package com.opozee.question_detail_mvp.model;

import com.opozee.params.QuestionDetailParams;
import com.opozee.pojo.QuestionDetailResponse;
import com.opozee.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailInteractorImpl implements QuestionDetailInteractor {
    @Override
    public void getQuestionDetail(QuestionDetailParams params, final OnDetailFinishListener mListener) {
        Call<QuestionDetailResponse> call = WebServiceFactory.getInstance().getQuestionDetail(params.getQuestionId(), params.getUserid(), 1, 15);

        call.enqueue(new Callback<QuestionDetailResponse>() {
            @Override
            public void onResponse(Call<QuestionDetailResponse> call, Response<QuestionDetailResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        QuestionDetailResponse LoginSignupResponse = response.body();
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
            public void onFailure(Call<QuestionDetailResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again 15");
            }
        });

    }
}
