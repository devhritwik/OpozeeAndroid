package com.opozee.profile_mvp.presenter;

import com.opozee.params.ProfileParams;
import com.opozee.profile_mvp.model.ProfileInteractorImpl;
import com.opozee.profile_mvp.view.ProfileView;

public interface ProfilePresenter {
    void attachView(ProfileView pView, ProfileInteractorImpl pInteractor);
    void dettachView();
    void profile(ProfileParams params);
}
