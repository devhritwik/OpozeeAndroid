package com.opozeeApp.tag_users_mvp.view;

import com.opozeeApp.pojo.TagUsersResponse;

public interface TagUsersView {
    void showProgress();
    void hideProgress();
    void onSuccess(TagUsersResponse response);
    void onFailure(String error);
}
