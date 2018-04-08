package com.yuang.yuangapplication.recyclerview;

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
        mRxManager.add(mModel.getData(url)
                .subscribe(
                        data -> mView.showContent(data),
                        Throwable::printStackTrace
                ));
    }
}
