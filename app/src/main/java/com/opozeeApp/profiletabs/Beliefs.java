package com.opozeeApp.profiletabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.opozeeApp.DeleteQuestions;
import com.opozeeApp.R;
import com.opozeeApp.WebRequest;
import com.opozeeApp.adapters.UserBeliefAdapter;
import com.opozeeApp.delete_post_mvp.model.DeleteInteractorImpl;
import com.opozeeApp.delete_post_mvp.presenter.DeletePresenterImpl;
import com.opozeeApp.delete_post_mvp.view.DeleteView;
import com.opozeeApp.models.Belief;
import com.opozeeApp.params.DeletePostParams;
import com.opozeeApp.pojo.DeletePostResponse;
import com.opozeeApp.pojo.PostedQuestionsResponse;
import com.opozeeApp.pojo.deletebelief.DeleteBelief;
import com.opozeeApp.user_belief_mvp.model.UserBeliefInteractorImpl;
import com.opozeeApp.user_belief_mvp.presenter.UserBeliefPresenter;
import com.opozeeApp.user_belief_mvp.presenter.UserBeliefPresenterImpl;
import com.opozeeApp.user_belief_mvp.view.UserBeliefView;
import com.opozeeApp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Beliefs extends Fragment implements UserBeliefView, DeleteQuestions {
    private static final String TAG = "Beliefs_Log";
    private int pageIndex = 1;
    private int pageSize = 1000;
    private int mUserId = Integer.valueOf(Utils.idprofileget(getContext()));

    private List<PostedQuestionsResponse.PostQuestionDetail> questionsList = new ArrayList<>();
    private boolean isRefreshed = false;
    private boolean isLastPage = false;
    private List<Belief> mBeliefList = new ArrayList<>();
    private RecyclerView rv_beliefs;
    private static UserBeliefPresenter mUserBeliefPresenter;
    private static UserBeliefAdapter mBeliefsAdapter;
    public static Context context;
    public static WebRequest webRequest;
    public static DeleteBelief deleteBelief;
    public static retrofit2.Call<DeleteBelief> deleteBeliefCall;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_beliefs, null);
        rv_beliefs = view.findViewById(R.id.rv_beliefs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_beliefs.setLayoutManager(linearLayoutManager);
        context = getActivity();
        webRequest = WebRequest.getSingleton(context);
        setPresenters();
        getBeliefs();
        return view;

    }

    private void getBeliefs() {
        if (Utils.isNetworkAvail(context)) {
            mUserBeliefPresenter.getUserBeliefs(mUserId);
        } else {
            Toast.makeText(context, "Please connect to internet first", Toast.LENGTH_SHORT).show();

        }

    }

    private void setPresenters() {
        mUserBeliefPresenter = new UserBeliefPresenterImpl();
        mUserBeliefPresenter.attachView(this, new UserBeliefInteractorImpl());


    }


    @Override
    public void showProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog == null)
                Utils.showProgress(getActivity());
    }

    @Override
    public void hideProgress() {
        if (!isRefreshed && !isLastPage)
            if (Utils.mProgressDialog != null)
                Utils.dismissProgress();
    }


    @Override
    public void onSuccess(List<Belief> beliefList) {
        Log.d(TAG, "onSuccess Belief list");
        mBeliefList.clear();
        mBeliefList.addAll(beliefList);
//        mBeliefCountView.setText(String.format("Beliefs Posted : %d", mBeliefList.size()));
//        mBeliefsAdapter.notifyDataSetChanged();
        mBeliefsAdapter = new UserBeliefAdapter(getContext(), mBeliefList);
        rv_beliefs.setAdapter(mBeliefsAdapter);
        isRefreshed = false;
        isLastPage = false;
    }

    public static void deleterequest(int id) {
        DeleteQuestions deleteQuestions = new Beliefs();
        deleteQuestions.deletequestions(id);
    }

    @Override
    public void onFailure(String error) {
        Utils.showCustomToast(getActivity(), error);
    }

    @Override
    public void deletequestions(int id) {
        final ACProgressFlower  progressDialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);
        alert.setMessage("Are you sure you want to delete ?");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               if(progressDialog!=null){
                   progressDialog.show();
               }
                String json = getjsondata(id, Utils.getLoggedInUserId(context));
                deleteBeliefCall = webRequest.apiInterface.deletebelief("application/json", json);
                deleteBeliefCall.enqueue(new Callback<DeleteBelief>() {
                    @Override
                    public void onResponse(Call<DeleteBelief> call, Response<DeleteBelief> response) {
                        if (response != null) {
                            deleteBelief = response.body();
                            int code = Integer.parseInt(deleteBelief.getResponse().getCode());
                            switch (code) {
                                case 0:
                                    try {
                                        if (progressDialog != null) {
                                            if (progressDialog.isShowing()) {
                                                progressDialog.dismiss();

                                            }
                                        }
                                    } catch (Exception e) {

                                    }
                                    getBeliefs();
                                    break;
                                    default:
                                        try {
                                            if (progressDialog != null) {
                                                if (progressDialog.isShowing()) {
                                                    progressDialog.dismiss();

                                                }
                                            }
                                        } catch (Exception e) {

                                        }
                                        break;

                            }
                        } else {
                            try {
                                if (progressDialog != null) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();

                                    }
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteBelief> call, Throwable t) {

                        try {
                            if (progressDialog != null) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();

                                }
                            }
                        } catch (Exception e) {

                        }

                    }
                });

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();

    }

    private String getjsondata(int id, String loggedInUserId) {
        JSONObject obj = new JSONObject();
        String userdata = "";
        try {

            obj.put("Id", id);
            obj.put("OwnerUserID", loggedInUserId);


//            obj.put("",)


            userdata = obj.toString();
            Log.d("Jason", userdata);

        } catch (JSONException e1) {
            Log.e("ERROR :", "" + e1);
            e1.printStackTrace();

        }
        return userdata;
    }

}
