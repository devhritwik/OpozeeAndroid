package com.opozee.profile_mvp.model;

import android.util.Log;

import com.opozee.params.ProfileParams;
import com.opozee.pojo.ProfileResponse;
import com.opozee.retrofit_api.ServiceGenerator;
import com.opozee.retrofit_api.WebService;
import com.opozee.retrofit_api.WebServiceFactory;
import com.opozee.utils.AppGlobal;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileInteractorImpl implements ProfileInteractor {
    @Override
    public void profile(ProfileParams params, OnProfileFinishListener mListener) {
        if(params.getType() == AppGlobal.TYPE_GET_PROFILE)
        {
            getProfile(params, mListener);
        }
        else if(params.getType() == AppGlobal.TYPE_PROFILE_UPDATE){
            if(params.getFirstName() == null || params.getFirstName().length() == 0)
            {
                mListener.onFirstNameError("Please enter your firstname");
            }
            else if(params.getLastName() == null || params.getLastName().length() == 0)
            {
                mListener.onLastNameError("Please enter your lastname");
            }
            else
            {
                updateProfile(params, mListener);
            }

        }
    }

    private void updateProfile(ProfileParams params, final OnProfileFinishListener mListener) {

        Call<ProfileResponse> req;
        RequestBody firstNameBody = RequestBody.create(MediaType.parse("text/plain"), params.getFirstName());
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), params.getUser_id());
        RequestBody lastNameBody = RequestBody.create(MediaType.parse("text/plain"), params.getLastName());
        WebService webService = (WebService) ServiceGenerator.createService(WebService.class);
        if (params.getProfilePic() != null) {
            req = webService.editProfile(MultipartBody.Part.createFormData(AppGlobal.KEY_USER_PROFILE_PIC, params.getProfilePic().getName(), RequestBody.create(MediaType.parse("image/*"), params.getProfilePic())), userIdBody, firstNameBody, lastNameBody);
        } else {
            req = webService.editProfile(null, userIdBody, firstNameBody, lastNameBody);
        }
        req.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.d("Profile_Log",response.toString());
                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        ProfileResponse LoginSignupResponse = response.body();
                        if (LoginSignupResponse != null) {

                            response.body().getResponse().setType(AppGlobal.TYPE_PROFILE_UPDATE);
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
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("Response=",t.toString());
                mListener.onFailure("Request Failed, Please try again 3");
            }
        });

    }

    private void getProfile(ProfileParams params, final OnProfileFinishListener mListener) {
        Call<ProfileResponse> call = WebServiceFactory.getInstance().getUserProfile(params.getUser_id());

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        ProfileResponse LoginSignupResponse = response.body();
                        if (LoginSignupResponse != null) {
                            response.body().getResponse().setType(AppGlobal.TYPE_GET_PROFILE);

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
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("RequestError=","4="+t.toString());
                mListener.onFailure("Request Failed, Please try again 4");
            }
        });

    }
}
