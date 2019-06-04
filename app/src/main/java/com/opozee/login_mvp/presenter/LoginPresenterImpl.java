package com.opozee.login_mvp.presenter;

import com.opozee.login_mvp.model.LoginInteractor;
import com.opozee.login_mvp.model.LoginInteractorImpl;
import com.opozee.login_mvp.view.LoginView;
import com.opozee.params.LoginParams;
import com.opozee.pojo.LoginResponse;

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
}
