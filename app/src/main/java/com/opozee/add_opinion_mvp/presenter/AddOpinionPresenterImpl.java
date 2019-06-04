package com.opozee.add_opinion_mvp.presenter;

import com.opozee.add_opinion_mvp.model.AddOpinionInteractor;
import com.opozee.add_opinion_mvp.model.AddOpinionInteractorImpl;
import com.opozee.add_opinion_mvp.view.AddOpinionView;
import com.opozee.params.OpinionParams;
import com.opozee.pojo.OpinionResponse;

public class AddOpinionPresenterImpl implements AddOpinionPresenter, AddOpinionInteractor.OnOpinionFinishedListener {

    private AddOpinionView aView;
    private AddOpinionInteractorImpl aInteractor;

    @Override
    public void onSuccess(OpinionResponse response) {
        aView.hideProgress();
        aView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        aView.hideProgress();
        aView.onFailure(error);
    }

    @Override
    public void attachView(AddOpinionView aView, AddOpinionInteractorImpl aInteractor) {
        this.aView = aView;
        this.aInteractor = aInteractor;
    }

    @Override
    public void dettachView() {
        this.aView = null;
        this.aInteractor = null;
    }

    @Override
    public void addOpinion(OpinionParams params) {
        aView.showProgress();
        aInteractor.addOpinion(params, this);
    }
}
