package com.youi.business.monitoring.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youi.business.common.dao.HwVirtualMachineDao;
import com.youi.business.common.dao.HwX86Dao;
import com.youi.business.common.dao.IpAddressDao;
import com.youi.business.common.entity.HW_VIRTUAL_MACHINE;
import com.youi.business.common.entity.HW_X86;
import com.youi.business.common.entity.IP_ADDRESS;
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
public class MonitoringHostsService {
    @Autowired
    private IpAddressDao ipAddressDao;
    @Autowired
    private ZabbixCommonService zabbixCommonService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private HwX86Dao hwX86Dao;
    @Autowired
    private HwVirtualMachineDao hwVirtualMachineDao;

    public void createMonitoring(Map<String, String> param) {
        String id = param.get("id");
        IP_ADDRESS ipAddress = ipAddressDao.get(Long.parseLong(id));
        //删除原来的监控
        if (ipAddress.getZabbixid() != null) {
            ZabbixApi.deleteZabbixHost(ipAddress.getZabbixid());
        }
        //创建主机
        JSONObject r = ZabbixApi.createHostZabbixHost(String.valueOf(ipAddress.getId()), ipAddress.getIp_add(),ZabbixApi.getTemplate_host_ids(ZabbixApi.TEMPLATE_TYPE_HOST_LINUX));
        if(r.getJSONObject("result")==null){return;}
        JSONArray hostids = r.getJSONObject("result").getJSONArray("hostids");
        Long hostid = null;
        if (hostids.size() > 0) {
            hostid = hostids.getLong(0);
        }
        // 修改hostid数据
        ipAddress.setZabbix_switch("1");
        ipAddress.setZabbixid(hostid);
        ipAddressDao.update(ipAddress);

        //比较耗时，开启1个线程去做
      /*  final Long run_hostid = hostid;
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    /*//***************修改主机状态**********************//*
                    zabbixCommonService.updateHostTriggerStatus(run_hostid,ZabbixApi.PRE_HOST_HOST);
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
        IP_ADDRESS a = ipAddressDao.get(Long.parseLong(id));
        //删除原来的监控
        if (a.getZabbixid() != null) {
            ZabbixApi.deleteZabbixHost(a.getZabbixid());
        }
        // 修改hostid数据
        a.setZabbix_switch("0");
        a.setZabbixid(null);
        ipAddressDao.update(a);
        //删除主机状态
        zabbixCommonService.deleteHostTriggerStatus(a.getZabbixid());
        //删除主机items
        zabbixCommonService.deleteHostItems(a.getZabbixid());
    }

    public Map<String,Object> getHostMonitoringList(Map<String, String> param) {
        Map<String,Object> result =new HashMap<String,Object>();
        StringBuilder sql= new StringBuilder();
    /*    sql.append("select id host_id,ip_add ip,zabbixid hostid,zabbix_switch zabbix_on,");
        sql.append(" case when biz_type='HW_X86' then (select name from hw_x86 t1 where t1.id=t.biz_id)");
        sql.append(" else (case when biz_type='HW_VIRTUAL_MACHINE' then(select name from hw_virtual_machine t2 where t2.id=t.biz_id) else '' end) end host_name");
        sql.append(" from ip_address t where biz_type in('HW_X86','HW_VIRTUAL_MACHINE') and ip_type='1'");*/
        sql.append("select id host_id,ip_add ip,zabbixid hostid,zabbix_switch zabbix_on,biz_type,biz_id");
        sql.append(" from ip_address t where biz_type in('HW_X86','HW_VIRTUAL_MACHINE') and ip_type='1'");
       Page page1 = ipAddressDao.pagedSQLQuery(sql.toString(),Integer.parseInt(param.get("pageno")),Integer.parseInt(param.get("pagesize")));
        List<Map<String,Object>> list = (List<Map<String,Object>>)page1.getResult();

        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                Map<String,Object> m=list.get(i);
                String host_id = String.valueOf(m.get("host_id"));
                String biz_type = String.valueOf(m.get("biz_type"));
                String biz_id = String.valueOf(m.get("biz_id"));
                String host_name="";
                if("HW_X86".equals(biz_type)){
                  HW_X86 r1= hwX86Dao.get(Long.parseLong(biz_id));
                    if(r1!=null){
                        host_name = r1.getName();
                    }

                }else if("HW_VIRTUAL_MACHINE".equals(biz_type)){
                    HW_VIRTUAL_MACHINE r1= hwVirtualMachineDao.get(Long.parseLong(biz_id));
                    if(r1!=null){
                        host_name = r1.getName();
                    }
                }
                String ip = String.valueOf(m.get("ip"));
                String hostid = String.valueOf(m.get("hostid"));
                String zabbix_on = String.valueOf(m.get("zabbix_on"));
                //获取当前
                Map<String,Object> m1= new HashMap<String,Object>();
                m1.put("host_id",host_id);
                m1.put("host_name",host_name);
                m1.put("ip",ip);
              //  m1.put("hostid",hostid);
                m1.put("zabbix_on",zabbix_on);
                Map<String,Map<String,Object>> items = zabbixCommonService.getHostItemsFromDB(hostid);
                m1.put("items",items);
                List triggers = zabbixCommonService.getHostTriggersFromDB(hostid);
                m1.put("triggers",triggers);
                dataList.add(m1);
            }
        }
        result.put("list",dataList);
        Long total =page1.getTotalCount();
        Long warningNumber = zabbixCommonService.getHostWarningNumber();
        result.put("pagetotal",total);
        result.put("normalNumber",total-warningNumber); //当前总的正常数量
        result.put("warningNumber",warningNumber); //当前总的报警数量
        result.put("lastUpdate",zabbixCommonService.getHostLastUpdateTimeStringDate()); //最后监控周期时间戳
        return result;
    }

}
