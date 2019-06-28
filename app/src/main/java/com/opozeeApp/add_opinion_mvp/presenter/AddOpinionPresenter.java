package com.opozeeApp.add_opinion_mvp.presenter;

import com.opozeeApp.add_opinion_mvp.model.AddOpinionInteractorImpl;
import com.opozeeApp.add_opinion_mvp.view.AddOpinionView;
import com.opozeeApp.params.OpinionParams;

public interface AddOpinionPresenter {
    void attachView(AddOpinionView aView, AddOpinionInteractorImpl aInteractor);
    void dettachView();
    void addOpinion(OpinionParams params);
}
