package com.yuang.library.base;

/**
 * Created by Yuang on 17/12/8.
 * Summary:BaseResponse
 */
public class BaseResponse<DataType> {
    //服务器返回错误
    public static final int RESULT_CODE_ERROR = 0;
    //服务器返回成功
    public static final int RESULT_CODE_SUCCESS = 1;
    //服务器返回错误
    public static final int RESULT_CODE_TOKEN_EXPIRED = 401;
    /**
     * 通用返回值属性
     */
    private int status;
    /**
     * 通用返回信息。
     */
    private String msg;

    /**
     * 具体的内容。
     */
    private DataType data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataType getData() {
        return data;
    }

    public void setData(DataType data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}