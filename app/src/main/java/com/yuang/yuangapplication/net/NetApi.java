package com.yuang.yuangapplication.net;

import com.yuang.yuangapplication.entity.GankBaseResponse;
import com.yuang.yuangapplication.entity.GankItemBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Yuang on 17/12/15.
 * Summary:
 */

public interface NetApi {
    @GET("api/data/福利/{size}/{page}")
    Observable<GankBaseResponse<List<GankItemBean>>> getOtherList(@Path("size") String size, @Path("page") String page);
}
