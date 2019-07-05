package com.opozeeApp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;

import com.opozeeApp.OpozeeActivity;
import com.opozeeApp.R;
//import com.opozee.fragments.ProfileFragment;
import com.opozeeApp.fragments.Profile_New_Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends OpozeeActivity {

    private Profile_New_Fragment profileFragment;

    @BindView(R.id.iv_back)
    ImageView mBackButton;
    public final String TAG = "ProfileActivity_LOG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_activity_layout);
        ButterKnife.bind(this);

        Log.d(TAG, "Profile_Activity_Call");

        this.profileFragment = new Profile_New_Fragment();
        Bundle bundle = getIntent().getExtras();
        profileFragment.setArguments(bundle);

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.root_layout, fr, null);
//        fragmentTransaction.commit();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.profile_activity_fragment_container, profileFragment, null);
        fragmentTransaction.commit();
    }


    @OnClick(R.id.iv_back)
    public void onBackButtonClicked() {
        onBackPressed();
    }


}
