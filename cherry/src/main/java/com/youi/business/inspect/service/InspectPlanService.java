package com.youi.business.inspect.service;

import com.youi.business.common.dao.*;
import com.youi.business.common.entity.RI_PLAN;
import com.youi.business.common.entity.RI_PLAN_TARGET;
import com.youi.business.common.entity.RI_TEMPLATE;
import com.youi.business.common.entity.RI_TPL_ITEMS;
import com.youi.business.common.store.StoreConnector;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡检计划
 */
@Service
public class InspectPlanService {
    @Autowired
    private RiPlanDao riPlanDao;
    @Autowired
    private VHwDevDao vHwDevDao;
    @Autowired
    private RiTplItemsDao riTplItemsDao;
    @Autowired
    private RiTemplateDao riTemplateDao;
    @Autowired
    private RiPlanTargetDao riPlanTargetDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private StoreConnector storeConnector;

    public Object queryInspectPlanList(Map<String, String> param) throws Exception{
         //01 每日一次 11 每周一次 21 每月一次 31 每季一次
        String sql = " select t1.*,t2.ri_code from RI_PLAN t1,RI_TEMPLATE t2 where t1.tpl_id=t2.id";
        List<Map<String, Object>> list = riPlanDao.createSQLQuery(sql).list();
        //巡检类型 0 机房基础设施、1 服务器、2 网络设备、3 支撑软件
        List<Map<String, Object>> list0 = new ArrayList<Map<String, Object>>();//0 机房基础设施
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();//1 服务器
        List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();//2 网络设备
        List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();//3 支撑软件
        if (list != null) {
            for (Map<String, Object> p : list) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("id", p.get("id"));
                m.put("name", p.get("name"));
                m.put("period", p.get("period"));
                m.put("worker_id", p.get("worker_id"));
                m.put("worker_name", CodeTableUtil.code2mean("SYSUSER",p.get("worker_id")));
                m.put("status", p.get("status"));
                String ri_code = StringUtils.null2String(p.get("ri_code")) ;
                if("0".equals(ri_code)){
                    list0.add(m);
                }
                else  if("1".equals(ri_code)){
                    list1.add(m);
                }
                else  if("2".equals(ri_code)){
                    list1.add(m);
                }else  if("3".equals(ri_code)){
                    list1.add(m);
                }
            }
        }
        Map<String,Object>result = new HashMap<String,Object>();
        result.put("0",list0);
        result.put("1",list1);
        result.put("2",list2);
        result.put("3",list3);
        return result;
    }



    public Object selectInspectPlanDetail(Long id) throws Exception {
        StringBuilder sql = new StringBuilder();
        RI_PLAN plan =  riPlanDao.get(id);
        RI_TEMPLATE template = riTemplateDao.get(plan.getTpl_id());
        List<Map<String,Object>> targets = riPlanTargetDao.createSQLQuery("select t1.dev_id,t1.dev_type,t1.plan_id,t2.dev_name,t2.dev_no from RI_PLAN_TARGET t1,V_HW_DEV t2 where t1.plan_id=? and t1.dev_id=t2.dev_id and t1.dev_type=t2.dev_type ",plan.getId()).list();
        Map<String,Object> detail = new HashMap<String,Object>();
        List<Map<String,Object>> dev_list = new ArrayList<Map<String,Object>>();
        if(targets!=null){
            for (Map<String,Object> target:targets) {
                Map<String,Object> dev = new HashMap<String,Object>();
                dev.put("dev_id",target.get("dev_id"));
                dev.put("dev_type",target.get("dev_type"));
                dev.put("dev_no",target.get("dev_no"));
                dev.put("dev_name",target.get("dev_name"));
                Integer num = riPlanTargetDao.getJdbcTemplate().queryForObject("select ifnull(count(*),0) as num from RI_PLAN_TARGET where dev_id=? and dev_type=? and plan_id <>?",new Object[]{target.get("dev_id"),target.get("dev_type"),plan.getId()},Integer.class);
                dev.put("is_other_plan",num>0?"1":"0");
                dev_list.add(dev);
            }
        }
        detail.put("id",plan.getId());
        detail.put("ri_code",template.getRi_code());
        detail.put("period",plan.getPeriod());
        detail.put("name",plan.getName());
        detail.put("worker_id",plan.getWorker_id());
        detail.put("worker_name",CodeTableUtil.code2mean("SYSUSER",plan.getWorker_id()));
        detail.put("status",plan.getStatus());
        Map<String,Object> tpl = new HashMap<String,Object>();
        tpl.put("id",template.getId());
        tpl.put("name",template.getName());
        tpl.put("description",template.getDescription());
        tpl.put("status",template.getStatus());
        List<RI_TPL_ITEMS> items = riTplItemsDao.find("from RI_TPL_ITEMS where tpl_id=?",template.getId());
        List<String> strItems = new ArrayList<String>();
        if(items!=null){
            for (RI_TPL_ITEMS item:items) {
                strItems.add(item.getItem());
            }
        }
        tpl.put("items", strItems);
        detail.put("tpl",tpl);
        detail.put("dev_list",dev_list);
        return  detail;
    }

    public Object saveInspectPlan(Map<String, Object> param)throws Exception {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            RI_PLAN obj = new RI_PLAN();
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("tpl_id"))){
                obj.setTpl_id(Long.parseLong(StringUtils.null2String(param.get("tpl_id"))));
            }
            if(!StringUtils.isEmpty(param.get("period"))){
                obj.setPeriod(StringUtils.null2String(param.get("period")));
            }
            if(!StringUtils.isEmpty(param.get("worker_id"))){
                obj.setWorker_id(Long.parseLong(StringUtils.null2String(param.get("worker_id"))));
            }
            if(!StringUtils.isEmpty(param.get("status"))){
                obj.setStatus(StringUtils.null2String(param.get("status")));
            }
            riPlanDao.save(obj);
            id=obj.getId();
        }else{
            //更新
            RI_PLAN obj = riPlanDao.get(Long.parseLong(StringUtils.null2String(param.get("id"))));
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("tpl_id"))){
                obj.setTpl_id(Long.parseLong(StringUtils.null2String(param.get("tpl_id"))));
            }
            if(!StringUtils.isEmpty(param.get("period"))){
                obj.setPeriod(StringUtils.null2String(param.get("period")));
            }
            if(!StringUtils.isEmpty(param.get("worker_id"))){
                obj.setWorker_id(Long.parseLong(StringUtils.null2String(param.get("worker_id"))));
            }
            if(!StringUtils.isEmpty(param.get("status"))){
                obj.setStatus(StringUtils.null2String(param.get("status")));
            }
            riPlanDao.update(obj);
            id=obj.getId();
        }
        List<Map<String,Object>> devList =(List<Map<String,Object>>) param.get("dev_list");
        if(devList!=null){
            //删除原来的RI_PLAN_TARGET
            riPlanTargetDao.batchUpdate("delete from RI_PLAN_TARGET where plan_id=?",id);
            for(Map<String,Object> dev:devList){
                RI_PLAN_TARGET o = new RI_PLAN_TARGET();
                o.setStatus("1");
                if(!StringUtils.isEmpty(dev.get("dev_id"))){
                    o.setDev_id(Long.parseLong(StringUtils.null2String(dev.get("dev_id"))));
                }
                if(!StringUtils.isEmpty(dev.get("dev_type"))){
                    o.setDev_type(StringUtils.null2String(dev.get("dev_type")));
                }
               o.setPlan_id(id);
                riPlanTargetDao.save(o);
            }
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("id",id);
        return result;
    }

    
    
}
