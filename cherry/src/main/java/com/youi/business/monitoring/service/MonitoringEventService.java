package com.youi.business.monitoring.service;

import com.youi.business.common.dao.HwVirtualMachineDao;
import com.youi.business.common.dao.HwX86Dao;
import com.youi.business.common.dao.ResMonitoringEventsDao;
import com.youi.business.common.entity.HW_VIRTUAL_MACHINE;
import com.youi.business.common.entity.HW_X86;
import com.youi.business.common.util.DateKit;
import com.youi.business.common.zabbix.api.ZabbixApi;
import com.youi.core.page.Page;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinliang on 2016/11/14.
 */
@Transactional
@Service
public class MonitoringEventService {
    @Autowired
    private ResMonitoringEventsDao resMonitoringEventsDao;
    @Autowired
    private HwX86Dao hwX86Dao;
    @Autowired
    private HwVirtualMachineDao hwVirtualMachineDao;

    public List<Map<String,Object>> getMonitoringEentList(Map<String, String> param) {
        String sql="select t1.eventid source_id,t1.type source_type,t1.hostid ,t1.value status,t1.clock last_clock ,t1.trigger_description message from res_monitoring_events t1 order by clock desc";
        Page page1 = resMonitoringEventsDao.pagedSQLQuery(sql,1,10);
        List<Map<String,Object>> list = (List<Map<String,Object>>)page1.getResult();
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        if(list!=null){
            for(Map<String,Object>m:list){
                Map<String,Object> m1 = new HashMap<String,Object>();
                m1.put("source_id",m.get("source_id"));
                String source_type = (String)m.get("source_type");
                m1.put("source_type",m.get("source_type"));

                m1.put("status",m.get("status"));
               String lastClock =  DateKit.date2StrSecond(new Date(Long.parseLong(String.valueOf(m.get("last_clock")))*1000));
                m1.put("lastClock",lastClock);
                Long hostid = Long.parseLong(String.valueOf(m.get("hostid")));
                String hostname ="";
                if(ZabbixApi.PRE_HOST_BIZ.equals(source_type)){
                    String s ="select server_name from res_biz_app_deploy where zabbixid=? limit 1";
                    List list1 = resMonitoringEventsDao.getJdbcTemplate().queryForList(s,new Object[]{hostid});
                    if(list1!=null&&list1.size()>0){
                        Map<String,String>tm1=(Map<String,String>) list1.get(0);
                        hostname = tm1.get("server_name");
                    }
                }else if(ZabbixApi.PRE_HOST_HOST.equals(source_type)){
                    String s ="select biz_type,biz_id from ip_address where zabbixid=? limit 1";
                    List list1 = resMonitoringEventsDao.getJdbcTemplate().queryForList(s,new Object[]{hostid});
                    if(list1!=null&&list1.size()>0){
                        Map<String,Object>tm = (Map<String,Object>)list1.get(0);
                        String biz_type = String.valueOf(m.get("biz_type"));
                        String biz_id = String.valueOf(m.get("biz_id"));
                        if("HW_X86".equals(biz_type)){
                            HW_X86 r1= hwX86Dao.get(Long.parseLong(biz_id));
                            if(r1!=null){
                                hostname = r1.getName();
                            }

                        }else if("HW_VIRTUAL_MACHINE".equals(biz_type)){
                            HW_VIRTUAL_MACHINE r1= hwVirtualMachineDao.get(Long.parseLong(biz_id));
                            if(r1!=null){
                                hostname= r1.getName();
                            }
                        }
                    }
                }else if(ZabbixApi.PRE_HOST_DB.equals(source_type)){
                    String s ="select name from hw_database where zabbixid=? limit 1";
                    List list1 = resMonitoringEventsDao.getJdbcTemplate().queryForList(s,new Object[]{hostid});
                    if(list1!=null&&list1.size()>0){
                        Map<String,String>tm1=(Map<String,String>) list1.get(0);
                        hostname = tm1.get("name");
                    }
                }
                if(!StringUtils.isEmpty(hostname)){
                    m1.put("message","["+hostname+"]"+m.get("message"));
                }else{
                    m1.put("message",m.get("message"));
                }
                m1.put("source_name",hostname);
                result.add(m1);
            }
        }
        return result;
    }
}
