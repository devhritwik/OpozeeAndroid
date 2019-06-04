package com.opozee.add_opinion_mvp.presenter;

import com.opozee.add_opinion_mvp.model.AddOpinionInteractorImpl;
import com.opozee.add_opinion_mvp.view.AddOpinionView;
import com.opozee.params.OpinionParams;

public interface AddOpinionPresenter {
    void attachView(AddOpinionView aView, AddOpinionInteractorImpl aInteractor);
    void dettachView();
    void addOpinion(OpinionParams params);
}
