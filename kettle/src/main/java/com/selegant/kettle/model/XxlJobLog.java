package com.selegant.kettle.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "xxl_job_log")
public class XxlJobLog {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 执行器主键ID
     */
    @TableField(value = "job_group")
    private Integer jobGroup;

    /**
     * 任务，主键ID
     */
    @TableField(value = "job_id")
    private Integer jobId;

    /**
     * 执行器地址，本次执行的地址
     */
    @TableField(value = "executor_address")
    private String executorAddress;

    /**
     * 执行器任务handler
     */
    @TableField(value = "executor_handler")
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    @TableField(value = "executor_param")
    private String executorParam;

    /**
     * 执行器任务分片参数，格式如 1/2
     */
    @TableField(value = "executor_sharding_param")
    private String executorShardingParam;

    /**
     * 失败重试次数
     */
    @TableField(value = "executor_fail_retry_count")
    private Integer executorFailRetryCount;

    /**
     * 调度-时间
     */
    @TableField(value = "trigger_time")
    private Date triggerTime;

    /**
     * 调度-结果
     */
    @TableField(value = "trigger_code")
    private Integer triggerCode;

    /**
     * 调度-日志
     */
    @TableField(value = "trigger_msg")
    private String triggerMsg;

    /**
     * 执行-时间
     */
    @TableField(value = "handle_time")
    private Date handleTime;

    /**
     * 执行-状态
     */
    @TableField(value = "handle_code")
    private Integer handleCode;

    /**
     * 执行-日志
     */
    @TableField(value = "handle_msg")
    private String handleMsg;

    /**
     * 告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败
     */
    @TableField(value = "alarm_status")
    private Byte alarmStatus;
}
