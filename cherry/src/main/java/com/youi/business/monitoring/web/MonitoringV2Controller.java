package com.youi.business.monitoring.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.scheduler.CacheMonitoringUtil;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.monitoring.service.MonitoringV2Service;
import com.youi.core.util.StringUtils;
import org.hibernate.metamodel.relational.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by jinliang on 2017/2/25.
 */
@RestController
public class MonitoringV2Controller {
    private static Logger logger = LoggerFactory.getLogger(MonitoringV2Controller.class);
    @Autowired
    private JWTService jwtService;
    @Autowired
    private MonitoringV2Service monitoringV2Service;

   // private final int NUM_LIMIT=3600/10;

    private final long TIME_LIMIT=10000;

   // private final long HISTORY_TIME_LIMIT=TIME_LIMIT*NUM_LIMIT;
    /**
     * 查询库监控结果
     * @return
     * @throws Exception
     */
    @RequestMapping("/monitoringv2/statistic_info")
    public RestResult getMonitoringStatInfo(@RequestBody(required = false) Map<String,String>param) throws Exception {
       Map<String,Object> result= monitoringV2Service.getMonitoringStatInfo(param);
        String token ="";
        return new RestResult(token,result);
    }


    private static void sortHostList(List<Map<String,Object>> list){
        if(list!=null){
            //采用选择排序
            int size = list.size();
            for(int i=0;i<size;i++){
                int k=i;
                Map<String,Object> m1 = list.get(i);
                for(int j=i+1;j<size;j++){
                    Map<String,Object> m2 = list.get(j);
                    if(Integer.parseInt(String.valueOf(m1.get("id")))>Integer.parseInt(String.valueOf(m2.get("id")))){
                        k=j;
                    }
                }
                if(k!=i){
                    Map<String,Object> temp =  list.get(i);
                    list.set(i,list.get(k));
                    list.set(k,temp);
                }
            }

        }
    }

    @RequestMapping("/monitoringv2/init_host_items")
    public RestResult initHostItems(@RequestBody(required = false) Map<String,String>param,HttpServletResponse response) throws Exception {
/*        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1800");//30 min*/
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Long currentTime = System.currentTimeMillis();
        ConcurrentMap<Object, Object> cache =(ConcurrentMap<Object, Object>)CacheMonitoringUtil.getCache("host_items").getNativeCache();
        if(cache!=null){
            for(Map.Entry<Object,Object> entry:cache.entrySet()){
                BlockingDeque deque = (BlockingDeque) entry.getValue();
                if(deque!=null&&deque.size()>0){
                    JSONObject o  =(JSONObject)deque.getLast();
                    if(currentTime-o.getLong("time")<=TIME_LIMIT){
                        JSONObject m = new JSONObject();
                        m.put("name",o.getString("name"));
                        m.put("id",o.getString("id"));
                        list.add(m);
                    }
                }

            }
        }
        sortHostList(list);
        result.put("datas",list);
        String token ="";
        return new RestResult(token,result);
    }

    @RequestMapping("/monitoringv2/init_env_items")
    public RestResult initEnvItems(@RequestBody(required = false) Map<String,String>param, HttpServletResponse response) throws Exception {
  /*      response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "1800");//30 min*/
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Long currentTime = System.currentTimeMillis();
        ConcurrentMap<Object, Object> cache =(ConcurrentMap<Object, Object>)CacheMonitoringUtil.getCache("env_items").getNativeCache();
        if(cache!=null){
            for(Map.Entry<Object,Object> entry:cache.entrySet()){
                BlockingDeque deque = (BlockingDeque) entry.getValue();
                if(deque!=null&&deque.size()>0){
                    JSONObject o  =(JSONObject)deque.getLast();
                    if(currentTime-o.getLong("time")<=TIME_LIMIT){
                        JSONObject m = new JSONObject();
                        m.put("name",o.getString("name"));
                        m.put("id",o.getString("id"));
                        list.add(m);
                    }
                }
            }
        }
        sortHostList(list);
        result.put("datas",list);
        String token ="";
        return new RestResult(token,result);

    }

   /* @RequestMapping("/monitoringv2/init_vm_items")
    public RestResult initVmItems(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Long currentTime = System.currentTimeMillis();
        Map<String,JSONObject> zabbixHostMap = new HashMap<String,JSONObject>();
        ConcurrentMap<Object, Object> cache =(ConcurrentMap<Object, Object>)CacheMonitoringUtil.getCache("vm_items").getNativeCache();
        if(cache!=null){
            for(Map.Entry<Object,Object> entry:cache.entrySet()){
                BlockingDeque deque = (BlockingDeque) entry.getValue();
                if(deque!=null&&deque.size()>0){
                    JSONObject o  =(JSONObject)deque.getLast();
                    if(currentTime-o.getLong("time")<=TIME_LIMIT){
                        zabbixHostMap.put(String.valueOf(entry.getKey()),o);
                    }
                }
            }
        }
        //查询虚拟机
        Map<String,String> platformMap = new HashMap<String,String>();
        Map<String,List<JSONObject>> xmHostMap = new HashMap<String,List<JSONObject>>();
        List<Map<String,Object>> listData = monitoringV2Service.getVmPlatAndHostList();
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
            platformObj.put("hosts",xmHostMap.get(platform_id));
            list.add(platformObj);
        }
        result.put("datas",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }*/
   /* @RequestMapping("/monitoringv2/init_switch_items")
    public RestResult initSwitchItems(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Long currentTime = System.currentTimeMillis();
        ConcurrentMap<Object, Object> cache =(ConcurrentMap<Object, Object>)CacheMonitoringUtil.getCache("switch_items").getNativeCache();
        if(cache!=null){
            for(Map.Entry<Object,Object> entry:cache.entrySet()){
                BlockingDeque deque = (BlockingDeque) entry.getValue();
                if(deque!=null&&deque.size()>0){
                    JSONObject o  =(JSONObject)deque.getLast();
                    if(currentTime-o.getLong("time")<=TIME_LIMIT){
                        list.add(o);
                    }
                }
            }
        }
        result.put("datas",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);

    }
   */
  /*  @RequestMapping("/monitoringv2/init_water_items")
    public RestResult initWaterItems(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Long currentTime = System.currentTimeMillis();
        ConcurrentMap<Object, Object> cache =(ConcurrentMap<Object, Object>)CacheMonitoringUtil.getCache("water_items").getNativeCache();
        if(cache!=null){
            for(Map.Entry<Object,Object> entry:cache.entrySet()){
                BlockingDeque deque = (BlockingDeque) entry.getValue();
                if(deque!=null&&deque.size()>0){
                    JSONObject o  =(JSONObject)deque.getLast();
                    if(currentTime-o.getLong("time")<=TIME_LIMIT){
                        list.add(o);
                    }
                }
            }
        }
        result.put("datas",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }*/
}
