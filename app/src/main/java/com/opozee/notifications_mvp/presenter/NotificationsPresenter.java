package com.opozee.notifications_mvp.presenter;

import com.opozee.notifications_mvp.model.NotificationsInteractorImpl;
import com.opozee.notifications_mvp.view.NotificationsView;
import com.opozee.params.NotificationsParams;

public interface NotificationsPresenter {
    void attachView(NotificationsView nView, NotificationsInteractorImpl nInteractor);
    void dettachView();
    void getNotifications(NotificationsParams params);
}
