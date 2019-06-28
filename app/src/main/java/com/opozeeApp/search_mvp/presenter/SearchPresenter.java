package com.opozeeApp.search_mvp.presenter;

import com.opozeeApp.params.SearchParams;
import com.opozeeApp.search_mvp.model.SearchIntractorImpl;
import com.opozeeApp.search_mvp.view.SearchView;

public interface SearchPresenter {
    void attachView(SearchView sView, SearchIntractorImpl sIntractor);
    void dettachView();
    void search(SearchParams params);
}
