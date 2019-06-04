package com.opozee.tag_users_mvp.model;

import com.opozee.params.TagUserParams;
import com.opozee.pojo.TagUsersResponse;

public interface TagUsersInteractor {
    void getUsers(TagUserParams params, OnTagUsersFinishedListener mListener);

    interface OnTagUsersFinishedListener{
        void onSuccess(TagUsersResponse response);
        void onFailure(String error);
    }
}
