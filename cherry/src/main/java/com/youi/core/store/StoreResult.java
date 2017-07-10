package com.youi.core.store;

import javax.activation.DataSource;

public class StoreResult {
    private long file_size;
    private DataSource dataSource;

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
