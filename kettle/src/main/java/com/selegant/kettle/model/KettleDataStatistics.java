package com.selegant.kettle.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * KETTLE传输数据统计
 * @author 薛云腾
 */
@Data
@TableName(value = "kettle_data_statistics")
@AllArgsConstructor
public class KettleDataStatistics {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 采集数据的目标库名称
     */
    private String databaseName;

    /**
     * 采集数据的目标库类型:MYSQL\HIVE\ORACLE..
     */
    private String databaseType;

    /**
     * 采集数据的目标表名称
     */
    private String tableName;

    /**
     * 采集类型:0=离线采集,1=实时采集
     */
    private Integer collectionType;

    /**
     * 数据新增的个数
     */
    private Integer dataInsertNum;

    /**
     * 历史数据修改的个数
     */
    private Integer dataUpdateNum;

    /**
     * 统计时间,天维度
     */
    private LocalDate statisticsTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 任务id
     */
    private Integer jobId;

    /**
     * 采集工具类型:0=datax,1=kettle
     */
    private Integer toolType;

    public KettleDataStatistics() {
        this.collectionType = BigDecimal.ZERO.intValue();
        this.toolType = BigDecimal.ONE.intValue();
        this.databaseType = "MYSQL";
    }
}
