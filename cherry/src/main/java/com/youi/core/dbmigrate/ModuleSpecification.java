package com.youi.core.dbmigrate;

public interface ModuleSpecification {
    boolean isEnabled();

    String getSchemaTable();

    String getSchemaLocation();

    boolean isInitData();

    String getDataTable();

    String getDataLocation();
}
