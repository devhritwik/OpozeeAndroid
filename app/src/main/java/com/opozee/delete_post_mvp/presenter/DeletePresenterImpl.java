package com.opozee.delete_post_mvp.presenter;

import com.opozee.delete_post_mvp.model.DeleteInteractor;
import com.opozee.delete_post_mvp.model.DeleteInteractorImpl;
import com.opozee.delete_post_mvp.view.DeleteView;
import com.opozee.params.DeletePostParams;
import com.opozee.pojo.DeletePostResponse;

public class DeletePresenterImpl implements DeletePresenter, DeleteInteractor.OnDeleteFinishedListener {
    private DeleteView dView;
    private DeleteInteractorImpl dInteractor;

    @Override
    public void onSuccess(DeletePostResponse response) {
        dView.hideProgress();
        dView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        dView.hideProgress();
        dView.onFailure(error);
    }

    @Override
    public void attachView(DeleteView dView, DeleteInteractorImpl dInteractor) {

        this.dView = dView;
        this.dInteractor = dInteractor;
    }

    @Override
    public void dettachView() {
        dView = null;
        dInteractor = null;
    }

    @Override
    public void deletePost(DeletePostParams params) {
        dView.showProgress();
        dInteractor.deletePost(params, this);
    }
}
