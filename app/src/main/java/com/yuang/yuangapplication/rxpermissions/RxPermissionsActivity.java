package com.yuang.yuangapplication.rxpermissions;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yuang.library.base.BaseActivity;
import com.yuang.yuangapplication.R;

import butterknife.BindView;
import butterknife.OnClick;

public class RxPermissionsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.get_Permissions)
    Button getPermissions;
    @BindView(R.id.permissions_name)
    TextView permissionsName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_permissions;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBar(toolbar, "获取权限", true);
    }

    @OnClick(R.id.get_Permissions)
    public void onViewClicked() {
        StringBuffer stringBuffer = new StringBuffer();
        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
        rxPermissions
                .requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        stringBuffer.append(permission.name).append("\n");
                    }
                });
        permissionsName.setText(stringBuffer.toString());

    }

}
