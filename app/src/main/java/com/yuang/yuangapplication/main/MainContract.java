package com.yuang.yuangapplication.main;

import com.yuang.library.base.BaseModel;
import com.yuang.library.base.BasePresenter;
import com.yuang.library.base.BaseView;

/**
 * Created by Yuang on 17/12/12.
 * Summary:Main控制器接口
 */

public interface MainContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<Model, View> {

    }
}
