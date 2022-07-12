package com.selegant.kettle.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName(value = "table_desc")
@NoArgsConstructor
@AllArgsConstructor
public class TableDesc {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "table_name")
    private String tableName;

    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "`number`")
    private String number;
}
