package com.yuang.yuangapplication.recyclerview;

import com.yuang.library.base.BaseModel;
import com.yuang.library.base.BasePresenter;
import com.yuang.library.base.BaseView;
import com.yuang.yuangapplication.recyclerview.entity.TvBean;

import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public interface RecyclerViewContract {
    interface Model extends BaseModel {
        Observable<TvBean> getData(String url);
    }

    interface View extends BaseView {
        void showContent(TvBean info);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getData(String url);
    }

}
