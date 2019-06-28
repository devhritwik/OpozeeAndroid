package com.opozeeApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpozeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }
}
