package com.yuang.yuangapplication.net;

import com.yuang.library.base.BaseResponse;
import com.yuang.yuangapplication.recyclerview.entity.TvBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public interface NetApi {
    @GET("http://www.quanmin.tv/{url}?11211639&os=1&v=2.2.4&os=1&ver=4")
    Observable<BaseResponse<TvBean>> getOtherList(@Path("url") String url);
}
