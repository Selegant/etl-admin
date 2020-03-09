package com.selegant.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "etl_permission")
public class EtlPermission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限ID
     */
    @TableField(value = "permission_id")
    private String permissionId;

    /**
     * 权限名称
     */
    @TableField(value = "permission_name")
    private String permissionName;

    public static final String COL_ID = "id";

    public static final String COL_PERMISSION_ID = "permission_id";

    public static final String COL_PERMISSION_NAME = "permission_name";
}