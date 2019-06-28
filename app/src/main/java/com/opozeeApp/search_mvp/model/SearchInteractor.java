package com.opozeeApp.search_mvp.model;

import com.opozeeApp.params.SearchParams;
import com.opozeeApp.pojo.SearchQuestionResponse;

public interface SearchInteractor {
    void search(SearchParams params, OnSearchFinishedListener mListener);

    interface OnSearchFinishedListener{
        void onSuccess(SearchQuestionResponse response);
        void onFailure(String error);
    }
}
