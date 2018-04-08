package com.yuang.library.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import com.yuang.library.BuildConfig;


/**
 * Log 工具类
 */

public class Logg {
    private static boolean open = BuildConfig.LOGG_OPEN;
    private static String TAG = "_Logg";
    private static String TAG_RELEASE = TAG + "_release";
    private static StringBuilder sb = new StringBuilder();

    /**
     * 初始化Logg
     *
     * @param open 是否开启Logg （发布后建议设置为false）
     * @param TAG  Logg的TAG 默认为_Logg
     */
    public static void init(boolean open, String TAG) {
        Logg.open = open;
        Logg.TAG = TAG;
    }

    public static void init(boolean open) {
        Logg.open = open;
    }

    public static void init(String TAG) {
        Logg.TAG = TAG;
    }

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
    private static String getMethodLine() {
        if (sb == null)
            sb = new StringBuilder();
        if (sb.length() > 0)
            sb.delete(0, sb.length());
        try {
            StackTraceElement traceElement = new Throwable().fillInStackTrace().getStackTrace()[2];
            sb.append("At ");
            String fullClassName = traceElement.getClassName();
            sb.append(fullClassName);
            sb.append(".");
            String methodName = traceElement.getMethodName();
            sb.append(methodName);
            sb.append(" (");
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            sb.append(className);
            sb.append(".java:");
            String lineNumber = String.valueOf(traceElement.getLineNumber());
            sb.append(lineNumber);
            sb.append(")");
            Log.v(TAG, "↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            Log.v(TAG, "|"+fullClassName + "." + methodName + "(" + className + ".java:" + lineNumber + ")"+" |");
            Log.v(TAG, "↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
            return sb.toString();
        } catch (Exception e) {

        }
        return "";

    }

}
