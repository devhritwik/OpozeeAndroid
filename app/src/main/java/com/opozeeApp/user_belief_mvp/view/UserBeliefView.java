package com.opozeeApp.user_belief_mvp.view;


import com.opozeeApp.models.Belief;

import java.util.List;

public interface UserBeliefView {

    void showProgress();
    void hideProgress();
    void onSuccess(List<Belief> beliefList);
    void onFailure(String error);

}
