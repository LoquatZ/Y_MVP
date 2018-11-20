package com.yuang.library.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.jakewharton.rxbinding.view.RxView;
import com.yuang.library.R;
import com.yuang.library.utils.Constants;
import com.yuang.library.utils.OptAnimationLoader;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * 类描述:
 * 创建人: Yuang QQ:274122635
 * 创建时间: 2018/8/29 下午4:42
 */
public abstract class BaseDialog extends Dialog {

    private final AnimationSet mModalOutAnim;
    private Unbinder binder;
    private final AnimationSet mModalInAnim;
    private View mDialogView;
    private boolean mCloseFromCancel;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.dialog_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.dialog_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            cancel();
                        } else {
                            dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        binder = ButterKnife.bind(this);
        if (getWindow() != null) {
            mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        }
        this.initView();
        this.setListeners();
    }

    protected abstract void initView();


    public abstract int getLayoutId();

    @Override
    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
    }

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

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void cancel() {
        super.cancel();
        dismissWithAnimation(true);
    }

    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }
}
