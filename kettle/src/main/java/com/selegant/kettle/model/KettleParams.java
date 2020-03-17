package com.selegant.kettle.model;

import lombok.Data;

@Data
public class KettleParams {

    /**
     * 任务ID
     */
    private String objectId;

    /**
     * 任务名称
     */
    private String objectName;

    /**
     * 任务地址
     */
    private String objectDirectory;


    /**
     * 日志等级
     */
    private Integer logLevel;
}
