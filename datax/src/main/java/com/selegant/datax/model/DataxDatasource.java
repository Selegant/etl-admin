package com.selegant.datax.model;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName(value = "datax_datasource")
public class DataxDatasource {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据源名称
     */
    @TableField(value = "datasource_name")
    private String datasourceName;

    /**
     * 数据源
     */
    @TableField(value = "datasource")
    private String datasource;

    /**
     * 数据源分组
     */
    @TableField(value = "datasource_group")
    private String datasourceGroup;

    /**
     * 用户名
     */
    @TableField(value = "jdbc_username")
    private String jdbcUsername;

    /**
     * 密码
     */
    @TableField(value = "jdbc_password")
    private String jdbcPassword;

    /**
     * jdbc url
     */
    @TableField(value = "jdbc_url")
    private String jdbcUrl;

    /**
     * jdbc驱动类
     */
    @TableField(value = "jdbc_driver_class")
    private String jdbcDriverClass;

    /**
     * 状态：0删除 1启用 2禁用
     */
    @TableField(value = "status")
    private Boolean status;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    @JsonFormat(pattern= DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date createDate;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_date")
    @JsonFormat(pattern= DatePattern.NORM_DATETIME_PATTERN,timezone = "GMT+8")
    private Date updateDate;

    /**
     * 备注
     */
    @TableField(value = "comments")
    private String comments;
}
