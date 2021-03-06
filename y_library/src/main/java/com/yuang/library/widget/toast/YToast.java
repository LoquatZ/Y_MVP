package com.yuang.library.widget.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuang.library.BaseApp;
import com.yuang.library.R;
import com.yuang.library.utils.Preconditions;

import java.lang.reflect.Field;

/**
 * 项目名称: YToast
 * 类描述: 防IOS Toast 弹窗
 * 创建人: Yuang QQ:274122635
 * 创建时间: 2018/10/11 下午1:48
 */
public class YToast extends Toast {
    private static Toast lastInstance;
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    private YToast(Context context) {
        super(context);
    }

    public static YToast makeCustomText(CharSequence text,
                                        YToastStyle toastType) {
        Preconditions.checkNotNull(BaseApp.getAppContext());
        YToast toast = new YToast(BaseApp.getAppContext());
        /*设置Toast的位置*/
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        /*让Toast显示为我们自定义的样子*/
        toast.setView(getToastView(BaseApp.getAppContext(), text, toastType));
        try {
            Object mTN = getField(toast.getClass().getSuperclass(), toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN.getClass(), mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    params.windowAnimations = R.style.toast_style;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toast;
    }

    @Override
    public void show() {
        if (lastInstance != null) {
            lastInstance.cancel();
        }
        super.show();
        lastInstance = this;
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    private static View getToastView(Context context, CharSequence msg, YToastStyle style) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.item_custom_toast, null);
        ImageView toastIcom = v.findViewById(R.id.toast_icon);
        switch (style) {
            case SUCCESS:
                toastIcom.setImageResource(R.drawable.ic_prompt_success);
                break;
            case ERROR:
                toastIcom.setImageResource(R.drawable.ic_prompt_error);
                break;
            case WARN:
                toastIcom.setImageResource(R.drawable.ic_prompt_warn);
                break;
        }
        TextView text = v.findViewById(R.id.toast_text);
        text.setText(msg);
        return v;
    }

    public static int getWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private static Object getField(Class<?> clz, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = clz.getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    public enum YToastStyle {
        SUCCESS, ERROR, WARN
    }
}
