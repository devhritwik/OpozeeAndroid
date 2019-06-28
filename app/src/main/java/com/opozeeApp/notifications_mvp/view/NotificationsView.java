package com.opozeeApp.notifications_mvp.view;

import com.opozeeApp.pojo.NotificationsResponse;

public interface NotificationsView {
    void showProgress();
    void hideProgress();
    void onSuccess(NotificationsResponse response);
    void onFailure(String error);
}
