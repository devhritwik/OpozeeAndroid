package com.opozeeApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.opozeeApp.activities.HomeActivity;
import com.opozeeApp.utils.Utils;

import io.fabric.sdk.android.Fabric;

public class OpozeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        logUser();
    }


    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("Unknown");
        Crashlytics.setUserEmail("unknown@gmail.com");
        Crashlytics.setUserName("Unknown");
    }
}
