package com.youi.bak.monitoring;

import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.scheduler.CacheMonitoringUtil;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.monitoring.service.MonitoringV2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by jinliang on 2017/2/25.
 */

public class MonitoringV2ControllerHasHistoryBak {
    private static Logger logger = LoggerFactory.getLogger(MonitoringV2ControllerHasHistoryBak.class);
    @Autowired
    private JWTService jwtService;
    @Autowired
    private MonitoringV2Service monitoringV2Service;

    private final int NUM_LIMIT=3600/5;

    private final long TIME_LIMIT=5000;

    private final long HISTORY_TIME_LIMIT=TIME_LIMIT*NUM_LIMIT;
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
                LinkedBlockingDeque<JSONObject> deque = (LinkedBlockingDeque<JSONObject>) entry.getValue();
                if(deque!=null&&deque.size()>0){
                    JSONObject o  =(JSONObject)deque.getLast();
                    JSONObject m = new JSONObject();
                    m.put("name",o.getString("name"));
                    m.put("id",o.getString("id"));
                    /*int size = deque.size();
                    JSONArray items = new JSONArray(NUM_LIMIT);
                    m.put("history_data",items);
                    //初始化数据列表
                    if(size<NUM_LIMIT){
                     for(int i=0;i<NUM_LIMIT- size;i++){
                         JSONObject obj = new JSONObject();
                         obj.put("cpu","0");
                         obj.put("memory_used","0");
                         obj.put("memory_total","0");
                         obj.put("upload_flow", "0");
                         obj.put("download_flow","0");
                         obj.put("time","0");
                         items.add(obj);
                     }
                    }
                    int index = 0;
                    Iterator<JSONObject> iter= deque.iterator();
                    while(iter.hasNext()&&index++<(size-NUM_LIMIT)){
                        iter.next();
                    }
                    while(iter.hasNext()){
                        JSONObject t = iter.next();
                        JSONObject obj = new JSONObject();
                        obj.put("cpu",t.getString("cpu"));
                        obj.put("memory_used",t.getString("memory_used"));
                        obj.put("memory_total",t.getString("memory_total"));
                        obj.put("upload_flow", t.getString("upload_flow"));
                        obj.put("download_flow",t.getString("download_flow"));
                        obj.put("time",t.getString("time"));
                        items.add(obj);
                    }*/

                    list.add(m);
                }
            }
        }
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
                LinkedBlockingDeque<JSONObject> deque = (LinkedBlockingDeque<JSONObject>) entry.getValue();
                if(deque!=null&&deque.size()>0){
                    JSONObject o  =(JSONObject)deque.getLast();
                    JSONObject m = new JSONObject();
                    m.put("name",o.getString("name"));
                    m.put("id",o.getString("id"));
                  /*  int size = deque.size();
                    JSONArray items = new JSONArray(NUM_LIMIT);
                    m.put("history_data",items);
                    //初始化数据列表
                    if(size<NUM_LIMIT){
                        for(int i=0;i<NUM_LIMIT- size;i++){
                            JSONObject obj = new JSONObject();
                            obj.put("tempreature","0");
                            obj.put("humidity","0");
                            obj.put("time","0");
                            items.add(obj);
                        }
                    }
                    int index = 0;
                    Iterator<JSONObject> iter= deque.iterator();
                    while(iter.hasNext()&&index++<(size-NUM_LIMIT)){
                        iter.next();
                    }
                    while(iter.hasNext()){
                        JSONObject t = iter.next();
                        JSONObject obj = new JSONObject();
                        obj.put("tempreature",t.getString("tempreature"));
                        obj.put("humidity",t.getString("humidity"));
                        obj.put("time",t.getString("time"));
                        items.add(obj);
                    }*/
                    list.add(m);
                }
            }
        }
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
