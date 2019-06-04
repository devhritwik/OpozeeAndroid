package com.opozee.search_mvp.view;

import com.opozee.pojo.SearchQuestionResponse;

public interface SearchView {
    void showProgress();
    void hideProgress();
    void onSuccess(SearchQuestionResponse response);
    void onFailure(String error);
}
