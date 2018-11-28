package com.yuang.library.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding.view.RxView;
import com.umeng.analytics.MobclickAgent;
import com.yuang.library.AppManager;
import com.yuang.library.R;
import com.yuang.library.utils.Constants;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.TUtil;
import com.yuang.library.utils.TitleBuilder;
import com.yuang.library.utils.ToastUtils;
import com.yuang.library.widget.dialog.LoadingDialog;
import com.yuang.library.widget.toast.YToast;

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
    public ImmersionBar mImmersionBar;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(false, 0.2f)//状态栏字体是深色，不写默认为亮色
                .navigationBarColor(R.color.white)//导航栏颜色
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .init();   //所有子类都将继承这些相同的属性
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
        this.initData();
        setListeners();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (binder != null && binder != Unbinder.EMPTY)
            binder.unbind();
        if (mPresenter != null)
            mPresenter.detachVM();
        this.mPresenter = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        this.loadingDialog = null;
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
     *
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

    protected abstract void initData();

    @Override
    public void onBackPressedSupport() {
        supportFinishAfterTransition();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
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
                .setLeftOnClickListener(v -> onBackPressedSupport());
    }

    /**
     * 使用默认的throttle设置来注册点击事件。
     *
     * @param view    要注册的View
     * @param action1 点击后执行的事件
     */
    protected void subscribeClick(View view, Action1<Void> action1) {
        RxView.clicks(view)
                .throttleFirst(Constants.VIEW_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(action1);
    }

    /**
     * 注册点击事件，不允许throttle。
     *
     * @param view    要注册的View
     * @param action1 点击后执行的事件
     */
    protected void subscribeClickWithoutThrottle(View view, Action1<Void> action1) {
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
     *
     * @param tarActivity
     */
    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    /**
     * 快速打印Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 快速打印Log
     *
     * @param msg
     */
    public void showLog(String msg) {
        Logg.d(msg);
    }

    @Override
    public void showDataException(String msg) {
        YToast.makeCustomText(msg, YToast.YToastStyle.WARN).show();
    }

    @Override
    public void showError(String msg) {
        YToast.makeCustomText(msg, YToast.YToastStyle.ERROR).show();
    }

    @Override
    public void showNetworkException() {
        YToast.makeCustomText("无网络连接", YToast.YToastStyle.WARN).show();
    }

    @Override
    public void showUnknownException() {
        YToast.makeCustomText("未知错误", YToast.YToastStyle.ERROR).show();
    }

    @Override
    public void showLoadingComplete() {
        dismissLoadingDialog();
        //Empty implementation
    }

    @Override
    public Dialog showLoadingDialog(String msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        LoadingDialog.Builder builder = new LoadingDialog.Builder(this)
                .setMessage(msg)
                .setCancelable(true);
        loadingDialog = builder.create();
        loadingDialog.show();
        return loadingDialog;
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog == null || !(loadingDialog.isShowing())) {
            return;
        } else {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
