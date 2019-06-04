package com.opozee.bookmark_mvp.model;

import com.opozee.params.BookMarkParams;
import com.opozee.pojo.BookMarkResponse;

public interface BookMarkInteractor {
    void bookMark(BookMarkParams params, OnBookMarkFinishedListener mListener);

    interface OnBookMarkFinishedListener{
        void onSuccess(BookMarkResponse response);
        void onFailure(String error);
    }
}
