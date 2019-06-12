package com.opozee.activities;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.opozee.OpozeeActivity;
import com.opozee.R;
import com.opozee.adapters.UserPostsAdapter;
import com.opozee.delete_post_mvp.model.DeleteInteractorImpl;
import com.opozee.delete_post_mvp.presenter.DeletePresenterImpl;
import com.opozee.delete_post_mvp.view.DeleteView;
import com.opozee.fragments.EditPostFragment;
import com.opozee.fragments.PostQuestionFragment;
import com.opozee.params.DeletePostParams;
import com.opozee.params.PostedQuestionsParams;
import com.opozee.pojo.DeletePostResponse;
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
import butterknife.OnClick;

public class UserQuestionPostsActivity extends OpozeeActivity implements PostedQuestionsView, DeleteView {

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.tv_title)
    public TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.root_layout)
    ScrollView root_layout;
    @BindView(R.id.btn_add_post)
    FloatingActionButton btn_add_post;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private PostedQuestionsPresenterImpl mPresenter;
    private DeletePresenterImpl mDeletePresenter;


    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isLastPage = false;
    private boolean isRefreshed = false;
    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private UserPostsAdapter mAdapter;

    private Fragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_question_posts);
        ButterKnife.bind(this);

//set scroll listener for fab visibility
        setScrollListener();

        setPresenter();

        //get all the questions
        getQuestions();
        //set refresh listeners
        setRefreshListener();

        setAdapter();

    }

    private void setScrollListener() {

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        btn_add_post.show();
                        break;
                    default:
                        btn_add_post.hide();
                        break;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

        };


        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(scrollListener);

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
        params.setUser_id(Utils.getLoggedInUserId(UserQuestionPostsActivity.this));
        mPresenter.getQuestions(params);
    }


    @OnClick(R.id.iv_back)
    void onBackClick() {
        if (fr == null) {
            finish();
        } else if (fr instanceof EditPostFragment || fr instanceof PostQuestionFragment) {
            fr = null;

            refreshLayout.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            root_layout.setVisibility(View.GONE);
            tv_title.setText("USER POSTS");
        }

    }

    @Override
    public void onBackPressed() {
        if (fr == null) {
            finish();
        } else if (fr instanceof EditPostFragment || fr instanceof PostQuestionFragment) {
            fr = null;

            refreshLayout.setVisibility(View.VISIBLE);
            iv_back.setVisibility(View.VISIBLE);
            root_layout.setVisibility(View.GONE);
            tv_title.setText("USER POSTS");
        } else {
            super.onBackPressed();
        }

    }

    //setPresenter to attach the view to the model
    void setPresenter() {
        mPresenter = new PostedQuestionsPresenterImpl();
        mPresenter.attachView(this, new PostedQuestionsInteractorImpl());

        //set Presenter for delete post
        mDeletePresenter = new DeletePresenterImpl();
        mDeletePresenter.attachView(this, new DeleteInteractorImpl());

    }


    @SuppressLint("RestrictedApi")
    public void loadFragment(Fragment fr, String backStack) {
        this.fr = fr;

        if (fr instanceof EditPostFragment) {
            refreshLayout.setVisibility(View.GONE);
            iv_back.setVisibility(View.VISIBLE);
            root_layout.setVisibility(View.VISIBLE);
            tv_title.setText("EDIT POST");
        } else if (fr instanceof PostQuestionFragment) {
            refreshLayout.setVisibility(View.GONE);
            iv_back.setVisibility(View.VISIBLE);
            root_layout.setVisibility(View.VISIBLE);
            tv_title.setText("POST QUESTION");
        }


        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.root_layout, fr, backStack);

        fragmentTransaction.addToBackStack(backStack);

        fragmentTransaction.commit();

    }


    public void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserQuestionPostsActivity.this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new UserPostsAdapter(UserQuestionPostsActivity.this, questionsList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog == null)
                Utils.showProgress(UserQuestionPostsActivity.this);
    }

    @Override
    public void hideProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog != null)
                Utils.dismissProgress();
    }


    @Override
    public void onSuccess(PostedQuestionsResponse response) {
        if (response.getResponse().getAllUserQuestions().getPostQuestionDetail() != null)
            if (response.getResponse().getAllUserQuestions().getPostQuestionDetail().size() > 0) {
                questionsList.addAll(response.getResponse().getAllUserQuestions().getPostQuestionDetail());

                mAdapter.notifyDataSetChanged();
                isRefreshed = false;
                isLastPage = false;
            }
    }

    @Override
    public void onSuccess(DeletePostResponse response) {
        Utils.showCustomToast(UserQuestionPostsActivity.this, "Post deleted successfully");
        getQuestions();
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(UserQuestionPostsActivity.this, error);
    }

    public void deletePost(Integer id) {
        DeletePostParams params = new DeletePostParams();
        params.setQuestId(id);
        mDeletePresenter.deletePost(params);
    }

    @OnClick(R.id.btn_add_post)
    public void fabOnClick() {
        btn_add_post.hide();
        loadFragment(new PostQuestionFragment(), "");
    }


}


