package com.opozeeApp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opozeeApp.R;
import com.opozeeApp.activities.HomeActivity;
import com.opozeeApp.adapters.HomeQuestionsAdapter;
import com.opozeeApp.params.PostedQuestionsParams;
import com.opozeeApp.pojo.PostedQuestionsResponse;
import com.opozeeApp.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
import com.opozeeApp.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
import com.opozeeApp.posted_questions_mvp.view.PostedQuestionsView;
import com.opozeeApp.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements PostedQuestionsView {

    //    @BindView(R.id.recyclerView)
    public static RecyclerView recyclerView;
    //    @BindView(R.id.refreshLayout)
    public static SmartRefreshLayout refreshLayout;
    private static PostedQuestionsPresenterImpl mPresenter;

    private static int pageIndex = 1;
    private static int pageSize = 10;
    private static List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private static HomeQuestionsAdapter mAdapter;
    private static LinearLayoutManager mLayoutManager;
    private static boolean isLoading = false;
    private static boolean isLastPage = false;
    private static boolean isRefreshed = false;
    public static boolean loaddata = false;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
//        ButterKnife.bind(this, rootView);
        Log.e("onCreateView", "onCreateView");

        //set scroll listener for fab visibility
        setScrollListener();
//        recyclerView.clearOnScrollListeners();
        setAdapter();
        //set refresh listeners
        setRefreshListener();
        setPresenter();
        pageIndex = 1;
        questionsList.clear();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
        //get all the questions
        getQuestions();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loaddata = true;
        Log.e("onResume", "onResume");
    }

    private void setRefreshListener() {

        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
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
                if (mAdapter != null)
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
        if (HomeNewFragment.tabName.trim().length() > 0) {
            params.setSearchtext(HomeNewFragment.tabName);
        }
        if (Utils.isNetworkAvail(getActivity())) {
            mPresenter.getQuestions(params);
        } else {
            Utils.showCustomToast(getActivity(), getString(R.string.internet_alert));
        }
    }

    //setPresenter to attach the view to the model
    void setPresenter() {
        mPresenter = new PostedQuestionsPresenterImpl();
        mPresenter.attachView(this, new PostedQuestionsInteractorImpl());
    }

    private void setScrollListener() {

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        ((HomeActivity) getActivity()).btn_add_post.show();
                        break;
                    default:
                        ((HomeActivity) getActivity()).btn_add_post.hide();
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
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog == null)
                Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if (!isRefreshed && !isLastPage)
            try {
                if (Utils.mProgressDialog != null)
                    Utils.dismissProgress();
            }catch (Exception e){

            }
    }


    @Override
    public void onSuccess(PostedQuestionsResponse response) {
        Log.d("HomeQuestionLog=", "" + response.getResponse());
        Log.d("HomeQuestionLog=", "" + response);
        if (response.getResponse().getAllUserQuestions().getPostQuestionDetail() != null)
            if (response.getResponse().getAllUserQuestions().getPostQuestionDetail().size() > 0) {
                Log.d("HomeQuestionLog=", response.getResponse().getAllUserQuestions().getPostQuestionDetail().toString());
                questionsList.addAll(response.getResponse().getAllUserQuestions().getPostQuestionDetail());
                setAdapter();
//                mAdapter.notifyDataSetChanged();
                isRefreshed = false;
                isLastPage = false;
            }
    }


    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }

//    public static void tabseleted() {
//        LoadTabData loadTabData = new HomeFragment();
//        loadTabData.LoadData();
//    }
//
//    @Override
//    public void LoadData() {
//        questionsList.clear();
//        pageIndex = 1;
//        isLoading = false;
//        isLastPage = false;
//        isRefreshed = false;
//        loaddata = false;
//        getQuestions();
//    }
}


