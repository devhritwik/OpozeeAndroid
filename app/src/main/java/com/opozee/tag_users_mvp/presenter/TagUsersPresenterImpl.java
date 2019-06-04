package com.opozee.tag_users_mvp.presenter;

import com.opozee.params.TagUserParams;
import com.opozee.pojo.TagUsersResponse;
import com.opozee.tag_users_mvp.model.TagUsersInteractor;
import com.opozee.tag_users_mvp.model.TagUsersInteractorImpl;
import com.opozee.tag_users_mvp.view.TagUsersView;

public class TagUsersPresenterImpl implements TagUsersPresenter, TagUsersInteractor.OnTagUsersFinishedListener {

    private TagUsersView tView;
    private TagUsersInteractorImpl tInteractor;

    @Override
    public void onSuccess(TagUsersResponse response) {
        tView.hideProgress();
        tView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        tView.hideProgress();
        tView.onFailure(error);
    }

    @Override
    public void attachView(TagUsersView tView, TagUsersInteractorImpl tInteractor) {

        this.tView = tView;
        this.tInteractor = tInteractor;
    }

    @Override
    public void dettachView() {
        tView = null;
        tInteractor = null;
    }

    @Override
    public void getAllUsers(TagUserParams params) {
        tView.showProgress();
        tInteractor.getUsers(params, this);
    }
}
