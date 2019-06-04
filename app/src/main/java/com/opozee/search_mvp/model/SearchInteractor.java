package com.opozee.search_mvp.model;

import com.opozee.params.SearchParams;
import com.opozee.pojo.SearchQuestionResponse;

public interface SearchInteractor {
    void search(SearchParams params, OnSearchFinishedListener mListener);

    interface OnSearchFinishedListener{
        void onSuccess(SearchQuestionResponse response);
        void onFailure(String error);
    }
}
