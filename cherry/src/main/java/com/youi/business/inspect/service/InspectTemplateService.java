package com.youi.business.inspect.service;

import com.youi.business.common.dao.RiTemplateDao;
import com.youi.business.common.dao.RiTplItemsDao;
import com.youi.business.common.entity.RI_TEMPLATE;
import com.youi.business.common.entity.RI_TPL_ITEMS;
import com.youi.business.common.store.StoreConnector;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡检模板
 */
@Service
public class InspectTemplateService {
    @Autowired
    private RiTemplateDao riTemplateDao;

    @Autowired
    private RiTplItemsDao riTplItemsDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private StoreConnector storeConnector;

    public Object queryInspectTemplateList(Map<String, String> param) throws Exception{
        //分页
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        if (!StringUtils.isEmpty(param.get("ri_code"))) { //合同编号
            filters.add(new PropertyFilter("EQS_ri_code", StringUtils.null2String(param.get("ri_code"))));
        }
        if (!StringUtils.isEmpty(param.get("name"))) {
            filters.add(new PropertyFilter("LIKES_name", "%" + param.get("name") + "%"));
        }
        String sql = " select t1.*,(select ifnull(count(*),0) from RI_TPL_ITEMS t2 where t2.tpl_id =t1.id) as item_count from RI_TEMPLATE t1 where 1=1";
        List<Map<String, Object>> list = riTemplateDao.createSQLQuery(sql, filters,"").list();
        if (list != null) {
            for (Map<String, Object> p : list) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("id", p.get("id"));
                m.put("name", p.get("name"));
                m.put("ri_code", p.get("ri_code"));
                m.put("description", p.get("description"));
                m.put("item_count", p.get("item_count"));
                m.put("status", p.get("status"));
                results.add(m);
            }
        }
        return results;
    }



    public Object selectInspectTemplateDetail(Long id) throws Exception {
        StringBuilder sql = new StringBuilder();
        RI_TEMPLATE m =  riTemplateDao.get(id);
        Map<String,Object> detail = new HashMap<String,Object>();
        List<RI_TPL_ITEMS> items = riTplItemsDao.find("from RI_TPL_ITEMS where tpl_id=?",m.getId());
        List<String> strItems = new ArrayList<String>();
        if(items!=null){
            for (RI_TPL_ITEMS item:items) {
                strItems.add(item.getItem());
            }
        }
         detail.put("id",m.getId());
        detail.put("ri_code",m.getRi_code());
        detail.put("description",m.getDescription());
        detail.put("status",m.getStatus());
        detail.put("item_count",strItems.size());
        detail.put("items",strItems);
        return  detail;
    }

    public Object saveInspectTemplate(Map<String, Object> param)throws Exception {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            RI_TEMPLATE obj = new RI_TEMPLATE();
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("ri_code"))){
                obj.setRi_code(StringUtils.null2String(param.get("ri_code")));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                obj.setDescription(StringUtils.null2String(param.get("description")));
            }
            if(!StringUtils.isEmpty(param.get("status"))){
                obj.setStatus(StringUtils.null2String(param.get("status")));
            }
            riTemplateDao.save(obj);
            id=obj.getId();
        }else{
            //更新
            RI_TEMPLATE obj = riTemplateDao.get(Long.parseLong(StringUtils.null2String(param.get("id"))));
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("ri_code"))){
                obj.setRi_code(StringUtils.null2String(param.get("ri_code")));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                obj.setDescription(StringUtils.null2String(param.get("description")));
            }
            if(!StringUtils.isEmpty(param.get("status"))){
                obj.setStatus(StringUtils.null2String(param.get("status")));
            }
            riTemplateDao.update(obj);
            id=obj.getId();
        }
        //删除原来的items关联
        riTplItemsDao.batchUpdate("delete from RI_TPL_ITEMS where tpl_id=?",id);
        List<String> items =(List<String>) param.get("items");
        if(items!=null){
            for(String item:items){
                RI_TPL_ITEMS o = new RI_TPL_ITEMS();
                o.setItem(item);
                o.setTpl_id(id);
                riTplItemsDao.save(o);
            }
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("id",id);
        return result;
    }

    
    
}
