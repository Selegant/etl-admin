package com.selegant.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "etl_role")
public class EtlRole {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户描述
     */
    @TableField(value = "role_desc")
    private String roleDesc;

    /**
     * 角色状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建ID
     */
    @TableField(value = "creator_id")
    private String creatorId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

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

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_ROLE_DESC = "role_desc";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATOR_ID = "creator_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_DELETED = "deleted";

    public static final String COL_ROLE_ID = "role_id";
}