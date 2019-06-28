package com.opozeeApp.login_mvp.view;

import com.opozeeApp.pojo.LoginResponse;

public interface LoginView {

    void showProgress();
    void hideProgress();
    void onSuccess(LoginResponse response);
    void onFailure(String error);
}
