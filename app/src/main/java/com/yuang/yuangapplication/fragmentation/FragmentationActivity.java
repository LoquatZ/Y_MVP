package com.yuang.yuangapplication.fragmentation;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.yuang.library.base.BaseActivity;
import com.yuang.library.utils.Logg;
import com.yuang.library.utils.rx_utils.RxDataUtils;
import com.yuang.library.widget.bottom_bar.BottomBar;
import com.yuang.library.widget.bottom_bar.BottomBarTab;
import com.yuang.yuangapplication.R;
import com.yuang.yuangapplication.fragmentation.fragment.FragmentOneFragment;
import com.yuang.yuangapplication.fragmentation.fragment.FragmentThreeFragment;
import com.yuang.yuangapplication.fragmentation.fragment.FragmentTwoFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class FragmentationActivity extends BaseActivity {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    @BindView(R.id.fl_tab_container)
    FrameLayout flTabContainer;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mFragments = new SupportFragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragmentation;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        FragmentOneFragment firstFragment = findFragment(FragmentOneFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FragmentOneFragment.newInstance();
            mFragments[SECOND] = FragmentTwoFragment.newInstance();
            mFragments[THIRD] = FragmentThreeFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(FragmentTwoFragment.class);
            mFragments[THIRD] = findFragment(FragmentThreeFragment.class);
        }

        mBottomBar
                .addItem(new BottomBarTab(this, R.mipmap.ic_icon, R.mipmap.ic_launcher_round, getString(R.string.app_name), R.color.brown))
                .addItem(new BottomBarTab(this, R.mipmap.ic_icon, R.mipmap.ic_launcher_round, getString(R.string.app_name), R.color.brown))
                .addItem(new BottomBarTab(this, R.mipmap.ic_icon, R.mipmap.ic_launcher_round, getString(R.string.app_name), R.color.brown));

        // 模拟未读消息
        mBottomBar.getItem(FIRST).setUnreadCount(9);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                //被选中
                showHideFragment(mFragments[position], mFragments[prePosition]);
                BottomBarTab tab = mBottomBar.getItem(FIRST);
                if (position == FIRST) {
                    tab.setUnreadCount(0);
                } else {
                    tab.setUnreadCount(tab.getUnreadCount() + 1);
                }
            }

            @Override
            public void onTabUnselected(int position) {
                //取消选中
            }

            @Override
            public void onTabReselected(int position) {
                //Fragment重复点击
            }
        });
        Logg.e(RxDataUtils.isUrlNO("http://tool.chinaz.com/regex/")+"");
    }

    @Override
    protected void initData() {

    }
}
