package com.opozeeApp.notifications_mvp.model;

import com.opozeeApp.params.NotificationsParams;
import com.opozeeApp.pojo.NotificationsResponse;

public interface NotificationsInteractor {
    void getNotifications(NotificationsParams params, OnNotificationsFinishedListener mListener);

    interface OnNotificationsFinishedListener{
        void onSuccess(NotificationsResponse response);
        void onFailure(String error);
    }
}
