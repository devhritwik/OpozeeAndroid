package com.opozeeApp.tag_users_mvp.model;

import com.opozeeApp.params.TagUserParams;
import com.opozeeApp.pojo.TagUsersResponse;

public interface TagUsersInteractor {
    void getUsers(TagUserParams params, OnTagUsersFinishedListener mListener);

    interface OnTagUsersFinishedListener{
        void onSuccess(TagUsersResponse response);
        void onFailure(String error);
    }
}
