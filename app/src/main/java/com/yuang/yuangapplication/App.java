package com.yuang.yuangapplication;

import com.squareup.leakcanary.LeakCanary;
import com.yuang.library.BaseApp;

public class App extends BaseApp {


    @Override
    public String setBaseUrl() {
        return "http://gank.io/";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
