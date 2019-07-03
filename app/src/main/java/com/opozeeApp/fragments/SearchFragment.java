package com.opozeeApp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.opozeeApp.R;
import com.opozeeApp.adapters.SearchAdapter;
import com.opozeeApp.application.QuestionnaireApplication;
import com.opozeeApp.params.SearchParams;
import com.opozeeApp.pojo.SearchQuestionResponse;
import com.opozeeApp.search_mvp.model.SearchIntractorImpl;
import com.opozeeApp.search_mvp.presenter.SearchPresenterImpl;
import com.opozeeApp.search_mvp.view.SearchView;
import com.opozeeApp.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

import static android.view.View.GONE;

public class SearchFragment extends Fragment implements SearchView {

    @BindView(R.id.no_results_view)
    RelativeLayout mNoResultsView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.edit_search)
    EditText edit_search;
    private SearchPresenterImpl mPresenter;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isRefreshed = false;
    private List<SearchQuestionResponse.SearchQuestion> questionsList = new ArrayList<>();
    private SearchAdapter mAdapter;
    private boolean isClicked = false;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);

        //set Presenter to attach with view and model
        setPresenter();
        //set refresh listeners
        setRefreshListener();

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);

        setAdapter();

        return rootView;
    }

    private void setRefreshListener() {
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener(){
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                String editStr = edit_search.getText().toString();
                if(editStr != null && editStr.length() > 0) {
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
                String editStr = edit_search.getText().toString();
                if(editStr != null && editStr.length() > 0) {
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

    @OnEditorAction(R.id.edit_search)
    boolean onEditorAction()
    {
        hideNoResultsview();
        questionsList.clear();
        search();

        return false;
    }

    private void search() {
        String searchStr = edit_search.getText().toString();
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
                getSearch(searchStr);
            }

        }
        else
        {
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setEnableLoadmore(false);
        }
    }

    private void getSearch(String searchStr) {
        Map<String, String> map = new HashMap<>();
        map.put("SearchString" ,  searchStr);
        QuestionnaireApplication.getMixpanelApi().track("Search Event", new JSONObject(map));
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
                if (pageIndex == 1){
                    showNoResultsView();
                }
                isRefreshed = false;
                isLastPage = false;
                isClicked = false;
            }
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }

    public void showNoResultsView(){
        refreshLayout.setVisibility(GONE);
        mNoResultsView.setVisibility(View.VISIBLE);

    }

    public void hideNoResultsview(){
        refreshLayout.setVisibility(View.VISIBLE);
        mNoResultsView.setVisibility(View.GONE);

    }

}
