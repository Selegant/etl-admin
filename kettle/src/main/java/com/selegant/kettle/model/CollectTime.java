package com.selegant.kettle.model;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 采集时间记录
 */
@Data
@TableName(value = "collect_time")
public class CollectTime {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "view_name")
    private String viewName;

    @TableField(value = "last_collect_time")
    @JsonFormat(pattern= DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date lastCollectTime;

    @TableField(value = "current_collect_time")
    @JsonFormat(pattern= DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date currentCollectTime;

    @TableField(value = "view_desc")
    private String viewDesc;
}
