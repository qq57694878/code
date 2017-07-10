package com.youi.bak.monitoring;

import com.alibaba.fastjson.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by jinliang on 2017/2/25.
 */

public class WebSocketMonitoringV2ControllerBak {
    private static long last=System.currentTimeMillis();
    private static long index=0;
    @MessageMapping("/send_host_items")
    @SendTo("/topic/subscribe_host_items")
    public JSONObject subscribe_host_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Random random = new Random(100);
        int n = random.nextInt(100);
        for(int i=0;i<7;i++){
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id","subscribe_host_items"+i);
            m.put("name","主机"+i);
            m.put("flow",random.nextInt(100)+"m/s");
            m.put("cpu",random.nextInt(100)+"%");
            m.put("memory",random.nextInt(100)+"%");
            m.put("io",random.nextInt(100)+"m/s");
            m.put("time",System.currentTimeMillis());
            m.put("signal","1");
            list.add(m);
        }
        long second = (System.currentTimeMillis()-last)/1000;
        last= System.currentTimeMillis();
        result.put("间隔",second);
        result.put("index",index++);
        result.put("datas",list);
        return result;
    }
    @MessageMapping("/send_vm_items")
    @SendTo("/topic/subscribe_vm_items")
    public JSONObject subscribe_vm_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Random random = new Random(100);
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
        }

        result.put("datas",list);
        return result;
    }
    @MessageMapping("/send_switch_items")
    @SendTo("/topic/subscribe_switch_items")
    public JSONObject subscribe_switch_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Random random = new Random();
       // int n = random.nextInt(100);
        for(int i=0;i<11;i++){
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id","subscribe_switch_items"+i);
            m.put("name","交换机"+i);
            m.put("flow",random.nextInt(100)+"m/s");
            m.put("time",System.currentTimeMillis());
            m.put("signal","1");
            list.add(m);
        }
        result.put("datas",list);
        return result;
    }
    @MessageMapping("/send_env_items")
    @SendTo("/topic/subscribe_env_items")
    public JSONObject subscribe_env_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Random random = new Random();
        int n = random.nextInt(100);
        for(int i=0;i<3;i++){
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id","subscribe_env_items"+i);
            m.put("name","温湿度设备"+i);
            m.put("tempreature",random.nextInt(100)+i);
            m.put("humidity",random.nextInt(100)+1+i);
            m.put("time",System.currentTimeMillis());
            m.put("signal","1");
            list.add(m);
        }
        result.put("datas",list);
        return result;
    }
    @MessageMapping("/send_water_items")
    @SendTo("/topic/subscribe_water_items")
    public JSONObject subscribe_water_items(JSONObject message) throws Exception {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Random random = new Random();
        int n = random.nextInt(100);
        for(int i=0;i<2;i++){
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("id","subscribe_water_items"+i);
            m.put("name","漏水设备"+i);
            m.put("state",""+random.nextInt(100)%2);
            m.put("time",System.currentTimeMillis());
            m.put("signal","1");
            list.add(m);
        }
        result.put("datas",list);
        return result;
    }
}
