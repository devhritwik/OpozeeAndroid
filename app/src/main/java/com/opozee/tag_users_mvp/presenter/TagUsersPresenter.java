package com.opozee.tag_users_mvp.presenter;

import com.opozee.params.TagUserParams;
import com.opozee.tag_users_mvp.model.TagUsersInteractorImpl;
import com.opozee.tag_users_mvp.view.TagUsersView;

public interface TagUsersPresenter {
    void attachView(TagUsersView tView, TagUsersInteractorImpl tInteractor);
    void dettachView();
    void getAllUsers(TagUserParams params);
}
