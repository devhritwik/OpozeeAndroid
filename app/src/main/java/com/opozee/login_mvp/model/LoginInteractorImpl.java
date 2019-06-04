package com.opozee.login_mvp.model;

import com.opozee.application.QuestionnaireApplication;
import com.opozee.params.LoginParams;
import com.opozee.pojo.LoginResponse;
import com.opozee.retrofit_api.WebServiceFactory;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(final LoginParams params, final OnLoginFinishListener mListener) {


        Call<LoginResponse> call = WebServiceFactory.getInstance().loginUser(params);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0 ) {
                        LoginResponse LoginSignupResponse = response.body();
                        setMixpanelSuperProperties(LoginSignupResponse);
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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mListener.onFailure("Request Failed, Please try again");
            }
        });

    }

    public void setMixpanelSuperProperties( LoginResponse response){
        JSONObject jsonObject = new JSONObject();
        LoginResponse.UserData userData =  response.getResponse().getUserData();

        try {
            if (userData.getUserID() != null)
                jsonObject.putOpt("UserId", userData.getUserID());
            if (userData.getSocialType() != null)
                jsonObject.put("ThirdPartyType", userData.getSocialType());
            if (userData.getSocialID() != null)
                jsonObject.put("SocialId", userData.getSocialID());
            if (userData.getFirstName() != null)
                jsonObject.put("Name", userData.getFirstName() + userData.getLastName());
            if (userData.getEmail() != null)
                jsonObject.put("Email", userData.getEmail());
            if (userData.getUserName() != null)
                jsonObject.put("Username", userData.getUserName());
            if (userData.getDeviceToken() != null)
                jsonObject.put("DeviceToken", userData.getDeviceToken());
            if (userData.getDeviceType() != null)
                jsonObject.put("DeviceType", userData.getDeviceType());

        } catch (JSONException e){
            // do nothing for now
        }

        QuestionnaireApplication.getMixpanelApi().registerSuperProperties(jsonObject);
        QuestionnaireApplication.getMixpanelApi().identify(userData.getUserName());
        QuestionnaireApplication.getMixpanelApi().getPeople().identify(userData.getUserName());
        QuestionnaireApplication.getMixpanelApi().alias(userData.getUserName(),null  );
        QuestionnaireApplication.getMixpanelApi().flush();

    }


}
