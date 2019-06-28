package com.opozeeApp.delete_post_mvp.model;

import com.opozeeApp.params.DeletePostParams;
import com.opozeeApp.pojo.DeletePostResponse;

public interface DeleteInteractor {
    void deletePost(DeletePostParams params, OnDeleteFinishedListener mListener);
    interface OnDeleteFinishedListener{
        void onSuccess(DeletePostResponse response);
        void onFailure(String error);
    }
}
