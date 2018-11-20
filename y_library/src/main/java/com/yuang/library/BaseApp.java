package com.yuang.library;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.yuang.library.utils.DeviceUtil;

import timber.log.Timber;


public abstract class BaseApp extends Application {
    private static BaseApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        DeviceUtil.init(getAppContext());
        if (BuildConfig.LOGG_OPEN) {//Timber初始化
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static synchronized BaseApp getInstance() {
        return mApp;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

    public abstract String setBaseUrl();
}
