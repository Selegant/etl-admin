package com.selegant.datax.tool;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;
import com.selegant.datax.exception.RdbmsException;
import com.selegant.datax.model.DataxDatasource;

import java.sql.SQLException;

public class QueryToolFactory {

    public static final BaseQueryTool getByDbType(DataxDatasource dataxDatasource) {
        //获取dbType
        String dbType = JdbcUtils.getDbType(dataxDatasource.getJdbcUrl(), dataxDatasource.getJdbcDriverClass());
        if (JdbcConstants.MYSQL.equalsIgnoreCase(dbType)) {
            return getMySQLQueryToolInstance(dataxDatasource);
        } else if (JdbcConstants.ORACLE.equalsIgnoreCase(dbType)) {
            return getOracleQueryToolInstance(dataxDatasource);
        }
        throw new UnsupportedOperationException("找不到该类型: ".concat(dbType));
    }

    private static BaseQueryTool getMySQLQueryToolInstance(DataxDatasource dataxDatasource) {
        try {
            return new MySqlQueryTool(dataxDatasource);
        } catch (Exception e) {
            throw RdbmsException.asConnException(JdbcConstants.MYSQL,
                    e,dataxDatasource.getJdbcUsername(),dataxDatasource.getDatasourceName());
        }
    }

    private static BaseQueryTool getOracleQueryToolInstance(DataxDatasource dataxDatasource) {
        try {
            return new OracleQueryTool(dataxDatasource);
        } catch (SQLException e) {
            throw RdbmsException.asConnException(JdbcConstants.ORACLE,
                    e,dataxDatasource.getJdbcUsername(),dataxDatasource.getDatasourceName());
        }
    }
}
