package com.opozeeApp.profile_mvp.view;

import com.opozeeApp.pojo.ProfileResponse;

public interface ProfileView {
    void showProgress();
    void hideProgress();
    void onFirstNameError(String error);
    void onLastNameError(String error);
    void onSuccess(ProfileResponse response);
    void onFailure(String error);
}
