package com.yuang.library.net;

import com.yuang.library.BaseApp;
import com.yuang.library.BuildConfig;
import com.yuang.library.net.log.DefaultFormatPrinter;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yuang on 17/12/8.
 * Summary:网络请求
 */

public class RxService {
    private static final int TIMEOUT_READ = 60;
    private static final int TIMEOUT_CONNECTION = 60;
    //    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
//            //打印日志
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(@NonNull Chain chain) throws IOException {
//                    Request request = chain.request();
//                    Response response = chain.proceed(request);
//                    Logg.d("API:" + request.url());
//                    return response;
//                }
//            })
            //设置Cache
//            .addNetworkInterceptor(cacheInterceptor)//缓存方面需要加入这个拦截器
//            .addNetworkInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request();
//                    HttpUrl.Builder builder = request
//                            .url()
//                            .newBuilder()
//                            .addQueryParameter("b", android.os.Build.BRAND)
//                            .addQueryParameter("m", android.os.Build.MODEL)
//                            .addQueryParameter("ovn", android.os.Build.VERSION.RELEASE)
//                            .addQueryParameter("ov",
//                                    String.valueOf(android.os.Build.VERSION.SDK_INT))
//                            .addQueryParameter("da", DeviceUtil.getIMEI())
//                            .addQueryParameter("ui", DeviceUtil.getIMSI())
//                            .addQueryParameter("uuid", DeviceUtil.getUUID())
//                            .addQueryParameter("ch", DeviceUtil.getChannel())
//                            .addQueryParameter("apk_pkg", DeviceUtil.getPackname());
//                    request = request.newBuilder().url(builder.build()).build();
//                    return chain.proceed(request);
//                }
//            })
            .addNetworkInterceptor(new YHttpLoggingInterceptor(new DefaultFormatPrinter()).setLevel(BuildConfig.DEBUG ? YHttpLoggingInterceptor.Level.ALL : YHttpLoggingInterceptor.Level.NONE))
            .cache(HttpCache.getCache())
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            .build();

    public static <T> T createApi(Class<T> clazz) {
        return createApi(clazz, BaseApp.getInstance().setBaseUrl());
    }

    public static <T> T createApi(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


}

