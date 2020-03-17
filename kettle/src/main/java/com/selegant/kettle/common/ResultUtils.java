package com.selegant.kettle.common;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhihan on 2018/11/2 10:24
 */
public class ResultUtils {
    public static ResultResponse setError(ResultCode exceptionCode){
        return setError(null, exceptionCode);
    }

    public static ResultResponse setError(ResultResponse response, ResultCode mpExceptionCode) {
        if (response == null) {
            response = new ResultResponse();
        }
        response.setApiResponse(mpExceptionCode);
        return response;
    }

    public static ResultResponse setError(int code, String message) {
        ResultResponse response = new ResultResponse();
        response.setMsg(message);
        response.setCode(code);
        response.setData(new JSONObject());
        return response;
    }

    public static ResultResponse setError(String message){
        ResultResponse response = new ResultResponse();
        response.setMsg(message);
        response.setCode(ResultCode.FAILED.getErrorCode());
        response.setData(new JSONObject());
        return response;
    }

    /**
     * 可以自定义 {@link ResultCode} 中某个异常信息的msg
     * @author zhihan
     * @date 2018-11-02 10:44
     * @throws
     * @return
    */
    public static ResultResponse setError(ResultCode mpExceptionCode, String msg) {
        ResultResponse response = new ResultResponse();
        response.setMsg(msg);
        response.setCode(mpExceptionCode.getErrorCode());
        response.setData(new JSONObject());
        return response;
    }


    public static ResultResponse setOk() {
        return setOk(new ResultResponse());
    }

    private static ResultResponse setOk(ResultResponse response) {
        if (response == null) {
            response = new ResultResponse();
            response.setData(new JSONObject());
        }
        response.setApiResponse(ResultCode.SUCCESS);
        return response;
    }

    public static ResultResponse setOk(Object data) {
        return setOk(null, data);
    }

    private static ResultResponse setOk(ResultResponse response, Object data) {
        if (response == null) {
            response = new ResultResponse();
        }
        if(data == null){
            data = new JSONObject();
        }
        response.setApiResponse(ResultCode.SUCCESS);
        response.setData(data);
        return response;
    }

    /**
     *  自定义data和{@link ResultCode} 中SUCCESS类型的msg
     *
     * @author zhihan
     * @date 2018-11-02 10:42
     * @throws
     * @return
    */
    public static ResultResponse setOk(Object data, String msg) {
        if(data == null){
            data = new JSONObject();
        }
        ResultResponse response = new ResultResponse();
        response.setApiResponse(ResultCode.SUCCESS);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }
}
