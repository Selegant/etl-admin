package com.selegant.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "etl_user")
public class EtlUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户名称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 电话号码
     */
    @TableField(value = "telephone")
    private String telephone;

    /**
     * 最后一次登陆IP
     */
    @TableField(value = "last_login_ip")
    private String lastLoginIp;

    /**
     * 最后一次登陆时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    /**
     * 创建Id
     */
    @TableField(value = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 商号
     */
    @TableField(value = "merchant_code")
    private String merchantCode;

    /**
     * 是否删除 0正常 1删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * access_token
     */
    @TableField(value = "access_token")
    private String accessToken;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_STATUS = "status";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_LAST_LOGIN_IP = "last_login_ip";

    public static final String COL_LAST_LOGIN_TIME = "last_login_time";

    public static final String COL_CREATOR_ID = "creator_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MERCHANT_CODE = "merchant_code";

    public static final String COL_DELETED = "deleted";

    public static final String COL_ROLE_ID = "role_id";
}
