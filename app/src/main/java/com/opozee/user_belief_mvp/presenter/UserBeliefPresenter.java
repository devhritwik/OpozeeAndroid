package com.opozee.user_belief_mvp.presenter;

import com.opozee.user_belief_mvp.model.UserBeliefInteractor;
import com.opozee.user_belief_mvp.view.UserBeliefView;

public interface UserBeliefPresenter {
    public void attachView(UserBeliefView userBeliefView, UserBeliefInteractor userBeliefInteractor);
    public void dettachView();

    public void getUserBeliefs(int userId);
}
