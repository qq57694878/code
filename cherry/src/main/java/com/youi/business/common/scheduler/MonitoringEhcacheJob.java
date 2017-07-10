package com.youi.business.common.scheduler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.service.ResSettingsService;
import com.youi.business.common.util.DevelopKit;
import com.youi.business.common.zabbix.api.ZabbixV2Api;
import com.youi.business.monitoring.websocket.MessageSenderListener;
import com.youi.business.monitoring.websocket.MyWebSocketSockjsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
@Service
public class MonitoringEhcacheJob {
    private static Logger logger = LoggerFactory.getLogger(MonitoringEhcacheJob.class);
    @Autowired
    private ResSettingsService resSettingsService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MessageSenderListener messageSenderListener;


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

    private final static int PERIOD_SECOND=10;
    private final static int HOST_ITEMS_SIZE=3600*1/PERIOD_SECOND;

    private double vcenter_cpu_freq;

    public double getVcenter_cpu_freq() {
        return vcenter_cpu_freq;
    }
    @Value("${monitoring.vcenter_cpu_freq}")
    public void setVcenter_cpu_freq(double vcenter_cpu_freq) {
        this.vcenter_cpu_freq = vcenter_cpu_freq;
    }

    /**
     *
     * 每1分钟同步一次服务器监控items
     */
    @Scheduled(cron = "*/10 * * * * ?")
   public void synchronizeHostItems(){
        JSONObject sendObj = new JSONObject();
        JSONArray hostids = new JSONArray();
        Long currentTime = System.currentTimeMillis();
        //1.单独的x86主机
        if(true){
            JSONObject r =   ZabbixV2Api.getHostAndItemsByGroupid(ZabbixV2Api.getGroup_host_id());
            JSONArray hosts = r.getJSONArray("result");
            Cache cache = CacheMonitoringUtil.getCache("host_items");
            for(int i=0;i<hosts.size();i++){
                JSONObject host = hosts.getJSONObject(i);
                String key =host.getString("hostid");
                hostids.add(key);
                JSONObject convertObject = convertHostItems(host);
                Cache.ValueWrapper valueWrapper = cache.get(key);
                BlockingDeque deque = null;
                if(valueWrapper==null){
                    deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                    cache.put(key,deque);
                }else{
                    deque = (BlockingDeque)valueWrapper.get();
                }
                if(!deque.offer(convertObject)){
                    deque.pollFirst();
                    deque.addLast(convertObject);
                }
            }
        }
        //2.vcenter下的x86主机
        if(true){
            JSONObject r =   ZabbixV2Api.getVcenterHostAndItemsByGroupid(ZabbixV2Api.getGroup_vcenter_x86_id());
            JSONArray hosts = r.getJSONArray("result");
            Cache cache = CacheMonitoringUtil.getCache("host_items");
            for(int i=0;i<hosts.size();i++){
                JSONObject host = hosts.getJSONObject(i);
                String key =host.getString("hostid");
                hostids.add(key);
                JSONObject convertObject = convertVcenterHostItems(host);
                Cache.ValueWrapper valueWrapper = cache.get(key);
                BlockingDeque deque = null;
                if(valueWrapper==null){
                    deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                    cache.put(key,deque);
                }else{
                    deque = (BlockingDeque)valueWrapper.get();
                }
                if(!deque.offer(convertObject)){
                    deque.pollFirst();
                    deque.addLast(convertObject);
                }
            }
        }
        sendObj.put("hostids",hostids);
        sendObj.put("currentTime",currentTime);
        try {
            messageSenderListener.sendMessage("/subscribe_host_items",sendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private JSONObject convertHostItems(JSONObject host){
        JSONObject result = new JSONObject();
        result.put("id",host.getString("hostid"));
        result.put("name",host.getString("name"));
        double upload_flow=0;
        double download_flow=0;
        String cpu="0";
        String memory_used ="0";
        String memory_total ="0";
        JSONArray items = host.getJSONArray("items");
        for(int i=0;i<items.size();i++){
            JSONObject item =items.getJSONObject(i);
            if("1".equals(item.getString("status"))){
                continue;
            }
            if(item.getString("key_").startsWith("net.if.in")){
                download_flow   += Double.parseDouble(item.getString("lastvalue"));
            }
            if(item.getString("key_").startsWith("net.if.out")){
                upload_flow += Double.parseDouble(item.getString("lastvalue"));
            }
            else if(item.getString("key_").startsWith("system.cpu.util")){
                Double d= item.getDouble("lastvalue");
                //d=d*100;
                DecimalFormat df= new DecimalFormat("#####0.00");
                cpu = df.format(d);
            }
            else if("vm.memory.size[used]".equals(item.getString("key_"))){
                Double d= item.getDouble("lastvalue");
                d = d/1024/1024/1024;
                DecimalFormat df= new DecimalFormat("#####0.00");
                memory_used = df.format(d);
            }
            else if("vm.memory.size[total]".equals(item.getString("key_"))){
                Double d= item.getDouble("lastvalue");
                d = d/1024/1024/1024;
                DecimalFormat df= new DecimalFormat("#####0.00");
                memory_total = df.format(d);
            }
        }
        result.put("cpu",cpu+"");
        result.put("memory_used",memory_used);
        result.put("memory_total",memory_total);
        result.put("upload_flow", DevelopKit.convertFormatNetworkSpeed(upload_flow));
        result.put("download_flow",  DevelopKit.convertFormatNetworkSpeed(download_flow));
        result.put("time",System.currentTimeMillis());
        result.put("signal","0".equals(host.getString("status"))?"1":"0");
        return result;
    }

    private JSONObject convertVcenterHostItems(JSONObject host){
        JSONObject result = new JSONObject();
        result.put("id",host.getString("hostid"));
        result.put("name",host.getString("name"));
        double upload_flow=0;
        double download_flow=0;
        String cpu="0";
        String memory_used ="0";
        String memory_total ="0";
        int cpu_cores =1;//cpu核心数
        double cpu_freq =1;//每个cpu核心频率
        double cpu_usage =0;//cpu已用赫兹
        JSONArray items = host.getJSONArray("items");
        for(int i=0;i<items.size();i++){
            JSONObject item =items.getJSONObject(i);
            if("1".equals(item.getString("status"))){
                continue;
            }
           String key_ =  item.getString("key_");
            if(key_.startsWith("vmware.hv.hw.cpu.num")){
                cpu_cores = item.getInteger("lastvalue");
            }
            if(key_.startsWith("vmware.hv.cpu.usage")){
                cpu_usage = item.getDouble("lastvalue");
            }
            if(key_.startsWith("vmware.hv.hw.cpu.freq")){
                cpu_freq = item.getDouble("lastvalue");
            }
            if(key_.startsWith("vmware.hv.network.out")){
                upload_flow = Double.parseDouble(item.getString("lastvalue"));
            }
            if(key_.startsWith("vmware.hv.network.in")){
                download_flow  = Double.parseDouble(item.getString("lastvalue"));
            }
            if(key_.startsWith("vmware.hv.hw.memory")){
                Double d= item.getDouble("lastvalue");
                d = d/1024/1024/1024;
                DecimalFormat df= new DecimalFormat("#####0.00");
                memory_total = df.format(d);
            }
            if(key_.startsWith("vmware.hv.memory.used")){
                Double d= item.getDouble("lastvalue");
                d = d/1024/1024/1024;
                DecimalFormat df= new DecimalFormat("#####0.00");
                memory_used = df.format(d);
            }
        }
        DecimalFormat df= new DecimalFormat("#####0.00");
        double cpuused =cpu_usage/(cpu_cores*cpu_freq)*100;
        cpu = df.format(cpuused);
        result.put("cpu",cpu+"");
        result.put("memory_used",memory_used);
        result.put("memory_total",memory_total);
        result.put("upload_flow", DevelopKit.convertFormatNetworkSpeed(upload_flow));
        result.put("download_flow",  DevelopKit.convertFormatNetworkSpeed(download_flow));
        result.put("time",System.currentTimeMillis());
        result.put("signal","0".equals(host.getString("status"))?"1":"0");
        return result;
    }


    /**
     *   每1分钟同步一次虚拟机监控items
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void synchronizeVmItems(){
        JSONObject sendObj = new JSONObject();
        JSONArray hostids = new JSONArray();
        Long currentTime = System.currentTimeMillis();
        //查询虚拟机
        String sql = "select t1.`name`,t1.zabbixid,t2.`name` as platform_name,t2.id platform_id" +
                " from hw_virtual_machine t1,hw_vm_platform t2" +
                " where t1.platform_id=t2.id ";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<Object> hostidsparam = new ArrayList<Object>();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).get("zabbixid")!=null){
                    hostidsparam.add(list.get(i).get("zabbixid"));
                }
            }
        }
        if(true){
            JSONObject r =   ZabbixV2Api.getVmAndItemsByHostids(ZabbixV2Api.getGroup_vm_id(),hostidsparam);
            JSONArray hosts = r.getJSONArray("result");
            Cache cache = CacheMonitoringUtil.getCache("vm_items");
            for(int i=0;i<hosts.size();i++){
                JSONObject host = hosts.getJSONObject(i);
                JSONObject convertObject = convertVmItems(host);
                String key =host.getString("hostid");
                hostids.add(key);
                Cache.ValueWrapper valueWrapper = cache.get(key);
                BlockingDeque deque = null;
                if(valueWrapper==null){
                    deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                    cache.put(key,deque);
                }else{
                    deque = (BlockingDeque)valueWrapper.get();
                }
                if(!deque.offer(convertObject)){
                    deque.pollFirst();
                    deque.addLast(convertObject);
                }
            }
        }
        if(true){
            JSONObject r =   ZabbixV2Api.getVmAndItemsByHostids(ZabbixV2Api.getGroup_vcenter_vm_id(),hostidsparam);
            JSONArray hosts = r.getJSONArray("result");
            Cache cache = CacheMonitoringUtil.getCache("vm_items");
            for(int i=0;i<hosts.size();i++){
                JSONObject host = hosts.getJSONObject(i);
                JSONObject convertObject = convertVcenterVmItems(host);
                String key =host.getString("hostid");
                hostids.add(key);
                Cache.ValueWrapper valueWrapper = cache.get(key);
                BlockingDeque deque = null;
                if(valueWrapper==null){
                    deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                    cache.put(key,deque);
                }else{
                    deque = (BlockingDeque)valueWrapper.get();
                }
                if(!deque.offer(convertObject)){
                    deque.pollFirst();
                    deque.addLast(convertObject);
                }
            }
        }

        sendObj.put("hostids",hostids);
        sendObj.put("currentTime",currentTime);
        try {
            messageSenderListener.sendMessage("/subscribe_vm_items",sendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
   /* public void synchronizeVmItems(){
        JSONObject sendObj = new JSONObject();
        JSONArray hostids = new JSONArray();
        Long currentTime = System.currentTimeMillis();
        //查询虚拟机
        String sql = "select t1.`name`,t1.zabbixid,t2.`name` as platform_name,t2.id platform_id" +
                " from hw_virtual_machine t1,hw_vm_platform t2" +
                " where t1.platform_id=t2.id ";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<Object> hostidsparam = new ArrayList<Object>();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).get("zabbixid")!=null){
                    hostidsparam.add(list.get(i).get("zabbixid"));
                }
            }
        }
        JSONObject r =   ZabbixV2Api.getVmAndItemsByHostids(ZabbixV2Api.getGroup_vm_id(),hostidsparam);
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = CacheMonitoringUtil.getCache("vm_items");
        for(int i=0;i<hosts.size();i++){
            JSONObject host = hosts.getJSONObject(i);
             if("1".equals(item.getString("status"))){
                    continue;
           }
            JSONObject convertObject = convertVmItems(host);
            String key =host.getString("hostid");
            hostids.add(key);
            Cache.ValueWrapper valueWrapper = cache.get(key);
            BlockingDeque deque = null;
            if(valueWrapper==null){
                deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                cache.put(key,deque);
            }else{
                deque = (BlockingDeque)valueWrapper.get();
            }
            if(!deque.offer(convertObject)){
                deque.pollFirst();
                deque.addLast(convertObject);
            }
        }
        sendObj.put("hostids",hostids);
        sendObj.put("currentTime",currentTime);
        try {
            MyWebSocketClient myWebSocketClient = new MyWebSocketClient();
            myWebSocketClient.sendMessage("/app/send_vm_items",sendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/
   private JSONObject convertVcenterVmItems(JSONObject host){
       JSONObject result = new JSONObject();
       result.put("id",host.getString("hostid"));
       result.put("name",host.getString("name"));
       double upload_flow=0;
       double download_flow=0;
       String cpu="0";
       String memory_used ="0";
       String memory_total ="0";
       int cpu_cores =1;//cpu核心数
       double cpu_freq =vcenter_cpu_freq;//每个cpu核心频率
       double cpu_usage =0;//cpu已用赫兹
       JSONArray items = host.getJSONArray("items");
       for(int i=0;i<items.size();i++){
           JSONObject item =items.getJSONObject(i);
           if("1".equals(item.getString("status"))){
                    continue;
           }
           String key_ =  item.getString("key_");
           if(key_.startsWith("vmware.vm.cpu.num")){
               cpu_cores = item.getInteger("lastvalue");
           }
           else if(key_.startsWith("vmware.vm.cpu.usage")){
               cpu_usage = item.getDouble("lastvalue");
           }
           //TODO
         /*  if(key_.startsWith("vmware.hv.hw.cpu.freq")){
               cpu_freq = item.getDouble("lastvalue");
           }*/
           else if(key_.startsWith("vmware.vm.net.if.out")){
                   upload_flow = Double.parseDouble(item.getString("lastvalue"));
           }
           else if(key_.startsWith("vmware.vm.net.if.in")){
                   download_flow = Double.parseDouble(item.getString("lastvalue"));
           }
           else if(key_.startsWith("vmware.vm.memory.size")){
               Double d= item.getDouble("lastvalue");
               d = d/1024/1024/1024;
               DecimalFormat df= new DecimalFormat("#####0.00");
               memory_total = df.format(d);
           }
           else if(key_.startsWith("vmware.vm.memory.size.usage.guest")){
               Double d= item.getDouble("lastvalue");
               d = d/1024/1024/1024;
               DecimalFormat df= new DecimalFormat("#####0.00");
               memory_used = df.format(d);
           }
       }
       DecimalFormat df= new DecimalFormat("#####0.00");
       double cpuused =cpu_usage/(cpu_cores*cpu_freq)*100;
       cpu = df.format(cpuused);
       result.put("cpu",cpu+"");
       result.put("memory_used",memory_used);
       result.put("memory_total",memory_total);
       result.put("upload_flow", DevelopKit.convertFormatNetworkSpeed(upload_flow));
       result.put("download_flow",  DevelopKit.convertFormatNetworkSpeed(download_flow));
       result.put("time",System.currentTimeMillis());
       result.put("signal","0".equals(host.getString("status"))?"1":"0");
       return result;
   }

    private JSONObject convertVmItems(JSONObject host){
        JSONObject result = new JSONObject();
        result.put("id",host.getString("hostid"));
        result.put("name",host.getString("name"));
        double upload_flow=0;
        double download_flow=0;
        String cpu="0";
        String memory_used ="0";
        String memory_total ="0";
        JSONArray items = host.getJSONArray("items");
        for(int i=0;i<items.size();i++){
            JSONObject item =items.getJSONObject(i);
            if("1".equals(item.getString("status"))){
                continue;
            }
            if(item.getString("key_").startsWith("net.if.in")){
                download_flow += Double.parseDouble(item.getString("lastvalue"));
            }
            else if(item.getString("key_").startsWith("net.if.out")){
                upload_flow  += Double.parseDouble(item.getString("lastvalue"));
            }
            else if(item.getString("key_").startsWith("system.cpu.util")){
                Double d= item.getDouble("lastvalue");
                //d=d*100;
                DecimalFormat df= new DecimalFormat("#####0.00");
                cpu = df.format(d);
            }
            else if("vm.memory.size[used]".equals(item.getString("key_"))){
                Double d= item.getDouble("lastvalue");
                d = d/1024/1024/1024;
                DecimalFormat df= new DecimalFormat("#####0.00");
                memory_used = df.format(d);
            }
            else if("vm.memory.size[total]".equals(item.getString("key_"))){
                Double d= item.getDouble("lastvalue");
                d = d/1024/1024/1024;
                DecimalFormat df= new DecimalFormat("#####0.00");
                memory_total = df.format(d);
            }
        }
        result.put("cpu",cpu+"");
        result.put("memory_used",memory_used);
        result.put("memory_total",memory_total);
        result.put("upload_flow", DevelopKit.convertFormatNetworkSpeed(upload_flow));
        result.put("download_flow",  DevelopKit.convertFormatNetworkSpeed(download_flow));
        result.put("time",System.currentTimeMillis());
        result.put("signal","0".equals(host.getString("status"))?"1":"0");
        return result;
    }


    /**
     *   每1分钟同步一次交换机监控items
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void synchronizeSwitchItems(){
        JSONObject r =   ZabbixV2Api.getSwitchAndItemsByGroupid(ZabbixV2Api.getGroup_switch_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = CacheMonitoringUtil.getCache("switch_items");
        JSONObject sendObj = new JSONObject();
        JSONArray hostids = new JSONArray();
        Long currentTime = System.currentTimeMillis();
        for(int i=0;i<hosts.size();i++){
            JSONObject host = hosts.getJSONObject(i);
            JSONObject convertObject = convertSwitchItems(host);
            String key =host.getString("hostid");
            hostids.add(key);
            Cache.ValueWrapper valueWrapper = cache.get(key);
            BlockingDeque deque = null;
            if(valueWrapper==null){
                deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                cache.put(key,deque);
            }else{
                deque = (BlockingDeque)valueWrapper.get();
            }
            if(!deque.offer(convertObject)){
                deque.pollFirst();
                deque.addLast(convertObject);
            }
        }
        sendObj.put("hostids",hostids);
        sendObj.put("currentTime",currentTime);
        try {
            messageSenderListener.sendMessage("/subscribe_switch_items",sendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject convertSwitchItems(JSONObject host){
        JSONObject result = new JSONObject();
        result.put("id",host.getString("hostid"));
        result.put("name",host.getString("name"));
        String upload_flow="0kbps";
        String download_flow="0kbps";
        JSONArray items = host.getJSONArray("items");
        for(int i=0;i<items.size();i++){
            JSONObject item =items.getJSONObject(i);
            if("1".equals(item.getString("status"))){
                continue;
            }
            if("download".equals(item.getString("key_"))){
                Double d= item.getDouble("lastvalue");
                download_flow = DevelopKit.convertFormatNetworkSpeed(d);
            }
            else  if("upload".equals(item.getString("key_"))){
                Double d= item.getDouble("lastvalue");
                upload_flow = DevelopKit.convertFormatNetworkSpeed(d);
            }
        }
        result.put("upload_flow",upload_flow);
        result.put("download_flow",download_flow);
        result.put("time",System.currentTimeMillis());
        result.put("signal","0".equals(host.getString("status"))?"1":"0");
        return result;
    }

    /**
     *   每1分钟同步一次环境监控设备items
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void synchronizeEnvItems(){
        JSONObject r =   ZabbixV2Api.getEnvAndItemsByGroupid(ZabbixV2Api.getGroup_env_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = CacheMonitoringUtil.getCache("env_items");
        JSONObject sendObj = new JSONObject();
        JSONArray hostids = new JSONArray();
        Long currentTime = System.currentTimeMillis();
        for(int i=0;i<hosts.size();i++){
            JSONObject host = hosts.getJSONObject(i);
            JSONObject convertObject = convertEnvItems(host);
            String key =host.getString("hostid");
            hostids.add(key);
            Cache.ValueWrapper valueWrapper = cache.get(key);
            BlockingDeque deque = null;
            if(valueWrapper==null){
                deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                cache.put(key,deque);
            }else{
                deque = (BlockingDeque)valueWrapper.get();
            }
            if(!deque.offer(convertObject)){
                deque.pollFirst();
                deque.addLast(convertObject);
            }
        }
        sendObj.put("hostids",hostids);
        sendObj.put("currentTime",currentTime);
        try {
            messageSenderListener.sendMessage("/subscribe_env_items",sendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject convertEnvItems(JSONObject host){
        JSONObject result = new JSONObject();
        result.put("id",host.getString("hostid"));
        result.put("name",host.getString("name"));
        String tempreature="0";
        String humidity="0";
        JSONArray items = host.getJSONArray("items");
        for(int i=0;i<items.size();i++){
            JSONObject item =items.getJSONObject(i);
            if("1".equals(item.getString("status"))){
                continue;
            }
            if("wendu".equals(item.getString("key_"))){
               Double d= item.getDouble("lastvalue");
                DecimalFormat df= new DecimalFormat("##0.00");
                tempreature = df.format(d);

            }
            else if("shidu".equals(item.getString("key_"))){
                Double d= item.getDouble("lastvalue");
                DecimalFormat df= new DecimalFormat("##0.00");
                humidity = df.format(d);
            }
        }
        result.put("tempreature",tempreature);
        result.put("humidity",humidity+"");
        result.put("time",System.currentTimeMillis());
        result.put("signal","0".equals(host.getString("status"))?"1":"0");
        return result;
    }

    /**
     *   每1分钟同步一次环境监控设备items
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void synchronizeWaterItems(){
        JSONObject r =   ZabbixV2Api.getWaterAndItemsByGroupid(ZabbixV2Api.getGroup_water_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = CacheMonitoringUtil.getCache("water_items");
        JSONObject sendObj = new JSONObject();
        JSONArray hostids = new JSONArray();
        Long currentTime = System.currentTimeMillis();
        for(int i=0;i<hosts.size();i++){
            JSONObject host = hosts.getJSONObject(i);
            JSONObject convertObject = convertWaterItems(host);
            String key =host.getString("hostid");
            hostids.add(key);
            Cache.ValueWrapper valueWrapper = cache.get(key);
            BlockingDeque deque = null;
            if(valueWrapper==null){
                deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                cache.put(key,deque);
            }else{
                deque = (BlockingDeque)valueWrapper.get();
            }
            if(!deque.offer(convertObject)){
                deque.pollFirst();
                deque.addLast(convertObject);
            }
        }
        sendObj.put("hostids",hostids);
        sendObj.put("currentTime",currentTime);
        try {
            messageSenderListener.sendMessage("/subscribe_water_items",sendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private JSONObject convertWaterItems(JSONObject host){
        JSONObject result = new JSONObject();
        result.put("id",host.getString("hostid"));
        result.put("name",host.getString("name"));
        String state="1";
        JSONArray items = host.getJSONArray("items");
        for(int i=0;i<items.size();i++){
            JSONObject item =items.getJSONObject(i);
            if("1".equals(item.getString("status"))){
                continue;
            }
            if("shuijin".equals(item.getString("key_"))){
                if("on".equals(item.getString("lastvalue"))){
                    state="0";
                    break;
                }
            }
        }
        result.put("state",state);
        result.put("time",System.currentTimeMillis());
        result.put("signal","0".equals(host.getString("status"))?"1":"0");
        return result;
    }



}
