package com.youi.core.store;

import javax.activation.DataSource;

public interface StoreHelper {

    StoreResult getStore(String file_path) throws Exception;

    void removeStore(String file_path) throws Exception;

    StoreResult saveStore(String model, DataSource dataSource) throws Exception;

}
