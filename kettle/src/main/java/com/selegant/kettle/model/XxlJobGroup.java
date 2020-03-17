package com.selegant.kettle.model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
@Data
@TableName(value = "xxl_job_group")
public class XxlJobGroup {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 执行器AppName
     */
    @TableField(value = "app_name")
    private String appName;

    /**
     * 执行器名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 排序
     */
    @TableField(value = "`order`")
    private Integer order;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    @TableField(value = "address_type")
    private Byte addressType;

    /**
     * 执行器地址列表，多地址逗号分隔
     */
    @TableField(value = "address_list")
    private String addressList;
}
