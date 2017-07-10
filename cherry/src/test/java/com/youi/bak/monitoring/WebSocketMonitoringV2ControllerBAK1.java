package com.youi.bak.monitoring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

/**
 * Created by jinliang on 2017/2/25.
 */
public class WebSocketMonitoringV2ControllerBAK1 {
    private static long last=System.currentTimeMillis();
    private static long index=0;

    @Autowired
    private EhCacheCacheManager cacheManager;

    @MessageMapping("/send_host_items")
    @SendTo("/topic/subscribe_host_items")
    public JSONObject subscribe_host_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
       /* Cache cache = cacheManager.getCache("hostItems");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }*/
        result.put("datas",list);
        return result;
    }
    @MessageMapping("/send_vm_items")
    @SendTo("/topic/subscribe_vm_items")
    public JSONObject subscribe_vm_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
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
    @MessageMapping("/send_switch_items")
    @SendTo("/topic/subscribe_switch_items")
    public JSONObject subscribe_switch_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      /*  Cache cache = cacheManager.getCache("switchItems");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }*/
        result.put("datas",list);
        return result;
    }
    @MessageMapping("/send_env_items")
    @SendTo("/topic/subscribe_env_items")
    public JSONObject subscribe_env_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      /*  Cache cache = cacheManager.getCache("envItems");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }*/
        result.put("datas",list);
        return result;
    }
    @MessageMapping("/send_water_items")
    @SendTo("/topic/subscribe_water_items")
    public JSONObject subscribe_water_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
       /* Cache cache = cacheManager.getCache("waterItems");
        JSONArray hostids = message.getJSONArray("hostids");
        if(hostids!=null){
            for(int i=0;i<hostids.size();i++){
                BlockingDeque deque = (BlockingDeque) cache.get(hostids.getString(i)).get();
                JSONObject o  =(JSONObject)deque.getLast();
                list.add(o);
            }
        }*/
        result.put("datas",list);
        return result;
    }
}
