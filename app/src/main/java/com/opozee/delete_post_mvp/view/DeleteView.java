package com.opozee.delete_post_mvp.view;

import com.opozee.pojo.DeletePostResponse;

public interface DeleteView {
    void showProgress();
    void hideProgress();
    void onSuccess(DeletePostResponse response);
    void onFailure(String error);
}
