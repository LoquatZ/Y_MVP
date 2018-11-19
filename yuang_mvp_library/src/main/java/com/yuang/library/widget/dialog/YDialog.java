package com.yuang.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yuang.library.R;
import com.yuang.library.widget.popwindow.PopSimpleAnimationListener;

/**
 * Created by Yuang on 17/12/8.
 * Summary:Dialog
 */
public abstract class YDialog extends Dialog {

    protected Context mContext;

    protected LayoutParams mLayoutParams;

    private Animation mAlphaOpenAnimation;
    private Animation mAlphaCloseAnimation;
    private Animation mPopOpenAnimation;
    private Animation mPopCloseAnimation;
    private boolean mIsDismissed = true;

    public LayoutParams getLayoutParams() {
        return mLayoutParams;
    }

    public YDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public YDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    public YDialog(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        mContext = context;
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
    }

    /**
     * @param context
     * @param alpha   透明度 0.0f--1f(不透明)
     * @param gravity 方向(Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT)
     */
    public YDialog(Context context, float alpha, int gravity) {
        super(context);
        // TODO Auto-generated constructor stub
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        mContext = context;
        Window window = this.getWindow();
        mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = 1f;
        window.setAttributes(mLayoutParams);
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = gravity;
        }
    }

    /**
     * 隐藏头部导航栏状态栏
     */
    public void skipTools() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置全屏显示
     */
    public void setFullScreen() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.FILL_PARENT;
        lp.height = LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
    }

    /**
     * 设置宽度match_parent
     */
    public void setFullScreenWidth() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.FILL_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /**
     * 设置高度为match_parent
     */
    public void setFullScreenHeight() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.WRAP_CONTENT;
        lp.height = LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
    }

    public void setOnWhole() {
        getWindow().setType(LayoutParams.TYPE_SYSTEM_ALERT);
    }

    public abstract View getRootView();

    protected void initAnim(Context context, View mRootLayout) {
        mPopOpenAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pop_action_sheet_enter);
        mPopCloseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pop_action_sheet_exit);
        mPopCloseAnimation.setAnimationListener(new PopSimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mRootLayout.post(mDismissRunnable);
            }
        });

        mAlphaOpenAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pop_alpha_enter);
        mAlphaCloseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pop_alpha_exit);
        mAlphaCloseAnimation.setAnimationListener(new PopSimpleAnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mRootLayout.startAnimation(mPopCloseAnimation);
            }
        });
    }

    private Runnable mDismissRunnable = new Runnable() {
        @Override
        public void run() {
            YDialog.super.dismiss();
        }
    };

    @Override
    public void dismiss() {
        executeExitAnim();
    }

    @Override
    public void show() {
        super.show();
        if (mIsDismissed) {
            mIsDismissed = false;
            if (getRootView() != null) {
                getRootView().startAnimation(mPopOpenAnimation);
            }
        }
    }

    private void executeExitAnim() {
        if (!mIsDismissed) {
            mIsDismissed = true;
            if (getRootView() != null) {
                getRootView().startAnimation(mPopCloseAnimation);
            }
        }
    }
}
