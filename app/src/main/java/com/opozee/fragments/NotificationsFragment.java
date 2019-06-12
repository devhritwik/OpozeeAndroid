package com.opozee.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opozee.R;
import com.opozee.adapters.NotificationsAdapter;
import com.opozee.notifications_mvp.model.NotificationsInteractorImpl;
import com.opozee.notifications_mvp.presenter.NotificationsPresenterImpl;
import com.opozee.notifications_mvp.view.NotificationsView;
import com.opozee.params.NotificationsParams;
import com.opozee.pojo.NotificationsResponse;
import com.opozee.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment  extends Fragment implements NotificationsView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private NotificationsPresenterImpl mPresenter;

    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isRefreshed = false;
    private List<NotificationsResponse.AllOpinion> questionsList = new ArrayList<>();
    private NotificationsAdapter mAdapter;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, rootView);


        setPresenter();
        //set refresh listeners
        setRefreshListener();

        setAdapter();

        //get all the questions
        getQuestions();
        return rootView;
    }

    private void setRefreshListener() {
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener(){
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isLastPage = true;
                ++pageIndex;
                getQuestions();
                refreshlayout.finishLoadmore(2000/*,false*/);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefreshed = true;
                pageIndex = 1;
                questionsList.clear();
                if(mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                getQuestions();
                refreshlayout.finishRefresh(2000/*,false*/);
            }
        });
    }


    private void getQuestions() {
        NotificationsParams params = new NotificationsParams();
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setUser_id(Utils.getLoggedInUserId(getActivity()));
//        params.setUser_id(Utils.getLoggedInUserId(getActivity()));
        Log.d("notification_log","pageIndex="+pageIndex);
        Log.d("notification_log","pageSize="+pageSize);
        Log.d("notification_log","Utils.getLoggedInUserId(getActivity()="+Utils.getLoggedInUserId(getActivity()));

        mPresenter.getNotifications(params);
    }

    //setPresenter to attach the view to the model
    void setPresenter()
    {
        mPresenter = new NotificationsPresenterImpl();
        mPresenter.attachView(this, new NotificationsInteractorImpl());
    }



    public void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new NotificationsAdapter(getActivity(), questionsList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showProgress() {
        if(!isRefreshed && !isLastPage)
            if(Utils.mProgressDialog == null)
                Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if(!isRefreshed && !isLastPage)
            if(Utils.mProgressDialog != null)
                Utils.dismissProgress();
    }


    @Override
    public void onSuccess(NotificationsResponse response) {
        if(response.getResponse().getAllOpinion() != null)
            if(response.getResponse().getAllOpinion().size() > 0) {
                questionsList.addAll(removeEmptyItems(response.getResponse().getAllOpinion()));

                    mAdapter.notifyDataSetChanged();
                isRefreshed = false;
                isLastPage = false;
            }
    }

    private List<NotificationsResponse.AllOpinion> removeEmptyItems(List<NotificationsResponse.AllOpinion> allOpinion) {
        for(int i = 0; i < allOpinion.size(); i++)
        {
            if(allOpinion.get(i).getMessage().equals(""))
            {
                allOpinion.remove(i);
            }
        }

        return allOpinion;
    }


    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }
}


