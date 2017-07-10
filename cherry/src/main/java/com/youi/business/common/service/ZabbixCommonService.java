package com.youi.business.common.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.dao.ResMonitoringHostTriggerDao;
import com.youi.business.common.dao.ResMonitoringHostitemsDao;
import com.youi.business.common.entity.RES_MONITORING_HOSTITEMS;
import com.youi.business.common.entity.RES_MONITORING_HOSTTRIGGER;
import com.youi.business.common.util.DateKit;
import com.youi.business.common.zabbix.api.Request;
import com.youi.business.common.zabbix.api.RequestBuilder;
import com.youi.business.common.zabbix.api.ZabbixApi;
import com.youi.business.common.zabbix.api.ZabbixKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinliang on 2016/11/12.
 */
@Transactional
@Service
public class ZabbixCommonService {
    @Autowired
    private ResMonitoringHostTriggerDao resMonitoringHostTriggerDao;
    @Autowired
    private ResMonitoringHostitemsDao resMonitoringHostitemsDao;
    @Autowired
    private ResSettingsService resSettingsService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * ***************修改主机状态**********************
     * 修改单个host的主机状态
     * @param hostid
     * @param type
     */
    public void updateHostTriggerStatus(Long hostid, String type) {
        JSONObject r1 = ZabbixApi.getHostTriggersByHostid(String.valueOf(hostid));
        JSONArray hostArray = r1.getJSONArray("result");
        if (hostArray.size() > 0) {
            JSONObject a1 = hostArray.getJSONObject(0);
            JSONArray ta = a1.getJSONArray("triggers");
            if (ta != null) {
                String[] triggeridArray = new String[ta.size()];
                for (int j = 0; j < ta.size(); j++) {
                    JSONObject b = ta.getJSONObject(j);
                    if("1".equals( b.getString("status"))){
                        continue;
                    }
                    String triggerid = b.getString("triggerid");
                    triggeridArray[j] = triggerid;
                }
                Map<String, Map<String,String>> mm = ZabbixApi.getTriggerInfo(triggeridArray);
                for(String triggerid:mm.keySet()){
                    Map<String,String> t = mm.get(triggerid);

                    String hql1="from RES_MONITORING_HOSTTRIGGER where hostid=? and triggerid=?";
                    List<RES_MONITORING_HOSTTRIGGER>  os = resMonitoringHostTriggerDao.createQuery(hql1,new Object[]{hostid,Long.parseLong(triggerid)}).list();

                    if (os != null&&os.size()>0) {
                        RES_MONITORING_HOSTTRIGGER  o= os.get(0);
                        o.setHostid(hostid);
                        o.setTriggerid(Long.parseLong(t.get("triggerid")));
                        o.setTrigger_key(t.get("comments"));
                        o.setLastchange(new Date());
                        o.setTrigger_message(t.get("description"));
                        o.setTrigger_status(Long.parseLong(t.get("value")));
                        o.setBiz_type(type);
                        resMonitoringHostTriggerDao.update(o);
                    } else {
                        RES_MONITORING_HOSTTRIGGER o = new RES_MONITORING_HOSTTRIGGER();
                        o.setHostid(hostid);
                        o.setTriggerid(Long.parseLong(t.get("triggerid")));
                        o.setTrigger_key(t.get("comments"));
                        o.setLastchange(new Date());
                        o.setTrigger_message(t.get("description"));
                        o.setTrigger_status(Long.parseLong(t.get("value")));
                        o.setBiz_type(type);
                        resMonitoringHostTriggerDao.save(o);
                    }
                }

            }
        }
    }


    /**
     * ***************修改主机状态**********************
     * 修改某组内所有的host主机状态
     * @param groupid
     * @param type
     */
    public void updateAllGroupHostTriggerStatus(String groupid, String type) {
        JSONObject r1 = ZabbixApi.getHostTriggersByGroupid(groupid);
        JSONArray hostArray = r1.getJSONArray("result");
        for (int i = 0; i < hostArray.size(); i++) {
            JSONObject a1 = hostArray.getJSONObject(0);
            Long hostid = a1.getLong("hostid");
            JSONArray ta = a1.getJSONArray("triggers");
            if (ta != null) {
                String[] triggeridArray = new String[ta.size()];
                for (int j = 0; j < ta.size(); j++) {
                    JSONObject b = ta.getJSONObject(j);
                    if("1".equals( b.getString("status"))){
                        continue;
                    }
                    String triggerid = b.getString("triggerid");
                    triggeridArray[j] = triggerid;
                }
                Map<String, Map<String,String>> mm = ZabbixApi.getTriggerInfo(triggeridArray);
                for(String triggerid:mm.keySet()){
                    Map<String,String> t = mm.get(triggerid);

                    String hql1="from RES_MONITORING_HOSTTRIGGER where hostid=? and triggerid=?";
                    List<RES_MONITORING_HOSTTRIGGER>  os = resMonitoringHostTriggerDao.createQuery(hql1,new Object[]{hostid,Long.parseLong(triggerid)}).list();

                    if (os != null&&os.size()>0) {
                        RES_MONITORING_HOSTTRIGGER  o= os.get(0);
                        o.setHostid(hostid);
                        o.setTriggerid(Long.parseLong(t.get("triggerid")));
                        o.setTrigger_key(t.get("comments"));
                        o.setLastchange(new Date());
                        o.setTrigger_message(t.get("description"));
                        o.setTrigger_status(Long.parseLong(t.get("value")));
                        o.setBiz_type(type);
                        resMonitoringHostTriggerDao.update(o);
                    } else {
                        RES_MONITORING_HOSTTRIGGER o = new RES_MONITORING_HOSTTRIGGER();
                        o.setHostid(hostid);
                        o.setTriggerid(Long.parseLong(t.get("triggerid")));
                        o.setTrigger_key(t.get("comments"));
                        o.setLastchange(new Date());
                        o.setTrigger_message(t.get("description"));
                        o.setTrigger_status(Long.parseLong(t.get("value")));
                        o.setBiz_type(type);
                        resMonitoringHostTriggerDao.save(o);
                    }
                }
            }
        }
    }

