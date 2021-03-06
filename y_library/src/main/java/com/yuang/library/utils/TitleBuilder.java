package com.yuang.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.yuang.library.R;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;
import timber.log.Timber;


/**
 * 标题栏构造器,使用方法 new TitleBuilder().setMethod().setMethod()......
 * <p/>
 * 统一格式为标题文字,左右各自是文字/图片按钮
 * 按钮都默认不显示,只有在你调用setLeftText时才会显示左侧按钮文字,图片同理
 * 图片或文字的点击事件都用Left/RightOnClickListener
 */
/**
 * 项目名称: TitleBuilder
 * 类描述: Toolbar
 * 创建人: Yuang
 * 创建时间: 2018/11/19 3:04 PM
 */
public class TitleBuilder {

    private View rootView;
    private TextView tvTitle;
    private ImageView ivLeft;
    private ImageView ivRight;
    private TextView tvLeft;
    private TextView tvRight;
    private View status_bar;

    public View getRootView() {
        return rootView;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    /**
     * Activity中使用这个构造方法
     */
    public TitleBuilder(Activity context) {
        rootView = context.findViewById(R.id.rl_titlebar);
        if (rootView == null) {
            return;
        }
        tvTitle = (TextView) rootView.findViewById(R.id.titlebar_tv);
        ivLeft = (ImageView) rootView.findViewById(R.id.titlebar_iv_left);
        ivRight = (ImageView) rootView.findViewById(R.id.titlebar_iv_right);
        tvLeft = (TextView) rootView.findViewById(R.id.titlebar_tv_left);
        tvRight = (TextView) rootView.findViewById(R.id.titlebar_tv_right);
        status_bar = rootView.findViewById(R.id.status_bar);
        //是否可沉浸状态栏 如果 沉浸那么重新计算高度
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setViewFullScreen(status_bar,getStatusBarHeight(context));
        }
    }

    /**
     * Fragment中使用这个构造方法
     */
    public TitleBuilder(View view,Context context) {
        rootView = view.findViewById(R.id.rl_titlebar);
        if (rootView == null) {
            return;
        }
        tvTitle = (TextView) rootView.findViewById(R.id.titlebar_tv);
        ivLeft = (ImageView) rootView.findViewById(R.id.titlebar_iv_left);
        ivRight = (ImageView) rootView.findViewById(R.id.titlebar_iv_right);
        tvLeft = (TextView) rootView.findViewById(R.id.titlebar_tv_left);
        tvRight = (TextView) rootView.findViewById(R.id.titlebar_tv_right);
        status_bar = rootView.findViewById(R.id.status_bar);
        setViewFullScreen(status_bar,getStatusBarHeight(context));
    }

    public TitleBuilder setTitleText(String text) {
        tvTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE
                : View.VISIBLE);
        tvTitle.setText(text);
        return this;
    }

    // left
    public TitleBuilder setLeftImage(int resId) {
        ivLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        ivLeft.setImageResource(resId);
        return this;
    }

    public TitleBuilder setBackground(int resID) {
        rootView.setBackgroundResource(resID);
        return this;
    }

    public TitleBuilder setLeftText(String text) {
        tvLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvLeft.setText(text);
        return this;
    }

    public TitleBuilder setLeftOnClickListener(Action1<Void> action1) {
        RxView.clicks(ivLeft)
                .throttleFirst(Constants.VIEW_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .subscribe(action1);
        if (tvLeft.getVisibility() == View.VISIBLE) {
            RxView.clicks(tvLeft)
                    .throttleFirst(Constants.VIEW_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                    .subscribe(action1);
        }

        return this;
    }

    // right
    public TitleBuilder setRightImage(int resId) {
        ivRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        ivRight.setImageResource(resId);
        return this;
    }

    public TitleBuilder setRightText(String text) {
        tvRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE
                : View.VISIBLE);
        tvRight.setText(text);
        return this;
    }

    public TitleBuilder setRightTextColor(Context context, int resId) {
        tvRight.setTextColor(context.getResources().getColor(resId));
        return this;
    }

    public TitleBuilder setTitleTextColor(Context context, int resId) {
        tvTitle.setTextColor(context.getResources().getColor(resId));
        return this;
    }


    public TitleBuilder setRightOnClickListener(Action1<Void> action1) {
        if (tvRight.getVisibility() == View.VISIBLE) {
            RxView.clicks(tvRight)
                    .throttleFirst(Constants.VIEW_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                    .subscribe(action1);
        }
        return this;
    }

    public View build() {
        return rootView;
    }

    @SuppressLint("PrivateApi")
    private int getStatusBarHeight(Context context) {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Timber.e(e1);
            return 0;
        }

        return sbar;
    }

    private void setViewFullScreen(View view, int h) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        layoutParams.height = h;
        view.setLayoutParams(layoutParams);
    }

}
