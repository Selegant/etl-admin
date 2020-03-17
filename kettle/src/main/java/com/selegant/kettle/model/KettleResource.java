package com.selegant.kettle.model;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName(value = "kettle_resource")
public class KettleResource {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 转换或作业名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 资源地址
     */
    @TableField(value = "repository_directory")
    private String repositoryDirectory;

    /**
     * 编辑者
     */
    @TableField(value = "modified_user")
    private String modifiedUser;

    /**
     * 编辑时间
     */
    @TableField(value = "modified_date")
    @JsonFormat(pattern= DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date modifiedDate;

    /**
     * 资源类型1:转换 2:作业
     */
    @TableField(value = "object_type")
    private Integer objectType;

    @TableField(value = "object_id")
    private String objectId;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern= DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern= DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date updateTime;

    /**
     * 日志等级
     */
    @TableField(value = "log_level")
    private Integer logLevel;

    /**
     * 调度参数
     */
    @TableField(value = "kettle_params")
    private String kettleParams;
}
