package com.yuang.yuangapplication.main;

import com.yuang.yuangapplication.banner.BannerActivity;
import com.yuang.yuangapplication.dialog.DialogActivity;
import com.yuang.yuangapplication.entity.ActivityBean;
import com.yuang.yuangapplication.fragmentation.FragmentationActivity;
import com.yuang.yuangapplication.recyclerview.RecyclerViewActivity;
import com.yuang.yuangapplication.rxpermissions.RxPermissionsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuang on 17/12/12.
 * Summary:MainModel
 */

public class MainModel implements MainContract.Model {

    @Override
    public List<ActivityBean> getActivity() {
        List<ActivityBean> activityBeans = new ArrayList<>();
        activityBeans.add(new ActivityBean(RecyclerViewActivity.class,"RecyclerViewActivity"));
        activityBeans.add(new ActivityBean(RxPermissionsActivity.class,"RxPermissionsActivity"));
        activityBeans.add(new ActivityBean(BannerActivity.class,"BannerActivity"));
        activityBeans.add(new ActivityBean(DialogActivity.class,"DialogActivity"));
        activityBeans.add(new ActivityBean(FragmentationActivity.class,"FragmentationActivity"));
        return activityBeans;
    }
}
