package com.youi.business.common.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.activation.DataSource;


import com.youi.business.common.dao.SysAttachmentDao;
import com.youi.business.common.entity.SYS_ATTACHMENT;
import com.youi.business.common.holder.UserHolder;
import com.youi.business.common.store.StoreDTO;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.store.StoreHelper;
import com.youi.core.store.StoreResult;
import com.youi.core.util.StringUtils;
import com.youi.core.util.ToolWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoreService {
    @Autowired
    private SysAttachmentDao sysAttachmentDao;
    @Autowired
    private StoreHelper storeHelper;
    @Autowired
    private BeanMapper beanMapper;
    private String baseDir;

    public StoreDTO saveStore(DataSource dataSource,String valid_type) throws Exception {
        return this.saveStore("untype",null,dataSource,valid_type);
    }

    public StoreDTO saveStore(String yw_type,String yw_id, DataSource dataSource) throws Exception {
        return this.saveStore(yw_type,yw_id,dataSource,"1");
    }
    public StoreDTO saveStore(String yw_type,String yw_id, DataSource dataSource,String valid_type) throws Exception {
        String show_name = dataSource.getName();
        String suffix =   getSuffix(show_name);
        String  file_name =  UUID.randomUUID() +"."+ suffix;
        String prefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String file_path =baseDir+ "/"+yw_type+ "/"+prefix + "/" + UUID.randomUUID() +"."+ suffix;
        //1.保存文件
        StoreResult rdataSource = this.storeHelper.saveStore(file_path,dataSource);
        SYS_ATTACHMENT storeInfo = new SYS_ATTACHMENT();
        storeInfo.setCreate_date(new Date());
        storeInfo.setFile_name(file_name);
        storeInfo.setShow_name(show_name);
        storeInfo.setFile_path( file_path);
        storeInfo.setFile_size( String.valueOf(rdataSource.getFile_size()));
        storeInfo.setFile_suffix(suffix);
        storeInfo.setCreate_user(UserHolder.getThreadLocalUser().getUser_id());
        if(!StringUtils.isEmpty(yw_id)){
            storeInfo.setBiz_id(Long.parseLong(yw_id));
        }
        storeInfo.setBiz_type(yw_type);
        storeInfo.setValid_type(valid_type);
        //2.保存附件表
        sysAttachmentDao.save(storeInfo);
        //3.返回结果集
        StoreDTO result = new StoreDTO();
        beanMapper.copy(storeInfo,result);
        result.setYw_id(String.valueOf(yw_id));
        result.setYw_type(yw_type);
        result.setDataSource(rdataSource.getDataSource());
        return result;
    }

    public List<StoreDTO> getStore(String yw_type, String yw_id) throws Exception {
       List<SYS_ATTACHMENT> attachements = sysAttachmentDao.find("from SYS_ATTACHMENT WHERE biz_type=? and biz_id=? and valid_type='1'",new Object[]{yw_type,Long.parseLong(yw_id)});
        List<StoreDTO> result = result = new ArrayList<StoreDTO>();
        if(attachements!=null){
            for(SYS_ATTACHMENT attachement:attachements){
                StoreDTO s = new StoreDTO();
                beanMapper.copy(attachement,s);
                s.setYw_id(String.valueOf(attachement.getBiz_id()));
                s.setYw_type(attachement.getBiz_type());
                StoreResult storeResult =this.storeHelper.getStore(attachement.getFile_path());
                if(storeResult!=null){
                    s.setDataSource(storeResult.getDataSource());
                }
                result.add(s);
            }
        }
        return result;
    }

    public StoreDTO getStore(Long attachment_id) throws Exception {
        StoreDTO s = new StoreDTO();
        SYS_ATTACHMENT media = sysAttachmentDao.get(attachment_id);
        if (media != null) {
            beanMapper.copy(media, s);
        }

        StoreResult storeResult =this.storeHelper.getStore(media.getFile_path());
        if(storeResult!=null){
            s.setDataSource(storeResult.getDataSource());
        }
        s.setYw_id(String.valueOf(media.getBiz_id()));
        s.setYw_type(media.getBiz_type());
        return s;
    }

    public void updateStoreValidType(Long media_id, String valid_type) {
        SYS_ATTACHMENT media = sysAttachmentDao.get(media_id);
        if (media != null) {
            media.setValid_type(valid_type);
            sysAttachmentDao.save(media);
        }
    }

    public void removeStore(String yw_type, String yw_id) throws Exception {
        List<SYS_ATTACHMENT> attachements = sysAttachmentDao.find("from SYS_ATTACHMENT WHERE biz_type=? and biz_id=?",new Object[]{yw_type,Long.parseLong(yw_id)});
        if(attachements!=null){
            for(SYS_ATTACHMENT media:attachements){
                this.storeHelper.removeStore(media.getFile_path());
                sysAttachmentDao.remove(media);
            }
        }
    }

    public void removeStore(Long media_id) throws Exception {
        SYS_ATTACHMENT media =  sysAttachmentDao.get(media_id);
        this.storeHelper.removeStore(media.getFile_path());
        sysAttachmentDao.remove(media);

    }



    public String getSuffix(String name) {
        int lastIndex = name.lastIndexOf(".");

        if (lastIndex != -1) {
            return name.substring(lastIndex+1);
        } else {
            return "";
        }
    }


    @Value("${store.baseDir}")
    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }


}
