package com.opozeeApp.notifications_mvp.model;

import com.opozeeApp.params.NotificationsParams;
import com.opozeeApp.pojo.NotificationsResponse;
import com.opozeeApp.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsInteractorImpl implements NotificationsInteractor{

    @Override
    public void getNotifications(NotificationsParams params, final OnNotificationsFinishedListener mListener) {
        Call<NotificationsResponse> call = WebServiceFactory.getInstance().getAllNotifications(params.getUser_id(), params.getPageIndex(), params.getPageSize());

        call.enqueue(new Callback<NotificationsResponse>() {
            @Override
            public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        NotificationsResponse NotificationsResponse = response.body();
                        if (NotificationsResponse != null) {

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
            public void onFailure(Call<NotificationsResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again 12");
            }
        });
    }
}
