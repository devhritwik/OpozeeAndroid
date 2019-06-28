package com.opozeeApp.search_mvp.view;

import com.opozeeApp.pojo.SearchQuestionResponse;

public interface SearchView {
    void showProgress();
    void hideProgress();
    void onSuccess(SearchQuestionResponse response);
    void onFailure(String error);
}
