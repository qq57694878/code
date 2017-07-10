package com.youi.business.monitoring.service;

import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.zabbix.api.ZabbixV2Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by jinliang on 2017/3/13.
 */
@Service
public class MonitoringV2Service {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Map<String,Object> getMonitoringStatInfo(Map<String, String> param) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("vm_host_total",getVmHostTotalNumber());// 虚拟机的总数量
        result.put("host_total",getHostTotalNumber());//返回服务器数量
        result.put("switch_total",getSwitchTotalNumber());//返回交换机数量
        result.put("health_num",nextInt(80,100));//返回交换机数量
        return result;
    }
    private int nextInt(int min,int max){
        Random random = new Random();
        return  random.nextInt(max-min) + min;
    }
    /**
     * 获得服务器数量
     * @return
     */
    private Long getSwitchTotalNumber(){
        List<String> param = new ArrayList<>();
        param.add(ZabbixV2Api.getGroup_switch_id());
        JSONObject r =ZabbixV2Api.getHostCountByGroupids(param);
        return r.getLong("result");
    }
    /*
    /**
     * 获得服务器数量
     * @return
     */
    private Long getHostTotalNumber(){
        List<String> param = new ArrayList<>();
        param.add(ZabbixV2Api.getGroup_host_id());
        param.add(ZabbixV2Api.getGroup_vcenter_x86_id());
        JSONObject r =ZabbixV2Api.getHostCountByGroupids(param);
        return r.getLong("result");
    }
    /**
     * 获得虚拟机的总数量
     * @return
     */
    private Long getVmHostTotalNumber(){
        String sql="select count(1) num from hw_virtual_machine t1,hw_vm_platform t2" +
                " where t1.platform_id=t2.id " ;
        return (Long)jdbcTemplate.queryForObject(sql,Long.class);
    }

    public List<Map<String,Object>> getVmPlatAndHostList(){
        String sql = "select t1.id, t1.`name`,t1.zabbixid,t2.`name` as platform_name,t2.id platform_id" +
                " from hw_virtual_machine t1,hw_vm_platform t2" +
                " where t1.platform_id=t2.id order by t2.id";
        return jdbcTemplate.queryForList(sql);
    }
}
