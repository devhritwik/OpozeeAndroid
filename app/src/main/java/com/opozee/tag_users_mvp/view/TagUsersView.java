package com.opozee.tag_users_mvp.view;

import com.opozee.pojo.TagUsersResponse;

public interface TagUsersView {
    void showProgress();
    void hideProgress();
    void onSuccess(TagUsersResponse response);
    void onFailure(String error);
}
