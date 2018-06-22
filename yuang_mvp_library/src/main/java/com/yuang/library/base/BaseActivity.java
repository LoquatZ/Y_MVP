package com.yuang.library.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.umeng.analytics.MobclickAgent;
import com.yuang.library.AppManager;
import com.yuang.library.R;
import com.yuang.library.utils.Constants;
import com.yuang.library.utils.Loading;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.TUtil;
import com.yuang.library.utils.TitleBuilder;
import com.yuang.library.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.functions.Action1;

/**
 * Created by Yuang on 17/12/8.
 * Summary:Activity基类
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends SupportActivity implements BaseUiInterface {

    protected String TAG;
    public T mPresenter;
    public E mModel;
    protected Context mContext;
    Unbinder binder;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntentData(getIntent(), false);//得到传递信息
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) mPresenter.attachVM(this, mModel);
        this.initView(savedInstanceState);
        setListeners();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (binder != null) binder.unbind();
        if (mPresenter != null) mPresenter.detachVM();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 重新加载
     */
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
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

    /**
     * 设置按钮监
     */
    protected void setListeners() {
        //empty implementation
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onBackPressedSupport() {
        supportFinishAfterTransition();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    protected void setToolBar(Toolbar toolbar, String title,boolean navigation) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (navigation) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(view -> onBackPressedSupport());
        }
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitle(String title) {
        return new TitleBuilder(this)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_arrow_back_white_24dp)
                .setLeftOnClickListener(v -> finish());
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

    /**
     * 注册点击事件，不允许throttle。
     * @param view 要注册的View
     * @param action1 点击后执行的事件
     */
    protected void subscribeClickWithoutThrottle(View view, Action1<Void> action1){
        RxView.clicks(view)
                .subscribe(action1);
    }


    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    /**
     * 快速跳转
     * @param tarActivity
     */
    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    /**
     * 快速打印Toast
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 快速打印Log
     * @param msg
     */
    public void showLog(String msg) {
        Logg.i(msg);
    }

    /**
     * 加载中
     * @param msg
     * @param b
     */
    public void showLoading(String msg,boolean b) {
        if (b) {
            Loading.startLoading(this, msg);
        } else {
            Loading.stopLoading();
        }
    }

    @Override
    public void showDataException(String msg) {
        showToast(msg);
    }

    @Override
    public void showNetworkException() {
        showToast("网络异常，请稍后重试");
    }

    @Override
    public void showUnknownException() {
        showToast("未知错误，请稍后重试");
    }

    @Override
    public void showLoadingComplete() {
        //Empty implementation
    }

    @Override
    public Dialog showLoadingDialog() {
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(this, null, "请稍后", true, false);
        return mProgressDialog;
    }

    @Override
    public void dismissLoadingDialog() {
        if (mProgressDialog==null || (!mProgressDialog.isShowing())){
            return ;
        }
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }
}
