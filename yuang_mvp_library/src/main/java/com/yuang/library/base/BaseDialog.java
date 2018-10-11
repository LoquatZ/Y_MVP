package com.yuang.library.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.yuang.library.R;
import com.yuang.library.utils.Constants;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * 类描述:
 * 创建人: Yuang QQ:274122635
 * 创建时间: 2018/8/29 下午4:42
 */
public abstract class BaseDialog extends Dialog{

    private Unbinder binder;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        this.initView();
        this.setListeners();
    }

    protected abstract void initView();


    public abstract int getLayoutId();

    /**
     * 设置按钮监
     */
    protected void setListeners() {
        //empty implementation
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

    @Override
    public void dismiss() {
        super.dismiss();
        if (binder != null) binder.unbind();
    }
}
