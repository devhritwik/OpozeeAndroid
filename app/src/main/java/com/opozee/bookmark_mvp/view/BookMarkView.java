package com.opozee.bookmark_mvp.view;

import com.opozee.pojo.BookMarkResponse;

public interface BookMarkView {
    void showProgress();
    void hideProgress();
    void onSuccess(BookMarkResponse response);
    void onFailure(String error);
}
