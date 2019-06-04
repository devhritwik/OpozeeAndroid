package com.opozee.user_belief_mvp.model;

import android.util.Log;

import com.opozee.models.Belief;
import com.opozee.retrofit_api.WebServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserBeliefInteractorImpl implements UserBeliefInteractor{

    public static final String TAG = UserBeliefInteractorImpl.class.getSimpleName();

    @Override
    public void getUsersBelief(int userId, final OnUsersBeliefsFinishedListener mListener) {
        Call<List<Belief>> call = WebServiceFactory.getInstance().getUserBeliefs(userId);

        call.enqueue(new Callback<List<Belief>>() {
            @Override
            public void onResponse(Call<List<Belief>> call, Response<List<Belief>> response) {
                if ( response.body() == null ||  !response.isSuccessful()) {
                    Log.d(TAG, "response not succesfull : " + response.toString());
                    mListener.onFailure("An error occurred");
                    return;
                }
                for (Belief b : response.body()){
                    Log.d("PenisBalls", b.getBeliefText() + Boolean.toString(b.isAgree()));
                }
                mListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Belief>> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.toString());
                mListener.onFailure("An error occurred");
            }
        });
    }
}
