package com.yuang.yuangapplication.recyclerview;

import com.yuang.yuangapplication.entity.GankBaseResponse;
import com.yuang.yuangapplication.entity.GankItemBean;

import java.net.UnknownHostException;
import java.util.List;

import rx.Observer;
import timber.log.Timber;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public class RecyclerViewPresenter extends RecyclerViewContract.Presenter {
    private int size = 10;
    private int page = 1;

    @Override
    public void onStart() {

    }

    @Override
    void getFirstPage() {
        this.page = 1;
        getData(size, this.page);
    }

    @Override
    void getNextPage() {
        this.page++;
        getData(size, this.page);
    }

    @Override
    void getData(int size, int page) {
        mRxManager.add(mModel.getData(String.valueOf(size), String.valueOf(page)).subscribe(new Observer<GankBaseResponse<List<GankItemBean>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Timber.w(e);
                if (e instanceof UnknownHostException) {
                    mView.showNetworkException();
                }
                mView.showError(e.toString());
            }

            @Override
            public void onNext(GankBaseResponse<List<GankItemBean>> listBaseResponse) {
                mView.showContent(listBaseResponse.getResults(), page, size);
            }
        }));
    }
}
