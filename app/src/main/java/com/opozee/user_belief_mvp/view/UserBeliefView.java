package com.opozee.user_belief_mvp.view;


import com.opozee.models.Belief;

import java.util.List;

public interface UserBeliefView {

    void showProgress();
    void hideProgress();
    void onSuccess(List<Belief> beliefList);
    void onFailure(String error);

}
