package com.selegant.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "etl_action_permission")
public class EtlActionPermission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作ID
     */
    @TableField(value = "action_id")
    private String actionId;

    /**
     * 权限ID
     */
    @TableField(value = "permission_id")
    private String permissionId;

    public static final String COL_ID = "id";

    public static final String COL_ACTION_ID = "action_id";

    public static final String COL_PERMISSION_ID = "permission_id";
}