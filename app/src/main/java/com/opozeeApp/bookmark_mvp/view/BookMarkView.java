package com.opozeeApp.bookmark_mvp.view;

import com.opozeeApp.pojo.BookMarkResponse;

public interface BookMarkView {
    void showProgress();
    void hideProgress();
    void onSuccess(BookMarkResponse response);
    void onFailure(String error);
}
