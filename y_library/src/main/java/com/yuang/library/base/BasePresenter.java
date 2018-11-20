package com.yuang.library.base;


import com.yuang.library.rx.RxManager;

/**
 * Created by Yuang on 17/12/8.
 * Summary:Presenter基类
 */
public abstract class BasePresenter<M, T> {
    protected M mModel;
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void attachVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
        mRxManager.clear();
        mView = null;
        mModel = null;
    }

    public abstract void onStart();
}
