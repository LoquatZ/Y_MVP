package com.yuang.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yuang.library.AppManager;
import com.yuang.library.R;
import com.yuang.library.utils.Loading;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.TUtil;
import com.yuang.library.utils.TitleBuilder;
import com.yuang.library.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Yuang on 17/12/8.
 * Summary:Activity基类
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends SupportActivity {

    protected String TAG;
    public T mPresenter;
    public E mModel;
    protected Context mContext;
    Unbinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
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
                .setLeftImage(R.mipmap.ic_back)
                .setLeftOnClickListener(v -> finish());
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
}
