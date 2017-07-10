package com.youi.business.hardware.service;

import com.youi.business.common.dao.HwMiddlewareDao;
import com.youi.business.common.dao.HwMiddlewareVmRelationDao;
import com.youi.business.common.dao.HwMiddlewareX86RelationDao;
import com.youi.business.common.entity.HW_MIDDLEWARE;
import com.youi.business.common.entity.HW_MIDDLEWARE_VM_RELATION;
import com.youi.business.common.entity.HW_MIDDLEWARE_X86_RELATION;
import com.youi.business.common.util.DevelopKit;
import com.youi.business.hardware.vo.VMiddlewareDetail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinliang on 2016/9/21.
 */
@Transactional
@Service
public class HwMiddlewareService {
    @Autowired
    private HwMiddlewareDao hwMiddlewareDao;
    @Autowired
    private HwMiddlewareX86RelationDao hwMiddlewareX86RelationDao;
    @Autowired
    private HwMiddlewareVmRelationDao hwMiddlewareVmRelationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BeanMapper beanMapper;



    /**
     * 分页获得业务系统list
     * @param userid
     * @param page
     * @return
     * @throws Exception
     */
    public Page getMiddlewareList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwMiddlewareDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_MIDDLEWARE> list =   (List<HW_MIDDLEWARE>)page1.getResult();
        if(list!=null){
            for(HW_MIDDLEWARE p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("name",p.getName());
                m.put("brand",p.getBrand());
                m.put("version",p.getVersion());
                m.put("console",p.getConsole());
                int  x86_num = (int)jdbcTemplate.queryForObject("SELECT count(*) FROM HW_MIDDLEWARE_X86_RELATION WHERE MW_ID=?",new Object[]{p.getId()},Integer.class);
                m.put("x86_num",x86_num);
                int  vm_num = (int)jdbcTemplate.queryForObject("SELECT count(*) FROM HW_MIDDLEWARE_VM_RELATION WHERE MW_ID=?",new Object[]{p.getId()},Integer.class);
                m.put("vm_num",vm_num);
                results.add(m);
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }




    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectMiddlewareDetail(Long id)throws  Exception {
        HW_MIDDLEWARE hwMiddleware =  hwMiddlewareDao.get(id);
        if(hwMiddleware==null){return null;}
        VMiddlewareDetail detail = new VMiddlewareDetail();
        beanMapper.copy(hwMiddleware,detail);
        String x86Sql = "select t1.id x86_id,t1.name x86_name,t1.core_num,t1.memory_current_capacity from hw_x86 t1,hw_middleware_x86_relation t2 where t1.id=t2.x86_id and t2.mw_id=?";
        List<Map<String,Object>> x86List=  hwMiddlewareDao.createSQLQuery(x86Sql,new Object[]{hwMiddleware.getId()}).list();
        detail.setX86_list(x86List);
        String xmSql = "select t1.id vm_id,t1.name xm_name ,t1.core_num,t1.memory memory_current_capacity from hw_virtual_machine t1,hw_middleware_vm_relation t2 where t1.id=t2.vm_id and t2.mw_id=?";
        List<Map<String,Object>> xmList=  hwMiddlewareDao.createSQLQuery(xmSql,new Object[]{hwMiddleware.getId()}).list();
        detail.setVm_list(xmList);
        return  detail;
    }

    public Object saveMiddleware(Map<String, Object> param)throws  Exception  {
        Long id=null;
        DevelopKit.toStrMap(param);
        if(StringUtils.isEmpty(String.valueOf(param.get("id")))){
            //新增
            HW_MIDDLEWARE hwMiddleware = new HW_MIDDLEWARE();
            if(!StringUtils.isEmpty((String)param.get("brand"))){
                hwMiddleware.setBrand((String)param.get("brand"));
            }
            if(!StringUtils.isEmpty((String)param.get("name"))){
                hwMiddleware.setName((String)param.get("name"));
            }
            if(!StringUtils.isEmpty((String)param.get("version"))){
                hwMiddleware.setVersion((String)param.get("version"));
            }

            if(!StringUtils.isEmpty((String)param.get("console"))){
                hwMiddleware.setConsole((String)param.get("console"));
            }

            hwMiddleware.setCtime(new Date());
            hwMiddleware.setCuserid(Long.parseLong(String.valueOf(param.get("userid"))));
            hwMiddlewareDao.save(hwMiddleware);
            id=hwMiddleware.getId();
            List<Map<String,Object>> x86List =  (List<Map<String,Object>>)param.get("x86_list");
            if(x86List!=null){
                for(int i=0;i<x86List.size();i++){
                    Map<String,Object> m=  x86List.get(i);
                    Long  x86_id=  Long.parseLong(String.valueOf(m.get("x86_id"))) ;
                    HW_MIDDLEWARE_X86_RELATION x86_relation = new HW_MIDDLEWARE_X86_RELATION();
                    x86_relation.setMw_id(id);
                    x86_relation.setX86_id(x86_id);
                    hwMiddlewareX86RelationDao.save(x86_relation);
                }
            }
            List<Map<String,Object>> vmList =  (List<Map<String,Object>>)param.get("vm_list");
            if(vmList!=null){
                for(int i=0;i<vmList.size();i++){
                    Map<String,Object> m=  vmList.get(i);
                    Long  vm_id=  Long.parseLong(String.valueOf(m.get("vm_id"))) ;
                    HW_MIDDLEWARE_VM_RELATION vm_relation = new HW_MIDDLEWARE_VM_RELATION();
                    vm_relation.setMw_id(id);
                    vm_relation.setVm_id(vm_id);
                    hwMiddlewareVmRelationDao.save(vm_relation);
                }
            }

        }else{
            //修改
            HW_MIDDLEWARE hwMiddleware =  hwMiddlewareDao.get(Long.parseLong(String.valueOf(param.get("id"))));
            hwMiddleware.setId(Long.parseLong(String.valueOf(param.get("id"))));
            if(!StringUtils.isEmpty((String)param.get("brand"))){
                hwMiddleware.setBrand((String)param.get("brand"));
            }
            if(!StringUtils.isEmpty((String)param.get("name"))){
                hwMiddleware.setName((String)param.get("name"));
            }
            if(!StringUtils.isEmpty((String)param.get("version"))){
                hwMiddleware.setVersion((String)param.get("version"));
            }

            if(!StringUtils.isEmpty((String)param.get("console"))){
                hwMiddleware.setConsole((String)param.get("console"));
            }

            hwMiddlewareDao.update(hwMiddleware);
            id=hwMiddleware.getId();
            //删除原x86list
            jdbcTemplate.update("delete from hw_middleware_x86_relation where mw_id=?",new Object[]{id});
            List<Map<String,Object>> x86List =  (List<Map<String,Object>>)param.get("x86_list");
            if(x86List!=null){
                for(int i=0;i<x86List.size();i++){
                    Map<String,Object> m=  x86List.get(i);
                    Long  x86_id=  Long.parseLong(String.valueOf(m.get("x86_id"))) ;
                    HW_MIDDLEWARE_X86_RELATION x86_relation = new HW_MIDDLEWARE_X86_RELATION();
                    x86_relation.setMw_id(id);
                    x86_relation.setX86_id(x86_id);
                    hwMiddlewareX86RelationDao.save(x86_relation);
                }
            }
            //删除原xmlist
            jdbcTemplate.update("delete from hw_middleware_vm_relation where mw_id=?",new Object[]{id});
            List<Map<String,Object>> vmList =  (List<Map<String,Object>>)param.get("vm_list");
            if(vmList!=null){
                for(int i=0;i<vmList.size();i++){
                    Map<String,Object> m=  vmList.get(i);
                    Long  vm_id=  Long.parseLong(String.valueOf(m.get("vm_id"))) ;
                    HW_MIDDLEWARE_VM_RELATION vm_relation = new HW_MIDDLEWARE_VM_RELATION();
                    vm_relation.setMw_id(id);
                    vm_relation.setVm_id(vm_id);
                    hwMiddlewareVmRelationDao.save(vm_relation);
                }
            }
        }
        return this.selectMiddlewareDetail(id);
    }


    public boolean deleteMiddleware(Long userid, Long id) {
        //删除原x86list
        jdbcTemplate.update("delete from hw_middleware_x86_relation where mw_id=?",new Object[]{id});
        //删除原xmlist
        jdbcTemplate.update("delete from hw_middleware_vm_relation where mw_id=?",new Object[]{id});
        hwMiddlewareDao.removeById(id);
        return true;
    }
}
