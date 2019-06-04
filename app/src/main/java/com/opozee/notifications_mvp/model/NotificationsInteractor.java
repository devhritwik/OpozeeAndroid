package com.opozee.notifications_mvp.model;

import com.opozee.params.NotificationsParams;
import com.opozee.pojo.NotificationsResponse;

public interface NotificationsInteractor {
    void getNotifications(NotificationsParams params, OnNotificationsFinishedListener mListener);

    interface OnNotificationsFinishedListener{
        void onSuccess(NotificationsResponse response);
        void onFailure(String error);
    }
}
