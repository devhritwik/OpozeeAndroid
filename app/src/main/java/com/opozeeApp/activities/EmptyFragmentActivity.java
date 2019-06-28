package com.opozeeApp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.opozeeApp.OpozeeActivity;
import com.opozeeApp.R;
import com.opozeeApp.fragments.TagSeachFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 *      Empty Activity with Appbar and framelayout so you can simply replace with a Fragment
 *      and set the title with bundle arguments
 */

public class EmptyFragmentActivity extends OpozeeActivity {

    public static final String EMPTY_FRAGMENT_ACTIVITY_TITLE_ARGUMENT = "EmptyActivityTitleArgument";
    private TagSeachFragment mSearchFragment;
    private String mTitle;

    @BindView(R.id.empty_fragment_activity_title_view)
    TextView mTitleView;


    @BindView(R.id.iv_back)
    ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.empty_fragment_activity_layout);
        ButterKnife.bind(this);
        Bundle bundle  = getIntent().getExtras();

        mTitle = bundle.getString(EMPTY_FRAGMENT_ACTIVITY_TITLE_ARGUMENT, "");
        mTitleView.setText(mTitle);
        mSearchFragment = new TagSeachFragment();
        mSearchFragment.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.profile_activity_fragment_container, mSearchFragment);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.iv_back)
    public void onBackButtonClicked(){
        onBackPressed();
    }


}
