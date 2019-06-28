package com.opozeeApp.login_mvp.presenter;

import com.opozeeApp.login_mvp.model.LoginInteractorImpl;
import com.opozeeApp.login_mvp.view.LoginView;
import com.opozeeApp.params.LoginParams;

public interface LoginPresenter {
    void attachView(LoginView lView, LoginInteractorImpl loginInteractor);
    void dettachView();
    void loginUser(LoginParams params);
    void loginUserEmail(String data);

}
