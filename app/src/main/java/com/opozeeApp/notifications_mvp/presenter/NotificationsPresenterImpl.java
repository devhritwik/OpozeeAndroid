package com.opozeeApp.notifications_mvp.presenter;

import com.opozeeApp.notifications_mvp.model.NotificationsInteractor;
import com.opozeeApp.notifications_mvp.model.NotificationsInteractorImpl;
import com.opozeeApp.notifications_mvp.view.NotificationsView;
import com.opozeeApp.params.NotificationsParams;
import com.opozeeApp.pojo.NotificationsResponse;

public class NotificationsPresenterImpl implements NotificationsPresenter, NotificationsInteractor.OnNotificationsFinishedListener {
    private NotificationsView nView;
    private NotificationsInteractorImpl nInteractor;

    @Override
    public void onSuccess(NotificationsResponse response) {
        nView.hideProgress();
        nView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        nView.hideProgress();
        nView.onFailure(error);
    }

    @Override
    public void attachView(NotificationsView nView, NotificationsInteractorImpl nInteractor) {

        this.nView = nView;
        this.nInteractor = nInteractor;
    }

    @Override
    public void dettachView() {
        nView = null;
        nInteractor = null;
    }

    @Override
    public void getNotifications(NotificationsParams params) {
        nView.showProgress();
        nInteractor.getNotifications(params, this);
    }
}
