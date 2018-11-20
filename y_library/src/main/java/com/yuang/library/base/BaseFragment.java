package com.yuang.library.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.umeng.analytics.MobclickAgent;
import com.yuang.library.R;
import com.yuang.library.utils.Constants;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.TUtil;
import com.yuang.library.utils.TitleBuilder;
import com.yuang.library.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import rx.functions.Action1;

/**
 * Created by Yuang on 17/12/8.
 * Summary:Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends SupportFragment implements BaseUiInterface{
    protected String TAG;
    public T mPresenter;
    public E mModel;
    protected Context mContext;
    protected Activity mActivity;
    Unbinder binder;
    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        } else {
            return inflater.inflate(getLayoutId(), null);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        binder = ButterKnife.bind(this, view);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        getBundle(getArguments());
        initUI(view, savedInstanceState);
        setListeners();
        if (this instanceof BaseView) mPresenter.attachVM(this, mModel);
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binder != null) binder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = _mActivity.getFragmentAnimator();
        fragmentAnimator.setEnter(0);
        fragmentAnimator.setExit(0);
        return fragmentAnimator;
    }

    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /**
     * 得到Activity传进来的值
     */
    public void getBundle(Bundle bundle) {

    }

    /**
     * 初始化控件
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {

    }

    /**
     * 设置按钮监听
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
    protected TitleBuilder initTitleBar(String title) {
        return new TitleBuilder(mActivity)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_back)
                .setLeftOnClickListener(v -> {
                    _mActivity.onBackPressed();
                });
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(mContext);//统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(mContext);
    }

    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    public void showLog(String msg) {
        Logg.i(msg);
    }


    @Override
    public void showDataException(String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showDataException(msg);
        }
    }

    @Override
    public void showError(String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showError(msg);
        }
    }

    @Override
    public void showNetworkException() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showNetworkException();
        }
    }

    @Override
    public void showUnknownException() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showUnknownException();
        }
    }

    @Override
    public void showLoadingComplete() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).dismissLoadingDialog();
        }
        //Empty implementation
    }

    @Override
    public Dialog showLoadingDialog(String msg) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showLoadingDialog(msg);
        }
        return null;
    }

    @Override
    public void dismissLoadingDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).dismissLoadingDialog();
        }
    }
}
