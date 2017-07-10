package com.youi.business.monitoring.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.dao.HwDatabaseDao;
import com.youi.business.common.entity.HW_DATABASE;
import com.youi.business.common.service.ZabbixCommonService;
import com.youi.business.common.zabbix.api.ZabbixApi;
import com.youi.core.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jinliang on 2016/11/8.
 */
@Transactional
@Service
public class MonitoringDatabaseService {
    @Autowired
    private HwDatabaseDao hwDatabaseDao;
    @Autowired
    private ZabbixCommonService zabbixCommonService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createMonitoring(Map<String, String> param) {
        String id = param.get("id");
        HW_DATABASE a = hwDatabaseDao.get(Long.parseLong(id));
        //删除原来的监控
        if (a.getZabbixid() != null) {
            ZabbixApi.deleteZabbixHost(a.getZabbixid());
        }
        //创建主机
        JSONObject r = ZabbixApi.createDbZabbixHost(String.valueOf(a.getId()), a.getAddress(),a.getPort(),ZabbixApi.getTemplate_db_ids(ZabbixApi.TEMPLATE_TYPE_DB_MYSQL));
        if(r.getJSONObject("result")==null){return;}
        JSONArray hostids = r.getJSONObject("result").getJSONArray("hostids");
        Long hostid = null;
        if (hostids.size() > 0) {
            hostid = hostids.getLong(0);
        }
        // 修改hostid数据
        a.setZabbix_switch("1");
        a.setZabbixid(hostid);
        hwDatabaseDao.update(a);

        //比较耗时，开启1个线程去做
      /*  final Long run_hostid = hostid;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    /*//***************修改主机状态**********************//*
                    zabbixCommonService.updateHostTriggerStatus(run_hostid, ZabbixApi.PRE_HOST_DB);
                    /*//***************修改主机items值**********************//*
                    zabbixCommonService.updateHostItems(run_hostid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

    }

    public void deleteMonitoring(Map<String, String> param) {
        String id = param.get("id");
        HW_DATABASE a = hwDatabaseDao.get(Long.parseLong(id));
        //删除原来的监控
        if (a.getZabbixid() != null) {
            ZabbixApi.deleteZabbixHost(a.getZabbixid());
        }
        // 修改hostid数据
        a.setZabbix_switch("0");
        a.setZabbixid(null);
        hwDatabaseDao.update(a);
        //删除主机状态
        zabbixCommonService.deleteHostTriggerStatus(a.getZabbixid());
        //删除主机items
        zabbixCommonService.deleteHostItems(a.getZabbixid());
    }

    /**
     * 获得监控信息
     * @param param
     * @return
     */
    public Map<String,Object> getDatabaseMonitoringList(Map<String, String> param) {
        Map<String,Object> result =new HashMap<String,Object>();
        String sql="select id db_id,name db_name,address ip,zabbixid hostid,zabbix_switch zabbix_on from hw_database";
        Page page1 = hwDatabaseDao.pagedSQLQuery(sql,Integer.parseInt(param.get("pageno")),Integer.parseInt(param.get("pagesize")));
        List<Map<String,Object>> list = (List<Map<String,Object>>)page1.getResult();

        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                Map<String,Object> m=list.get(i);
                String db_id = String.valueOf(m.get("db_id"));
                String db_name = String.valueOf(m.get("db_name"));
                String ip = String.valueOf(m.get("ip"));
                String hostid = String.valueOf(m.get("hostid"));
                String zabbix_on = String.valueOf(m.get("zabbix_on"));
                //获取当前
                Map<String,Object> m1= new HashMap<String,Object>();
                m1.put("db_id",db_id);
                m1.put("db_name",db_name);
                m1.put("ip",ip);
                m1.put("hostid",hostid);
                m1.put("zabbix_on",zabbix_on);
              /*  Map<String,Map<String,Object>> items = zabbixCommonService.getHostItemsFromDB(hostid);
                m1.put("items",items);*/
                List triggers = zabbixCommonService.getHostTriggersFromDB(hostid);
                m1.put("triggers",triggers);
                dataList.add(m1);
            }
        }
        result.put("list",dataList);
        Long total =page1.getTotalCount();
        Long warningNumber = zabbixCommonService.getDbWarningNumber();
        result.put("pagetotal",total);
        result.put("normalNumber",total-warningNumber); //当前总的正常数量
        result.put("warningNumber",warningNumber); //当前总的报警数量
        result.put("lastUpdate",zabbixCommonService.getHostLastUpdateTimeStringDate()); //最后监控周期时间戳
        return result;
    }


}
