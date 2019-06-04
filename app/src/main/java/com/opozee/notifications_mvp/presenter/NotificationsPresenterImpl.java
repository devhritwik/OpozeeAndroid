package com.opozee.notifications_mvp.presenter;

import com.opozee.notifications_mvp.model.NotificationsInteractor;
import com.opozee.notifications_mvp.model.NotificationsInteractorImpl;
import com.opozee.notifications_mvp.view.NotificationsView;
import com.opozee.params.NotificationsParams;
import com.opozee.pojo.NotificationsResponse;

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
