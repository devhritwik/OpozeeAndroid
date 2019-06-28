package com.opozeeApp.login_mvp.model;

import com.opozeeApp.params.LoginParams;
import com.opozeeApp.pojo.LoginResponse;

public interface LoginInteractor {
    void login(LoginParams params, OnLoginFinishListener mListener);
    void loginemail(String data,OnLoginFinishListener mListener);

    interface OnLoginFinishListener
    {
        void onSuccess(LoginResponse loginData);
        void onFailure(String error);
    }

}
