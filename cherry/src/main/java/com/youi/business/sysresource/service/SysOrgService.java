package com.youi.business.sysresource.service;

import com.youi.business.common.dao.SysOrgInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/26.
 */
@Service
public class SysOrgService {
    @Autowired
    private SysOrgInfoDao sysOrgInfoDao;
    /**
     * 获取系统org信息
     * @return
     */
    public List<Map<String,Object>> getSysOrgList() {
       List list = sysOrgInfoDao.createSQLQuery("SELECT  ORG_ID CODE,ORG_NAME VALUE  FROM SYS_ORG_INFO WHERE VALID_TYPE='1'").list();
       return list;
    }
}
