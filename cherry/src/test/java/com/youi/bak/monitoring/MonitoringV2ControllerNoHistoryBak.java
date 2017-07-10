package com.youi.bak.monitoring;

import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.auth.Userid;
import com.youi.business.common.scheduler.CacheMonitoringUtil;
import com.youi.business.common.service.JWTService;
import com.youi.business.common.vo.RestResult;
import com.youi.business.monitoring.service.MonitoringV2Service;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jinliang on 2017/2/25.
 */
public class MonitoringV2ControllerNoHistoryBak {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private MonitoringV2Service monitoringV2Service;

    private final long TIME_LIMIT=5000;
    /**
     * 查询库监控结果
     * @param userid
     * @return
     * @throws Exception
     */
    @RequestMapping("/monitoringv2/statistic_info")
    public RestResult getMonitoringStatInfo(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
        param.put("userid",String.valueOf(userid));
       Map<String,Object> result= monitoringV2Service.getMonitoringStatInfo(param);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }

    @RequestMapping("/monitoringv2/init_host_items")
    public RestResult initHostItems(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
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
                        list.add(o);
                    }
                }
            }
        }
        result.put("datas",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);
    }
    @RequestMapping("/monitoringv2/init_vm_items")
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
    }
    @RequestMapping("/monitoringv2/init_switch_items")
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
    @RequestMapping("/monitoring/init_env_items")
    public RestResult initEnvItems(@Userid Long userid,@RequestBody(required = false) Map<String,String>param) throws Exception {
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
                        list.add(o);
                    }
                }
            }
        }
        result.put("datas",list);
        String token =jwtService.getToken(userid);
        return new RestResult(token,result);

    }
    @RequestMapping("/monitoringv2/init_water_items")
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

    }
}
