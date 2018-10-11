package com.yuang.yuangapplication.webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuang.library.base.BaseWebActivity;

public class WebViewActivity extends BaseWebActivity {

    @Override
    protected View setToolBar() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public LinearLayout webLayout() {
        return null;
    }

    @Override
    public TextView mTitleTextView() {
        return null;
    }

    @Override
    public String getUrl() {
        return "https://www.baidu.com/";
    }

    //设置WebViewClient
    @Override
    protected WebViewClient initWebViewClient() {
        return new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @SuppressLint("NewApi")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(view, request.getUrl() + "");
            }


            //
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                if (url.startsWith("intent://") && url.contains("com.youku.phone"))
                    return true;
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }
        };
    }
}
