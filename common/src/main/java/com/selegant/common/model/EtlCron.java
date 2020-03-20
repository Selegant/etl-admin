package com.selegant.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "etl_cron")
public class EtlCron {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * cron表达式
     */
    @TableField(value = "cron")
    private String cron;

    /**
     * cron描述
     */
    @TableField(value = "cron_desc")
    private String cronDesc;

    public static final String COL_ID = "id";

    public static final String COL_CRON = "cron";

    public static final String COL_CRON_DESC = "cron_desc";
}