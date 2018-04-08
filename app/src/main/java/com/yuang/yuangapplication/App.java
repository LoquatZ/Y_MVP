package com.yuang.yuangapplication;

import com.yuang.library.BaseApp;

public class App extends BaseApp {


    @Override
    public String setBaseUrl() {
        return "http://news-at.zhihu.com/api/4/";
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
