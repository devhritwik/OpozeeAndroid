package com.opozee.activities;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.opozee.OpozeeActivity;
import com.opozee.R;
import com.opozee.application.QuestionnaireApplication;
import com.opozee.fragments.FavouriteFragment;
import com.opozee.fragments.HomeFragment;
import com.opozee.fragments.NotificationsFragment;
import com.opozee.fragments.PostQuestionFragment;
import com.opozee.fragments.ProfileFragment;
import com.opozee.fragments.SearchFragment;
import com.opozee.params.ProfileParams;
import com.opozee.pojo.ProfileResponse;
import com.opozee.profile_mvp.model.ProfileInteractorImpl;
import com.opozee.profile_mvp.presenter.ProfilePresenter;
import com.opozee.profile_mvp.presenter.ProfilePresenterImpl;
import com.opozee.profile_mvp.view.ProfileView;
import com.opozee.utils.AppGlobal;
import com.opozee.utils.AppSP;
import com.opozee.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.opozee.fragments.ProfileFragment.PROFILE_FRAGMENG_ARGUEMENT_USER_ID;

public class HomeActivity extends OpozeeActivity implements ProfileView {


    private ProfilePresenter mProfilePresenter;
    private static final String TAG = "notificaton";
    private int currFrag = -1;
    @BindView(R.id.space)
    public SpaceNavigationView spaceNavView;
    private Fragment fr;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.toolbar)
    public Toolbar toolBar;
    @BindView(R.id.nested_scrollView)
    public NestedScrollView nested_scrollView;
    @BindView(R.id.btn_add_post)
    public FloatingActionButton btn_add_post;
    @BindView(R.id.app_bar_token_count)
    TextView mTokenCountView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        mProfilePresenter = new ProfilePresenterImpl();
        spaceNavView.setCentreButtonSelectable(true);
        mProfilePresenter.attachView(this, new ProfileInteractorImpl());
        mProfilePresenter.profile(getProfileParams());

        //add icons to the nav view
        addIconsToNavView(savedInstanceState);
        //set on clicklistener on space
        setOnclickListener();
        //setting the toolbar as actionbar
        setSupportActionBar(toolBar);

        int last_frag = getIntent().getIntExtra(AppGlobal.LAST_FRAG_TYPE, AppGlobal.HOMEFRAG);

        getLastFragment(last_frag);

Log.d(TAG,"id="+Utils.getLoggedInUserId(this));
        createNotificationChannel();
