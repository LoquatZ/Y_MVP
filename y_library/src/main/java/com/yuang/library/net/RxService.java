package com.yuang.library.net;

import android.support.annotation.NonNull;

import com.yuang.library.BaseApp;
import com.yuang.library.BuildConfig;
import com.yuang.library.net.log.DefaultFormatPrinter;
import com.yuang.library.utils.DeviceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yuang on 17/12/8.
 * Summary:网络请求
 */

public class RxService {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            //设置Cache
            .addNetworkInterceptor(new CacheInterceptor())//缓存方面需要加入这个拦截器
            .cache(HttpCache.getCache())
            //拼接请求体增加参数
            .addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl.Builder builder = request
                            .url()
                            .newBuilder()
                            .addQueryParameter("b", android.os.Build.BRAND)
                            .addQueryParameter("m", android.os.Build.MODEL)
                            .addQueryParameter("ovn", android.os.Build.VERSION.RELEASE)
                            .addQueryParameter("ov",
                                    String.valueOf(android.os.Build.VERSION.SDK_INT))
                            .addQueryParameter("da", DeviceUtil.getIMEI())
                            .addQueryParameter("ui", DeviceUtil.getIMSI())
                            .addQueryParameter("uuid", DeviceUtil.getUUID())
                            .addQueryParameter("ch", DeviceUtil.getChannel())
                            .addQueryParameter("apk_pkg", DeviceUtil.getPackname());
                    request = request.newBuilder().url(builder.build()).build();
                    return chain.proceed(request);
                }
            })
            //添加HTTPLog
            .addInterceptor(new YHttpLoggingInterceptor(new DefaultFormatPrinter())
                    .setLevel(BuildConfig.DEBUG ? YHttpLoggingInterceptor.Level.ALL : YHttpLoggingInterceptor.Level.NONE))
            //time out
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            .build();

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, BaseApp.getInstance().setBaseUrl());
    }

    private static <T> T createApi(Class<T> clazz, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(clazz);
    }


}

