package com.selegant.datax.tool;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.selegant.datax.model.DataxDatasource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class OracleQueryTool extends BaseQueryTool {
    public OracleQueryTool(DataxDatasource dataxDatasource) throws SQLException {
        super(dataxDatasource);
    }

    @Override
    public List<String> getTables() {
        List<String> results = new ArrayList<>();
        try {
            List<Entity> list = SqlExecutor.query(connection, "select table_name from user_tables", new EntityListHandler());
            list.forEach(entity -> {
                results.add(entity.getStr("TABLE_NAME"));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<String> getColumns(String tableName) {
        List<String> results = new ArrayList<>();
        try {
            List<Entity> list = SqlExecutor.query(connection, "SELECT column_name  FROM user_tab_columns where table_name = upper('" + tableName + "') order by column_name", new EntityListHandler());
            list.forEach(entity -> {
                results.add(entity.getStr("COLUMN_NAME"));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
