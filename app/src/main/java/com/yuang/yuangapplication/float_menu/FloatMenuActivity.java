package com.yuang.yuangapplication.float_menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import com.yuang.floatmenu.uitls.SettingsCompat;
import com.yuang.library.base.BaseActivity;
import com.yuang.yuangapplication.R;

import butterknife.BindView;
import rx.functions.Action1;

public class FloatMenuActivity extends BaseActivity {

    @BindView(R.id.open_float)
    CardView openFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_float_menu;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar("FloatMenu")
                .setBackground(R.drawable.bg_toolbar)
                .setLeftImage(R.mipmap.ic_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListeners() {
        super.setListeners();
        subscribeClick(openFloat, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (SettingsCompat.canDrawOverlays(mContext)) {
                    Intent intent = new Intent(getApplicationContext(), FlowMenuService.class);
                    startService(intent);
                } else {
                    SettingsCompat.manageDrawOverlays(mContext);
                }
            }
        });
    }
}
