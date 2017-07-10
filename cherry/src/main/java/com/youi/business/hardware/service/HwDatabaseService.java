package com.youi.business.hardware.service;

import com.youi.business.common.dao.HwDatabaseDao;
import com.youi.business.common.dao.HwDatabaseStorageRelationDao;
import com.youi.business.common.dao.HwDatabaseX86RelationDao;
import com.youi.business.common.entity.HW_DATABASE;
import com.youi.business.common.entity.HW_DATABASE_STORAGE_RELATION;
import com.youi.business.common.entity.HW_DATABASE_X86_RELATION;
import com.youi.business.common.entity.HW_X86;
import com.youi.business.common.util.DevelopKit;
import com.youi.business.hardware.vo.VDatabaseDetail;
import com.youi.business.ip.service.IpAddressService;
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
public class HwDatabaseService {
    @Autowired
    private HwDatabaseDao hwDatabaseDao;
    @Autowired
    private HwDatabaseX86RelationDao hwDatabaseX86RelationDao;
    @Autowired
    private HwDatabaseStorageRelationDao hwDatabaseStorageRelationDao;
    @Autowired
    private IpAddressService ipAddressService;
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
    public Page getDatabaseList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwDatabaseDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_DATABASE> list =   (List<HW_DATABASE>)page1.getResult();
        if(list!=null){
            for(HW_DATABASE p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("name",p.getName());
                m.put("brand",p.getBrand());
                m.put("version",p.getVersion());
                m.put("address",p.getAddress());
                m.put("instance_name",p.getInstance_name());
                m.put("port",p.getPort());
                int  x86_num = (int)jdbcTemplate.queryForObject("SELECT count(*) FROM HW_DATABASE_X86_RELATION WHERE DATABASE_ID=?",new Object[]{p.getId()},Integer.class);
                m.put("x86_num",x86_num);
                int  storage_num = (int)jdbcTemplate.queryForObject("SELECT count(*) FROM HW_DATABASE_STORAGE_RELATION WHERE DATABASE_ID=?",new Object[]{p.getId()},Integer.class);
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
    public Object selectDatabaseDetail(Long id)throws  Exception {
        HW_DATABASE hwDatabase =  hwDatabaseDao.get(id);
        if(hwDatabase==null){return null;}
        VDatabaseDetail detail = new VDatabaseDetail();
        beanMapper.copy(hwDatabase,detail);
        String x86Sql = "select t1.id x86_id,t1.brand x86_brand,type x86_type,t1.core_num,t1.memory_current_capacity from hw_x86 t1,hw_database_x86_relation t2 where t1.id=t2.x86_id and t2.database_id=?";
        List<Map<String,Object>> x86List=  hwDatabaseDao.createSQLQuery(x86Sql,new Object[]{hwDatabase.getId()}).list();
        detail.setX86_list(x86List);
        String storageSql = "select t1.id storage_id,t1.brand ,type ,t1.disk_size,t1.disk_current_num,t1.disk_raid from hw_storage t1,hw_database_storage_relation t2 where t1.id=t2.storage_id and t2.database_id=?";
        List<Map<String,Object>> storageList=  hwDatabaseDao.createSQLQuery(storageSql,new Object[]{hwDatabase.getId()}).list();
        detail.setStorage_list(storageList);
        return  detail;
    }

    public Object saveDatabase(Map<String, Object> param)throws  Exception  {
        Long id=null;
        DevelopKit.toStrMap(param);
        if(StringUtils.isEmpty(String.valueOf(param.get("id")))){
            //新增
            HW_DATABASE hwDatabase = new HW_DATABASE();
            if(!StringUtils.isEmpty((String)param.get("brand"))){
                hwDatabase.setBrand((String)param.get("brand"));
            }
            if(!StringUtils.isEmpty((String)param.get("name"))){
                hwDatabase.setName((String)param.get("name"));
            }
            if(!StringUtils.isEmpty((String)param.get("version"))){
                hwDatabase.setVersion((String)param.get("version"));
            }
            if(!StringUtils.isEmpty((String)param.get("address"))){
                hwDatabase.setAddress((String)param.get("address"));
            }
            if(!StringUtils.isEmpty((String)param.get("instance_name"))){
                hwDatabase.setInstance_name((String)param.get("instance_name"));
            }
            if(!StringUtils.isEmpty((String)param.get("port"))){
                hwDatabase.setPort((String)param.get("port"));
            }
            hwDatabase.setCtime(new Date());
            hwDatabase.setCuserid(Long.parseLong(String.valueOf(param.get("userid"))));
            hwDatabase.setZabbix_switch("0");
            hwDatabaseDao.save(hwDatabase);
            id=hwDatabase.getId();
            List<Map<String,Object>> x86List =  (List<Map<String,Object>>)param.get("x86_list");
            if(x86List!=null){
                for(int i=0;i<x86List.size();i++){
                    Map<String,Object> m=  x86List.get(i);
                    Long  x86_id=  Long.parseLong(String.valueOf(m.get("x86_id"))) ;
                    HW_DATABASE_X86_RELATION x86_relation = new HW_DATABASE_X86_RELATION();
                    x86_relation.setDatabase_id(id);
                    x86_relation.setX86_id(x86_id);
                    hwDatabaseX86RelationDao.save(x86_relation);
                }
            }
            List<Map<String,Object>> storageList =  (List<Map<String,Object>>)param.get("storage_list");
            if(storageList!=null){
                for(int i=0;i<storageList.size();i++){
                    Map<String,Object> m=  storageList.get(i);
                    Long  storage_id=  Long.parseLong(String.valueOf(m.get("storage_id"))) ;
                    HW_DATABASE_STORAGE_RELATION storage_relation = new HW_DATABASE_STORAGE_RELATION();
                    storage_relation.setDatabase_id(id);
                    storage_relation.setStorage_id(storage_id);
                    hwDatabaseStorageRelationDao.save(storage_relation);
                }
            }

        }else{
            //修改
            HW_DATABASE hwDatabase =  hwDatabaseDao.get(Long.parseLong(String.valueOf(param.get("id"))));
            hwDatabase.setId(Long.parseLong(String.valueOf(param.get("id"))));
            if(!StringUtils.isEmpty((String)param.get("brand"))){
                hwDatabase.setBrand((String)param.get("brand"));
            }
            if(!StringUtils.isEmpty((String)param.get("name"))){
                hwDatabase.setName((String)param.get("name"));
            }

            if(!StringUtils.isEmpty((String)param.get("version"))){
                hwDatabase.setVersion((String)param.get("version"));
            }
            if(!StringUtils.isEmpty((String)param.get("address"))){
                hwDatabase.setAddress((String)param.get("address"));
            }
            if(!StringUtils.isEmpty((String)param.get("instance_name"))){
                hwDatabase.setInstance_name((String)param.get("instance_name"));
            }
            if(!StringUtils.isEmpty((String)param.get("port"))){
                hwDatabase.setPort((String)param.get("port"));
            }

            hwDatabaseDao.update(hwDatabase);
            id=hwDatabase.getId();
            //删除原x86list
            jdbcTemplate.update("delete from hw_database_x86_relation where database_id=?",new Object[]{id});
            List<Map<String,Object>> x86List =  (List<Map<String,Object>>)param.get("x86_list");
            if(x86List!=null){
                for(int i=0;i<x86List.size();i++){
                    Map<String,Object> m=  x86List.get(i);
                    Long  x86_id=  Long.parseLong(String.valueOf(m.get("x86_id"))) ;
                    HW_DATABASE_X86_RELATION x86_relation = new HW_DATABASE_X86_RELATION();
                    x86_relation.setDatabase_id(id);
                    x86_relation.setX86_id(x86_id);
                    hwDatabaseX86RelationDao.save(x86_relation);
                }
            }
            //删除原storagelist
            jdbcTemplate.update("delete from hw_database_storage_relation where database_id=?",new Object[]{id});
            List<Map<String,Object>> storageList =  (List<Map<String,Object>>)param.get("storage_list");
            if(storageList!=null){
                for(int i=0;i<storageList.size();i++){
                    Map<String,Object> m=  storageList.get(i);
                    Long  storage_id=  Long.parseLong(String.valueOf(m.get("storage_id"))) ;
                    HW_DATABASE_STORAGE_RELATION storage_relation = new HW_DATABASE_STORAGE_RELATION();
                    storage_relation.setDatabase_id(id);
                    storage_relation.setStorage_id(storage_id);
                    hwDatabaseStorageRelationDao.save(storage_relation);
                }
            }
        }
        if(true){
            Map<String, String> ipAddress = new HashMap<String,String>();
            ipAddress.put("ip_add",StringUtils.null2String(param.get("address")));
            ipAddress.put("biz_type","HW_DATABASE");
            ipAddress.put("biz_id",StringUtils.null2String(id));
            ipAddressService.saveIpAddress(ipAddress);
        }
        return this.selectDatabaseDetail(id);
    }


    public boolean deleteDatabase(Long userid, Long id) {
        //删除原x86list
        jdbcTemplate.update("delete from hw_database_x86_relation where database_id=?",new Object[]{id});
        //删除原storagelist
        jdbcTemplate.update("delete from hw_database_storage_relation where database_id=?",new Object[]{id});
        hwDatabaseDao.removeById(id);
       /*     HW_DATABASE db = hwDatabaseDao.get(id);
            if(db!=null){
                return false;
            }
     */
        if(true){
            ipAddressService.deleteIpAddress("HW_DATABASE",id);
        }
        return true;
    }

    public List<Map<String,Object>> getDatabaseCodeTableList() {
        List list = hwDatabaseDao.createSQLQuery("SELECT  ID CODE,NAME VALUE  FROM HW_DATABASE").list();
        return list;
    }
}
