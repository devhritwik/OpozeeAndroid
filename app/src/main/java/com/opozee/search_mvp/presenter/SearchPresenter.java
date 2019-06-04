package com.opozee.search_mvp.presenter;

import com.opozee.params.SearchParams;
import com.opozee.search_mvp.model.SearchIntractorImpl;
import com.opozee.search_mvp.view.SearchView;

public interface SearchPresenter {
    void attachView(SearchView sView, SearchIntractorImpl sIntractor);
    void dettachView();
    void search(SearchParams params);
}
