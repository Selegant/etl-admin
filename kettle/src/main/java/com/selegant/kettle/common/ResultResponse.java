package com.selegant.kettle.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


/**
 *  接口最终响应模型
 * Created by zhihan on 2018/11/2 9:36
 */
@Getter
@Setter
//@ApiModel(value = "接口响应模型")
public class ResultResponse<T> {

//    @ApiModelProperty(value = "返回值",name = "code")
    private int code;
//    @ApiModelProperty(value = "返回信息",name = "msg")
    private String msg;
//    @ApiModelProperty(value = "返回对象",name = "data")
    private T data;

    public ResultResponse() {

    }

    public ResultResponse(ResultCode excCode) {
        this.code = excCode.getErrorCode();
        this.msg = excCode.getDesc();
        this.data = (T) new JSONObject();
    }

    public void setApiResponse(ResultCode excCode) {
        this.code = excCode.getErrorCode();
        this.msg = excCode.getDesc();
        this.data = (T) new JSONObject();
    }

    public void setResponseData(Object data) {
        if(Objects.isNull(data)){
            data = new JSONObject();
        }
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setData(data);
    }


    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
