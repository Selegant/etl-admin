package com.selegant.kettle.model;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

@Data
@TableName(value = "xxl_job_logglue")
public class XxlJobLogglue {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务，主键ID
     */
    @TableField(value = "job_id")
    private Integer jobId;

    /**
     * GLUE类型
     */
    @TableField(value = "glue_type")
    private String glueType;

    /**
     * GLUE源代码
     */
    @TableField(value = "glue_source")
    private String glueSource;

    /**
     * GLUE备注
     */
    @TableField(value = "glue_remark")
    private String glueRemark;

    @TableField(value = "add_time")
    private Date addTime;

    @TableField(value = "update_time")
    private Date updateTime;
}
