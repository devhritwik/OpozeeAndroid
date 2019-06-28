package com.opozeeApp.profile_mvp.model;

import com.opozeeApp.params.ProfileParams;
import com.opozeeApp.pojo.ProfileResponse;

public interface ProfileInteractor {
    void profile(ProfileParams params, OnProfileFinishListener mListener);

    interface OnProfileFinishListener{
        void onFirstNameError(String error);
        void onLastNameError(String error);
        void onSuccess(ProfileResponse response);
        void onFailure(String error);
    }

}


