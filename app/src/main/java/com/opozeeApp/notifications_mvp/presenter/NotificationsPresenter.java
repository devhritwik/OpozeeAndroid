package com.opozeeApp.notifications_mvp.presenter;

import com.opozeeApp.notifications_mvp.model.NotificationsInteractorImpl;
import com.opozeeApp.notifications_mvp.view.NotificationsView;
import com.opozeeApp.params.NotificationsParams;

public interface NotificationsPresenter {
    void attachView(NotificationsView nView, NotificationsInteractorImpl nInteractor);
    void dettachView();
    void getNotifications(NotificationsParams params);
}