    /**
     * 删除主机状态
     * @param hostid
     */
    public void deleteHostTriggerStatus(Long hostid){
        String sql="delete from res_monitoring_hosttrigger where hostid=?";
        resMonitoringHostTriggerDao.getJdbcTemplate().update(sql,new Object[]{hostid});
    }


    /**
     * ***************修改主机item值**********************
     * 修改主机items值
     * @param groupid
     */
    public void updateHostItemsByGroupid(String groupid) {
        JSONObject r1 = ZabbixApi.getHostItemsByGroupid(String.valueOf(groupid));
        JSONArray itemArray = r1.getJSONArray("result");
        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            Long hostid  =item.getLong("hostid");
            Long itemid = item.getLong("itemid");
            String name = item.getString("name");
            String key_ = item.getString("key_");
            String lastvalue = item.getString("lastvalue");
            String hql1 = "from RES_MONITORING_HOSTITEMS where hostid=? and itemid=?";
            List<RES_MONITORING_HOSTITEMS> os = resMonitoringHostitemsDao.createQuery(hql1, new Object[]{hostid, itemid}).list();
            if (os != null && os.size() > 0) {
                RES_MONITORING_HOSTITEMS o = os.get(0);
                o.setHostid(hostid);
                o.setItem_key(key_);
                o.setItem_name(name);
                o.setItem_value(lastvalue);
                o.setLastchange(new Date());
                o.setItemid(itemid);
                resMonitoringHostitemsDao.update(o);
            } else {
                RES_MONITORING_HOSTITEMS o = new RES_MONITORING_HOSTITEMS();
                o.setHostid(hostid);
                o.setItem_key(key_);
                o.setItem_name(name);
                o.setItem_value(lastvalue);
                o.setLastchange(new Date());
                o.setItemid(itemid);
                resMonitoringHostitemsDao.save(o);
            }
        }
    }

    /**
     * ***************修改主机item值**********************
     * 修改主机items值
     * @param hostid
     */
    public void updateHostItems(Long hostid) {
        JSONObject r1 = ZabbixApi.getHostItemsByHostid(String.valueOf(hostid));
        JSONArray itemArray = r1.getJSONArray("result");
        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            //Long hostid  =itemArray.getLong("hostid");
            Long itemid = item.getLong("itemid");
            String name = item.getString("name");
            String key_ = item.getString("key_");
            String lastvalue = item.getString("lastvalue");
            String hql1 = "from RES_MONITORING_HOSTITEMS where hostid=? and itemid=?";
            List<RES_MONITORING_HOSTITEMS> os = resMonitoringHostitemsDao.createQuery(hql1, new Object[]{hostid, itemid}).list();
            if (os != null && os.size() > 0) {
                RES_MONITORING_HOSTITEMS o = os.get(0);
                o.setHostid(hostid);
                o.setItem_key(key_);
                o.setItem_name(name);
                o.setItem_value(lastvalue);
                o.setLastchange(new Date());
                o.setItemid(itemid);
                resMonitoringHostitemsDao.update(o);
            } else {
                RES_MONITORING_HOSTITEMS o = new RES_MONITORING_HOSTITEMS();
                o.setHostid(hostid);
                o.setItem_key(key_);
                o.setItem_name(name);
                o.setItem_value(lastvalue);
                o.setLastchange(new Date());
                o.setItemid(itemid);
                resMonitoringHostitemsDao.save(o);
            }
        }
    }


    /**
     * 删除主机items
     * @param hostid
     */
    public void deleteHostItems(Long hostid){
        String sql="delete from res_monitoring_hostitems where hostid=?";
        resMonitoringHostTriggerDao.getJdbcTemplate().update(sql,new Object[]{hostid});
    }


    public Map<String,Map<String,Object>> getHostItemsFromDB(String hostid) {
        String sql ="select item_key ,item_value  from res_monitoring_hostitems where hostid=?";
        List<Map<String,Object>> list =  resMonitoringHostitemsDao.createSQLQuery(sql,hostid).list();
        Map<String,Map<String,Object>> result = new HashMap<String,Map<String,Object>>();
        if(list!=null){
            for(Map<String,Object>m:list){
               String key = (String)m.get("item_key");
                String ckey =key;
                if(key.indexOf("cpu")>0){
                    ckey = "cpu";
                }else if(key.indexOf("mem")>0){
                    ckey = "memory";
                }else if(key.indexOf("vfs")>0){
                    ckey = "disk";
                }else if(key.indexOf("net")>0){
                    ckey = "net";
                }
                Map<String,Object> m1 = new HashMap<String,Object>();
                m1.put("key",ckey);
                m1.put("value",(String)m.get("item_value"));
                result.put(ckey,m1);
            }
        }
        return result;

    }

    public List<Map<String,Object>> getHostTriggersFromDB(String hostid) {
        String sql ="select trigger_key,trigger_status,trigger_message, date_format(lastchange,'%Y-%m-%d %H:%i:%s') lastchange from res_monitoring_hosttrigger where hostid=?";
        List<Map<String,Object>> list =  resMonitoringHostitemsDao.createSQLQuery(sql,hostid).list();
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                Map<String,Object> m = list.get(i);
                Map<String,Object> m1 = new HashMap<String,Object>();
                m1.put("key",m.get("trigger_key"));
                m1.put("status",m.get("trigger_status"));
                m1.put("lastClock",m.get("lastchange"));
                m1.put("message",m.get("trigger_message"));
                result.add(m1);
            }
        }
        return result;

    }

    /**
     * 获取最后主机最后更新时间挫
     * @return
     */
    public Long getHostLastUpdateTime() {
        String s =resSettingsService.get(ResSettingsService.SETTRINGS_MONITORING_SYNCHRONIZE_HOST_KEY);
        Long result =null;
        if(s!=null){
            result = Long.parseLong(s);
        }
        return result;
    }
    /**
     * 获取最后主机最后更新时间挫
     * @return
     */
    public String getHostLastUpdateTimeStringDate() {
        String s =resSettingsService.get(ResSettingsService.SETTRINGS_MONITORING_SYNCHRONIZE_HOST_KEY);
        String result =null;
        if(s!=null){
           Long ltimeUnit = Long.parseLong(s);
            Date d1 = new Date(ltimeUnit*1000);
            result = DateKit.date2StrSecond(d1);
        }
        return result;
    }

    /**
     * 获得业务系统的警告数量
     * @return
     */
    public Long getBizWarningNumber(){
        String sql="select count(1) num from res_bussiness_app t1" +
                " where t1.status='1'" +
                " and exists (select 1 from res_biz_app_deploy t2, res_monitoring_hosttrigger t3 where t1.id =t2.biz_app_id and t2.zabbixid=t3.hostid and t3.trigger_status='1' )";
        return (Long)jdbcTemplate.queryForObject(sql,Long.class);
    }
    /**
     * 获得业务系统的总数量
     * @return
     */
    public Long getBizTotalNumber(){
        String sql="select count(1) num from res_bussiness_app t1 where t1.status='1'" ;
        return (Long)jdbcTemplate.queryForObject(sql,Long.class);
    }


    /**
     * 获得数据库监控的警告数量
     * @return
     */
    public Long getDbWarningNumber(){
        String sql="select count(1) num from hw_database t1 " +
                "where t1.zabbix_switch='1' and exists (select 1 from res_monitoring_hosttrigger t2 where t1.zabbixid =t2.hostid and t2.trigger_status='1')";
        return (Long)jdbcTemplate.queryForObject(sql,Long.class);
    }
    /**
     * 获得数据库监控的总数量
     * @return
     */
    public Long getDbTotalNumber(){
        String sql="select count(1) num from hw_database t1 " ;
        return (Long)jdbcTemplate.queryForObject(sql,Long.class);
    }

    /**
     * 获得服务器监控的警告数量
     * @return
     */
    public Long getHostWarningNumber(){
        String sql="select count(1) num from ip_address t1 where biz_type in('HW_X86','HW_VIRTUAL_MACHINE') and ip_type='1' and zabbix_switch='1' and exists (select 1 from res_monitoring_hosttrigger t2 where t1.zabbixid =t2.hostid and t2.trigger_status='1')";
        return (Long)jdbcTemplate.queryForObject(sql,Long.class);
    }
    /**
     * 获得服务器监控的总数量
     * @return
     */
    public Long getHostTotalNumber(){
        String sql="select count(1) num from ip_address t1 where biz_type in('HW_X86','HW_VIRTUAL_MACHINE') and ip_type='1' ";
        return (Long)jdbcTemplate.queryForObject(sql,Long.class);
    }

}
