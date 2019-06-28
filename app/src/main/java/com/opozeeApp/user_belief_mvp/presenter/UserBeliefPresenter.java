package com.opozeeApp.user_belief_mvp.presenter;

import com.opozeeApp.user_belief_mvp.model.UserBeliefInteractor;
import com.opozeeApp.user_belief_mvp.view.UserBeliefView;

public interface UserBeliefPresenter {
    void attachView(UserBeliefView userBeliefView, UserBeliefInteractor userBeliefInteractor);
    void dettachView();

    void getUserBeliefs(int userId);
}
