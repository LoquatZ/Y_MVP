package com.yuang.yuangapplication.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuang.library.base.BaseActivity;
import com.yuang.yuangapplication.R;
import com.yuang.yuangapplication.banner.BannerActivity;
import com.yuang.yuangapplication.dialog.DialogActivity;
import com.yuang.yuangapplication.popwindow.PopWindowActivity;
import com.yuang.yuangapplication.recyclerview.RecyclerViewActivity;
import com.yuang.yuangapplication.rxpermissions.RxPermissionsActivity;
import com.yuang.yuangapplication.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.baserecyclerview)
    CardView baserecyclerview;
    @BindView(R.id.rxpermissions)
    CardView rxpermissions;
    @BindView(R.id.banner)
    CardView banner;
    @BindView(R.id.dialog)
    CardView dialog;
    @BindView(R.id.pop)
    CardView pop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBar(toolbar, "YuangLibrary", false);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick({R.id.baserecyclerview, R.id.rxpermissions, R.id.banner, R.id.dialog,R.id.pop,R.id.webview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baserecyclerview:
                startActivity(RecyclerViewActivity.class);
                break;
            case R.id.rxpermissions:
                startActivity(RxPermissionsActivity.class);
                break;
            case R.id.banner:
                startActivity(BannerActivity.class);
                break;
            case R.id.dialog:
                startActivity(DialogActivity.class);
                break;

            case R.id.pop:
                startActivity(PopWindowActivity.class);
                break;
            case R.id.webview:
                startActivity(WebViewActivity.class);
                break;
        }
    }
}
