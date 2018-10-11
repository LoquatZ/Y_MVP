package com.yuang.yuangapplication.recyclerview;

import com.yuang.library.net.RxService;
import com.yuang.library.utils.rx_utils.RxUtil;
import com.yuang.yuangapplication.net.NetApi;
import com.yuang.yuangapplication.recyclerview.entity.GankBaseResponse;
import com.yuang.yuangapplication.recyclerview.entity.GankItemBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public class RecyclerViewModel implements RecyclerViewContract.Model{

    @Override
    public Observable<GankBaseResponse<List<GankItemBean>>> getData(String url) {
        return RxService.createApi(NetApi.class).getOtherList().compose(RxUtil.rxSchedulerHelper());
    }
}
