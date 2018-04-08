package com.yuang.library.utils.permissions;

/**
 * Created by Yuang on 17/12/8.
 * Summary:权限回调接口
 */

public interface onRequestPermissionsListener {
    void onRequestBefore();

    void onRequestLater();
}
