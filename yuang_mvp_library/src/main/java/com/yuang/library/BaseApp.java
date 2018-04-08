package com.yuang.library;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.yuang.library.utils.DeviceUtil;


public abstract class BaseApp extends Application {
    private static BaseApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        DeviceUtil.init(this);
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
