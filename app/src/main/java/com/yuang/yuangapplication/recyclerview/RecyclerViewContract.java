package com.yuang.yuangapplication.recyclerview;

import com.yuang.library.base.BaseModel;
import com.yuang.library.base.BasePresenter;
import com.yuang.library.base.BaseView;
import com.yuang.yuangapplication.entity.GankBaseResponse;
import com.yuang.yuangapplication.entity.GankItemBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public interface RecyclerViewContract {
    interface Model extends BaseModel {
        Observable<GankBaseResponse<List<GankItemBean>>> getData(String size, String page);
    }

    interface View extends BaseView {
        void showContent(List<GankItemBean> info,int page,int size);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getFirstPage();

        abstract void getNextPage();

        abstract void getData(int size, int page);
    }

}
