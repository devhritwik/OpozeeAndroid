package com.opozee.search_mvp.model;

import com.opozee.params.SearchParams;
import com.opozee.pojo.SearchQuestionResponse;
import com.opozee.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchIntractorImpl implements SearchInteractor {
    @Override
    public void search(SearchParams params, final OnSearchFinishedListener mListener) {
        Call<SearchQuestionResponse> call = WebServiceFactory.getInstance().searchQuestion(params.getSearchStr(), params.getPageIndex(), params.getPageSize());

        call.enqueue(new Callback<SearchQuestionResponse>() {
            @Override
            public void onResponse(Call<SearchQuestionResponse> call, Response<SearchQuestionResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        SearchQuestionResponse LoginSignupResponse = response.body();
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
            public void onFailure(Call<SearchQuestionResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again 2");
            }
        });
    }


}
