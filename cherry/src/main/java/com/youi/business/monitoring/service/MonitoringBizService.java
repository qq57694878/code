package com.youi.business.monitoring.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.dao.ResBizAppDeployDao;
import com.youi.business.common.entity.RES_BIZ_APP_DEPLOY;
import com.youi.business.common.service.ZabbixCommonService;
import com.youi.business.common.util.DateKit;
import com.youi.business.common.zabbix.api.ZabbixApi;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jinliang on 2016/11/8.
 */
@Transactional
@Service
public class MonitoringBizService {
    @Autowired
    private ResBizAppDeployDao resBizAppDeployDao;
    @Autowired
    private ZabbixCommonService zabbixCommonService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 创建监控
     * @param param
     */
    public void createMonitoring(Map<String, String> param) {
        String id = param.get("id");
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter("EQL_biz_app_id",id));
        List<RES_BIZ_APP_DEPLOY> hosts =resBizAppDeployDao.find(filters);
        if(hosts!=null){
            for(RES_BIZ_APP_DEPLOY a:hosts) {
                //删除原来的监控
                if (a.getZabbixid() != null) {
                    ZabbixApi.deleteZabbixHost(a.getZabbixid());
                }
                //创建主机
                JSONObject r = ZabbixApi.createBizZabbixHost(String.valueOf(a.getId()), a.getIp(), a.getPort(),ZabbixApi.getTemplate_biz_ids(ZabbixApi.TEMPLATE_TYPE_BIZ_LINUX));
                if(r.getJSONObject("result")==null){return;}
                JSONArray hostids = r.getJSONObject("result").getJSONArray("hostids");
                Long hostid = null;
                if (hostids.size() > 0) {
                    hostid = hostids.getLong(0);
                }
                // 修改hostid数据
                a.setZabbix_switch("1");
                a.setZabbixid(hostid);
                resBizAppDeployDao.update(a);
                //比较耗时，开启1个线程去做
               /* final Long run_hostid = hostid;
                ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
                 fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            try {
                                /*//***************修改主机状态**********************//*
                                zabbixCommonService.updateHostTriggerStatus(run_hostid, ZabbixApi.PRE_HOST_BIZ);
                                /*//***************修改主机items值**********************//*
                                zabbixCommonService.updateHostItems(run_hostid);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });*/

            }
        }
    }

    /**
     * 结束监控
     * @param param
     */
    public void deleteMonitoring(Map<String, String> param) {
        String id = param.get("id");
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter("EQL_biz_app_id",id));
        List<RES_BIZ_APP_DEPLOY> hosts =resBizAppDeployDao.find(filters);
        if(hosts!=null){
            for(RES_BIZ_APP_DEPLOY a:hosts){
                //删除原来的监控
                if(a.getZabbixid()!=null){
                    ZabbixApi.deleteZabbixHost(a.getZabbixid());
                }
                // 修改hostid数据
                a.setZabbix_switch("0");
                a.setZabbixid(null);
                resBizAppDeployDao.update(a);
                //删除主机状态
                zabbixCommonService.deleteHostTriggerStatus(a.getZabbixid());
                //删除主机items
                zabbixCommonService.deleteHostItems(a.getZabbixid());
            }
        }
    }

    public Map<String,Object> getBizMonitoringList(Map<String, String> param) {

        Map<String,Object> result =new HashMap<String,Object>();
        String sql="select biz_id,biz_name,zabbix_on from (select t1.ID biz_id, t1.name biz_name ,'1' zabbix_on FROM res_bussiness_app t1 where t1.status='1' and exists (select 1 from res_biz_app_deploy t2 where t1.id = t2.biz_app_id and t2.zabbix_switch='1')" +
                " union select t1.ID biz_id, t1.name biz_name ,'0' zabbix_on FROM res_bussiness_app t1  where t1.status='1' and not exists(select 1 from res_biz_app_deploy t2 where t1.id = t2.biz_app_id and t2.zabbix_switch='1')) t3";
        Page page1 = resBizAppDeployDao.pagedSQLQuery(sql,Integer.parseInt(param.get("pageno")),Integer.parseInt(param.get("pagesize")));
        List<Map<String,Object>> list = (List<Map<String,Object>>)page1.getResult();

        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                Map<String,Object> m=list.get(i);
                String biz_id = String.valueOf(m.get("biz_id"));
                String biz_name = String.valueOf(m.get("biz_name"));
                String zabbix_on = String.valueOf(m.get("zabbix_on"));
                //获取当前
                Map<String,Object> m1= new HashMap<String,Object>();
                m1.put("biz_id",biz_id);
                m1.put("biz_name",biz_name);
                m1.put("zabbix_on",zabbix_on);
                List<Map<String,Object>> hosts = new ArrayList<Map<String,Object>>();
                List<Map<String,Object>> bizAppDeployList = this.geBizAppDeployList(biz_id);
                if(bizAppDeployList!=null){
                    Map<String,Map<String,Map<String,Object>>>  temp1 = new HashMap<String,Map<String,Map<String,Object>>>();
                    for(Map<String,Object> m2:bizAppDeployList){
                       String ip =  (String)m2.get("ip");
                       String port =  String.valueOf(m2.get("port"));
                        String description =  String.valueOf(m2.get("description"));
                        String hostid =  String.valueOf(m2.get("zabbixid"));
                        Map<String,Map<String,Object>> temp2 =  temp1.get(ip);
                        if(temp2==null){
                            temp2 = new  HashMap<String,Map<String,Object>>();
                            temp1.put(ip,temp2);
                        }
                        Map<String,Object> temp3= temp2.get(port);
                        if(temp3==null){
                            temp3 = new HashMap<String,Object>();
                             temp2.put(port,temp3);
                        }
                        if("0".equals(zabbix_on)){
                            temp3.put("status","0");
                            temp3.put("lastClock",zabbixCommonService.getHostLastUpdateTimeStringDate());
                            temp3.put("message","");
                            temp3.put("description","");
                            temp3.put("port",port);
                        }else{
                            //查询
                            String status ="0";
                            StringBuffer message = new StringBuffer();
                            if(!StringUtils.isEmpty(hostid)){
                                List<Map<String,Object>> tirggers = this.geTriggerList(hostid);
                                if(tirggers!=null){
                                    for(Map<String,Object> t:tirggers){
                                        status = String.valueOf(Integer.parseInt(String.valueOf(t.get("trigger_status")))|Integer.parseInt(status));
                                        if("1".equals(String.valueOf(t.get("trigger_status")))){
                                            message.append(String.valueOf(t.get("trigger_message")));
                                        }
                                    }
                                }
                            }

                            temp3.put("status",status);
                            temp3.put("lastClock",zabbixCommonService.getHostLastUpdateTimeStringDate());
                            temp3.put("message",StringUtils.null2String(message.toString()));
                            temp3.put("description",StringUtils.null2String(description));
                            temp3.put("port",port);
                        }


                    }

                    for(String ip:temp1.keySet()){
                        Map<String,Object> host = new HashMap<String,Object>();
                        Map<String,Map<String,Object>> m5=temp1.get(ip);
                        host.put("ip",ip);
                        List<Map<String,Object>> list5= new ArrayList<Map<String,Object>>();
                        for(String port:m5.keySet()){
                            list5.add(m5.get(port));
                        }
                        host.put("services",list5);
                        hosts.add(host);
                    }

                }
                m1.put("hosts",hosts);
                dataList.add(m1);
            }
        }
        result.put("list",dataList);
        Long total =page1.getTotalCount();
        Long warningNumber = zabbixCommonService.getBizWarningNumber();
        result.put("pagetotal",total);
        result.put("normalNumber",total-warningNumber); //当前总的正常数量
        result.put("warningNumber",warningNumber); //当前总的报警数量

        result.put("lastUpdate",zabbixCommonService.getHostLastUpdateTimeStringDate()); //最后监控周期时间戳
        return result;
    }

    private List<Map<String,Object>> geTriggerList(String hostid) {
        String sql ="select t1.trigger_status ,t1.trigger_message,t1.lastchange,t1.trigger_key,t1.hostid from res_monitoring_hosttrigger t1 where  t1.hostid=?";
        return jdbcTemplate.queryForList(sql,new Object[]{Long.parseLong(hostid)});
    }

    private List<Map<String,Object>> geBizAppDeployList(String biz_app_id){
       //  String sql ="select t1.ip,t1.port,t1.server_name description,t2.trigger_status `status`,t2.trigger_message message,unix_timestamp(t2.lastchange) lastclock  from res_biz_app_deploy t1,res_monitoring_hosttrigger t2 where t1.zabbixid=t2.hostid and t1.biz_app_id=?";
        String sql ="select t1.ip,t1.port,t1.server_name description,t1.zabbixid,t1.zabbix_switch from res_biz_app_deploy t1 where  t1.biz_app_id=?";
        return jdbcTemplate.queryForList(sql,new Object[]{biz_app_id});
    }


}
