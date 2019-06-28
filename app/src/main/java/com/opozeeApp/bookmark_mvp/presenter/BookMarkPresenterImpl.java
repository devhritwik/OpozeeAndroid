package com.opozeeApp.bookmark_mvp.presenter;

import com.opozeeApp.bookmark_mvp.model.BookMarkInteractor;
import com.opozeeApp.bookmark_mvp.model.BookMarkInteractorImpl;
import com.opozeeApp.bookmark_mvp.view.BookMarkView;
import com.opozeeApp.params.BookMarkParams;
import com.opozeeApp.pojo.BookMarkResponse;

public class BookMarkPresenterImpl implements BookMarkPresenter, BookMarkInteractor.OnBookMarkFinishedListener {


    private BookMarkView bView;
    private BookMarkInteractorImpl bInteractor;

    @Override
    public void onSuccess(BookMarkResponse response) {
        bView.onSuccess(response);
    }

    @Override
    public void onFailure(String error) {
        bView.onFailure(error);
    }

    @Override
    public void attachView(BookMarkView bView, BookMarkInteractorImpl bInteractor) {

        this.bView = bView;
        this.bInteractor = bInteractor;
    }

    @Override
    public void dettachView() {
        this.bView = null;
        this.bInteractor = null;
    }

    @Override
    public void bookMark(BookMarkParams params) {
        bInteractor.bookMark(params, this);
    }
}
