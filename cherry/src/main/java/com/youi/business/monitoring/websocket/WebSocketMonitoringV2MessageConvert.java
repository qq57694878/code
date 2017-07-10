package com.youi.business.monitoring.websocket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.scheduler.CacheMonitoringUtil;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * Created by jinliang on 2017/2/25.
 */
@Component
public class WebSocketMonitoringV2MessageConvert {
    private static long last=System.currentTimeMillis();
    private static long index=0;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final long TIME_LIMIT=19000;

    private  void sortHostList(List<JSONObject> list){
        if(list!=null){
            //采用选择排序
            int size = list.size();
            for(int i=0;i<size;i++){
                int k=i;
                JSONObject m1 = list.get(i);
                for(int j=i+1;j<size;j++){
                    JSONObject m2 = list.get(j);
                    if(Integer.parseInt(String.valueOf(m1.get("id")))>Integer.parseInt(String.valueOf(m2.get("id")))){
                        k=j;
                    }
                }
                if(k!=i){
                    JSONObject temp =  list.get(i);
                    list.set(i,list.get(k));
                    list.set(k,temp);
                }
            }

        }
    }

    public JSONObject subscribe_host_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        Cache cache = CacheMonitoringUtil.getCache("host_items");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }
        sortHostList(list);
        result.put("datas",list);
        return result;
    }
    public JSONObject subscribe_vm_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Cache cache = CacheMonitoringUtil.getCache("vm_items");
        JSONArray hostids = message.getJSONArray("hostids");
        Long currentTime = message.getLong("currentTime");
        Map<String,JSONObject> zabbixHostMap = new HashMap<String,JSONObject>();
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                if(currentTime-o.getLong("time")<=TIME_LIMIT){
                    zabbixHostMap.put(hostids.getString(i),o);
                }
            }
        }
        //查询虚拟机
        Map<String,String> platformMap = new HashMap<String,String>();
        Map<String,List<JSONObject>> xmHostMap = new HashMap<String,List<JSONObject>>();
        String sql = "select t1.id, t1.`name`,t1.zabbixid,t2.`name` as platform_name,t2.id platform_id" +
                " from hw_virtual_machine t1,hw_vm_platform t2" +
                " where t1.platform_id=t2.id order by t2.id";
        List<Map<String,Object>> listData = jdbcTemplate.queryForList(sql);
        if(listData!=null){
            for(int i=0;i<listData.size();i++){
                Map<String,Object> m = listData.get(i);
                String platform_id =String.valueOf(m.get("platform_id"));
                String hostid = StringUtils.null2String(m.get("zabbixid"));
                String id = StringUtils.null2String(m.get("id"));
                String name =String.valueOf(m.get("name"));
                platformMap.put(platform_id,String.valueOf(m.get("platform_name")));
                List<JSONObject> ml = xmHostMap.get(platform_id);
                if(ml==null){
                    ml = new ArrayList<JSONObject>();
                    xmHostMap.put(platform_id,ml);
                }
                JSONObject obj =null;
                if(!StringUtils.isEmpty(hostid)){
                    obj = zabbixHostMap.get(hostid);
                }
                if(obj==null){
                    obj = new JSONObject();
                    obj.put("signal","0");
                    obj.put("cpu","");
                    obj.put("memory_used","");
                    obj.put("memory_total","");
                    obj.put("upload_flow","");
                    obj.put("download_flow","");
                    obj.put("time",System.currentTimeMillis());
                }
                obj.put("id",id);
                obj.put("name",name);
                ml.add(obj);
            }
        }
        //整理返回数据

        for(String platform_id:xmHostMap.keySet()){
            Map<String,Object> platformObj = new HashMap<String,Object>();
            platformObj.put("platform_id",platform_id);
            platformObj.put("platform_name", platformMap.get(platform_id));
            sortHostList(xmHostMap.get(platform_id));
            platformObj.put("hosts",xmHostMap.get(platform_id));
            list.add(platformObj);
        }

    /*    Random random = new Random(100);
        int n = random.nextInt(100);
        for(int i=0;i<20;i++){
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id","subscribe_vm_items"+i);
            m.put("name","linux虚拟机"+i);
            m.put("flow",random.nextInt(100)+"m/s");
            m.put("cpu",random.nextInt(100)+"%");
            m.put("memory",random.nextInt(100)+"%");
            m.put("io",random.nextInt(100)+"m/s");
            m.put("group","集群"+i%2);
            m.put("time",System.currentTimeMillis());
            m.put("signal","1");
            list.add(m);
        }*/

        result.put("datas",list);
        return result;
    }
    public JSONObject subscribe_switch_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        Cache cache = CacheMonitoringUtil.getCache("switch_items");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }
        sortHostList(list);
        result.put("datas",list);
        return result;
    }
    public JSONObject subscribe_env_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        Cache cache = CacheMonitoringUtil.getCache("env_items");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }
        sortHostList(list);
        result.put("datas",list);
        return result;
    }
    public JSONObject subscribe_water_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<JSONObject> list = new ArrayList<JSONObject>();
        Cache cache = CacheMonitoringUtil.getCache("water_items");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }
        sortHostList(list);
        result.put("datas",list);
        return result;
    }
}
