package com.opozee.notifications_mvp.view;

import com.opozee.pojo.NotificationsResponse;

public interface NotificationsView {
    void showProgress();
    void hideProgress();
    void onSuccess(NotificationsResponse response);
    void onFailure(String error);
}
