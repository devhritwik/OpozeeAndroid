package com.opozeeApp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.opozeeApp.OpozeeActivity;
import com.opozeeApp.R;

public class RegisterUserActivity extends OpozeeActivity implements View.OnClickListener {
TextView tv_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        tv_cancel=findViewById(R.id.tv_canel_register);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_canel_register:
                finish();
                break;
        }
    }
}
