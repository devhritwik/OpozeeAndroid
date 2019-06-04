package com.opozee.login_mvp.view;

import com.opozee.pojo.LoginResponse;

public interface LoginView {

    void showProgress();
    void hideProgress();
    void onSuccess(LoginResponse response);
    void onFailure(String error);
}
