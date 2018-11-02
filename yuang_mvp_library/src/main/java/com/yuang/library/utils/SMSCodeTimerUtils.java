package com.yuang.library.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

/**
 * 项目名称: SMSCodeTimerUtils
 * 类描述: 获取短信倒计时
 * 创建人: Yuang
 * 创建时间: 2018/11/2 上午11:49
 */
public class SMSCodeTimerUtils extends CountDownTimer {
    private Button btn;

    /**
     * @param btn          The TextView
     *
     *
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receiver
     *                          {@link #onTick(long)} callbacks.
     */
    public SMSCodeTimerUtils(Button btn, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false); //设置不可点击
        btn.setText(millisUntilFinished / 1000 + "s 后可重新获取");  //设置倒计时时间
//        btn.setTextColor(Color.parseColor("#cccccc")); //设置按钮为灰色，这时是不能点击的

        /**
         * 超链接 URLSpan
         * 文字背景颜色 BackgroundColorSpan
         * 文字颜色 ForegroundColorSpan
         * 字体大小 AbsoluteSizeSpan
         * 粗体、斜体 StyleSpan
         * 删除线 StrikethroughSpan
         * 下划线 UnderlineSpan
         * 图片 ImageSpan
         * http://blog.csdn.net/ah200614435/article/details/7914459
         */
        SpannableString spannableString = new SpannableString(btn.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#cccccc"));
        /**
         * public void setSpan(Object what, int start, int end, int flags) {
         * 主要是start跟end，start是起始位置,无论中英文，都算一个。
         * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
         */
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        btn.setText(spannableString);
    }

    @Override
    public void onFinish() {
        btn.setText("重新获取验证码");
        btn.setClickable(true);//重新获得点击
//        btn.setTextColor(Color.parseColor("#F65668"));  //还原背景色
    }
}