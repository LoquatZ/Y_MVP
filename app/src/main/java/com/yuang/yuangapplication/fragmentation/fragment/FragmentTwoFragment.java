package com.yuang.yuangapplication.fragmentation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuang.library.base.BaseFragment;
import com.yuang.yuangapplication.R;

public class FragmentTwoFragment extends BaseFragment{

    public static FragmentTwoFragment newInstance() {
        return new FragmentTwoFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_fragment_two;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

}
