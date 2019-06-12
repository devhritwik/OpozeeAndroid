package com.opozee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class SendCrashEmail extends AppCompatActivity {
    String crashReason = "undefined error type";
    String snackbarMessage = "";
    private ConstraintLayout clParent;
    boolean isEmailSend = false;
    ProgressDialog progressDialog;
    WebRequest webRequest;
    WebRequest.APIInterface apiInterface;
//    Call<EmailCrash> emailCrashCall;
//    EmailCrash emailCrash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_crash);
        webRequest = WebRequest.getSingleton(SendCrashEmail.this);
        clParent = (ConstraintLayout) findViewById(R.id.cl_parent);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            crashReason = extras.getString("error");
        }
        SignInRequest(crashReason);
    }

    private void SignInRequest(String reason) {
        if (InternetConnection.isNetworkConnected(SendCrashEmail.this)) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Sending issue report to developers...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

//        JsonCreator jsonCreator=new JsonCreator(SendCrashEmail.this);
//        String json=jsonCreator.errorreport(reason);
//    Log.d("ErrorSign",json);
//        emailCrashCall = webRequest.apiInterface.emailcrash(json, Globalvariables.anonymousToken, Globalvariables.CONTENT_TYPE);
//        getSignupResponse(emailCrashCall);
//        } else {
//            snackbarMessage = "No internet connection";
//            Snackbar(clParent);
//        }
        }

//    private void getSignupResponse(Call<EmailCrash> profileCall) {
//        emailCrashCall.enqueue(new Callback<EmailCrash>() {
//            @Override
//            public void onResponse(Call<EmailCrash> call, Response<EmailCrash> response) {
//                emailCrash = response.body();
//                if (emailCrash != null) {
//                    if (emailCrash.getResp().equals("0")) {
//                        String msg = "";
//                        msg = emailCrash.getMessage();
//                        isEmailSend = true;
//                        Toast.makeText(SendCrashEmail.this, "" + msg, Toast.LENGTH_SHORT).show();
//                        onBackPressed();
//                    } else {
//                        snackbarMessage = "Failed to send email";
//                        Snackbar(clParent);
//                    }
//
//                } else {
//                    onBackPressed();
//                }
//                if (progressDialog.isShowing()) progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<EmailCrash> call, Throwable t) {
//
//                if (t instanceof SocketTimeoutException) {
//                    snackbarMessage = "Connection timeout";
//                    Snackbar(clParent);
//                }
//                if (progressDialog.isShowing()) progressDialog.dismiss();
//            }
//        });
//    }
//
//
////        profile.enqueue(new Callback<CrashEmailResponse>() {
////            @Override
////            public void onResponse(Call<CrashEmailResponse> call, retrofit2.Response<CrashEmailResponse> response) {
////                CrashEmailResponse crashResponse = response.body();
////                if (crashResponse != null) {
////                    if (crashResponse.getResponse() == 1) {
////                        String msg = "";
////                        msg = crashResponse.getData().getMessage();
////                        isEmailSend = true;
////                        Toast.makeText(SendCrashEmail.this, "" + msg, Toast.LENGTH_SHORT).show();
////                        onBackPressed();
////                    } else {
////                        snackbarMessage = "Failed to send email";
////                        Snackbar(clParent);
////                    }
////                } else {
////                    onBackPressed();
////                }
////                if (progressDialog.isShowing()) progressDialog.dismiss();
////            }
////
////            @Override
////            public void onFailure(Call<CrashEmailResponse> call, Throwable t) {
////                if (t instanceof SocketTimeoutException) {
////                    snackbarMessage = "Connection timeout";
////                    Snackbar(clParent);
////                }
////                if (progressDialog.isShowing()) progressDialog.dismiss();
////            }
////        });
////    }
//
//    public void Snackbar(final View layout) {
//        Snackbar mySnackbar = Snackbar.make(layout, snackbarMessage, Snackbar.LENGTH_INDEFINITE);
//        View snackBarView = mySnackbar.getView();
//        snackBarView.setBackgroundColor(Color.RED);
//        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(Color.WHITE);
//        mySnackbar.setActionTextColor(Color.YELLOW);
//        mySnackbar.setAction("Retry", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (InternetConnection.isNetworkConnected(SendCrashEmail.this)) {
//                    SignInRequest(crashReason);
//                } else {
//                    Snackbar(layout);
//                }
//            }
//        });
//        mySnackbar.show();
//    }
//
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setMessage("Want to exit app or continue ?");
//        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                onQuitPressed();
//            }
//        });
//
//        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                boolean is_available = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
////                if (is_available) {
//                Intent intent = new Intent(SendCrashEmail.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
////                } else {
////                    AlertDialog.Builder builder = new AlertDialog.Builder(SendCrashEmail.this);
////                    builder.setCancelable(false);
////                    builder.setMessage("Location enabled required ");
////                    builder.setPositiveButton("Turn on", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialogInterface, int i) {
////                            Intent openLocationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
////                            startActivity(openLocationSettings);
////                        }
////                    });
////                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialogInterface, int i) {
////                            Intent intent = new Intent(Intent.ACTION_MAIN);
////                            intent.addCategory(Intent.CATEGORY_HOME);
////                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                            startActivity(intent);
////                            onQuitPressed();
////                        }
////                    });
////                    builder.show();
////                }
//            }
//        });
//        builder.show();
//    }
//
//    public void onQuitPressed() {
//        int pid = android.os.Process.myPid();
//        android.os.Process.killProcess(pid);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (isEmailSend == true) {
//            onBackPressed();
//        }
//    }

    }
}