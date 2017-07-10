package com.youi.business.common.store;

import javax.activation.DataSource;

import javax.annotation.Resource;


import com.youi.business.common.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class StoreConnectorImpl implements StoreConnector {
    private Logger logger = LoggerFactory.getLogger(StoreConnectorImpl.class);
    @Autowired
    private StoreService storeService;

    @Override
    public StoreDTO saveStore(DataSource dataSource, String valid_type) throws Exception {
        return storeService.saveStore(dataSource,valid_type);
    }

    @Override
    public StoreDTO saveStore(String yw_type, String yw_id, DataSource dataSource) throws Exception {
        return storeService.saveStore(yw_type,yw_id,dataSource);
    }

    @Override
    public StoreDTO saveStore(String yw_type, String yw_id, DataSource dataSource,String valid_type) throws Exception {
        return storeService.saveStore(yw_type,yw_id,dataSource,valid_type);
    }

    @Override
    public List<StoreDTO> getStore(String yw_type, String yw_id) throws Exception {
        return storeService.getStore(yw_type,yw_id);
    }

    @Override
    public StoreDTO getStore(Long media_id) throws Exception {
        return storeService.getStore(media_id);
    }

    @Override
    public void updateStoreValidType(Long media_id,String valid_type) throws Exception {
         storeService.updateStoreValidType(media_id,valid_type);
    }

    @Override
    public void removeStore(String yw_type, String yw_id) throws Exception {
        storeService.removeStore(yw_type,yw_id);
    }

    @Override
    public void removeStore(Long media_id) throws Exception {
        storeService.removeStore(media_id);
    }
}
