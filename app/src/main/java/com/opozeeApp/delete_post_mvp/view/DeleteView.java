package com.opozeeApp.delete_post_mvp.view;

import com.opozeeApp.pojo.DeletePostResponse;

public interface DeleteView {
    void showProgress();
    void hideProgress();
    void onSuccess(DeletePostResponse response);
    void onFailure(String error);
}
