package com.yuang.library.utils.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by Yuang on 17/12/8.
 * Summary:动态申请权限
 */
public class YPermissionsUtils {

    //请求Camera权限
    public static void requestCamera(Context mContext, onRequestPermissionsListener onRequestPermissionsListener) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, 1);
            onRequestPermissionsListener.onRequestBefore();
        } else {
            onRequestPermissionsListener.onRequestLater();
        }
    }
    //打电话权限
    public static void requestCall(Context mContext, onRequestPermissionsListener onRequestPermissionsListener) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, 1);
            onRequestPermissionsListener.onRequestBefore();
        } else {
            onRequestPermissionsListener.onRequestLater();
        }
    }

    //读取手机信息权限
    public static void requestPhoneState(Context mContext, onRequestPermissionsListener onRequestPermissionsListener) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            onRequestPermissionsListener.onRequestBefore();
        } else {
            onRequestPermissionsListener.onRequestLater();
        }
    }

    //写入权限
    public static void requestWriteExternalStorage(Context mContext, onRequestPermissionsListener onRequestPermissionsListener) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            onRequestPermissionsListener.onRequestBefore();
        } else {
            onRequestPermissionsListener.onRequestLater();
        }
    }
    //读取权限
    public static void requestReadExternalStorage(Context mContext, onRequestPermissionsListener onRequestPermissionsListener) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            onRequestPermissionsListener.onRequestBefore();
        } else {
            onRequestPermissionsListener.onRequestLater();
        }
    }
}
