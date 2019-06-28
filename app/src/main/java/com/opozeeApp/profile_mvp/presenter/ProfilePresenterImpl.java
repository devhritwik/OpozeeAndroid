package com.opozeeApp.profile_mvp.presenter;

import com.opozeeApp.params.ProfileParams;
import com.opozeeApp.pojo.ProfileResponse;
import com.opozeeApp.profile_mvp.model.ProfileInteractor;
import com.opozeeApp.profile_mvp.model.ProfileInteractorImpl;
import com.opozeeApp.profile_mvp.view.ProfileView;

public class ProfilePresenterImpl implements ProfilePresenter, ProfileInteractor.OnProfileFinishListener {
    private ProfileView pView;
    private ProfileInteractorImpl pInteractor;

    @Override
    public void onFirstNameError(String error) {
        pView.hideProgress();
        pView.onFirstNameError(error);
    }

    @Override
    public void onLastNameError(String error) {
        pView.hideProgress();
        pView.onLastNameError(error);
    }

    @Override
    public void onSuccess(ProfileResponse response) {
        pView.hideProgress();
        pView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        pView.hideProgress();
        pView.onFailure(error);
    }

    @Override
    public void attachView(ProfileView pView, ProfileInteractorImpl pInteractor) {

        this.pView = pView;
        this.pInteractor = pInteractor;
    }

    @Override
    public void dettachView() {
        pView = null;
        pInteractor = null;
    }

    @Override
    public void profile(ProfileParams params) {
        pView.showProgress();
        pInteractor.profile(params, this);
    }
}
