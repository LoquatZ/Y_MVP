package com.yuang.library.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yuang.library.BuildConfig;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 项目名称: Logg
 * 类描述: Log 工具类
 * 注意: 使用是 build.gradle 添加 BuildConfig.LOGG_OPEN
 * 创建人: Yuang QQ:274122635
 * 创建时间: 2018/10/11 下午1:46
 */

public final class Logg implements HttpLoggingInterceptor.Logger {
    private static boolean open = BuildConfig.LOGG_OPEN;
    private static String TAG = "_Logg";
    private static String TAG_RELEASE = TAG + "_release";
    private static final String LOG_UP_LINE = "┌────── Log ────────────────────────────────────────────────────────────────────────";
    private static final String LOG_END_LINE = "└───────────────────────────────────────────────────────────────────────────────────";
    private static final String CENTER_LINE = "├ ";

    public static void v(@NonNull String o) {
        if (!open)
            return;
        getMethodLine();
        Log.v(TAG, o);
    }

    public static void d(@NonNull String o) {
        if (!open)
            return;
        getMethodLine();
        Log.d(TAG, o);
    }

    public static void i(@NonNull String o) {
        if (!open)
            return;
        getMethodLine();
        Log.i(TAG, o);
    }

    public static void w(@NonNull String o) {
        if (!open)
            return;
        getMethodLine();
        Log.w(TAG, o);
    }

    public static void e(@NonNull String o) {
        if (!open)
            return;
        getMethodLine();
        Log.e(TAG, o);
    }

    /**
     * release version always open log
     */
    public static void r(@NonNull String o) {
        Log.d(TAG_RELEASE, o);
    }


    /**
     * 显示 调用的 来源(package.method lineNum)
     *
     * @return 用于自定义打印‘调用方法行数’
     */
    private static void getMethodLine() {
        try {
            StackTraceElement traceElement = new Throwable().fillInStackTrace().getStackTrace()[2];
            String fullClassName = traceElement.getClassName();
            String methodName = traceElement.getMethodName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String lineNumber = String.valueOf(traceElement.getLineNumber());
            Log.v(resolveTag(TAG), LOG_UP_LINE);
            Log.v(resolveTag(TAG), CENTER_LINE + fullClassName + "." + methodName + "(" + className + ".java:" + lineNumber + ")");
            Log.v(resolveTag(TAG), LOG_END_LINE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void log(@NonNull String message) {
        d(message);
    }

    private static ThreadLocal<Integer> last = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    private static final String[] ARMS = new String[]{"-A-", "-R-", "-M-", "-S-"};

    private static String computeKey() {
        if (last.get() >= 4) {
            last.set(0);
        }
        String s = ARMS[last.get()];
        last.set(last.get() + 1);
        return s;
    }

    private static String resolveTag(String tag) {
        return computeKey() + tag;
    }
}
