package com.paularagones.moode.Models;

/**
 * Created by Paul.Aragones on 3/3/2016.
 */
public abstract class DbRequest {

    private String tableName;
    private String columnName;
    private String tableID;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }
}