//package com.opozee.fragments;
//
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.opozee.LoadTabData;
//import com.opozee.OnSwipeTouchListener;
//import com.opozee.R;
//import com.opozee.WebRequest;
//import com.opozee.activities.HomeActivity;
//import com.opozee.adapters.GetTagsAdapter;
//import com.opozee.adapters.HomeQuestionsAdapter;
//import com.opozee.model.GetTagsModel;
//import com.opozee.params.PostedQuestionsParams;
//import com.opozee.pojo.PostedQuestionsResponse;
//import com.opozee.pojo.getallhashtags.GetAllTags;
//import com.opozee.posted_questions_mvp.model.PostedQuestionsInteractorImpl;
//import com.opozee.posted_questions_mvp.presenter.PostedQuestionsPresenterImpl;
//import com.opozee.posted_questions_mvp.view.PostedQuestionsView;
//import com.opozee.utils.Utils;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class HomeFragment extends Fragment implements PostedQuestionsView {
//
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
//    private PostedQuestionsPresenterImpl mPresenter;
//
//    private int pageIndex = 1;
//    private int pageSize = 10;
//    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
//    private HomeQuestionsAdapter mAdapter;
//    private LinearLayoutManager mLayoutManager;
//    private boolean isLoading = false;
//    private boolean isLastPage = false;
//    private boolean isRefreshed = false;
//    public static WebRequest webRequest;
//    WebRequest.APIInterface apiInterface;
//    retrofit2.Call<GetAllTags> getAllTagsCall;
//    GetAllTags getAllTags;
//    public RecyclerView rv_tags;
//    public GetTagsAdapter getTagsAdapter;
//    public ArrayList<GetTagsModel> getTagsModelist = new ArrayList<>();
//    public TabLayout tab_layout;
//
//    public HomeFragment() {
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        tab_layout = rootView.findViewById(R.id.tab_layout);
//        ButterKnife.bind(this, rootView);
//        webRequest = WebRequest.getSingleton(getActivity());
////        rv_tags=rootView.findViewById(R.id.rv_tags);
////        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
////        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
////        rv_tags.setLayoutManager(linearLayoutManager);
////        rv_tags.setItemAnimator(new DefaultItemAnimator());
//
//
////        getalltags();
//        Log.e("onCreateView", "onCreateView");
//
//        //set scroll listener for fab visibility
//        setScrollListener();
////        recyclerView.clearOnScrollListeners();
//        setAdapter();
//        //set refresh listeners
//        setRefreshListener();
//        setPresenter();
//        pageIndex = 1;
//        questionsList.clear();
//        if (mAdapter != null)
//            mAdapter.notifyDataSetChanged();
//        //get all the questions
//
//
////        ArrayList<GetTagsModel> titleTabs=new ArrayList<>();
////         titleTabs = getAllTags();
////         tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
////             @Override
////             public void onTabSelected(TabLayout.Tab tab) {
////                 Log.d("Data=","tab="+tab.getText());
////             }
////
////             @Override
////             public void onTabUnselected(TabLayout.Tab tab) {
////
////             }
////
////             @Override
////             public void onTabReselected(TabLayout.Tab tab) {
////
////             }
////         });
//
//
////        for (String module : titleTabs) {
////            tab_layout.addTab(tab_layout.newTab().setText(module));
////        }
//
//
//        return rootView;
//    }
//
//
////    private ArrayList<GetTagsModel> getAllTags() {
////        getAllTagsCall = webRequest.apiInterface.getalltags();
////        getAllTagsCall.enqueue(new Callback<GetAllTags>() {
////            @Override
////            public void onResponse(Call<GetAllTags> call, Response<GetAllTags> response) {
////                if (response != null) {
////                    GetAllTags getAllTags = response.body();
////                    int code = Integer.parseInt(getAllTags.getSuccess());
////                    switch (code) {
////                        case 200:
////                            for (int i = 0; i < getAllTags.getData().size(); i++) {
////                                GetTagsModel getTagsModel = new GetTagsModel();
////                                getTagsModel.setHashtag(getAllTags.getData().get(i).getHashTag());
////                                getTagsModel.setCount(getAllTags.getData().get(i).getCount());
////                                getTagsModelist.add(getTagsModel);
////
////                            }
////                            for(int i=0;i<getTagsModelist.size();i++){
////                                tab_layout.addTab(tab_layout.newTab().setText(getTagsModelist.get(i).getHashtag()));
////                            }
////                            tab_layout.post(new Runnable() {
////                                @Override
////                                public void run() {
////                                    // don't forget to add Tab first before measuring..
////                                    DisplayMetrics displayMetrics = new DisplayMetrics();
////                                    ((Activity) getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
////                                    int widthS = displayMetrics.widthPixels;
////                                    tab_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
////                                    int widthT = tab_layout.getMeasuredWidth();
////
////                                    if (widthS > widthT) {
////                                        tab_layout.setTabMode(TabLayout.MODE_FIXED);
////                                        tab_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
////                                                LinearLayout.LayoutParams.WRAP_CONTENT));
////                                    }
////                                }
////                            });
//////                            getTagsAdapter = new GetTagsAdapter(getActivity(), getTagsModelist);
//////                            rv_tags.setAdapter(getTagsAdapter);
////                            break;
////                        default:
////                            break;
////                    }
////                }
////            }
////
////            @Override
////            public void onFailure(Call<GetAllTags> call, Throwable t) {
////
////            }
////        });
////        return getTagsModelist;
////    }
//
//
////    public ArrayList<GetTagsModel> getalltags() {
////
////        return getTagsModelist;
////    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("onResumeTAG", "onResume");
//    }
//
//    private void setRefreshListener() {
//
//        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                isLastPage = true;
//                ++pageIndex;
//                getQuestions();
//                refreshlayout.finishLoadmore(2000/*,false*/);
//            }
//
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                isRefreshed = true;
//                pageIndex = 1;
//                questionsList.clear();
//                if (mAdapter != null)
//                    mAdapter.notifyDataSetChanged();
//                getQuestions();
//                refreshlayout.finishRefresh(2000/*,false*/);
//            }
//        });
//    }
//
//
//
//
//    private void getQuestions() {
//        questionsList.clear();
//        PostedQuestionsParams params = new PostedQuestionsParams();
//        params.setPageIndex(pageIndex);
//        params.setPageSize(pageSize);
//        params.setUser_id("0");
//        params.setSearchtext(HomeNewFragment.tabName);
//        mPresenter.getQuestions(params);
//    }
//
//    //setPresenter to attach the view to the model
//    void setPresenter() {
//        mPresenter = new PostedQuestionsPresenterImpl();
//        mPresenter.attachView(this, new PostedQuestionsInteractorImpl());
//    }
//
//    private void setScrollListener() {
//
//        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                switch (newState) {
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        ((HomeActivity) getActivity()).btn_add_post.show();
//                        break;
//                    default:
//                        ((HomeActivity) getActivity()).btn_add_post.hide();
//                        break;
//                }
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//        };
//
//
//        recyclerView.clearOnScrollListeners();
//        recyclerView.addOnScrollListener(scrollListener);
//
//    }
//
//
//    public void setAdapter() {
//        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mAdapter = new HomeQuestionsAdapter(getActivity(), questionsList);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(mAdapter);
//    }
//
//
//    @Override
//    public void showProgress() {
//        if (!isRefreshed && !isLastPage)
//            if (Utils.mProgressDialog == null)
//                Utils.showProgress(getActivity());
//    }
//
//    @Override
//    public void hideProgress() {
//        if (!isRefreshed && !isLastPage)
//            if (Utils.mProgressDialog != null)
//                Utils.dismissProgress();
//    }
//
//
//    @Override
//    public void onSuccess(PostedQuestionsResponse response) {
//        Log.d("HomeQuestionLog=", "" + response.getResponse());
//        Log.d("HomeQuestionLog=", "" + response);
//        if (response.getResponse().getAllUserQuestions().getPostQuestionDetail() != null)
//            if (response.getResponse().getAllUserQuestions().getPostQuestionDetail().size() > 0) {
//                Log.d("HomeQuestionLog=", response.getResponse().getAllUserQuestions().getPostQuestionDetail().toString());
//                questionsList.addAll(response.getResponse().getAllUserQuestions().getPostQuestionDetail());
//                mAdapter.notifyDataSetChanged();
//                isRefreshed = false;
//                isLastPage = false;
//            }
//    }
//
//
//    @Override
//    public void onFailure(String error) {
//        Utils.showCustomToast(getActivity(), error);
//    }
//}
//
