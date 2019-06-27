package com.opozee.profiletabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.opozee.R;
import com.opozee.adapters.UserBeliefAdapter;
import com.opozee.adapters.UserPostsAdapter;
import com.opozee.fragments.Profile_New_Fragment;
import com.opozee.models.Belief;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.user_belief_mvp.model.UserBeliefInteractorImpl;
import com.opozee.user_belief_mvp.presenter.UserBeliefPresenter;
import com.opozee.user_belief_mvp.presenter.UserBeliefPresenterImpl;
import com.opozee.user_belief_mvp.view.UserBeliefView;
import com.opozee.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.opozee.fragments.Profile_New_Fragment.scrollView;

public class Beliefs extends Fragment implements UserBeliefView {
    private static final String TAG = "Beliefs_Log";
    private int pageIndex = 1;
    private int pageSize = 1000;
    private int mUserId = Integer.valueOf(Utils.idprofileget(getContext()));

    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private boolean isRefreshed = false;
    private boolean isLastPage = false;
    private List<Belief> mBeliefList = new ArrayList<>();
    private RecyclerView rv_beliefs;
    private UserBeliefPresenter mUserBeliefPresenter;
    private UserBeliefAdapter mBeliefsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_beliefs,null);
        rv_beliefs=view.findViewById(R.id.rv_beliefs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_beliefs.setLayoutManager(linearLayoutManager);
        setPresenters();
        getBeliefs();
        return view;

    }

    private void getBeliefs() {
        mUserBeliefPresenter.getUserBeliefs(mUserId);
    }

    private void setPresenters() {
        mUserBeliefPresenter = new UserBeliefPresenterImpl();
        mUserBeliefPresenter.attachView(this, new UserBeliefInteractorImpl());
    }


    @Override
    public void showProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog == null)
                Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog != null)
                Utils.dismissProgress();
    }

    @Override
    public void onSuccess(List<Belief> beliefList) {
        Log.d(TAG, "onSuccess Belief list");
        mBeliefList.clear();
        mBeliefList.addAll(beliefList);
//        mBeliefCountView.setText(String.format("Beliefs Posted : %d", mBeliefList.size()));
//        mBeliefsAdapter.notifyDataSetChanged();
        mBeliefsAdapter = new UserBeliefAdapter(getContext(), mBeliefList);
        rv_beliefs.setAdapter(mBeliefsAdapter);
        isRefreshed = false;
        isLastPage = false;
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }
}
