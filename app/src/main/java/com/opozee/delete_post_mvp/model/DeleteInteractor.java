package com.opozee.delete_post_mvp.model;

import com.opozee.params.DeletePostParams;
import com.opozee.pojo.DeletePostResponse;

public interface DeleteInteractor {
    void deletePost(DeletePostParams params, OnDeleteFinishedListener mListener);
    interface OnDeleteFinishedListener{
        void onSuccess(DeletePostResponse response);
        void onFailure(String error);
    }
}
