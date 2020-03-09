package com.selegant.common.base;

import lombok.Data;

import java.util.Map;

@Data
public class Result<T> {

    private T data;

    private String message;

    private Integer code;

    private Map<String,String> headers;

    public Result(T data, String message, Integer code, Map<String, String> headers) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.headers = headers;
    }
}
