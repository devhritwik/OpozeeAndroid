package com.opozee.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.opozee.R;
import com.opozee.utils.AppGlobal;
import com.opozee.utils.AppSP;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName() ;
    @BindView(R.id.main_relative)
    RelativeLayout main_relative;
    private AppSP appSP;

    @BindView(R.id.iv_logo)
    ImageView mLogo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStatusBarHeight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mLogo.setImageDrawable(getResources().getDrawable(R.drawable.opozee_logo));
        checkDeviceID();
        showSplash();
//        main_relative.post(new Runnable() {
//            @Override
//            public void run() {
//                circularReveal();
//            }
//        });
    }

    private void checkDeviceID() {
        appSP = AppSP.getInstance(SplashActivity.this);

        String deviceId = appSP.readString(AppGlobal.REGISTRATION_ID, "");
        Log.d(TAG , " " + deviceId);

        if(deviceId.equals("")) {
            getDeviceToken();
        }
    }

    private void getDeviceToken() {
        Log.d(TAG, "GetDeviceToken()");


            // Get token
            // [START retrieve_current_token]
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            Log.d(TAG, "onComplete()");
                            if (!task.isSuccessful()) {
                                Log.d(TAG, "Task not succesful");

                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }
                            Log.d(TAG, task.getResult().getToken());
                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            appSP.savePreferences(AppGlobal.REGISTRATION_ID, token);

                            // Log and toast
                            String msg = getString(R.string.msg_token_fmt, token);
                            Log.d(TAG, msg);
                            Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
            // [END retrieve_current_token]
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void showSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 500);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circularReveal()
    {
        int x = main_relative.getRight()/2;
        int y = main_relative.getBottom()/2;

        int startRadius = 0;
        int endRadius = (int) Math.hypot(main_relative.getWidth(), main_relative.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(main_relative, x, y, startRadius, endRadius);
        anim.setDuration(500);
//        main_relative.setVisibility(View.VISIBLE);


        main_relative.clearAnimation();

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showSplash();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        anim.start();
    }
}
