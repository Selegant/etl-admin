package com.selegant.kettle.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * kettle 采集分析
 */
@Data
@TableName(value = "kettle_collection")
public class KettleCollection {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 视图名称
     */
    @TableField(value = "view_name")
    private String viewName;

    /**
     * 输入量
     */
    @TableField(value = "input_num")
    private Integer inputNum;

    /**
     * 输出量
     */
    @TableField(value = "output_num")
    private Integer outputNum;

    /**
     * 读取数量
     */
    @TableField(value = "read_num")
    private Integer readNum;

    /**
     * 写入数量
     */
    @TableField(value = "write_num")
    private Integer writeNum;

    /**
     * 更新数量
     */
    @TableField(value = "update_num")
    private Integer updateNum;

    /**
     * 错误数量
     */
    @TableField(value = "error_num")
    private Integer errorNum;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 采集开始时间
     */
    @TableField(value = "collect_start_time")
    private Date collectStartTime;

    /**
     * 日志ID
     */
    @TableField(value = "log_id")
    private Integer logId;

    /**
     * 错误标志 1:错误 0:成功
     */
    @TableField(value = "error_flag")
    private Integer errorFlag;

    /**
     * 采集结束时间
     */
    @TableField(value = "collect_end_time")
    private Date collectEndTime;

    @TableField(value = "job_type")
    private Integer jobType;

    /**
     * 采集执行时间
     */
    @TableField(value = "collect_execute_time")
    private Date collectExecuteTime;
}