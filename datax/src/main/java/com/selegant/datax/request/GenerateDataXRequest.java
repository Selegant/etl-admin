package com.selegant.datax.request;

import lombok.Data;

import java.util.List;

@Data
public class GenerateDataXRequest {

    private String querySql;

    private String splitPk;

    private String where;

    private String preSql;

    private String originDatasourceId;

    private String targetDatasourceId;

    private String originTableName;

    private String targetTableName;

    private List<String> originColumns;

    private List<String> targetColumns;

    private String writeMode;

    private String batchSize;

    private Integer channel;

    private Integer record;

    private double percentage;

}
