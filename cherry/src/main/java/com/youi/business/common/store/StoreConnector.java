package com.youi.business.common.store;

import javax.activation.DataSource;
import java.util.List;

public interface StoreConnector {
    /**
     * 保存文件上传
     * @param valid_type 有效标记
     * @param dataSource 文件数据源
     * @return
     * @throws Exception
     */
    StoreDTO saveStore( DataSource dataSource,String valid_type)throws Exception;
    /**
     * 保存文件上传
     * @param yw_type 业务分类
     * @param yw_id  业务id
     * @param dataSource 文件数据源
     * @return
     * @throws Exception
     */
    StoreDTO saveStore(String yw_type,String yw_id, DataSource dataSource)throws Exception;

    /**
     * 保存文件上传
     * @param yw_type
     * @param yw_id
     * @param dataSource
     * @param valid_type
     * @return
     * @throws Exception
     */
    StoreDTO saveStore(String yw_type, String yw_id, DataSource dataSource,String valid_type) throws Exception;

    /**
     * 获得业务文件列表
     * @param yw_type 业务分类
     * @param yw_id 业务id
     * @return
     * @throws Exception
     */
    List<StoreDTO> getStore(String yw_type, String yw_id)throws Exception;

    /**
     * 获得文件信息
     * @param media_id
     * @return
     * @throws Exception
     */
    StoreDTO getStore(Long media_id)throws Exception;

    /**
     * 更新存储有效类型
     * @param media_id
     * @return
     * @throws Exception
     */
    void updateStoreValidType(Long media_id,String valid_type)throws Exception;


    /**
     *  删除业务文件可能多个
     * @param yw_type
     * @param yw_id
     * @throws Exception
     */
    void removeStore(String yw_type, String yw_id)throws Exception;

    /**
     * 删除单个业务文件
     * @param media_id
     * @throws Exception
     */
    void removeStore(Long media_id)throws Exception;
}
