package com.opozee.tag_users_mvp.model;

import com.opozee.params.TagUserParams;
import com.opozee.pojo.TagUsersResponse;
import com.opozee.retrofit_api.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagUsersInteractorImpl implements TagUsersInteractor{

    @Override
    public void getUsers(TagUserParams params, final OnTagUsersFinishedListener mListener) {
        if(params.getType() == TagUserParams.Type.ADD_TAG_USERS)
        {
            getAllUsers(params, mListener);
        }
        else if(params.getType() == TagUserParams.Type.TAGGED_USERS)
        {
            getAllTaggedUsers(params, mListener);
        }
    }

    private void getAllTaggedUsers(TagUserParams params, final OnTagUsersFinishedListener mListener) {
        Call<TagUsersResponse> call = WebServiceFactory.getInstance().getAllTaggedUsers(params.getQuestId());

        call.enqueue(new Callback<TagUsersResponse>() {
            @Override
            public void onResponse(Call<TagUsersResponse> call, Response<TagUsersResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        TagUsersResponse LoginSignupResponse = response.body();
                        if (LoginSignupResponse != null) {

//                            Utils.saveDataInSharePreferences(LoginSignupResponse, null);
                            mListener.onSuccess(response.body());
                        }
                    } else {
                        mListener.onFailure(response.body().getResponse().getStatus());

                    }
                } else {
                    mListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<TagUsersResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again");
            }
        });
    }

    private void getAllUsers(TagUserParams params, final OnTagUsersFinishedListener mListener) {
        Call<TagUsersResponse> call = WebServiceFactory.getInstance().getAllUsers(params.getPageIndex(), params.getPageSize());

        call.enqueue(new Callback<TagUsersResponse>() {
            @Override
            public void onResponse(Call<TagUsersResponse> call, Response<TagUsersResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        TagUsersResponse LoginSignupResponse = response.body();
                        if (LoginSignupResponse != null) {

//                            Utils.saveDataInSharePreferences(LoginSignupResponse, null);
                            mListener.onSuccess(response.body());
                        }
                    } else {
                        mListener.onFailure(response.body().getResponse().getStatus());

                    }
                } else {
                    mListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<TagUsersResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again");
            }
        });
    }
}
