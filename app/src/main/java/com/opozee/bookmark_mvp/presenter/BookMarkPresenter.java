package com.opozee.bookmark_mvp.presenter;

import com.opozee.bookmark_mvp.model.BookMarkInteractorImpl;
import com.opozee.bookmark_mvp.view.BookMarkView;
import com.opozee.params.BookMarkParams;

public interface BookMarkPresenter {
    void attachView(BookMarkView bView, BookMarkInteractorImpl bInteractor);
    void dettachView();
    void bookMark(BookMarkParams params);
}
