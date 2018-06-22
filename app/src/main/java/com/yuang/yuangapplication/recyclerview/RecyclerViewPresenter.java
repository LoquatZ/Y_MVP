package com.yuang.yuangapplication.recyclerview;

import com.yuang.library.base.BaseObserver;
import com.yuang.library.base.BaseResponse;
import com.yuang.yuangapplication.recyclerview.entity.TvBean;

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
//        mRxManager.add(mModel.getData(url)
//                .subscribe(
//                        data -> mView.showContent(data),
//                        Throwable::printStackTrace
//                ));

        mRxManager.add(mModel.getData(url).subscribe(new BaseObserver<BaseResponse<TvBean>>(mView) {
            @Override
            public void onSuccess(BaseResponse<TvBean> response) {
                mView.showContent(response.getData());
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
            }


        }));
    }
}
