package com.yuang.yuangapplication.net;

import com.yuang.yuangapplication.recyclerview.entity.GankBaseResponse;
import com.yuang.yuangapplication.recyclerview.entity.GankItemBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public interface NetApi {
    @GET("api/data/福利/100/1")
    Observable<GankBaseResponse<List<GankItemBean>>> getOtherList();
}
