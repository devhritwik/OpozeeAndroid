package com.opozeeApp.profile_mvp.model;

import android.util.Log;

import com.opozeeApp.params.ProfileParams;
import com.opozeeApp.pojo.ProfileResponse;
import com.opozeeApp.retrofit_api.ServiceGenerator;
import com.opozeeApp.retrofit_api.WebService;
import com.opozeeApp.retrofit_api.WebServiceFactory;
import com.opozeeApp.utils.AppGlobal;

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
//            if(params.getFirstName() == null || params.getFirstName().length() == 0)
//            {
//                mListener.onFirstNameError("Please enter your firstname");
//            }
//            else if(params.getLastName() == null || params.getLastName().length() == 0)
//            {
//                mListener.onLastNameError("Please enter your lastname");
//            }
//            else
//            {
                updateProfile(params, mListener);
//            }

        }
    }

    private void updateProfile(ProfileParams params, final OnProfileFinishListener mListener) {

        Call<ProfileResponse> req;
        RequestBody firstNameBody = RequestBody.create(MediaType.parse("text/plain"), params.getFirstName());
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), params.getUser_id());
        RequestBody lastNameBody = RequestBody.create(MediaType.parse("text/plain"), params.getLastName());
        RequestBody UserName = RequestBody.create(MediaType.parse("text/plain"), params.getUserName());
        WebService webService = ServiceGenerator.createService(WebService.class);
        if (params.getProfilePic() != null) {

            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), params.getProfilePic());
            MultipartBody.Part part = MultipartBody.Part.createFormData("upload", params.getProfilePic().getName(), fileReqBody);
//            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
//         Log.d("ImageBitmap=",fileReqBody.toString());
//         Log.d("ImageBitmap=",part.toString());
//         Log.d("ImageBitmap=",""+params.getProfilePic());
//         Log.d("ImageBitmap=",""+params.getProfilePic().getName());
//         Log.d("ImageBitmap=",""+params.getProfilePic());
//            req = webService.editProfile(MultipartBody.Part.createFormData(AppGlobal.KEY_USER_PROFILE_PIC, params.getProfilePic().getName(), RequestBody.create(MediaType.parse("image/*"), params.getProfilePic())), userIdBody, firstNameBody, lastNameBody);
            req = webService.editProfile(part, userIdBody, firstNameBody, lastNameBody,UserName);
        } else {
            req = webService.editProfile(null, userIdBody, firstNameBody, lastNameBody,UserName);
        }
        req.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
//                Log.d("Profile_Log",""+response.body().toString());
//                Log.d("Profile_Log",""+response);
//                Log.d("Profile_Log",""+response.body().getResponse().getCode());
//                Log.d("Profile_Log",""+response.body().getResponse().getStatus());
//                Log.d("Profile_Log",""+response.body().getResponse().getStatus());
//                Log.d("Profile_Log",""+response);
//                Log.d("Profile_Log",""+response.body().getResponse());
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
//                Log.d("Response=",t.toString());
                mListener.onFailure("Request Failed, Please try again ");
            }
        });

    }

    private void getProfile(ProfileParams params, final OnProfileFinishListener mListener) {
        Call<ProfileResponse> call = WebServiceFactory.getInstance().getUserProfile(params.getUser_id(),params.getViewuserid());

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {

                        ProfileResponse LoginSignupResponse = response.body();
//                        Log.d("Userprofile",""+LoginSignupResponse);
//                        Log.d("Userprofile",""+response.body());
//                        Log.d("Userprofile","hasfollowed="+LoginSignupResponse.getResponse().getUserProfile().getHasfollowed());
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
//                Log.d("RequestError=","4="+t.toString());
                mListener.onFailure("Request Failed, Please try again ");
            }
        });

    }
}
