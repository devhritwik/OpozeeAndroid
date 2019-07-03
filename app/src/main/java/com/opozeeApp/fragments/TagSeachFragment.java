package com.opozeeApp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opozeeApp.R;
import com.opozeeApp.adapters.SearchAdapter;
import com.opozeeApp.params.SearchParams;
import com.opozeeApp.pojo.SearchQuestionResponse;
import com.opozeeApp.search_mvp.model.SearchIntractorImpl;
import com.opozeeApp.search_mvp.presenter.SearchPresenterImpl;
import com.opozeeApp.search_mvp.view.SearchView;
import com.opozeeApp.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagSeachFragment extends Fragment implements SearchView {


    private static final String TAG = TagSeachFragment.class.getSimpleName();
    public static final String SEARCH_TAG_ARGUMENT = "SearchTagArgument";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private SearchPresenterImpl mPresenter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isRefreshed = false;
    private List<SearchQuestionResponse.SearchQuestion> questionsList = new ArrayList<>();
    private SearchAdapter mAdapter;
    private boolean isClicked = false;
    String mTag = "";

    public TagSeachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.tag_search_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);
        mTag = getArguments().getString(SEARCH_TAG_ARGUMENT, "");

        //set Presenter to attach with view and model
        setPresenter();
        //set refresh listeners
        setRefreshListener();
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
        search(mTag);
        setAdapter();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setRefreshListener() {
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener(){
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(mTag != null && mTag.length() > 0) {
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableLoadmore(true);
                    isLastPage = true;
                    ++pageIndex;
                    search();
                    refreshlayout.finishLoadmore(2000/*,false*/);
                }
                else
                {
                    refreshlayout.finishLoadmore(2000/*,false*/);
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableLoadmore(false);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(mTag != null && mTag.length() > 0) {
                    isRefreshed = true;
                    pageIndex = 1;
                    questionsList.clear();
                    if(mAdapter != null)
                        mAdapter.notifyDataSetChanged();
                    search();
                    refreshlayout.finishRefresh(2000/*,false*/);
                }
                else
                {
                    refreshlayout.finishRefresh(2000/*,false*/);
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableLoadmore(false);
                }

            }
        });
    }


    private void search() {
        String searchStr = mTag;
        if(searchStr.length() > 0 && searchStr != null)
        {
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableLoadmore(true);
            if(!isClicked) {
                isClicked = true;
                if(searchStr.contains("#"))
                {
                    searchStr.replace("#", "~");
                }
                search(searchStr);
            }

        }
        else
        {
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setEnableLoadmore(false);
        }
    }

    private void search(String searchStr) {
        SearchParams params = new SearchParams();
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setSearchStr(searchStr);
        if (Utils.isNetworkAvail(getActivity())) {
            mPresenter.search(params);
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }

    }

    private void setPresenter() {
        mPresenter = new SearchPresenterImpl();
        mPresenter.attachView(this, new SearchIntractorImpl());
    }

    public void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new SearchAdapter(getActivity(), questionsList);
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
    public void onSuccess(SearchQuestionResponse response) {
        if(response.getResponse().getSearchQuestion() != null)
            if(response.getResponse().getSearchQuestion().size() > 0) {
                questionsList.addAll(response.getResponse().getSearchQuestion());

                mAdapter.notifyDataSetChanged();
                isRefreshed = false;
                isLastPage = false;
                isClicked = false;
            }
            else
            {
                isRefreshed = false;
                isLastPage = false;
                isClicked = false;
                showNoResultsFound();
            }
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }


    public void showNoResultsFound() {

    }

}
