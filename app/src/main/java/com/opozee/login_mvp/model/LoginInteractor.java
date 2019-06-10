package com.opozee.login_mvp.model;

import com.opozee.params.LoginParams;
import com.opozee.pojo.LoginResponse;

public interface LoginInteractor {
    void login(LoginParams params, OnLoginFinishListener mListener);
    void loginemail(String data,OnLoginFinishListener mListener);

    interface OnLoginFinishListener
    {
        void onSuccess(LoginResponse loginData);
        void onFailure(String error);
    }

}
