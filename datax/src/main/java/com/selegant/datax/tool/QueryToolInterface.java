package com.selegant.datax.tool;

import java.util.List;

public interface QueryToolInterface {

    List<String> getTables();

    List<String> getColumns(String tableName);
}
