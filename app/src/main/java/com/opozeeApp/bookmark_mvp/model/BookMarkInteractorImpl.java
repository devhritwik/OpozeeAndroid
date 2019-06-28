package com.opozeeApp.bookmark_mvp.model;

import com.opozeeApp.params.BookMarkParams;
import com.opozeeApp.pojo.BookMarkResponse;
import com.opozeeApp.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookMarkInteractorImpl implements BookMarkInteractor{
    @Override
    public void bookMark(BookMarkParams params, final OnBookMarkFinishedListener mListener) {
        Call<BookMarkResponse> call = WebServiceFactory.getInstance().bookMarkQuestion(params);

        call.enqueue(new Callback<BookMarkResponse>() {
            @Override
            public void onResponse(Call<BookMarkResponse> call, Response<BookMarkResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        BookMarkResponse BookMarkResponse = response.body();
                        if (BookMarkResponse != null) {
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
            public void onFailure(Call<BookMarkResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again 5");
            }
        });
    }
}
