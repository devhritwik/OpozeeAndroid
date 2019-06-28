package com.opozeeApp.delete_post_mvp.presenter;

import com.opozeeApp.delete_post_mvp.model.DeleteInteractorImpl;
import com.opozeeApp.delete_post_mvp.view.DeleteView;
import com.opozeeApp.params.DeletePostParams;

public interface DeletePresenter {
    void attachView(DeleteView dView, DeleteInteractorImpl dInteractor);
    void dettachView();
    void deletePost(DeletePostParams params);
}