//        mTokenCountView.setText("30");



        handleData();
    }

    public void getLastFragment(int last_frag) {
        if (currFrag == last_frag) return;


        switch (last_frag)
        {
            case AppGlobal.HOMEFRAG:
                spaceNavView.setCentreButtonSelected();
                //customized method in the library to change the color of the item icons
//                spaceNavView.onCenterClickedorSelected(0);
//                spaceNavView.onCenterClickedorSelected(1);
//                spaceNavView.onCenterClickedorSelected(2);
//                spaceNavView.onCenterClickedorSelected(3);
                tv_title.setText(getString(R.string.title_home));
                btn_add_post.show();
                spaceNavView.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.INVISIBLE);
                //show fragment
                loadFragment(new HomeFragment());
                QuestionnaireApplication.getMixpanelApi().track("Question List Fragment on HomeActivity");
                break;

            case AppGlobal.PROFILEFRAG:
                tv_title.setText(getString(R.string.title_profile));
                btn_add_post.hide();
                spaceNavView.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.INVISIBLE);
                //show Fragment
                Bundle bundle  = new Bundle();
                bundle.putInt(PROFILE_FRAGMENG_ARGUEMENT_USER_ID , Integer.valueOf(Utils.getLoggedInUserId(this)));
                loadFragment(new ProfileFragment(), bundle);
                QuestionnaireApplication.getMixpanelApi().track("Profile Fragment on HomeActivity");

                break;

            case AppGlobal.NOTIFICATIONFRAG:
                tv_title.setText(getString(R.string.title_notifications));
                btn_add_post.hide();
                spaceNavView.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.INVISIBLE);
                //show fragment
                loadFragment(new NotificationsFragment());
                QuestionnaireApplication.getMixpanelApi().track("Notification Fragment on HomeActivity");

                break;

            case AppGlobal.SEARCHFRAG:
                tv_title.setText(getString(R.string.title_search));
                btn_add_post.hide();
                spaceNavView.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.INVISIBLE);
                //show Fragment
                loadFragment(new SearchFragment());
                QuestionnaireApplication.getMixpanelApi().track("Search Fragment on HomeActivity");

                break;

            case AppGlobal.FAVOURITEFRAG:
                tv_title.setText(getString(R.string.title_bookmarks));
                btn_add_post.hide();
                spaceNavView.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.INVISIBLE);
                //show Fragment
                loadFragment(new FavouriteFragment());
                QuestionnaireApplication.getMixpanelApi().track("Bookmark Fragment on HomeActivity");

                break;

            case AppGlobal.POSTQUESTFRAG:
                tv_title.setText(getString(R.string.title_post_questions));
                btn_add_post.hide();
                spaceNavView.setVisibility(View.GONE);
                iv_back.setVisibility(View.VISIBLE);
                //show Fragment
                loadFragment(new PostQuestionFragment());

                break;

            default:
                tv_title.setText(getString(R.string.title_home));
                btn_add_post.show();
                spaceNavView.setVisibility(View.VISIBLE);
                iv_back.setVisibility(View.INVISIBLE);
                //show fragment
                loadFragment(new HomeFragment());
                QuestionnaireApplication.getMixpanelApi().track("Question list Fragment on HomeActivity");

                break;

        }
        currFrag = last_frag;
    }

    private void handleData() {
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
// [END handle_data_extras]
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }
    }

    private void setOnclickListener() {
        spaceNavView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                getLastFragment(AppGlobal.HOMEFRAG);

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if(itemIndex + 1 == AppGlobal.PROFILEFRAG)
                {
                    getLastFragment(AppGlobal.PROFILEFRAG);
                }
                else if(itemIndex + 1 == AppGlobal.NOTIFICATIONFRAG)
                {
                    getLastFragment(AppGlobal.NOTIFICATIONFRAG);
                }
                else if(itemIndex + 1 == AppGlobal.SEARCHFRAG)
                {
                    getLastFragment(AppGlobal.SEARCHFRAG);
                }
                else if(itemIndex + 1 == AppGlobal.FAVOURITEFRAG)
                {
                    getLastFragment(AppGlobal.FAVOURITEFRAG);
                }


            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                if(itemIndex + 1 == AppGlobal.PROFILEFRAG)
                {
                    getLastFragment(AppGlobal.PROFILEFRAG);
                }
                else if(itemIndex + 1 == AppGlobal.NOTIFICATIONFRAG)
                {
                    getLastFragment(AppGlobal.NOTIFICATIONFRAG);
                }
                else if(itemIndex + 1 == AppGlobal.SEARCHFRAG)
                {
                    getLastFragment(AppGlobal.SEARCHFRAG);
                }
                else if(itemIndex + 1 == AppGlobal.FAVOURITEFRAG)
                {
                    getLastFragment(AppGlobal.FAVOURITEFRAG);
                }

            }
        });
    }

    private void addIconsToNavView(Bundle savedInstanceState) {
        spaceNavView.initWithSaveInstanceState(savedInstanceState);
        spaceNavView.showIconOnly();
        spaceNavView.setCentreButtonRippleColor(R.color.black_color);
        spaceNavView.addSpaceItem(new SpaceItem("", R.mipmap.user_icon));
        spaceNavView.addSpaceItem(new SpaceItem("", R.mipmap.message_icon));
        spaceNavView.addSpaceItem(new SpaceItem("", R.mipmap.search_icon));
        spaceNavView.addSpaceItem(new SpaceItem("", R.drawable.bookmark_icon_bottom_navigation));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavView.onSaveInstanceState(outState);
    }

    @OnClick(R.id.btn_add_post)
    public void fabOnClick()
    {
        QuestionnaireApplication.getMixpanelApi().track("Post Question Clicked from home activity");
        AppSP.getInstance(HomeActivity.this).savePreferences("last_frag", AppGlobal.HOMEFRAG);
        btn_add_post.hide();
        spaceNavView.setVisibility(View.GONE);
        getLastFragment(AppGlobal.POSTQUESTFRAG);
    }


    @SuppressLint("RestrictedApi")
    public void loadFragment(Fragment fr) {
        this.fr = fr;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.root_layout, fr, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QuestionnaireApplication.getMixpanelApi().flush();
    }

    public void loadFragment(Fragment fr, Bundle bundle) {
        this.fr = fr;
        fr.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.root_layout, fr, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {

        int last_frag = AppSP.getInstance(HomeActivity.this).readInt("last_frag");
        if(last_frag != -1)
        {
            AppSP.getInstance(HomeActivity.this).savePreferences("last_frag", -1);
            getLastFragment(last_frag);
        }
        else {

            if (fr instanceof PostQuestionFragment || fr instanceof NotificationsFragment || fr instanceof ProfileFragment
                    || fr instanceof SearchFragment || fr instanceof FavouriteFragment) {
                Log.e("onBackPressed", " ONBACK Pressed");
                getLastFragment(AppGlobal.HOMEFRAG);
            } else {
                exitAlert();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", " onResume");

        String from = getIntent().getStringExtra("from");
        if(from != null)
           if(from.equals("DetailActivity"))
        {
            getLastFragment(AppGlobal.HOMEFRAG);
        }

    }

    @OnClick(R.id.iv_back)
    public void onBackClick()
    {
        int last_frag = AppSP.getInstance(HomeActivity.this).readInt("last_frag");
        if(last_frag != -1)
        {
            AppSP.getInstance(HomeActivity.this).savePreferences("last_frag", -1);
            getLastFragment(last_frag);
        }
        else {

            if (fr instanceof PostQuestionFragment || fr instanceof NotificationsFragment || fr instanceof ProfileFragment
                    || fr instanceof SearchFragment || fr instanceof FavouriteFragment) {
                Log.e("onBackPressed", " ONBACK Pressed");
                getLastFragment(AppGlobal.HOMEFRAG);
            } else {
                exitAlert();
            }
        }
    }


    public void onLogoutClick()
    {
        logoutAlert();
    }


    private void exitAlert() {
        QuestionnaireApplication.getMixpanelApi().track("Alerting Exit from home fragment");
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        QuestionnaireApplication.getMixpanelApi().track("Alerting Exit from home fragment Yes clicked");
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        QuestionnaireApplication.getMixpanelApi().track("Alerting Exit from home fragment no clicked");
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    private void logoutAlert() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AppSP.getInstance(getApplicationContext()).clear();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private ProfileParams getProfileParams(){
        ProfileParams params = new ProfileParams();
        params.setType(AppGlobal.TYPE_GET_PROFILE);
        // params.setUser_id(Utils.getLoggedInUserId(getActivity()));
        params.setUser_id(Utils.getLoggedInUserId(this));
        return params;
    }
    @Override
    public void showProgress() {
        Utils.showProgress(this);
    }

    @Override
    public void hideProgress() {
        Utils.dismissProgress();

    }

    @Override
    public void onFirstNameError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLastNameError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccess(ProfileResponse response) {
        Log.d("CountHome_Log","Token="+response.getResponse().getUserProfile().getBalanceToken());
        mTokenCountView.setText(Integer.toString(response.getResponse().getUserProfile().getBalanceToken()));

    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
