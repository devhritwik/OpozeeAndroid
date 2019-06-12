package com.opozee.posted_questions_mvp.model;

import android.util.Log;

import com.opozee.params.PostedQuestionsParams;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostedQuestionsInteractorImpl implements PostedQuestionsInteractor {
    @Override
    public void getQuestions(PostedQuestionsParams params, final OnPostedQuestionsFinishListener mListener) {
        if(params.getUser_id().equals("0"))
        {
            getAllPosts(params, mListener);
        }
        else
        {
            getAllPostsByUserId(params, mListener);
        }
    }

    private void getAllPostsByUserId(PostedQuestionsParams params, final OnPostedQuestionsFinishListener mListener) {
        Call<PostedQuestionsResponse> call = WebServiceFactory.getInstance().getAllPostsByUserId(params.getUser_id(), params.getPageIndex(), params.getPageSize());

        call.enqueue(new Callback<PostedQuestionsResponse>() {
            @Override
            public void onResponse(Call<PostedQuestionsResponse> call, Response<PostedQuestionsResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("Response_Profile=",""+response.body().toString());
                    Log.d("Response_Profile=",""+response.body());
                    if (response.body().getResponse().getCode() == 0 ) {
                        PostedQuestionsResponse LoginSignupResponse = response.body();
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
            public void onFailure(Call<PostedQuestionsResponse> call, Throwable t) {
                Log.d("toString", call.toString() );
                Log.d("localizedMessage", t.getLocalizedMessage());
                mListener.onFailure("Request Failed, Please try again 16");
            }
        });

    }

    private void getAllPosts(PostedQuestionsParams params, final OnPostedQuestionsFinishListener mListener) {
        Call<PostedQuestionsResponse> call = WebServiceFactory.getInstance().getAllQuestions(params.getUser_id(), params.getPageIndex(), params.getPageSize(),1);

        call.enqueue(new Callback<PostedQuestionsResponse>() {
            @Override
            public void onResponse(Call<PostedQuestionsResponse> call, Response<PostedQuestionsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        PostedQuestionsResponse LoginSignupResponse = response.body();
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
            public void onFailure(Call<PostedQuestionsResponse> call, Throwable t) {
                Log.d("toString", call.toString() );
                Log.d("localizedMessage", t.getLocalizedMessage());
                mListener.onFailure("Request Failed, Please try again 17");
            }
        });

    }
}
