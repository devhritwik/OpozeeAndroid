package com.opozeeApp.user_belief_mvp.model;

import com.opozeeApp.models.Belief;

import java.util.List;

public interface UserBeliefInteractor {

    void getUsersBelief(int userId, OnUsersBeliefsFinishedListener mListener);


    interface OnUsersBeliefsFinishedListener{
        void onSuccess(List<Belief> beliefList);
        void onFailure(String error);
    }
}
