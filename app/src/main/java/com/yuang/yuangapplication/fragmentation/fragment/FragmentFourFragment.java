package com.yuang.yuangapplication.fragmentation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuang.library.base.BaseFragment;
import com.yuang.yuangapplication.R;

public class FragmentFourFragment extends BaseFragment{

    public static FragmentFourFragment newInstance() {
        return new FragmentFourFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_fragment_four;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

}
