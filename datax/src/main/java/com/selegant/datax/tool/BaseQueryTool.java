package com.selegant.datax.tool;

import cn.hutool.db.ds.simple.SimpleDataSource;
import com.selegant.datax.model.DataxDatasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseQueryTool implements QueryToolInterface {

    Connection connection;

    public BaseQueryTool(DataxDatasource dataxDatasource) throws SQLException {
        DataSource dataSource = new SimpleDataSource(dataxDatasource.getJdbcUrl(),dataxDatasource.getJdbcUsername(),dataxDatasource.getJdbcPassword());
        connection = dataSource.getConnection();
    }

}
