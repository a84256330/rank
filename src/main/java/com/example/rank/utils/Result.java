/*
 * Copyright (C) 2019 www.meituan.cn All rights reserved.
 *
 * Created by gongninggang@meituan.cn on 2019-12-05.
 */

package com.example.rank.utils;


/**
 * 服务内使用的工具类.
 *
 * @author gongninggang@meituan.com
 */
public class Result<T> {
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    private T data;
    private int code = 0;
    private String msg = "SUCCESS";

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 判断结果是否异常.
     *
     * @return true正常、false异常
     */
    public boolean isSuccess() {
        return this.code == SUCCESS;
    }


    /**
     * 封装错误返回.
     *
     * @param code 错误码(可根据业务需要任意指定)
     * @param msg  错误信息
     * @return ""
     */
    public static <T> Result<T> wrapError(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> wrapError(String msg) {
        return new Result<>(ERROR, msg);
    }

    /**
     * 封装正确返回的数据.
     *
     * @param data 数据
     * @param <T>  任意java类型
     * @return
     */
    public static <T> Result<T> wrapSuccess(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> wrapSuccess(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setMsg(msg);
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> wrapSuccess() {
        return new Result<>(SUCCESS, "SUCCESS");
    }

    public static <T> Result<T> wrapSuccess(String msg) {
        return new Result<>(SUCCESS, msg);
    }


    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 重写toString
     *
     * @return json格式
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"data\":")
                .append(data);
        sb.append(",\"code\":")
                .append(code);
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append('}');
        return sb.toString();
    }
}

