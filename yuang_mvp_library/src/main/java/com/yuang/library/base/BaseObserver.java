package com.yuang.library.base;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.yuang.library.utils.Logg;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by Yuang on 17/12/8.
 * Summary:BaseObserver
 */
public abstract class BaseObserver<E extends BaseResponse> implements Observer<E> {

    private final String LOG_TAG = getClass().getSimpleName();

    private final BaseUiInterface mUiInterface;

    protected BaseObserver(BaseUiInterface baseUiInterface) {
        mUiInterface = baseUiInterface;
    }

    @Override
    public void onCompleted() {
        mUiInterface.showLoadingComplete();
    }

    @Override
    public void onError(Throwable throwable) {
        handleError(throwable, mUiInterface, LOG_TAG);
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    public static void handleError(Throwable throwable, BaseUiInterface mUiInterface, String LOG_TAG){
        mUiInterface.showLoadingComplete();
        if (throwable == null) {
            mUiInterface.showUnknownException();
            return;
        }
        throwable.printStackTrace();
        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable instanceof SocketTimeoutException || throwable
                instanceof ConnectException || throwable instanceof UnknownHostException) {
            mUiInterface.showNetworkException();
        } else if ((throwable instanceof JsonSyntaxException) || (throwable instanceof
                NumberFormatException) || (throwable instanceof MalformedJsonException)) {
            mUiInterface.showDataException("数据解析出错");
        } else if ((throwable instanceof HttpException)) {
            mUiInterface.showDataException("服务器错误(" + ((HttpException) throwable).code()+")");
        } else if (throwable instanceof NullPointerException) {
            mUiInterface.showDataException("客户端出异常！");
        } else {
            mUiInterface.showUnknownException();
        }
    }

    @Override
    public void onNext(E response) {
        switch (response.getStatus()) {
            case BaseResponse.RESULT_CODE_SUCCESS:
                onSuccess(response);
                break;
            case BaseResponse.RESULT_CODE_TOKEN_EXPIRED:

                break;
            case BaseResponse.RESULT_CODE_ERROR:
                onError(response);
                break;
            default:
                onDataFailure(response);
        }
    }

    public abstract void onSuccess(E response);

    /**
     * 服务器返回错误
     * @param response
     */
    protected void onError(E response) {
        String msg = response.getMsg();
        if (!TextUtils.isEmpty(msg)) {
            mUiInterface.showError(response.getMsg());
        }
        else{
            mUiInterface.showUnknownException();
        }
    }

    /**
     * 未知数据类型
     * @param response
     */
    private void onDataFailure(E response) {
        String msg = response.getMsg();
        Logg.w("request data but get failure:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            mUiInterface.showDataException(response.getMsg());
        }
        else{
            mUiInterface.showUnknownException();
        }
    }

    /**
     * Create a new silence, non-leak observer.
     */
    public static <T> Observer<T> silenceObserver(){
        return new Observer<T>() {
            @Override
            public void onCompleted() {
                //Empty
            }

            @Override
            public void onError(Throwable e) {
                //Empty
            }

            @Override
            public void onNext(T t) {
                //Empty
            }
        };
    }

}