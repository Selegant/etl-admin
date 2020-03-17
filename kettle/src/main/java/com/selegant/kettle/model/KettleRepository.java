package com.selegant.kettle.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "kettle_repository")
public class KettleRepository {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer repositoryId;

    /**
     * 资源库名称
     */
    @TableField(value = "repository_name")
    private String repositoryName;

    /**
     * 登录用户名
     */
    @TableField(value = "repository_username")
    private String repositoryUsername;

    /**
     * 登录密码
     */
    @TableField(value = "repository_password")
    private String repositoryPassword;

    /**
     * 资源库数据库类型（MYSQL、ORACLE）
     */
    @TableField(value = "repository_type")
    private String repositoryType;

    /**
     * 资源库数据库访问模式（"Native", "ODBC", "OCI", "Plugin", "JNDI")
     */
    @TableField(value = "database_access")
    private String databaseAccess;

    /**
     * 资源库数据库主机名或者IP地址
     */
    @TableField(value = "database_host")
    private String databaseHost;

    /**
     * 资源库数据库端口号
     */
    @TableField(value = "database_port")
    private String databasePort;

    /**
     * 资源库数据库名称
     */
    @TableField(value = "database_name")
    private String databaseName;

    /**
     * 数据库登录账号
     */
    @TableField(value = "database_username")
    private String databaseUsername;

    /**
     * 数据库登录密码
     */
    @TableField(value = "database_password")
    private String databasePassword;

    /**
     * 添加时间
     */
    @TableField(value = "add_time")
    private Date addTime;

    /**
     * 添加者
     */
    @TableField(value = "add_user")
    private Integer addUser;

    /**
     * 编辑时间
     */
    @TableField(value = "edit_time")
    private Date editTime;

    /**
     * 编辑者
     */
    @TableField(value = "edit_user")
    private Integer editUser;

    /**
     * 是否删除（1：存在；0：删除）
     */
    @TableField(value = "del_flag")
    private Integer delFlag;
}
