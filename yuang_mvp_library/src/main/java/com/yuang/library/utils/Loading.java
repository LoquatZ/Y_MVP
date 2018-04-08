package com.yuang.library.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yuang.library.R;


/**
 * Created by yuang on 16/10/20.
 */

public class Loading {

    private static Dialog dialog;

    /**
     * @param activity
     * @param message
     */
    public static void startLoading(Activity activity, String message) {
        try {
            LayoutInflater inflater = activity.getLayoutInflater();
            View dailogView = inflater.inflate(R.layout.progress_loading, null);
            TextView tvTitle = (TextView) dailogView.findViewById(R.id.tv_loading);
            tvTitle.setText(message);
            dialog = new Dialog(activity, R.style.LoadingDialog);
            dialog.setContentView(dailogView);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLoading() {
        dialog.dismiss();
    }
}
