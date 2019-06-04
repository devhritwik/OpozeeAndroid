package com.opozee.login_mvp.model;

import com.opozee.params.LoginParams;
import com.opozee.pojo.LoginResponse;

public interface LoginInteractor {
    void login(LoginParams params, OnLoginFinishListener mListener);

    interface OnLoginFinishListener
    {
        void onSuccess(LoginResponse loginData);
        void onFailure(String error);
    }

}
