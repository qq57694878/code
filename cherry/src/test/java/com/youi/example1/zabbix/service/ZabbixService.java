package com.youi.example1.zabbix.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.zabbix.api.Request;
import com.youi.business.common.zabbix.api.RequestBuilder;
import com.youi.business.common.zabbix.api.ZabbixKit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/23.
 */

public class ZabbixService {

   private final int  interface_type=1; //1 agent 2 SNMP 3 IPMI 4 JMX
    private final int  interface_port=10050;
    private final String templateid="10001";

    /**
     * 登录
     * @param param
     * @return
     */
    public Object login(Map<String, String> param) {
        String user=param.get("user");
        String password = param.get("password");
        return  ZabbixKit.login(user,password);
    }
    /**
     * 退出登录
     * @param param
     * @return
     */
    public boolean logout(Map<String, String> param) {
        String auth=param.get("auth");
        return ZabbixKit.logout(auth);

    }
    public Object getHost(Map<String, Object> param) {
        JSONObject params = new JSONObject();
        JSONObject filter = new JSONObject();
        String host = (String) param.get("host");
        filter.put("host", new String[] {host });
        params.put("filter",filter);
        params.put("output",new String[]{"hostid","host","available","snmp_available"});
        params.put("selectItems",new String[]{"itemid","key_","name","lastclock","lastvalue"});
        params.put("selectInterfaces",new String[]{"type","ip","port"});
        Request getRequest = RequestBuilder.newBuilder()
                .method("host.get")
                .build();
        getRequest.setParams(params);
        JSONObject r = ZabbixKit.call(getRequest);
        return r;
    }

    public Object createHost(Map<String, Object> param) {
        JSONObject filter = new JSONObject();
        filter.put("host", param.get("host"));
        JSONArray interfaces = new JSONArray();



        JSONObject interface1 = new JSONObject();
        interface1.put("type",interface_type);
        interface1.put("main", 1);
        interface1.put("useip",1);
        interface1.put("ip",param.get("ip"));//"218.60.41.68"
        interface1.put("dns","");
        interface1.put("port",interface_port);
        interfaces.add(interface1);
        filter.put("interfaces",interfaces);

        JSONArray groups = new JSONArray();
        JSONObject group = new JSONObject();
        group.put("groupid","9");
        groups.add(group);
        filter.put("groups",group);

        JSONArray templates = new JSONArray();
        JSONObject template = new JSONObject();
        template.put("templateid","10117");
        templates.add(template);
        filter.put("templates",template);
  /*      filter.put("inventory_mode",0);
        JSONObject inventory = new JSONObject();
        inventory.put("macaddress_a", "56768");
        inventory.put("macaddress_b", "01234");
        filter.put("inventory",inventory);*/
        JSONObject macro = new JSONObject();
        macro.put("macro","{$PORT}");
        macro.put("value","8080");
        filter.put("macros",macro);

      // System.out.println(filter.toJSONString()) ;
        Request getRequest = RequestBuilder.newBuilder()
                .method("host.create")
                .build();
        getRequest.setParams(filter);
        JSONObject r = ZabbixKit.call(getRequest);
        return r;
    }

    public Object getEvents(Map<String, Object> param) {
        JSONObject params = new JSONObject();
        params.put("output",new String[]{"eventid","objectid","clock","value"});
        params.put("selectHosts",new String[]{"hostid","host"});
        params.put("source",0);
        params.put("object",0);
        params.put("time_from",param.get("time_from"));
        params.put("time_till",param.get("time_till"));
        Request getRequest = RequestBuilder.newBuilder()
                .method("event.get")
                .build();
        getRequest.setParams(params);
        JSONObject r = ZabbixKit.call(getRequest);
        return r;
    }

    public Object getTrends(Map<String, Object> param) {
        JSONObject params = new JSONObject();
        List itemids = (List)param.get("itemids");
        if(itemids!=null){
            params.put("itemids",itemids.toArray());
        }
        params.put("time_from",param.get("time_from"));
        params.put("time_till",param.get("time_till"));
        Request getRequest = RequestBuilder.newBuilder()
                .method("trend.get")
                .build();
        getRequest.setParams(params);

        JSONObject r = ZabbixKit.call(getRequest);
        return r;
    }

    //10050
    public Object getTriggers(Map<String, Object> param) {
        JSONObject params = new JSONObject();
        String  hostids = String.valueOf(param.get("hostid"));
        if(hostids!=null){
            params.put("hostids",hostids);
        }
        String  templateids = String.valueOf(param.get("templateid"));
        if(templateids!=null){
            params.put("templateids",templateids);
        }
        params.put("selectHosts",new String[]{"hostid","host"});
        params.put("expandComment","1");
        params.put("expandDescription","1");
        params.put("expandExpression","1");

        Request getRequest = RequestBuilder.newBuilder()
                .method("trigger.get")
                .build();
        getRequest.setParams(params);

        JSONObject r = ZabbixKit.call(getRequest);
        return r;
    }
}
