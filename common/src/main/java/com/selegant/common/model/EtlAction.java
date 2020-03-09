package com.selegant.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "etl_action")
public class EtlAction {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作ID
     */
    @TableField(value = "action_id")
    private String actionId;

    /**
     * 操作
     */
    @TableField(value = "action")
    private String action;

    /**
     * 操作描述
     */
    @TableField(value = "action_desc")
    private String actionDesc;

    /**
     * 操作检察
     */
    @TableField(value = "default_check")
    private Boolean defaultCheck;

    public static final String COL_ID = "id";

    public static final String COL_ACTION_ID = "action_id";

    public static final String COL_ACTION = "action";

    public static final String COL_ACTION_DESC = "action_desc";

    public static final String COL_DEFAULT_CHECK = "default_check";
}