package com.yuang.yuangapplication.recyclerview;

import com.yuang.yuangapplication.recyclerview.entity.GankBaseResponse;
import com.yuang.yuangapplication.recyclerview.entity.GankItemBean;

import java.net.UnknownHostException;
import java.util.List;

import rx.Observer;
import timber.log.Timber;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public class RecyclerViewPresenter extends RecyclerViewContract.Presenter {

    @Override
    public void onStart() {
        getData("lol");
    }

    @Override
    public void getData(String url) {
        mRxManager.add(mModel.getData(url).subscribe(new Observer<GankBaseResponse<List<GankItemBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Timber.w(e);
                if (e instanceof UnknownHostException) {
                    mView.showNetworkException();
                }
            }

            @Override
            public void onNext(GankBaseResponse<List<GankItemBean>> listBaseResponse) {
                Timber.d(listBaseResponse.getResults().toString());
                mView.showContent(listBaseResponse.getResults());
            }
        }));
    }
}
