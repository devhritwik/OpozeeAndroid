package com.opozeeApp.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.opozeeApp.R;
import com.opozeeApp.utils.Utils;

public class RefferealCodeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_code, et_link;
    ImageView iv_back, iv_copy, iv_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reffereal_code);
        et_code = findViewById(R.id.et_referralcode);
        et_link = findViewById(R.id.et_referrallink);
        iv_back = findViewById(R.id.iv_back);
        iv_copy = findViewById(R.id.iv_copy);
        iv_share = findViewById(R.id.iv_share);

        String referralcode= Utils.getreferralcode(RefferealCodeActivity.this);
        if(referralcode!=null){
            if(referralcode.trim().length()>0){
                et_code.setText(referralcode);
                et_link.setText("http://opozee.com/invite/"+referralcode);
            }
        }
        iv_back.setOnClickListener(this);
        iv_copy.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copy", et_code.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Copy to Clipboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_share:
                Intent sendIntent = new Intent();
                String url = "https://play.google.com/store/apps/details?id=com.opozeeApp&hl=en";
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey , Have you tried this awesome app? To get started use my referral link "+et_link.getText().toString()+" Download the app at " + url);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
    }


}
