package com.yuang.yuangapplication.fragmentation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuang.library.base.BaseFragment;
import com.yuang.yuangapplication.R;

public class FragmentOneFragment extends BaseFragment{

    public static FragmentOneFragment newInstance() {
        return new FragmentOneFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_fragment_one;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

}
