package com.youi.business.common.scheduler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.Constants;
import com.youi.business.common.dao.ResMonitoringEventsDao;
import com.youi.business.common.dao.ResMonitoringHostTriggerDao;
import com.youi.business.common.entity.RES_MONITORING_EVENTS;
import com.youi.business.common.entity.RES_MONITORING_HOSTTRIGGER;
import com.youi.business.common.service.ResSettingsService;
import com.youi.business.common.service.ZabbixCommonService;
import com.youi.business.common.zabbix.api.Request;
import com.youi.business.common.zabbix.api.RequestBuilder;
import com.youi.business.common.zabbix.api.ZabbixApi;
import com.youi.business.common.zabbix.api.ZabbixKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class ZabbixMonitoringJob {
    private static Logger logger = LoggerFactory
            .getLogger(ZabbixMonitoringJob.class);
    @Autowired
    private ResMonitoringEventsDao resMonitoringEventsDao;
    @Autowired
    private ResSettingsService resSettingsService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ZabbixCommonService zabbixCommonService;
    @Autowired
    private ResMonitoringHostTriggerDao resMonitoringHostTriggerDao;


    private final static ZabbixMonitoringJob LOCK = new ZabbixMonitoringJob();
    /**
     * getevents任务是否在运行
     */
    private static boolean GET_EVENTS_RUNNING = false;
    /**
     * getevents任务是否在运行
     */
    private static boolean SYNCHRONIZE_HOST_RUNNING = false;
    /**
     * getevents任务是否在运行
     */
    private static boolean SYNCHRONIZE_TRIGGER_RUNNING = false;
    /**
     * getevents任务是否在运行
     */
    private static boolean SYNCHRONIZE_ITEMS_RUNNING = false;
/*
    */
/**
     * 同步业务系统主机任务是否在运行
     *//*

    private static boolean SYNCHRONIZE_BIZ_HOST_RUNNING = false;
    */
/**
     * 同步服务器任务是否在运行
     *//*

    private static boolean SYNCHRONIZE_HOST_HOST_RUNNING = false;
    */
/**
     * 同步数据库任务是否在运行
     *//*

    private static boolean SYNCHRONIZE_DB_HOST_RUNNING = false;
*/


    /**
     * 同步event
     * @throws IOException
     */
    //@Transactional
    @Scheduled(cron = "0 */1 * * * ?")
    public void synchronizeEvents() throws IOException {

        if(GET_EVENTS_RUNNING){return;}
        GET_EVENTS_RUNNING=true;
        logger.info("定时任务获取触发器事件开始");
        long currentUnix = System.currentTimeMillis()/1000;

        //1.获得所有的event事件
        JSONArray events = new JSONArray();
        if(true){
            JSONObject p1 = new JSONObject();
            p1.put("output",new String[]{"eventid","objectid","clock","value"});
            p1.put("selectHosts",new String[]{"hostid","host"});
            // p1.put("selectRelatedObject",new String[]{"triggerid","description","comments","priority","value"});
            p1.put("source",0);
            p1.put("object",0);
            p1.put("groupids",  new String[]{ZabbixApi.getGroup_biz_id(),ZabbixApi.getGroup_db_id(),ZabbixApi.getGroup_host_id()});
            long time_from =0;
            String lastTime = resSettingsService.get(resSettingsService.SETTRINGS_MONITORING_GET_EVENTS_END_TIME_KEY);
            if(lastTime!=null){
                time_from = Long.parseLong(lastTime)+1;
            }
            long time_till = currentUnix;
            p1.put("time_from",time_from);
            p1.put("time_till",time_till);
            Request request1 = RequestBuilder.newBuilder()
                    .method("event.get")
                    .build();
            request1.setParams(p1);
            JSONObject r = ZabbixKit.call(request1);
            events = r.getJSONArray("result");
        }
        //获取所有的trigger信息
        Set<String> triggeridList = new HashSet<String>(events.size());
        for(int i=0;i<events.size();i++){
            JSONObject event = events.getJSONObject(i);
           String triggerid = event.getString("objectid");
            triggeridList.add(triggerid);
        }
            Map<String ,Map<String,String>> triggers = ZabbixApi.getTriggerInfo(triggeridList.toArray());

        for(int i=0;i<events.size();i++){
            JSONObject event = events.getJSONObject(i);
            if(event.getJSONArray("hosts").size()==0){continue;}
            Long hostid =event.getJSONArray("hosts").getJSONObject(0).getLong("hostid");
            String host =event.getJSONArray("hosts").getJSONObject(0).getString("host");
            Long triggerid =  event.getLong("objectid");
            //添加events记录
            RES_MONITORING_EVENTS res_monitoring_events = new RES_MONITORING_EVENTS();
            res_monitoring_events.setClock(event.getLong("clock"));
            res_monitoring_events.setCtime(new Date());
            res_monitoring_events.setEventid(event.getLong("eventid"));
            res_monitoring_events.setHostid(hostid);
            res_monitoring_events.setObjectid(triggerid);
            res_monitoring_events.setValue(event.getLongValue("value"));
            Map<String,String> triggerInfo = triggers.get(String.valueOf(triggerid));
                res_monitoring_events.setTrigger_comments(triggerInfo.get("comments"));
                res_monitoring_events.setTrigger_description(triggerInfo.get("description"));


            String type =getHostType(host);
            res_monitoring_events.setType(type);
            resMonitoringEventsDao.save(res_monitoring_events);

                //更新服务器状态
                String hql1="from RES_MONITORING_HOSTTRIGGER where hostid=? and triggerid=?";
                List<RES_MONITORING_HOSTTRIGGER>  os = resMonitoringHostTriggerDao.createQuery(hql1,new Object[]{hostid,triggerid}).list();

                if (os != null&&os.size()>0) {
                    RES_MONITORING_HOSTTRIGGER  o= os.get(0);
                    o.setHostid(hostid);
                    o.setTriggerid(Long.parseLong(triggerInfo.get("triggerid")));
                    o.setTrigger_key(triggerInfo.get("comments"));
                    o.setLastchange(new Date());
                    o.setTrigger_message(triggerInfo.get("description"));
                    o.setTrigger_status(Long.parseLong(triggerInfo.get("value")));
                    o.setBiz_type(type);
                    resMonitoringHostTriggerDao.update(o);
                } else {
                    RES_MONITORING_HOSTTRIGGER o = new RES_MONITORING_HOSTTRIGGER();
                    o.setHostid(hostid);
                    o.setTriggerid(Long.parseLong(triggerInfo.get("triggerid")));
                    o.setTrigger_key(triggerInfo.get("comments"));
                    o.setLastchange(new Date());
                    o.setTrigger_message(triggerInfo.get("description"));
                    o.setTrigger_status(Long.parseLong(triggerInfo.get("value")));
                    o.setBiz_type(type);
                    resMonitoringHostTriggerDao.save(o);
                }
           //***************修改主机items值**********************/
            //zabbixCommonService.updateHostItems(hostid);
        }
        //更新主机最后同步时间挫
        resSettingsService.put(ResSettingsService.SETTRINGS_MONITORING_SYNCHRONIZE_HOST_KEY,String.valueOf(System.currentTimeMillis()/1000));
        resSettingsService.put(resSettingsService.SETTRINGS_MONITORING_GET_EVENTS_END_TIME_KEY,String.valueOf(currentUnix));
        GET_EVENTS_RUNNING=false;
        logger.info("定时任务获取触发器事件结束");
    }

    private String getHostType(String host){
        String type ="";
        if(host.startsWith(ZabbixApi.PRE_HOST_BIZ)){
            type = ZabbixApi.PRE_HOST_BIZ;
        }else if(host.startsWith(ZabbixApi.PRE_HOST_DB)){
            type = ZabbixApi.PRE_HOST_DB;
        }else if(host.startsWith(ZabbixApi.PRE_HOST_HOST)){
            type = ZabbixApi.PRE_HOST_HOST;
        }
        return type;

    }

    //@Transactional
    @Scheduled(cron = "0 */5 * * * ?")
    public void synchronizeHost2Zabbix(){
        if(SYNCHRONIZE_HOST_RUNNING){return;}
        SYNCHRONIZE_HOST_RUNNING=true;
       //业务系统主机
        synchronizeBizHost();
        //业务数据库主机
        synchronizeDbHost();
        //业务主机
        synchronizeHostHost();
        //更新主机最后同步时间挫
        resSettingsService.put(ResSettingsService.SETTRINGS_MONITORING_SYNCHRONIZE_HOST_KEY,String.valueOf(System.currentTimeMillis()/1000));
        SYNCHRONIZE_HOST_RUNNING=false;
    }
    /**
     * 同步业务系统zabbix主机
     */
    private void synchronizeBizHost(){
        //1.查询所有的业务主机
        String selectNeedMonitoringSql = "select id,ip,port,zabbixid hostid from res_biz_app_deploy t1 where deploy_type in('2','3') and zabbix_switch='1' " +
                "and exists (select 1 from res_bussiness_app t2 where t1.biz_app_id=t2.id and t2.status='1')";
        List<Map<String,Object>> bizHostList = jdbcTemplate.queryForList(selectNeedMonitoringSql);
        Map<String,Map<String,Object>> bizHostMap = new HashMap<String,Map<String,Object>>();
        if(bizHostList!=null){
            for(Map<String,Object> m:bizHostList){
                String key = ZabbixApi.PRE_HOST_BIZ+"_"+m.get("ip")+"_"+m.get("port")+"_"+m.get("id");
                bizHostMap.put(key,m);
            }
        }
        //2.查询zabbix目前业务系统组已经在监控的主机
        Map<String,Long> zabbixHosts = new HashMap<String,Long>();
        {
            JSONObject params = new JSONObject();
            params.put("groupids", ZabbixApi.getGroup_biz_id());
            params.put("output",new String[]{"hostid","host","available"});//new String[]{"hostid","host","available","snmp_available","status"}
           // params.put("selectItems",new String[]{"itemid","key_","name","lastclock","lastvalue"});
           // params.put("selectInterfaces",new String[]{"type","ip","port"});
            Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
            getRequest.setParams(params);
            JSONObject r = ZabbixKit.call(getRequest);
            JSONArray hostArray = r.getJSONArray("result");
            for(int i=0;i<hostArray.size();i++){
               JSONObject host = hostArray.getJSONObject(i);
               String hostname =  host.getString("host");
                Long hostid = host.getLong("hostid");
                zabbixHosts.put(hostname,hostid);
            }
        }
        //查找出需要增加的主机，条件{业务存在而zabbix不存在的}
        //查找出需要删除的主机，条件{业务不存在而zabbix存在的}
        //需要删除的主机
        Set<Long> deleteHostArray = new HashSet<Long>();
        //需要增加的主机
        List<Map<String,Object>> addHostArray = new ArrayList<Map<String,Object>>();
        for(String key:bizHostMap.keySet()){
            if(!zabbixHosts.containsKey(key)||!(bizHostMap.get(key).get("hostid").equals(zabbixHosts.get(key)))){
                addHostArray.add(bizHostMap.get(key));
            }
        }
        //需要删除的主机
        for(String key:zabbixHosts.keySet()){
            if(!bizHostMap.containsKey(key)||!(zabbixHosts.get(key).equals(bizHostMap.get(key).get("hostid")))){
                deleteHostArray.add(zabbixHosts.get(key));
            }
        }
        for(int i=0;i<addHostArray.size();i++){
            Map<String,Object> m =  addHostArray.get(i);
            String id =  String.valueOf(m.get("id"));
            String ip =  String.valueOf(m.get("ip"));
            String port =  String.valueOf(m.get("port"));
            //创建主机
            JSONObject r = ZabbixApi.createBizZabbixHost(id,ip,port,ZabbixApi.getTemplate_biz_ids(ZabbixApi.TEMPLATE_TYPE_BIZ_LINUX));
            if(r.getJSONObject("result")==null){continue;}
            JSONArray hostids =  r.getJSONObject("result").getJSONArray("hostids");
            Long hostid=null;
            if(hostids.size()>0){
                hostid = hostids.getLong(0);
            }
            //更新业务表zabbixid，zabbixswitch字段值
            jdbcTemplate.update("update res_biz_app_deploy set zabbixid=? , zabbix_switch='1' where id=?",new Object[]{hostid,id});
            //更新主机触发器监控状态
            zabbixCommonService.updateHostTriggerStatus(hostid,ZabbixApi.PRE_HOST_BIZ);
            //***************修改主机items值**********************/
            zabbixCommonService.updateHostItems(hostid);
        }
        for(Iterator<Long> ite=deleteHostArray.iterator();ite.hasNext();){
              Long hostid = ite.next();
            //删除主机
            JSONObject r = ZabbixApi.deleteZabbixHost(hostid);
            //删除主机触发器监控状态
            zabbixCommonService.deleteHostTriggerStatus(hostid);
            //删除主机items
            zabbixCommonService.deleteHostItems(hostid);
        }
    }


    /**
     * 每个小时同步一次业务系统zabbix主机
     */
    private void synchronizeHostHost(){
        //1.查询所有的业务主机
        String selectNeedMonitoringSql = "select id,ip_add ip,zabbixid hostid from ip_address where biz_type in('HW_X86','HW_VIRTUAL_MACHINE') and ip_type='1' and zabbix_switch='1'";
        List<Map<String,Object>> bizHostList = jdbcTemplate.queryForList(selectNeedMonitoringSql);
        Map<String,Map<String,Object>> bizHostMap = new HashMap<String,Map<String,Object>>();
        if(bizHostList!=null){
            for(Map<String,Object> m:bizHostList){
                String key = ZabbixApi.PRE_HOST_HOST+"_"+m.get("ip")+"_"+m.get("id");
                bizHostMap.put(key,m);
            }
        }
        //2.查询zabbix目前业务系统组已经在监控的主机
        Map<String,Long> zabbixHosts = new HashMap<String,Long>();
        {
            JSONObject params = new JSONObject();
            params.put("groupids", ZabbixApi.getGroup_host_id());
            params.put("output",new String[]{"hostid","host","available"});//new String[]{"hostid","host","available","snmp_available","status"}
            // params.put("selectItems",new String[]{"itemid","key_","name","lastclock","lastvalue"});
            // params.put("selectInterfaces",new String[]{"type","ip","port"});
            Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
            getRequest.setParams(params);
            JSONObject r = ZabbixKit.call(getRequest);
            JSONArray hostArray = r.getJSONArray("result");
            for(int i=0;i<hostArray.size();i++){
                JSONObject host = hostArray.getJSONObject(i);
                String hostname =  host.getString("host");
                Long hostid = host.getLong("hostid");
                zabbixHosts.put(hostname,hostid);
            }
        }
        //查找出需要增加的主机，条件{业务存在而zabbix不存在的}
        //查找出需要删除的主机，条件{业务不存在而zabbix存在的}
        //需要删除的主机
        Set<Long> deleteHostArray = new HashSet<Long>();
        //需要增加的主机
        List<Map<String,Object>> addHostArray = new ArrayList<Map<String,Object>>();
        for(String key:bizHostMap.keySet()){
            if(!zabbixHosts.containsKey(key)||!(bizHostMap.get(key).get("hostid").equals(zabbixHosts.get(key)))){
                addHostArray.add(bizHostMap.get(key));
            }
        }
        //需要删除的主机
        for(String key:zabbixHosts.keySet()){
            if(!bizHostMap.containsKey(key)||!(zabbixHosts.get(key).equals(bizHostMap.get(key).get("hostid")))){
                deleteHostArray.add(zabbixHosts.get(key));
            }
        }
        for(int i=0;i<addHostArray.size();i++){
            Map<String,Object> m =  addHostArray.get(i);
            String id =  String.valueOf(m.get("id"));
            String ip =  String.valueOf(m.get("ip"));
            String port =  String.valueOf(m.get("port"));
            //创建主机
            JSONObject r = ZabbixApi.createHostZabbixHost(id,ip,ZabbixApi.getTemplate_host_ids(ZabbixApi.TEMPLATE_TYPE_HOST_LINUX));
            if(r.getJSONObject("result")==null){continue;}
            JSONArray hostids =  r.getJSONObject("result").getJSONArray("hostids");
            Long hostid=null;
            if(hostids.size()>0){
                hostid = hostids.getLong(0);
            }
            //更新业务表zabbixid，zabbixswitch字段值
            jdbcTemplate.update("update ip_address set zabbixid=? , zabbix_switch='1' where id=?",new Object[]{hostid,id});
            //更新主机触发器监控状态
            zabbixCommonService.updateHostTriggerStatus(hostid,ZabbixApi.PRE_HOST_HOST);
            //***************修改主机items值**********************/
            zabbixCommonService.updateHostItems(hostid);
        }
        for(Iterator<Long> ite=deleteHostArray.iterator();ite.hasNext();){
            Long hostid = ite.next();
            //删除主机
            JSONObject r = ZabbixApi.deleteZabbixHost(hostid);
            //删除主机触发器监控状态
            zabbixCommonService.deleteHostTriggerStatus(hostid);
            //删除主机items
            zabbixCommonService.deleteHostItems(hostid);
        }
    }

    /**
     * 每个小时同步数据库zabbix主机
     */
    private void synchronizeDbHost(){
        //1.查询所有的业务主机
        String selectNeedMonitoringSql = "select id,address ip,port,zabbixid hostid from hw_database where   zabbix_switch='1'";
        List<Map<String,Object>> bizHostList = jdbcTemplate.queryForList(selectNeedMonitoringSql);
        Map<String,Map<String,Object>> bizHostMap = new HashMap<String,Map<String,Object>>();
        if(bizHostList!=null){
            for(Map<String,Object> m:bizHostList){
                String key = ZabbixApi.PRE_HOST_DB+"_"+m.get("ip")+"_"+m.get("id");
                bizHostMap.put(key,m);
            }
        }
        //2.查询zabbix目前业务系统组已经在监控的主机
        Map<String,Long> zabbixHosts = new HashMap<String,Long>();
        {
            JSONObject params = new JSONObject();
            params.put("groupids", ZabbixApi.getGroup_db_id());
            params.put("output",new String[]{"hostid","host","available"});//new String[]{"hostid","host","available","snmp_available","status"}
            // params.put("selectItems",new String[]{"itemid","key_","name","lastclock","lastvalue"});
            // params.put("selectInterfaces",new String[]{"type","ip","port"});
            Request getRequest = RequestBuilder.newBuilder().method("host.get").build();
            getRequest.setParams(params);
            JSONObject r = ZabbixKit.call(getRequest);
            JSONArray hostArray = r.getJSONArray("result");
            for(int i=0;i<hostArray.size();i++){
                JSONObject host = hostArray.getJSONObject(i);
                String hostname =  host.getString("host");
                Long hostid = host.getLong("hostid");
                zabbixHosts.put(hostname,hostid);
            }
        }
        //查找出需要增加的主机，条件{业务存在而zabbix不存在的}
        //查找出需要删除的主机，条件{业务不存在而zabbix存在的}
        //需要删除的主机
        Set<Long> deleteHostArray = new HashSet<Long>();
        //需要增加的主机
        List<Map<String,Object>> addHostArray = new ArrayList<Map<String,Object>>();
        for(String key:bizHostMap.keySet()){
            if(!zabbixHosts.containsKey(key)||!(bizHostMap.get(key).get("hostid").equals(zabbixHosts.get(key)))){
                addHostArray.add(bizHostMap.get(key));
            }
        }
        //需要删除的主机
        for(String key:zabbixHosts.keySet()){
            if(!bizHostMap.containsKey(key)||!(zabbixHosts.get(key).equals(bizHostMap.get(key).get("hostid")))){
                deleteHostArray.add(zabbixHosts.get(key));
            }
        }
        for(int i=0;i<addHostArray.size();i++){
            Map<String,Object> m =  addHostArray.get(i);
            String id =  String.valueOf(m.get("id"));
            String ip =  String.valueOf(m.get("ip"));
            String port =  String.valueOf(m.get("port"));
            //创建主机
            JSONObject r = ZabbixApi.createDbZabbixHost(id,ip,port,ZabbixApi.getTemplate_db_ids(ZabbixApi.TEMPLATE_TYPE_DB_MYSQL));
            if(r.getJSONObject("result")==null){continue;}
            JSONArray hostids =  r.getJSONObject("result").getJSONArray("hostids");
            Long hostid=null;
            if(hostids.size()>0){
                hostid = hostids.getLong(0);
            }
            //更新业务表zabbixid，zabbixswitch字段值
            jdbcTemplate.update("update hw_database set zabbixid=? , zabbix_switch='1' where id=?",new Object[]{hostid,id});
            //更新主机触发器监控状态
            zabbixCommonService.updateHostTriggerStatus(hostid,ZabbixApi.PRE_HOST_DB);
            //***************修改主机items值**********************/
             zabbixCommonService.updateHostItems(hostid);
        }
        for(Iterator<Long> ite=deleteHostArray.iterator();ite.hasNext();){
            Long hostid = ite.next();
            //删除主机
            JSONObject r = ZabbixApi.deleteZabbixHost(hostid);
            //删除主机触发器监控状态
            zabbixCommonService.deleteHostTriggerStatus(hostid);
            //删除主机items
            zabbixCommonService.deleteHostItems(hostid);
        }
    }

    /**
     * 每天同步更新主机状态,主要是为了增加
     */
   /* @Scheduled(cron = "0 0 1 * * ?")
    public void updateZabbixHostStatus(){
        //biz
        if(true){
            zabbixCommonService.updateAllGroupHostTriggerStatus(ZabbixApi.getGroup_biz_id(),ZabbixApi.PRE_HOST_BIZ);
            zabbixCommonService.updateHostItemsByGroupid(ZabbixApi.getGroup_biz_id());
        }
        //host
        if(true){
            zabbixCommonService.updateAllGroupHostTriggerStatus(ZabbixApi.getGroup_host_id(),ZabbixApi.PRE_HOST_HOST);
            zabbixCommonService.updateHostItemsByGroupid(ZabbixApi.getGroup_host_id());
        }
        //db
        if(true) {
            zabbixCommonService.updateAllGroupHostTriggerStatus(ZabbixApi.getGroup_db_id(),ZabbixApi.PRE_HOST_DB);
            zabbixCommonService.updateHostItemsByGroupid(ZabbixApi.getGroup_db_id());
        }

    }*/
    //@Transactional
    @Scheduled(cron = "0 */5 * * * ?")
    public void synchronizeHostItems(){
        if(SYNCHRONIZE_ITEMS_RUNNING){return;}
        SYNCHRONIZE_ITEMS_RUNNING=true;

        //biz
            zabbixCommonService.updateHostItemsByGroupid(ZabbixApi.getGroup_biz_id());
        //host
            zabbixCommonService.updateHostItemsByGroupid(ZabbixApi.getGroup_host_id());
        //db
            zabbixCommonService.updateHostItemsByGroupid(ZabbixApi.getGroup_db_id());
        SYNCHRONIZE_ITEMS_RUNNING=false;

    }
    //@Transactional
    @Scheduled(cron = "0 */5 * * * ?")
    public void synchronizeHostTriggerStatus(){
        if(SYNCHRONIZE_TRIGGER_RUNNING){return;}
        SYNCHRONIZE_TRIGGER_RUNNING=true;
        //biz
        zabbixCommonService.updateAllGroupHostTriggerStatus(ZabbixApi.getGroup_biz_id(),ZabbixApi.PRE_HOST_BIZ);
        //host
        zabbixCommonService.updateAllGroupHostTriggerStatus(ZabbixApi.getGroup_host_id(),ZabbixApi.PRE_HOST_HOST);
        //db
        zabbixCommonService.updateAllGroupHostTriggerStatus(ZabbixApi.getGroup_db_id(),ZabbixApi.PRE_HOST_DB);
        SYNCHRONIZE_TRIGGER_RUNNING=false;
    }









}
