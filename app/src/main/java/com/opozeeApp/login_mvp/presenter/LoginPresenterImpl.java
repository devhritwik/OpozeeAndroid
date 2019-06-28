package com.opozeeApp.login_mvp.presenter;

import com.opozeeApp.login_mvp.model.LoginInteractor;
import com.opozeeApp.login_mvp.model.LoginInteractorImpl;
import com.opozeeApp.login_mvp.view.LoginView;
import com.opozeeApp.params.LoginParams;
import com.opozeeApp.pojo.LoginResponse;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishListener {

    private LoginView lView;
    private LoginInteractorImpl lInteractor;

    @Override
    public void onSuccess(LoginResponse loginData) {
        lView.hideProgress();
        lView.onSuccess(loginData);
    }

    @Override
    public void onFailure(String error) {
        lView.hideProgress();
        lView.onFailure(error);
    }

    @Override
    public void attachView(LoginView lView, LoginInteractorImpl lInteractor) {

        this.lView = lView;
        this.lInteractor = lInteractor;
    }

    @Override
    public void dettachView() {
        lView = null;
        lInteractor = null;
    }

    @Override
    public void loginUser(LoginParams params) {
        lView.showProgress();
        lInteractor.login(params, this);
    }

    @Override
    public void loginUserEmail(String data) {
        lView.showProgress();
        lInteractor.loginemail(data, this);
    }
}
