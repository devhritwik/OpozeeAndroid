package com.opozee.login_mvp.presenter;

import com.opozee.login_mvp.model.LoginInteractorImpl;
import com.opozee.login_mvp.view.LoginView;
import com.opozee.params.LoginParams;

public interface LoginPresenter {
    void attachView(LoginView lView, LoginInteractorImpl loginInteractor);
    void dettachView();
    void loginUser(LoginParams params);
    void loginUserEmail(String data);

}
