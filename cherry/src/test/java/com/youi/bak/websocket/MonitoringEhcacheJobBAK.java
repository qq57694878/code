package com.youi.bak.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.service.ResSettingsService;
import com.youi.business.common.util.DevelopKit;
import com.youi.business.monitoring.websocket.MyWebSocketSockjsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DecimalFormat;


public class MonitoringEhcacheJobBAK {
    private static Logger logger = LoggerFactory.getLogger(MonitoringEhcacheJobBAK.class);
    @Autowired
    private ResSettingsService resSettingsService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


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
    @Autowired
    private EhCacheCacheManager cacheManager;
    private final static int PERIOD_SECOND=5;
    private final static int HOST_ITEMS_SIZE=3600*24/PERIOD_SECOND;


    /**
     *   每1分钟同步一次服务器监控items
     */
 /*   @Scheduled(cron = "*//*5 * * * * ?")
    public void synchronizeHostItems(){

        try {
            MyWebSocketClient myWebSocketClient1 = new MyWebSocketClient();
            myWebSocketClient1.sendMessage("/app/send_host_items",new JSONObject());
            MyWebSocketClient myWebSocketClient2 = new MyWebSocketClient();
            myWebSocketClient2.sendMessage("/app/send_vm_items",new JSONObject());
            MyWebSocketClient myWebSocketClient3 = new MyWebSocketClient();
            myWebSocketClient3.sendMessage("/app/send_env_items",new JSONObject());
            MyWebSocketClient myWebSocketClient4 = new MyWebSocketClient();
            myWebSocketClient4.sendMessage("/app/send_switch_items",new JSONObject());
            MyWebSocketClient myWebSocketClient5 = new MyWebSocketClient();
            myWebSocketClient5.sendMessage("/app/send_water_items",new JSONObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
    /**
     *
     * 每1分钟同步一次服务器监控items
     */
    @Scheduled(cron = "*/5 * * * * ?")
   public void synchronizeHostItems(){
        JSONObject sendObj = new JSONObject();
      /*  JSONObject r =   ZabbixV2Api.getHostAndItemsByGroupid(ZabbixV2Api.getGroup_host_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = cacheManager.getCache("hostItems");

        JSONArray hostids = new JSONArray();
        Long currentTime = System.currentTimeMillis();
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
        sendObj.put("hostids",hostids);
        sendObj.put("currentTime",currentTime);*/
        try {
            MyWebSocketSockjsClient myWebSocketSockjsClient = new MyWebSocketSockjsClient();
            myWebSocketSockjsClient.sendMessage("/app/send_host_items",sendObj);
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
            if(item.getString("key_").startsWith("net.if.in")){
                upload_flow += Double.parseDouble(item.getString("lastvalue"));
            }
            if(item.getString("key_").startsWith("net.if.out")){
                download_flow += Double.parseDouble(item.getString("lastvalue"));
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
     *   每1分钟同步一次虚拟机监控items
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void synchronizeVmItems(){
        JSONObject sendObj = new JSONObject();
      /*  JSONObject r =   ZabbixV2Api.getHostAndItemsByGroupid(ZabbixV2Api.getGroup_vm_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = cacheManager.getCache("vmItems");
        for(int i=0;i<hosts.size();i++){
            JSONObject host = hosts.getJSONObject(i);
            String key =host.getString("hostid");
            Cache.ValueWrapper valueWrapper = cache.get(key);
            BlockingDeque deque = null;
            if(valueWrapper==null){
                deque = new LinkedBlockingDeque(HOST_ITEMS_SIZE);
                cache.put(key,deque);
            }else{
                deque = (BlockingDeque)valueWrapper.get();
            }
            if(!deque.offer(host)){
                deque.pollFirst();
                deque.addLast(host);
            }
        }
*/
        try {
            MyWebSocketSockjsClient myWebSocketSockjsClient = new MyWebSocketSockjsClient();
            myWebSocketSockjsClient.sendMessage("/app/send_vm_items",sendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     *   每1分钟同步一次交换机监控items
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void synchronizeSwitchItems(){
        JSONObject sendObj = new JSONObject();
        /*JSONObject r =   ZabbixV2Api.getSwitchAndItemsByGroupid(ZabbixV2Api.getGroup_switch_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = cacheManager.getCache("switchItems");

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
        sendObj.put("currentTime",currentTime);*/
        try {
            MyWebSocketSockjsClient myWebSocketSockjsClient = new MyWebSocketSockjsClient();
            myWebSocketSockjsClient.sendMessage("/app/send_switch_items",sendObj);
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
    @Scheduled(cron = "*/5 * * * * ?")
    public void synchronizeEnvItems(){
        JSONObject sendObj = new JSONObject();
      /*  JSONObject r =   ZabbixV2Api.getEnvAndItemsByGroupid(ZabbixV2Api.getGroup_env_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = cacheManager.getCache("envItems");

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
        sendObj.put("currentTime",currentTime);*/
        try {
            MyWebSocketSockjsClient myWebSocketSockjsClient = new MyWebSocketSockjsClient();
            myWebSocketSockjsClient.sendMessage("/app/send_env_items",sendObj);
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
    @Scheduled(cron = "*/5 * * * * ?")
    public void synchronizeWaterItems(){
        JSONObject sendObj = new JSONObject();
        /*JSONObject r =   ZabbixV2Api.getWaterAndItemsByGroupid(ZabbixV2Api.getGroup_water_id());
        JSONArray hosts = r.getJSONArray("result");
        Cache cache = cacheManager.getCache("waterItems");

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
        sendObj.put("currentTime",currentTime);*/
        try {
            MyWebSocketSockjsClient myWebSocketSockjsClient = new MyWebSocketSockjsClient();
            myWebSocketSockjsClient.sendMessage("/app/send_water_items",sendObj);
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
