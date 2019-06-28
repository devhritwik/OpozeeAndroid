package com.opozeeApp.bookmark_mvp.model;

import com.opozeeApp.params.BookMarkParams;
import com.opozeeApp.pojo.BookMarkResponse;

public interface BookMarkInteractor {
    void bookMark(BookMarkParams params, OnBookMarkFinishedListener mListener);

    interface OnBookMarkFinishedListener{
        void onSuccess(BookMarkResponse response);
        void onFailure(String error);
    }
}
