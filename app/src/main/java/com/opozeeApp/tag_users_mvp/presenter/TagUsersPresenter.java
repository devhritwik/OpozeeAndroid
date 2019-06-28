package com.opozeeApp.tag_users_mvp.presenter;

import com.opozeeApp.params.TagUserParams;
import com.opozeeApp.tag_users_mvp.model.TagUsersInteractorImpl;
import com.opozeeApp.tag_users_mvp.view.TagUsersView;

public interface TagUsersPresenter {
    void attachView(TagUsersView tView, TagUsersInteractorImpl tInteractor);
    void dettachView();
    void getAllUsers(TagUserParams params);
}
