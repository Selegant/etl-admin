package com.selegant.kettle.common;

import lombok.Getter;

/**
 * @author WangTao
 * @CreateTime 2018/11/05
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS("SUCCESS", 200, "success", "成功"),
    /**
     * 失败
     */
    FAILED("FAILED", 1000, "failed", "失败"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误编号
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误描述
     */
    private String desc;

    ResultCode(String code, int errorCode, String errorMsg, String desc) {
        this.code = code;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.desc = desc;
    }

}
