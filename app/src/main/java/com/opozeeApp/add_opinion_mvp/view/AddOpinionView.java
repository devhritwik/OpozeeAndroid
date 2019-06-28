package com.opozeeApp.add_opinion_mvp.view;

import com.opozeeApp.pojo.OpinionResponse;

public interface AddOpinionView {
    void showProgress();
    void hideProgress();
    void onSuccess(OpinionResponse response);
    void onFailure(String error);
}
