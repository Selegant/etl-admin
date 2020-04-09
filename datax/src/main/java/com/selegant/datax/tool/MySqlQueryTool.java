package com.selegant.datax.tool;

import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.selegant.datax.model.DataxDatasource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MySqlQueryTool extends BaseQueryTool {
    public MySqlQueryTool(DataxDatasource dataxDatasource) throws SQLException {
        super(dataxDatasource);
    }

    @Override
    public List<String> getTables() {
        List<String> results = new ArrayList<>();
        try {
            List<Entity> list = SqlExecutor.query(connection,"show tables",new EntityListHandler());
            list.forEach(entity -> {
                Set<String> stringSet = entity.getFieldNames();
                stringSet.forEach(s -> {
                    results.add(entity.getStr(s));
                });
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
            List<Entity> list = SqlExecutor.query(connection,"select column_name from information_schema.columns where table_name='"+tableName+"' order by column_name",new EntityListHandler());
            list.forEach(entity -> {
                Set<String> stringSet = entity.getFieldNames();
                stringSet.forEach(s -> {
                    results.add(entity.getStr(s));
                });
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
