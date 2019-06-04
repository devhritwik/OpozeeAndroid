package com.opozee.search_mvp.presenter;

import com.opozee.params.SearchParams;
import com.opozee.pojo.SearchQuestionResponse;
import com.opozee.search_mvp.model.SearchInteractor;
import com.opozee.search_mvp.model.SearchIntractorImpl;
import com.opozee.search_mvp.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter, SearchInteractor.OnSearchFinishedListener {
    private SearchView sView;
    private SearchIntractorImpl sIntractor;

    @Override
    public void onSuccess(SearchQuestionResponse response) {
        sView.hideProgress();
        sView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        sView.hideProgress();
        sView.onFailure(error);
    }

    @Override
    public void attachView(SearchView sView, SearchIntractorImpl sIntractor) {

        this.sView = sView;
        this.sIntractor = sIntractor;
    }

    @Override
    public void dettachView() {
        this.sView = null;
        this.sIntractor = null;
    }

    @Override
    public void search(SearchParams params) {
        sIntractor.search(params, this);
    }
}
