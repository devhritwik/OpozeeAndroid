package com.opozee.searchfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opozee.LoadTabData;
import com.opozee.R;
import com.opozee.activities.HomeActivity;
import com.opozee.adapters.HomeQuestionsAdapter;
import com.opozee.fragments.HomeFragment;
import com.opozee.fragments.HomeNewFragment;
import com.opozee.params.PostedQuestionsParams;
import com.opozee.pojo.PostedQuestionsResponse;
import com.opozee.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozee.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
import com.opozee.posted_questions_mvp.view.PostedQuestionsView;
import com.opozee.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QaSearchFragment extends Fragment implements PostedQuestionsView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private PostedQuestionsPresenterImpl mPresenter;

    private int pageIndex = 1;
    private int pageSize = 10;
    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private HomeQuestionsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isRefreshed = false;


    public QaSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        Log.d("TESTFRAGMENT", "onCreateView4");
        //set scroll listener for fab visibility
        setScrollListener();
//        recyclerView.clearOnScrollListeners();
        setAdapter();
        //set refresh listeners
        setRefreshListener();
        setPresenter();
        pageIndex = 1;
        questionsList.clear();
        if(mAdapter != null)
            mAdapter.notifyDataSetChanged();
        //get all the questions
        getQuestions();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
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
        PostedQuestionsParams params = new PostedQuestionsParams();
        params.setPageIndex(pageIndex);
        params.setPageSize(pageSize);
        params.setUser_id("0");
        params.setSortorder(Integer.parseInt(Utils.getsortedorder(getActivity())));
        if (HomeNewFragment.getTagsModelist.size() >= 4) {
            params.setSearchtext(HomeNewFragment.getTagsModelist.get(3).getHashtag());
        }else{
            params.setSearchtext("All");
        }
        mPresenter.getQuestions(params);
    }

    //setPresenter to attach the view to the model
    void setPresenter()
    {
        mPresenter = new PostedQuestionsPresenterImpl();
        mPresenter.attachView(this, new PostedQuestionsInteractorImpl());
    }

    private void setScrollListener() {

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        ((HomeActivity)getActivity()).btn_add_post.show();
                        ((HomeActivity)getActivity()).btn_sort.setVisibility(View.VISIBLE);
                        break;
                    default:
                        ((HomeActivity)getActivity()).btn_add_post.hide();
                        ((HomeActivity)getActivity()).btn_sort.setVisibility(View.INVISIBLE);
                        break;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

        };


        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(scrollListener);

    }


    public void setAdapter() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new HomeQuestionsAdapter(getActivity(), questionsList);
        recyclerView.setLayoutManager(mLayoutManager);
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
    public void onSuccess(PostedQuestionsResponse response) {
        Log.d("HomeQuestionLog=",""+response.getResponse());
        Log.d("HomeQuestionLog=",""+response);
        if(response.getResponse().getAllUserQuestions().getPostQuestionDetail() != null)
            if(response.getResponse().getAllUserQuestions().getPostQuestionDetail().size() > 0) {
                Log.d("HomeQuestionLog=",response.getResponse().getAllUserQuestions().getPostQuestionDetail().toString());
                questionsList.addAll(response.getResponse().getAllUserQuestions().getPostQuestionDetail());
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
