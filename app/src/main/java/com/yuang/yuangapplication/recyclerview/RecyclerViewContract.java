package com.yuang.yuangapplication.recyclerview;

import com.yuang.library.base.BaseModel;
import com.yuang.library.base.BasePresenter;
import com.yuang.library.base.BaseView;
import com.yuang.yuangapplication.recyclerview.entity.GankBaseResponse;
import com.yuang.yuangapplication.recyclerview.entity.GankItemBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public interface RecyclerViewContract {
    interface Model extends BaseModel {
        Observable<GankBaseResponse<List<GankItemBean>>> getData(String url);
    }

    interface View extends BaseView {
        void showContent(List<GankItemBean> info);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getData(String url);
    }

}
