package com.selegant.datax.util;




import com.selegant.datax.base.Result;

import java.util.Map;

public class ResultUtil {

    public static Result setError(Object data) {
        return new Result(data, "", 1000, null);
    }

    public static Result setSuccess(Object data) {
        return new Result(data, "", 200, null);
    }

    public static Result setError(Object data, Map<String, String> headers) {
        return new Result(data, "", 1000, headers);
    }

    public static Result setSuccess(Object data, Map<String, String> headers) {
        return new Result(data, "", 200, headers);
    }

    public static Result setError(Object data, String message) {
        return new Result(data, message, 1000, null);
    }

    public static Result setSuccess(Object data, String message) {
        return new Result(data, message, 200, null);
    }

    public static Result setError(Object data, String message, Map<String, String> headers) {
        return new Result(data, message, 1000, headers);
    }

    public static Result setSuccess(Object data, String message, Map<String, String> headers) {
        return new Result(data, message, 200, headers);
    }

    public static Result setResult(Object data, String message, Integer code, Map<String, String> headers) {
        return new Result(data, message, code, headers);
    }
}
