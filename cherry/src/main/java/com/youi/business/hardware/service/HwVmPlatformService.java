package com.youi.business.hardware.service;

import com.youi.business.common.dao.*;
import com.youi.business.common.entity.*;
import com.youi.business.common.util.DevelopKit;
import com.youi.business.hardware.vo.VVmPlatformDetail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.util.StringUtils;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by jinliang on 2016/9/21.
 */
@Transactional
@Service
public class HwVmPlatformService {
    @Autowired
    private HwVmPlatformDao hwVmPlatformDao;
    @Autowired
    private HwVmPlatformX86RelationDao hwVmPlatformX86RelationDao;
    @Autowired
    private HwVmPlatformStorageRelationDao hwVmPlatformStorageRelationDao;
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
    public Page getVmPlatformList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwVmPlatformDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_VM_PLATFORM> list =   (List<HW_VM_PLATFORM>)page1.getResult();
        if(list!=null){
            for(HW_VM_PLATFORM p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("name",p.getName());
                m.put("brand",p.getBrand());
                m.put("type",p.getType());
                m.put("console",p.getConsole());
                int  x86_num = (int)jdbcTemplate.queryForObject("SELECT count(*) FROM HW_VM_PLATFORM_X86_RELATION WHERE VM_PLATFORM_ID=?",new Object[]{p.getId()},Integer.class);
                m.put("x86_num",x86_num);
                int  storage_num = (int)jdbcTemplate.queryForObject("SELECT count(*) FROM HW_VM_PLATFORM_STORAGE_RELATION WHERE VM_PLATFORM_ID=?",new Object[]{p.getId()},Integer.class);
                m.put("storage_num",storage_num);
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
    public Object selectVmPlatformDetail(Long id)throws  Exception {
        HW_VM_PLATFORM hwVmPlatform =  hwVmPlatformDao.get(id);
        if(hwVmPlatform==null){return null;}
        VVmPlatformDetail detail = new VVmPlatformDetail();
        beanMapper.copy(hwVmPlatform,detail);
        String x86Sql = "select t1.id x86_id,t1.brand x86_brand,type x86_type,t1.core_num,t1.memory_current_capacity from hw_x86 t1,hw_vm_platform_x86_relation t2 where t1.id=t2.x86_id and t2.vm_platform_id=?";
        List<Map<String,Object>> x86List=  hwVmPlatformDao.createSQLQuery(x86Sql,new Object[]{hwVmPlatform.getId()}).list();
        detail.setX86_list(x86List);
        String storageSql = "select t1.id storage_id,t1.brand ,type ,t1.disk_size,t1.disk_current_num,t1.disk_raid from hw_storage t1,hw_vm_platform_storage_relation t2 where t1.id=t2.storage_id and t2.vm_platform_id=?";
        List<Map<String,Object>> storageList=  hwVmPlatformDao.createSQLQuery(storageSql,new Object[]{hwVmPlatform.getId()}).list();
        detail.setStorage_list(storageList);
        return  detail;
    }

    public Object saveVmPlatform(Map<String, Object> param)throws  Exception  {
        Long id=null;
        DevelopKit.toStrMap(param);
        if(StringUtils.isEmpty(String.valueOf(param.get("id")))){
            //新增
            HW_VM_PLATFORM hwVmPlatform = new HW_VM_PLATFORM();
            if(!StringUtils.isEmpty((String)param.get("brand"))){
                hwVmPlatform.setBrand((String)param.get("brand"));
            }
            if(!StringUtils.isEmpty((String)param.get("name"))){
                hwVmPlatform.setName((String)param.get("name"));
            }

            if(!StringUtils.isEmpty((String)param.get("type"))){
                hwVmPlatform.setType((String)param.get("type"));
            }
            if(!StringUtils.isEmpty((String)param.get("console"))){
                hwVmPlatform.setConsole((String)param.get("console"));
            }
            hwVmPlatform.setCtime(new Date());
            hwVmPlatform.setCuserid(Long.parseLong(String.valueOf(param.get("userid"))));
            hwVmPlatformDao.save(hwVmPlatform);
            id=hwVmPlatform.getId();
            List<Map<String,Object>> x86List =  (List<Map<String,Object>>)param.get("x86_list");
            if(x86List!=null){
                for(int i=0;i<x86List.size();i++){
                    Map<String,Object> m=  x86List.get(i);
                    Long  x86_id=  Long.parseLong(String.valueOf(m.get("x86_id"))) ;
                    HW_VM_PLATFORM_X86_RELATION x86_relation = new HW_VM_PLATFORM_X86_RELATION();
                    x86_relation.setVm_platform_id(id);
                    x86_relation.setX86_id(x86_id);
                    hwVmPlatformX86RelationDao.save(x86_relation);
                }
            }
            List<Map<String,Object>> storageList =  (List<Map<String,Object>>)param.get("storage_list");
            if(storageList!=null){
                for(int i=0;i<storageList.size();i++){
                    Map<String,Object> m=  storageList.get(i);
                    Long  storage_id=  Long.parseLong(String.valueOf(m.get("storage_id"))) ;
                    HW_VM_PLATFORM_STORAGE_RELATION storage_relation = new HW_VM_PLATFORM_STORAGE_RELATION();
                    storage_relation.setVm_platform_id(id);
                    storage_relation.setStorage_id(storage_id);
                    hwVmPlatformStorageRelationDao.save(storage_relation);
                }
            }

        }else{
            //修改
            HW_VM_PLATFORM hwVmPlatform =  hwVmPlatformDao.get(Long.parseLong(String.valueOf(param.get("id"))));
            hwVmPlatform.setId(Long.parseLong(String.valueOf(param.get("id"))));
            if(!StringUtils.isEmpty((String)param.get("brand"))){
                hwVmPlatform.setBrand((String)param.get("brand"));
            }
            if(!StringUtils.isEmpty((String)param.get("name"))){
                hwVmPlatform.setName((String)param.get("name"));
            }

            if(!StringUtils.isEmpty((String)param.get("type"))){
                hwVmPlatform.setType((String)param.get("type"));
            }
            if(!StringUtils.isEmpty((String)param.get("console"))){
                hwVmPlatform.setConsole((String)param.get("console"));
            }

            hwVmPlatformDao.update(hwVmPlatform);
            id=hwVmPlatform.getId();
            //删除原x86list
            jdbcTemplate.update("delete from hw_vm_platform_x86_relation where vm_platform_id=?",new Object[]{id});
            List<Map<String,Object>> x86List =  (List<Map<String,Object>>)param.get("x86_list");
            if(x86List!=null){
                for(int i=0;i<x86List.size();i++){
                    Map<String,Object> m=  x86List.get(i);
                    Long  x86_id=  Long.parseLong(String.valueOf(m.get("x86_id"))) ;
                    HW_VM_PLATFORM_X86_RELATION x86_relation = new HW_VM_PLATFORM_X86_RELATION();
                    x86_relation.setVm_platform_id(id);
                    x86_relation.setX86_id(x86_id);
                    hwVmPlatformX86RelationDao.save(x86_relation);
                }
            }
            //删除原storagelist
            jdbcTemplate.update("delete from hw_vm_platform_storage_relation where vm_platform_id=?",new Object[]{id});
            List<Map<String,Object>> storageList =  (List<Map<String,Object>>)param.get("storage_list");
            if(storageList!=null){
                for(int i=0;i<storageList.size();i++){
                    Map<String,Object> m=  storageList.get(i);
                    Long  storage_id=  Long.parseLong(String.valueOf(m.get("storage_id"))) ;
                    HW_VM_PLATFORM_STORAGE_RELATION storage_relation = new HW_VM_PLATFORM_STORAGE_RELATION();
                    storage_relation.setVm_platform_id(id);
                    storage_relation.setStorage_id(storage_id);
                    hwVmPlatformStorageRelationDao.save(storage_relation);
                }
            }
        }
        return this.selectVmPlatformDetail(id);
    }


    public boolean deleteVmPlatform(Long userid, Long id) {

            //删除原x86list
            jdbcTemplate.update("delete from hw_vm_platform_x86_relation where vm_platform_id=?",new Object[]{id});
            //删除原storagelist
            jdbcTemplate.update("delete from hw_vm_platform_storage_relation where vm_platform_id=?",new Object[]{id});
            hwVmPlatformDao.removeById(id);
         /*   HW_VM_PLATFORM hwVmPlatform = hwVmPlatformDao.get(id);
            if(hwVmPlatform!=null){
                return false;
            }*/
        return true;
    }

    public List<Map<String,Object>> getVmPlatformCodeTableList() {
            List list = hwVmPlatformDao.createSQLQuery("SELECT  ID CODE,NAME VALUE  FROM HW_VM_PLATFORM").list();
            return list;
    }
}
