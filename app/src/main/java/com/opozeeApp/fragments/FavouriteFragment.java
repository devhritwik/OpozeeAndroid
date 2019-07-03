package com.opozeeApp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opozeeApp.R;
import com.opozeeApp.adapters.FavouriteAdapter;
import com.opozeeApp.favourite_mvp.model.FavouriteInteractorImpl;
import com.opozeeApp.favourite_mvp.presenter.FavouritePresenterImpl;
import com.opozeeApp.favourite_mvp.view.FavouriteView;
import com.opozeeApp.params.FavouriteParams;
import com.opozeeApp.pojo.FavouriteResponse;
import com.opozeeApp.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment implements FavouriteView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private FavouritePresenterImpl mPresenter;

    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isRefreshed = false;
    private List<FavouriteResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private FavouriteAdapter mAdapter;

    public FavouriteFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, rootView);


        setPresenter();
        //set refresh listeners
        setRefreshListener();

        setAdapter();

        //get all bookmarked questions
        if (Utils.isNetworkAvail(getActivity())) {
            getBookmarked();
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }

        return rootView;
    }

    private void setRefreshListener() {
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener(){
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isLastPage = true;
                ++pageIndex;
                if (Utils.isNetworkAvail(getActivity())) {
                    getBookmarked();
                } else {
                    Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
                }
                refreshlayout.finishLoadmore(2000/*,false*/);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefreshed = true;
                pageIndex = 1;
                questionsList.clear();
                if(mAdapter != null)
                    mAdapter.notifyDataSetChanged();
                if (Utils.isNetworkAvail(getActivity())) {
                    getBookmarked();
                } else {
                    Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
                }
                refreshlayout.finishRefresh(2000/*,false*/);
            }
        });
    }

    private void getBookmarked() {
        FavouriteParams params = new FavouriteParams();
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setUserId(Utils.getLoggedInUserId(getActivity()));
        mPresenter.getFavourites(params);
    }

    private void setPresenter() {
        mPresenter = new FavouritePresenterImpl();
        mPresenter.attachView(this, new FavouriteInteractorImpl());
    }

    public void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new FavouriteAdapter(getActivity(), questionsList);
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
    public void onSuccess(FavouriteResponse response) {
        if(response.getResponse().getGetBookmarkQuestion().getPostQuestionDetail() != null)
            if(response.getResponse().getGetBookmarkQuestion().getPostQuestionDetail().size() > 0) {
                questionsList.addAll(response.getResponse().getGetBookmarkQuestion().getPostQuestionDetail());

                mAdapter.notifyDataSetChanged();
                isRefreshed = false;
                isLastPage = false;
            }
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }

}
