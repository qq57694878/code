package com.youi.business.monitoring.service;

import com.youi.business.common.service.ZabbixCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/11/14.
 */
@Transactional
@Service
public class MonitoringStatService {
    @Autowired
    private ZabbixCommonService zabbixCommonService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public  Map<String,Map<String,Object>> getMonitoringStatInfo(Map<String, String> param) {
        Map<String,Map<String,Object>> result = new HashMap<String,Map<String,Object>>();
        Map<String,Object> biz = new HashMap<String,Object>();
        Map<String,Object> host = new HashMap<String,Object>();
        Map<String,Object> db = new HashMap<String,Object>();
        Long bizTotalNumber = zabbixCommonService.getBizTotalNumber();
        Long bizwarningNumber = zabbixCommonService.getBizWarningNumber();
        biz.put("normalNumber",bizTotalNumber- bizwarningNumber);
        biz.put("warningNumber",bizwarningNumber);
        result.put("biz",biz);

        Long hostTotalNumber = zabbixCommonService.getHostTotalNumber();
        Long hostwarningNumber = zabbixCommonService.getHostWarningNumber();
        host.put("normalNumber",hostTotalNumber- hostwarningNumber);
        host.put("warningNumber",hostwarningNumber);
        result.put("host",host);

        Long dbTotalNumber = zabbixCommonService.getDbTotalNumber();
        Long dbwarningNumber = zabbixCommonService.getDbWarningNumber();
        db.put("normalNumber",dbTotalNumber- dbwarningNumber);
        db.put("warningNumber",dbwarningNumber);
        result.put("db",db);
        return result;

    }

    public Map<String,Integer> getSysCounting(Map<String, String> param) {
        Map<String,Integer> result = new HashMap<String,Integer>();
        Integer x86 =   (Integer)jdbcTemplate.queryForObject("select count(*) from HW_X86 ",Integer.class);
        result.put("x86",x86);//x86服务器数量
        Integer vm =   (Integer)jdbcTemplate.queryForObject("select count(*) from hw_virtual_machine ",Integer.class);
        result.put("vm",vm);//虚拟机数量
        Integer db =   (Integer)jdbcTemplate.queryForObject("select count(*) from hw_database ",Integer.class);
        result.put("db",db); //数据库数量
        result.put("netdev",0);//网络设备数量
        Integer biz =   (Integer)jdbcTemplate.queryForObject("select count(*) from res_bussiness_app where status ='1' ",Integer.class);
        result.put("biz",biz);//业务系统
        Integer wf =   (Integer)jdbcTemplate.queryForObject("select count(*) from act_re_procdef t1,act_hi_procinst t2  where t1.id_=t2.PROC_DEF_ID_  and LOWER(t2.END_ACT_ID_)='end' ",Integer.class);
        result.put("wf",wf);//已办结流程
        Integer sw_requirement_change =   (Integer)jdbcTemplate.queryForObject("select count(*) from act_re_procdef t1,act_hi_procinst t2  where t1.id_=t2.PROC_DEF_ID_  and LOWER(t2.END_ACT_ID_)='end' and t1.KEY_='sw_requirement_change' ",Integer.class);
        result.put("sw_requirement_change",sw_requirement_change);
        Integer sw_data_change =   (Integer)jdbcTemplate.queryForObject("select count(*) from act_re_procdef t1,act_hi_procinst t2  where t1.id_=t2.PROC_DEF_ID_  and LOWER(t2.END_ACT_ID_)='end' and t1.KEY_='sw_data_change' ",Integer.class);
        result.put("sw_data_change",sw_data_change);
        Integer sw_data_query =   (Integer)jdbcTemplate.queryForObject("select count(*) from act_re_procdef t1,act_hi_procinst t2  where t1.id_=t2.PROC_DEF_ID_  and LOWER(t2.END_ACT_ID_)='end' and t1.KEY_='sw_data_query' ",Integer.class);
        result.put("sw_data_query",sw_data_query);
        Integer sw_program_deploy =   (Integer)jdbcTemplate.queryForObject("select count(*) from act_re_procdef t1,act_hi_procinst t2  where t1.id_=t2.PROC_DEF_ID_  and LOWER(t2.END_ACT_ID_)='end' and t1.KEY_='sw_program_deploy' ",Integer.class);
        result.put("sw_program_deploy",sw_program_deploy);
        return result;
    }
}
