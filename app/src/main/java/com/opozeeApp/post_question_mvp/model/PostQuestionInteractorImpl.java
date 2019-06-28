package com.opozeeApp.post_question_mvp.model;

import com.opozeeApp.params.PostQuestionParams;
import com.opozeeApp.pojo.PostQuestionResponse;
import com.opozeeApp.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostQuestionInteractorImpl implements PostQuestionInteractor{
    @Override
    public void postQuestion(PostQuestionParams params, OnPostFinishedListener mListener) {
        if(params.getQuestion() == null || params.getQuestion().length() == 0)
        {
            mListener.onQuestionError("Please enter question to post");
        }
//        else if(params.getHashTags() == null || params.getHashTags().length() == 0)
//        {
//            mListener.onHashTagError("Please enter hashtags");
//        }
        else
        {
            if(params.getId().equals("0"))
                post(params, mListener);
            else
                editPost(params, mListener);
        }
    }

    private void editPost(PostQuestionParams params, final OnPostFinishedListener mListener) {

        Call<PostQuestionResponse> call = WebServiceFactory.getInstance().editPost(params);

        call.enqueue(new Callback<PostQuestionResponse>() {
            @Override
            public void onResponse(Call<PostQuestionResponse> call, Response<PostQuestionResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        PostQuestionResponse postQuestionResponse = response.body();
                        if (postQuestionResponse != null) {

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
            public void onFailure(Call<PostQuestionResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again 13");
            }
        });
    }

    private void post(PostQuestionParams params, final OnPostFinishedListener mListener) {
        Call<PostQuestionResponse> call = WebServiceFactory.getInstance().postQuestion(params);

        call.enqueue(new Callback<PostQuestionResponse>() {
            @Override
            public void onResponse(Call<PostQuestionResponse> call, Response<PostQuestionResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        PostQuestionResponse postQuestionResponse = response.body();
                        if (postQuestionResponse != null) {

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
            public void onFailure(Call<PostQuestionResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again 14");
            }
        });
    }
}
