package com.youi.business.common.service;

import com.youi.business.common.dao.ResSettingsDao;
import com.youi.business.common.entity.RES_SETTINGS;
import com.youi.core.mapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/10/25.
 */
@Transactional
@Service
public class ResSettingsService {
    @Autowired
    private ResSettingsDao resSettingsDao;
    @Autowired
    private BeanMapper beanMapper;
    //时间监控最后更新时间挫
    public final static String SETTRINGS_MONITORING_GET_EVENTS_END_TIME_KEY="monitoring_get_events_end_time_key";
    //zabbix服务器同步最后时间挫
    public final static String SETTRINGS_MONITORING_SYNCHRONIZE_HOST_KEY="settrings_monitoring_synchronize_host_key";

    /**
     * 设置参数
     * @param key
     * @param value
     */
    public void put(String key,String value){
       RES_SETTINGS res_settings =  resSettingsDao.get(key);
        if(res_settings!=null){
            res_settings.setV(value);
            resSettingsDao.update(res_settings);
        }else{
             res_settings = new RES_SETTINGS(key,value);
            resSettingsDao.save(res_settings,null);
        }
    }

    /**
     * 获取参数
     * @param key
     * @return
     */
    public String get(String key){
        RES_SETTINGS res_settings =  resSettingsDao.get(key);
        if(res_settings!=null){
            return res_settings.getV();
        }
        return null;
    }

    public String remove(String key){
        RES_SETTINGS res_settings =  resSettingsDao.get(key);
        if(res_settings!=null){
            resSettingsDao.remove(res_settings);
            return res_settings.getV();
        }
        return null;
    }


}
