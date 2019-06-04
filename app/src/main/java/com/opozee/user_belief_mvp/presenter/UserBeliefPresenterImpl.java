package com.opozee.user_belief_mvp.presenter;

import com.opozee.models.Belief;
import com.opozee.user_belief_mvp.model.UserBeliefInteractor;
import com.opozee.user_belief_mvp.view.UserBeliefView;

import java.util.List;

public class UserBeliefPresenterImpl implements UserBeliefPresenter, UserBeliefInteractor.OnUsersBeliefsFinishedListener {
    private UserBeliefView mUserBeliefView;
    private UserBeliefInteractor mUserBeliefInteractor;

    @Override
    public void onSuccess(List<Belief> beliefList) {
        mUserBeliefView.hideProgress();
        mUserBeliefView.onSuccess(beliefList);
    }

    @Override
    public void onFailure(String error) {
        mUserBeliefView.hideProgress();
        mUserBeliefView.onFailure(error);


    }

    @Override
    public void attachView(UserBeliefView userBeliefView, UserBeliefInteractor userBeliefInteractor) {
        this.mUserBeliefView = userBeliefView;
        this.mUserBeliefInteractor = userBeliefInteractor;

    }

    @Override
    public void dettachView() {
        mUserBeliefView = null;

    }

    @Override
    public void getUserBeliefs(int userId) {
        mUserBeliefView.showProgress();
        mUserBeliefInteractor.getUsersBelief(userId , this);

    }
}
