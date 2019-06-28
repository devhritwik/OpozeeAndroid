package com.opozeeApp.bookmark_mvp.presenter;

import com.opozeeApp.bookmark_mvp.model.BookMarkInteractorImpl;
import com.opozeeApp.bookmark_mvp.view.BookMarkView;
import com.opozeeApp.params.BookMarkParams;

public interface BookMarkPresenter {
    void attachView(BookMarkView bView, BookMarkInteractorImpl bInteractor);
    void dettachView();
    void bookMark(BookMarkParams params);
}
