package com.opozeeApp.profile_mvp.presenter;

import com.opozeeApp.params.ProfileParams;
import com.opozeeApp.profile_mvp.model.ProfileInteractorImpl;
import com.opozeeApp.profile_mvp.view.ProfileView;

public interface ProfilePresenter {
    void attachView(ProfileView pView, ProfileInteractorImpl pInteractor);
    void dettachView();
    void profile(ProfileParams params);
}
