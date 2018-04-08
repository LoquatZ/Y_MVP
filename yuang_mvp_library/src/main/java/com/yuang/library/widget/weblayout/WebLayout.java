package com.yuang.library.widget.weblayout;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.agentweb.IWebLayout;
import com.yuang.library.R;


public class WebLayout implements IWebLayout {

    private Activity mActivity;
    private final LinearLayout mTwinklingRefreshLayout;
    private WebView mWebView = null;

    public WebLayout(Activity activity) {
        this.mActivity = activity;
        mTwinklingRefreshLayout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.fragment_twk_web, null);
        mWebView = (WebView) mTwinklingRefreshLayout.findViewById(R.id.webView);
    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mTwinklingRefreshLayout;
    }

    @Nullable
    @Override
    public WebView getWeb() {
        return mWebView;
    }



}
