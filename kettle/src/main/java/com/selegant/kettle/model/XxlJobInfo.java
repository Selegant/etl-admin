package com.selegant.kettle.model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
@Data
@TableName(value = "xxl_job_info")
public class XxlJobInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 执行器主键ID
     */
    @TableField(value = "job_group")
    private Integer jobGroup;

    /**
     * 任务执行CRON
     */
    @TableField(value = "job_cron")
    private String jobCron;

    @TableField(value = "job_desc")
    private String jobDesc;

    @TableField(value = "add_time")
    private Date addTime;

    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 作者
     */
    @TableField(value = "author")
    private String author;

    /**
     * 报警邮件
     */
    @TableField(value = "alarm_email")
    private String alarmEmail;

    /**
     * 执行器路由策略
     */
    @TableField(value = "executor_route_strategy")
    private String executorRouteStrategy;

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
     * 阻塞处理策略
     */
    @TableField(value = "executor_block_strategy")
    private String executorBlockStrategy;

    /**
     * 任务执行超时时间，单位秒
     */
    @TableField(value = "executor_timeout")
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    @TableField(value = "executor_fail_retry_count")
    private Integer executorFailRetryCount;

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

    /**
     * GLUE更新时间
     */
    @TableField(value = "glue_updatetime")
    private Date glueUpdatetime;

    /**
     * 子任务ID，多个逗号分隔
     */
    @TableField(value = "child_jobid")
    private String childJobid;

    /**
     * 调度状态：0-停止，1-运行
     */
    @TableField(value = "trigger_status")
    private Byte triggerStatus;

    /**
     * 上次调度时间
     */
    @TableField(value = "trigger_last_time")
    private Long triggerLastTime;

    /**
     * 下次调度时间
     */
    @TableField(value = "trigger_next_time")
    private Long triggerNextTime;

    /**
     * Kettle资源ID
     */
    @TableField(value = "object_id")
    private String objectId;

    /**
     * Kettle资源类型
     */
    @TableField(value = "object_type")
    private Integer objectType;
}
