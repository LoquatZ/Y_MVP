package com.yuang.yuangapplication.recyclerview;

import com.yuang.library.base.BaseResponse;
import com.yuang.library.net.RxService;
import com.yuang.library.utils.helper.RxUtil;
import com.yuang.yuangapplication.net.NetApi;
import com.yuang.yuangapplication.recyclerview.entity.TvBean;

import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public class RecyclerViewModel implements RecyclerViewContract.Model{

    @Override
    public Observable<BaseResponse<TvBean>> getData(String url) {
        return RxService.createApi(NetApi.class).getOtherList("json/categories/lol/list.json").compose(RxUtil.rxSchedulerHelper());
    }
}
