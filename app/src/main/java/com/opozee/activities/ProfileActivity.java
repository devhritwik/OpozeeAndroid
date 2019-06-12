package com.opozee.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.opozee.OpozeeActivity;
import com.opozee.R;
import com.opozee.fragments.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends OpozeeActivity {

    private ProfileFragment profileFragment;

    @BindView(R.id.iv_back)
    ImageView mBackButton ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_activity_layout);
        ButterKnife.bind(this);



        this.profileFragment = new ProfileFragment();
        Bundle bundle  = getIntent().getExtras();
        profileFragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.profile_activity_fragment_container, profileFragment);
        fragmentTransaction.commit();
    }


    @OnClick(R.id.iv_back)
    public void onBackButtonClicked(){
        onBackPressed();
    }



}
