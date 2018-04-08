package com.yuang.library.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettings;
import com.just.agentweb.ChromeClientCallbackManager;
import com.just.agentweb.DownLoadResultListener;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebDefaultSettingsManager;
import com.yuang.library.AppManager;
import com.yuang.library.R;
import com.yuang.library.utils.DownloadUtil;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.TUtil;
import com.yuang.library.widget.weblayout.WebLayout;

import java.io.File;
import java.util.Random;

/**
 * Created by cenxiaozhong on 2017/5/26.
 *
 * source CODE  https://github.com/Justson/AgentWeb
 *
 * <p>
 */

public abstract class BaseWebActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {

    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private AlertDialog mAlertDialog;
    public T mPresenter;
    public E mModel;
    protected Context mContext;
    public Toolbar mToolbar;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initUI();
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout,new LinearLayout.LayoutParams(-1,-1) )//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .closeWebViewClientHelper()
                .setAgentWebSettings(getSettings())
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(initWebViewClient())
                .setWebLayout(new WebLayout(this))
                .setSecurityType(AgentWeb.SecurityType.default_check)
                .addDownLoadResultListener(mDownLoadResultListener) //下载回调
                .openParallelDownload()//打开并行下载 , 默认串行下载
                .createAgentWeb()//
                .ready()
                .go(getUrl());
        mAgentWeb.getWebCreator().get().setDownloadListener((s, s1, s2, s3, l) ->
                DownloadUtil.getInstance(BaseWebActivity.this).download(s,mTitleTextView.getText().toString(),new Random().nextInt(100),false,true));
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.attachVM(this, mModel);
        AppManager.getAppManager().addActivity(this);
        initData();
    }

    private void initUI() {
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setToolbar();
    }

    public abstract void initData();

    public abstract void setToolbar();

    protected WebViewClient initWebViewClient(){
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

    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    private WebChromeClient mWebChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","progress:"+newProgress);
        }
        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
            Log.d("main", "onJsAlert:" + message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(BaseWebActivity.this)
                            .setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    webview.reload();//重写刷新页面

                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();

                }
            });
            result.confirm();//这里必须调用，否则页面会阻塞造成假死
            return true;
        }
    };
    public String getUrl(){
        return "https://www.baidu.com/";
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mTitleTextView != null)
                mTitleTextView.setText(title);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Info", "result:" + requestCode + " result:" + resultCode);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }
    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {
        //AgentWeb 在触发某些敏感的 Action 时候会回调该方法， 比如定位触发 。
        //例如 https//:www.baidu.com 该 Url 需要定位权限， 返回false ，如果版本大于等于23 ， agentWeb 会动态申请权限 ，true 该Url对应页面请求定位失败。
        //该方法是每次都会优先触发的 ， 开发者可以做一些敏感权限拦截 。
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            Logg.i("url:" + url + "  permission:" + permissions + " action:" + action);
            return false;
        }
    };

    protected DownLoadResultListener mDownLoadResultListener = new DownLoadResultListener() {
        @Override
        public void success(String path) {
            //do you work
            Logg.e("DownLoadPath:"+path);
            promptInstall(Uri.fromFile(new File(path)));

        }

        @Override
        public void error(String path, String resUrl, String cause, Throwable e) {
            //do you work
            Logg.e("DownLoadError！");
        }
    };

    private void promptInstall(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
