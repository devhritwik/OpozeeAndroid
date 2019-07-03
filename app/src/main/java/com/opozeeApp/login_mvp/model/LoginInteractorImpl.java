package com.opozeeApp.login_mvp.model;

import android.util.Log;

import com.opozeeApp.application.QuestionnaireApplication;
import com.opozeeApp.params.LoginParams;
import com.opozeeApp.pojo.LoginResponse;
import com.opozeeApp.retrofit_api.WebServiceFactory;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(final LoginParams params, final OnLoginFinishListener mListener) {
        Log.d("LoginActivity_LOG", "Params=" + params);
        Log.d("LoginActivity_LOG", "Params=" + params.getDeviceToken());
        Log.d("LoginActivity_LOG", "Params=" + params.getDeviceType());
        Log.d("LoginActivity_LOG", "Params=" + params.getThirdPartyId());
        Log.d("LoginActivity_LOG", "Params=" + params.getThirdPartyType());
        Log.d("LoginActivity_LOG", "Params=" + params.getUserName());
        Call<LoginResponse> call = WebServiceFactory.getInstance().loginUser(params);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Response=", response.body().toString());
                if (response.isSuccessful()) {
                    if (response.body().getResponse().getCode() == 0) {
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
                mListener.onFailure("Request Failed, Please try again ");
            }
        });

    }

    //    @Override
    public void loginemail(String data, final OnLoginFinishListener mListener) {
//        Log.d("Response=","Json="+data);
//        Call<List<LoginEmail>> call = WebServiceFactory.getInstance().loginemailuser("application/json", data);
//  call.enqueue(new Callback<List<LoginEmail>>() {
//      @Override
//      public void onResponse(Call<List<LoginEmail>> call, Response<List<LoginEmail>> response) {
//          Log.d("Response=",response.body().toString());
//      }
//
//      @Override
//      public void onFailure(Call<List<LoginEmail>> call, Throwable t) {
//Log.d("Response=",t.toString());
//      }
//  });
    }


    public void setMixpanelSuperProperties(LoginResponse response) {
        JSONObject jsonObject = new JSONObject();
        LoginResponse.UserData userData = response.getResponse().getUserData();

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

        } catch (JSONException e) {
            // do nothing for now
        }

        QuestionnaireApplication.getMixpanelApi().registerSuperProperties(jsonObject);
        QuestionnaireApplication.getMixpanelApi().identify(userData.getUserName());
        QuestionnaireApplication.getMixpanelApi().getPeople().identify(userData.getUserName());
        QuestionnaireApplication.getMixpanelApi().alias(userData.getUserName(), null);
        QuestionnaireApplication.getMixpanelApi().flush();

    }


}
