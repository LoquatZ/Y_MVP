package com.yuang.yuangapplication.main;

import com.yuang.library.base.BaseModel;
import com.yuang.library.base.BasePresenter;
import com.yuang.library.base.BaseView;
import com.yuang.yuangapplication.entity.ActivityBean;

import java.util.List;

/**
 * Created by Yuang on 17/12/12.
 * Summary:Main控制器接口
 */

public interface MainContract {
    interface Model extends BaseModel {
        List<ActivityBean> getActivity();
    }

    interface View extends BaseView {
        void setActivity(List<ActivityBean> activity);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getActivity();
    }
}
