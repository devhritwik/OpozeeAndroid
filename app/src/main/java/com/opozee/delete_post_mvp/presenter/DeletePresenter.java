package com.opozee.delete_post_mvp.presenter;

import com.opozee.delete_post_mvp.model.DeleteInteractorImpl;
import com.opozee.delete_post_mvp.view.DeleteView;
import com.opozee.params.DeletePostParams;

public interface DeletePresenter {
    void attachView(DeleteView dView, DeleteInteractorImpl dInteractor);
    void dettachView();
    void deletePost(DeletePostParams params);
}
