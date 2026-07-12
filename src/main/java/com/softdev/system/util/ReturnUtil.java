package com.softdev.system.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReturnUtil<T> implements Serializable {
    public static final long serialVersionUID = 42L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final int PAGE_CODE = 0;
    public static final String OBJECT_NOT_FOUND = "找不到该对象";
    public static final String OPERATION_SUCCESS = "操作成功";

    private int code;
    private String msg;
    private T data;
    private int count;

    private ReturnUtil(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ReturnUtil(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ReturnUtil(T data, int count) {
        this.code = PAGE_CODE;
        this.data = data;
        this.count = count;
    }

    public static <T> ReturnUtil<T> page(T data, int count) {
        return new ReturnUtil<>(data, count);
    }

    public static <T> ReturnUtil<T> define(int code, String msg, T data) {
        return new ReturnUtil<>(code, msg, data);
    }

    public static <T> ReturnUtil<T> page(T data, long count) {
        return new ReturnUtil<>(data, (int) count);
    }

    public static ReturnUtil<Void> success() {
        return new ReturnUtil<>(SUCCESS_CODE, OPERATION_SUCCESS);
    }

    public static ReturnUtil<Void> message(String msg) {
        return new ReturnUtil<>(SUCCESS_CODE, msg);
    }

    public static ReturnUtil<Void> notFound() {
        return new ReturnUtil<>(FAIL_CODE, OBJECT_NOT_FOUND);
    }

    public static <T> ReturnUtil<T> data(T data) {
        return new ReturnUtil<>(SUCCESS_CODE, OPERATION_SUCCESS, data);
    }

    public static ReturnUtil<Void> error(String msg) {
        return new ReturnUtil<>(FAIL_CODE, msg);
    }

    public static ReturnUtil<Void> error() {
        return new ReturnUtil<>(FAIL_CODE, OBJECT_NOT_FOUND);
    }
}
