package com.opozeeApp.application;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;


import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.opozeeApp.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import io.fabric.sdk.android.Fabric;
import java.io.File;

import static com.opozeeApp.utils.Utils.MIXPANEL_TOKEN;

public class QuestionnaireApplication extends MultiDexApplication {

    private static QuestionnaireApplication currentApplication = null;
    private static MixpanelAPI mMixpanelApi = null;
    // endregion


    @Override
    public Context getApplicationContext() {
        return this;
    }

    // region Lifecycle Methods
    @Override
    public void onCreate() {
        currentApplication = this;
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        //finally initialize twitter with created configs
        Twitter.initialize(config);
        mMixpanelApi =
                MixpanelAPI.getInstance(this, MIXPANEL_TOKEN);

        Fabric.with(this, new Crashlytics());
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_primary, android.R.color.white);
                return new MaterialHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new BallPulseFooter(context) {
                };
            }
        });
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    // region Helper Methods
    public static QuestionnaireApplication getInstance() {
        return currentApplication;
    }

    public static File getCacheDirectory() {
        return currentApplication.getCacheDir();
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static MixpanelAPI getMixpanelApi() {
        return mMixpanelApi;
    }
}
