package com.opozee.profile_mvp.model;

import com.opozee.params.ProfileParams;
import com.opozee.pojo.ProfileResponse;

public interface ProfileInteractor {
    void profile(ProfileParams params, OnProfileFinishListener mListener);

    interface OnProfileFinishListener{
        void onFirstNameError(String error);
        void onLastNameError(String error);
        void onSuccess(ProfileResponse response);
        void onFailure(String error);
    }

}


