package com.youi.business.hardware.service;

import com.youi.business.common.dao.HwCabinetDao;
import com.youi.business.common.dao.HwComputerRoomDao;
import com.youi.business.common.dao.HwStorageDao;
import com.youi.business.common.entity.HW_CABINET;
import com.youi.business.common.entity.HW_COMPUTER_ROOM;
import com.youi.business.common.entity.HW_STORAGE;
import com.youi.business.hardware.vo.VStorageDetail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.util.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinliang on 2016/9/21.
 */
@Transactional
@Service
public class HwStorageService {
    @Autowired
    private HwStorageDao hwStorageDao;
    @Autowired
    private HwCabinetDao hwCabinetDao;
    @Autowired
    private HwComputerRoomDao hwComputerRoomDao;

    @Autowired
    private BeanMapper beanMapper;



    /**
     * 分页获得业务系统list
     * @param userid
     * @param page
     * @return
     * @throws Exception
     */
    public Page getStorageList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwStorageDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_STORAGE> list =   (List<HW_STORAGE>)page1.getResult();
        if(list!=null){
            for(HW_STORAGE p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                String  room_name="";
                if(p.getRoom_id()!=null){
                   HW_COMPUTER_ROOM room = hwComputerRoomDao.get(p.getRoom_id());
                    if(room!=null){
                        room_name = room.getName();
                    }
                }
                m.put("room_name",room_name);
                String  cabinet_name="";
                if(p.getCabinet_id()!=null){
                    HW_CABINET cabinet = hwCabinetDao.get(p.getCabinet_id());
                    if(cabinet!=null){
                        cabinet_name = cabinet.getName();
                    }
                }
                m.put("cabinet_name",cabinet_name);
                m.put("brand",p.getBrand());
                m.put("type",p.getType());
                m.put("high",p.getHigh());
                m.put("width",p.getWidth());
                m.put("depth",p.getDepth());
                m.put("main_usage",p.getMain_usage());
                m.put("disk_interface_type",p.getDisk_interface_type());
                m.put("disk_size",p.getDisk_size());
                m.put("disk_current_num",p.getDisk_current_num());
                m.put("disk_max_num",p.getDisk_max_num());
                m.put("disk_raid",p.getDisk_raid());
                m.put("cpu_type",p.getCpu_type());
                results.add(m);
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }


    /**
     * 删除
     * @param userid
     * @param id
     * @return
     */
    public boolean deleteStorage(Long userid, Long id) {
            hwStorageDao.removeById(id);
         /*   if(hwStorageDao.get(id)!=null){
                return false;
            }*/

        return true;
    }


    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectStorageDetail(Long id)throws  Exception {
        HW_STORAGE hwStorage =  hwStorageDao.get(id);
        if(hwStorage==null){return null;}
        VStorageDetail detail = new VStorageDetail();
        beanMapper.copy(hwStorage,detail);
        return  detail;
    }

    public Object saveStorage(Map<String, String> param)throws  Exception  {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            HW_STORAGE hwStorage = new HW_STORAGE();
            if(!StringUtils.isEmpty(param.get("brand"))){
                hwStorage.setBrand(StringUtils.null2String(param.get("brand")));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                hwStorage.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("room_id"))){
                hwStorage.setRoom_id(Long.parseLong(param.get("room_id")));
            }
            if(!StringUtils.isEmpty(param.get("cabinet_id"))){
                hwStorage.setCabinet_id(Long.parseLong(param.get("cabinet_id")));
            }
            if(!StringUtils.isEmpty(param.get("type"))){
                hwStorage.setType(param.get("type"));
            }
            if(!StringUtils.isEmpty(param.get("high"))){
                hwStorage.setHigh(Long.parseLong(param.get("high")));
            }
            if(!StringUtils.isEmpty(param.get("depth"))){
                hwStorage.setDepth(Long.parseLong(param.get("depth")));
            }
            if(!StringUtils.isEmpty(param.get("width"))){
                hwStorage.setWidth(Long.parseLong(param.get("width")));
            }
            if(!StringUtils.isEmpty(param.get("main_usage"))){
                hwStorage.setMain_usage(param.get("main_usage"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_type"))){
                hwStorage.setCpu_type(param.get("cpu_type"));
            }

            if(!StringUtils.isEmpty(param.get("disk_interface_type"))){
                hwStorage.setDisk_interface_type(param.get("disk_interface_type"));
            }
            if(!StringUtils.isEmpty(param.get("disk_size"))){
                hwStorage.setDisk_size(Long.parseLong(param.get("disk_size")));
            }
            if(!StringUtils.isEmpty(param.get("disk_current_num"))){
                hwStorage.setDisk_current_num(Long.parseLong(param.get("disk_current_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_max_num"))){
                hwStorage.setDisk_max_num(Long.parseLong(param.get("disk_max_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_raid"))){
                hwStorage.setDisk_raid(param.get("disk_raid"));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                hwStorage.setDescription(param.get("description"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_type"))){
                hwStorage.setCpu_type(param.get("cpu_type"));
            }

            hwStorage.setCdate(new Date());
            hwStorage.setUserid(Long.parseLong(param.get("userid")));
            hwStorageDao.save(hwStorage);
            id=hwStorage.getId();
        }else{
            //更新
            HW_STORAGE hwStorage = hwStorageDao.get(Long.parseLong(param.get("id")));
            if(!StringUtils.isEmpty(param.get("brand"))){
                hwStorage.setBrand(StringUtils.null2String(param.get("brand")));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                hwStorage.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("room_id"))){
                hwStorage.setRoom_id(Long.parseLong(param.get("room_id")));
            }
            if(!StringUtils.isEmpty(param.get("cabinet_id"))){
                hwStorage.setCabinet_id(Long.parseLong(param.get("cabinet_id")));
            }
            if(!StringUtils.isEmpty(param.get("type"))){
                hwStorage.setType(param.get("type"));
            }
            if(!StringUtils.isEmpty(param.get("high"))){
                hwStorage.setHigh(Long.parseLong(param.get("high")));
            }
            if(!StringUtils.isEmpty(param.get("depth"))){
                hwStorage.setDepth(Long.parseLong(param.get("depth")));
            }
            if(!StringUtils.isEmpty(param.get("width"))){
                hwStorage.setWidth(Long.parseLong(param.get("width")));
            }
            if(!StringUtils.isEmpty(param.get("main_usage"))){
                hwStorage.setMain_usage(param.get("main_usage"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_type"))){
                hwStorage.setCpu_type(param.get("cpu_type"));
            }

            if(!StringUtils.isEmpty(param.get("disk_interface_type"))){
                hwStorage.setDisk_interface_type(param.get("disk_interface_type"));
            }
            if(!StringUtils.isEmpty(param.get("disk_size"))){
                hwStorage.setDisk_size(Long.parseLong(param.get("disk_size")));
            }
            if(!StringUtils.isEmpty(param.get("disk_current_num"))){
                hwStorage.setDisk_current_num(Long.parseLong(param.get("disk_current_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_max_num"))){
                hwStorage.setDisk_max_num(Long.parseLong(param.get("disk_max_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_raid"))){
                hwStorage.setDisk_raid(param.get("disk_raid"));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                hwStorage.setDescription(param.get("description"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_type"))){
                hwStorage.setCpu_type(param.get("cpu_type"));
            }
            hwStorageDao.update(hwStorage);
            id=hwStorage.getId();
        }
        return this.selectStorageDetail(id);
    }

    public List<Map<String,Object>> getStorageCodeTableList(Map<String, String> param) {
        StringBuilder sb = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sb.append("SELECT  ID CODE,NAME VALUE  FROM HW_STORAGE WHERE 1=1 ");
        if(!StringUtils.isEmpty(param.get("main_usage"))){
            sb.append(" and main_usage = ?");
            params.add(param.get("main_usage"));
        }
        SQLQuery query =  hwStorageDao.createSQLQuery(sb.toString());
        for(int i=0;i<params.size();i++){
            query.setParameter(i,params.get(i));
        }
        List list = query.list();
        return list;
    }
}
