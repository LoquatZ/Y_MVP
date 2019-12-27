package com.yuang.library.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding.view.RxView;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.just.agentweb.download.DownloadingService;
import com.yuang.library.AppManager;
import com.yuang.library.R;
import com.yuang.library.utils.Constants;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.TUtil;
import com.yuang.library.utils.TitleBuilder;
import com.yuang.library.widget.weblayout.WebLayout;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * Created by cenxiaozhong on 2017/5/26.
 *
 * source CODE  https://github.com/Justson/AgentWeb
 *
 * <p>
 */

public abstract class BaseWebActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {

    protected AgentWeb mAgentWeb;
    public T mPresenter;
    public E mModel;
    protected Context mContext;
    public ImmersionBar mImmersionBar;
    Unbinder binder;
    private DownloadingService mDownloadingService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(false, 0.2f)
                .init();   //所有子类都将继承这些相同的属性
        parseIntentData(getIntent(), false);//得到传递信息
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        initView();
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(webLayout(),new LinearLayout.LayoutParams(-1,-1))//
                .useDefaultIndicator()//
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .closeWebViewClientHelper()
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(initWebViewClient())
                .setWebLayout(new WebLayout(this))
                .setSecurityType(AgentWeb.SecurityType.DEFAULT_CHECK)
                .createAgentWeb()
                .ready()
                .go(getUrl());
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.attachVM(this, mModel);
        AppManager.getAppManager().addActivity(this);
        initData();
        setListeners();
    }

    protected void initView() {

    }

    protected abstract View setToolBar();

    public abstract void initData();

    public abstract int getLayoutId();

    /**
     * 设置Web布局
     * @return
     */
    public abstract LinearLayout webLayout();

    /**
     * 设置ToolBar Text
     * @return
     */
    public abstract TextView mTitleTextView();

    /**
     * 设置按钮监
     */
    protected void setListeners() {
        //empty implementation
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initToolbar(String title) {
        return new TitleBuilder(this)
                .setTitleText(title)
                .setLeftOnClickListener(v -> onBackPressed());
    }

    /**
     * 使用默认的throttle设置来注册点击事件。
     * @param view 要注册的View
     * @param action1 点击后执行的事件
     */
    protected void subscribeClick(View view, Action1<Void> action1){
        RxView.clicks(view)
                .throttleFirst(Constants.VIEW_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(action1);
    }


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

    public String getUrl(){
        return "https://www.baidu.com/";
    }


    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","onProgress:"+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView() != null)
                mTitleTextView().setText(title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntentData(intent, true);
    }


    /**
     * 得到传递信息
     * @param intent
     * @param isFromNewIntent
     */
    protected void parseIntentData(Intent intent, boolean isFromNewIntent) {
        //empty implementation
    }


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
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binder != null) binder.unbind();
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

    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;

            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            /**
             * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
             * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param downloadListener
             * @return WebListenerManager
             */
            @Override
            public WebListenerManager setDownloader(WebView webView, android.webkit.DownloadListener downloadListener) {
                return super.setDownloader(webView,
                        DefaultDownloadImpl
                                .create((Activity) webView.getContext(),
                                        webView,
                                        mDownloadListenerAdapter,
                                        mDownloadListenerAdapter,
                                        this.mAgentWeb.getPermissionInterceptor()));
            }
        };
    }

    /**
     * 更新于 AgentWeb  4.0.0
     */
    protected DownloadListenerAdapter mDownloadListenerAdapter = new DownloadListenerAdapter() {

        /**
         *
         * @param url                下载链接
         * @param userAgent          UserAgent
         * @param contentDisposition ContentDisposition
         * @param mimetype           资源的媒体类型
         * @param contentLength      文件长度
         * @param extra              下载配置 ， 用户可以通过 Extra 修改下载icon ， 关闭进度条 ， 是否强制下载。
         * @return true 表示用户处理了该下载事件 ， false 交给 AgentWeb 下载
         */
        @Override
        public boolean onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, AgentWebDownloader.Extra extra) {
            Logg.e("onStart:" + url);
            extra.setOpenBreakPointDownload(true) // 是否开启断点续传
                    .setIcon(R.drawable.ic_file_download_black_24dp) //下载通知的icon
                    .setConnectTimeOut(6000) // 连接最大时长
                    .setBlockMaxTime(10 * 60 * 1000)  // 以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常
                    .setDownloadTimeOut(Long.MAX_VALUE) // 下载最大时长
                    .setParallelDownload(false)  // 串行下载更节省资源哦
                    .setEnableIndicator(true)  // false 关闭进度通知
                    .addHeader("Cookie", "xx") // 自定义请求头
                    .setAutoOpen(true) // 下载完成自动打开
                    .setForceDownload(true); // 强制下载，不管网络网络类型
            return false;
        }

        /**
         *
         * 不需要暂停或者停止下载该方法可以不必实现
         * @param url
         * @param downloadingService  用户可以通过 DownloadingService#shutdownNow 终止下载
         */
        @Override
        public void onBindService(String url, DownloadingService downloadingService) {
            super.onBindService(url, downloadingService);
            mDownloadingService = downloadingService;
            Logg.i("onBindService:" + url + "  DownloadingService:" + downloadingService);
        }

        /**
         * 回调onUnbindService方法，让用户释放掉 DownloadingService。
         * @param url
         * @param downloadingService
         */
        @Override
        public void onUnbindService(String url, DownloadingService downloadingService) {
            super.onUnbindService(url, downloadingService);
            mDownloadingService = null;
            Logg.i("onUnbindService:" + url);
        }

        /**
         *
         * @param url  下载链接
         * @param loaded  已经下载的长度
         * @param length    文件的总大小
         * @param usedTime   耗时 ，单位ms
         * 注意该方法回调在子线程 ，线程名 AsyncTask #XX 或者 AgentWeb # XX
         */
        @Override
        public void onProgress(String url, long loaded, long length, long usedTime) {
            int mProgress = (int) ((loaded) / Float.valueOf(length) * 100);
            Logg.i("onProgress:" + mProgress);
            super.onProgress(url, loaded, length, usedTime);
        }

        /**
         *
         * @param path 文件的绝对路径
         * @param url  下载地址
         * @param throwable    如果异常，返回给用户异常
         * @return true 表示用户处理了下载完成后续的事件 ，false 默认交给AgentWeb 处理
         */
        @Override
        public boolean onResult(String path, String url, Throwable throwable) {
            if (null == throwable) { //下载成功
                //do you work
            } else {//下载失败

            }
            return false; // true  不会发出下载完成的通知 , 或者打开文件
        }
    };
}
