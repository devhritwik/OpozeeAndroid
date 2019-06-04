package com.opozee.user_belief_mvp.model;

import com.opozee.models.Belief;

import java.util.List;

public interface UserBeliefInteractor {

    public void getUsersBelief(int userId, OnUsersBeliefsFinishedListener mListener);


    interface OnUsersBeliefsFinishedListener{
        public void onSuccess(List<Belief> beliefList);
        public void onFailure(String error);
    }
}
