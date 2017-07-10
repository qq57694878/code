package com.youi.business.hardware.service;

import com.youi.business.common.dao.HwCabinetDao;
import com.youi.business.common.dao.HwComputerRoomDao;
import com.youi.business.common.dao.HwX86Dao;
import com.youi.business.common.entity.HW_CABINET;
import com.youi.business.common.entity.HW_COMPUTER_ROOM;
import com.youi.business.common.entity.HW_X86;
import com.youi.business.common.util.DevelopKit;
import com.youi.business.hardware.vo.VX86Detail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.query.PropertyFilterUtils;
import com.youi.core.util.StringUtils;
import org.hibernate.Query;
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
public class HwX86Service {
    @Autowired
    private HwX86Dao hwX86Dao;
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
    public Page getX86List(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwX86Dao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_X86> list =   (List<HW_X86>)page1.getResult();
        if(list!=null){
            for(HW_X86 p:list){
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
                m.put("name",p.getName());
                m.put("type",p.getType());
                m.put("high",p.getHigh());
                m.put("width",p.getWidth());
                m.put("depth",p.getDepth());
                m.put("main_usage",p.getMain_usage());
                m.put("cpu_type",p.getCpu_type());
                m.put("cpu_frequency",p.getCpu_type());
                m.put("core_num",p.getCore_num());
                m.put("cpu_current_num",p.getCpu_current_num());
                m.put("memory_current_capacity",p.getMemory_current_capacity());
                m.put("asset_num",p.getAsset_num());
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
    public boolean deleteX86(Long userid, Long id) {
            hwX86Dao.removeById(id);
           /* HW_X86 x86 = hwX86Dao.get(id);
            if(x86!=null){
                return false;
            }*/
        return true;

    }


    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectX86Detail(Long id)throws  Exception {
        HW_X86 hwX86 =  hwX86Dao.get(id);
        if(hwX86==null){return null;}
        VX86Detail detail = new VX86Detail();
        beanMapper.copy(hwX86,detail);
        return  detail;
    }

    public Object saveX86(Map<String, String> param)throws  Exception  {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            HW_X86 hwX86 = new HW_X86();
            if(!StringUtils.isEmpty(param.get("brand"))){
                hwX86.setBrand(StringUtils.null2String(param.get("brand")));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                hwX86.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("room_id"))){
                hwX86.setRoom_id(Long.parseLong(param.get("room_id")));
            }
            if(!StringUtils.isEmpty(param.get("cabinet_id"))){
                hwX86.setCabinet_id(Long.parseLong(param.get("cabinet_id")));
            }
            if(!StringUtils.isEmpty(param.get("type"))){
                hwX86.setType(param.get("type"));
            }
            if(!StringUtils.isEmpty(param.get("high"))){
                hwX86.setHigh(Long.parseLong(param.get("high")));
            }
            if(!StringUtils.isEmpty(param.get("depth"))){
                hwX86.setDepth(Long.parseLong(param.get("depth")));
            }
            if(!StringUtils.isEmpty(param.get("width"))){
                hwX86.setWidth(Long.parseLong(param.get("width")));
            }
            if(!StringUtils.isEmpty(param.get("main_usage"))){
                hwX86.setMain_usage(param.get("main_usage"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_type"))){
                hwX86.setCpu_type(param.get("cpu_type"));
            }
            if(!StringUtils.isEmpty(param.get("core_num"))){
                hwX86.setCore_num(Long.parseLong(param.get("core_num")));
            }
            if(!StringUtils.isEmpty(param.get("cpu_frequency"))){
                hwX86.setCpu_frequency(param.get("cpu_frequency"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_current_num"))){
                hwX86.setCpu_current_num(Long.parseLong(param.get("cpu_current_num")));
            }
            if(!StringUtils.isEmpty(param.get("cpu_max_num"))){
                hwX86.setCpu_max_num(Long.parseLong(param.get("cpu_max_num")));
            }
            if(!StringUtils.isEmpty(param.get("memory_type"))){
                hwX86.setMemory_type(param.get("memory_type"));
            }
            if(!StringUtils.isEmpty(param.get("memory_current_capacity"))){
                hwX86.setMemory_current_capacity(Long.parseLong(param.get("memory_current_capacity")));
            }
            if(!StringUtils.isEmpty(param.get("memory_max_capacity"))){
                hwX86.setMemory_max_capacity(Long.parseLong(param.get("memory_max_capacity")));
            }
            if(!StringUtils.isEmpty(param.get("mainboard_type"))){
                hwX86.setMainboard_type(param.get("mainboard_type"));
            }
            if(!StringUtils.isEmpty(param.get("disk_interface_type"))){
                hwX86.setDisk_interface_type(param.get("disk_interface_type"));
            }
            if(!StringUtils.isEmpty(param.get("disk_size"))){
                hwX86.setDisk_size(Long.parseLong(param.get("disk_size")));
            }
            if(!StringUtils.isEmpty(param.get("disk_current_num"))){
                hwX86.setDisk_current_num(Long.parseLong(param.get("disk_current_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_max_num"))){
                hwX86.setDisk_max_num(Long.parseLong(param.get("disk_max_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_raid"))){
                hwX86.setDisk_raid(param.get("disk_raid"));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                hwX86.setDescription(param.get("description"));
            }
            if(!StringUtils.isEmpty(param.get("asset_num"))){
                hwX86.setAsset_num(param.get("asset_num"));
            }
            hwX86.setCdate(new Date());
            hwX86.setUserid(Long.parseLong(param.get("userid")));
            hwX86Dao.save(hwX86);
            id=hwX86.getId();
        }else{
            //更新
            HW_X86 hwX86 = hwX86Dao.get(Long.parseLong(param.get("id")));

            if(!StringUtils.isEmpty(param.get("brand"))){
                hwX86.setBrand(StringUtils.null2String(param.get("brand")));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                hwX86.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("room_id"))){
                hwX86.setRoom_id(Long.parseLong(param.get("room_id")));
            }
            if(!StringUtils.isEmpty(param.get("cabinet_id"))){
                hwX86.setCabinet_id(Long.parseLong(param.get("cabinet_id")));
            }
            if(!StringUtils.isEmpty(param.get("type"))){
                hwX86.setType(param.get("type"));
            }
            if(!StringUtils.isEmpty(param.get("high"))){
                hwX86.setHigh(Long.parseLong(param.get("high")));
            }
            if(!StringUtils.isEmpty(param.get("depth"))){
                hwX86.setDepth(Long.parseLong(param.get("depth")));
            }
            if(!StringUtils.isEmpty(param.get("width"))){
                hwX86.setWidth(Long.parseLong(param.get("width")));
            }
            if(!StringUtils.isEmpty(param.get("main_usage"))){
                hwX86.setMain_usage(param.get("main_usage"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_type"))){
                hwX86.setCpu_type(param.get("cpu_type"));
            }
            if(!StringUtils.isEmpty(param.get("core_num"))){
                hwX86.setCore_num(Long.parseLong(param.get("core_num")));
            }
            if(!StringUtils.isEmpty(param.get("cpu_frequency"))){
                hwX86.setCpu_frequency(param.get("cpu_frequency"));
            }
            if(!StringUtils.isEmpty(param.get("cpu_current_num"))){
                hwX86.setCpu_current_num(Long.parseLong(param.get("cpu_current_num")));
            }
            if(!StringUtils.isEmpty(param.get("cpu_max_num"))){
                hwX86.setCpu_max_num(Long.parseLong(param.get("cpu_max_num")));
            }
            if(!StringUtils.isEmpty(param.get("memory_type"))){
                hwX86.setMemory_type(param.get("memory_type"));
            }
            if(!StringUtils.isEmpty(param.get("memory_current_capacity"))){
                hwX86.setMemory_current_capacity(Long.parseLong(param.get("memory_current_capacity")));
            }
            if(!StringUtils.isEmpty(param.get("memory_max_capacity"))){
                hwX86.setMemory_max_capacity(Long.parseLong(param.get("memory_max_capacity")));
            }
            if(!StringUtils.isEmpty(param.get("mainboard_type"))){
                hwX86.setMainboard_type(param.get("mainboard_type"));
            }
            if(!StringUtils.isEmpty(param.get("disk_interface_type"))){
                hwX86.setDisk_interface_type(param.get("disk_interface_type"));
            }
            if(!StringUtils.isEmpty(param.get("disk_size"))){
                hwX86.setDisk_size(Long.parseLong(param.get("disk_size")));
            }
            if(!StringUtils.isEmpty(param.get("disk_current_num"))){
                hwX86.setDisk_current_num(Long.parseLong(param.get("disk_current_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_max_num"))){
                hwX86.setDisk_max_num(Long.parseLong(param.get("disk_max_num")));
            }
            if(!StringUtils.isEmpty(param.get("disk_raid"))){
                hwX86.setDisk_raid(param.get("disk_raid"));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                hwX86.setDescription(param.get("description"));
            }
            if(!StringUtils.isEmpty(param.get("asset_num"))){
                hwX86.setAsset_num(param.get("asset_num"));
            }
            hwX86Dao.update(hwX86);
            id=hwX86.getId();
        }
        return this.selectX86Detail(id);
    }


    public List<Map<String,Object>> getX86CodeTableList(Map<String, String> param) {
        StringBuilder sb = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sb.append("SELECT  ID CODE,NAME VALUE  FROM HW_X86 WHERE 1=1 ");
        if(!StringUtils.isEmpty(param.get("main_usage"))){
            sb.append(" and main_usage like ?");
            params.add("%"+param.get("main_usage")+"%");
        }
        SQLQuery query =  hwX86Dao.createSQLQuery(sb.toString());
       for(int i=0;i<params.size();i++){
           query.setParameter(i,params.get(i));
       }
        List list = query.list();
        return list;
    }
    public List<Map<String,Object>> getX86CodeTableListM2(Map<String, String> parameterMap) {
        List<PropertyFilter> propertyFilters = PropertyFilter
                .buildFromMap(DevelopKit.transation2ObjectMap(parameterMap));
        String sql = "SELECT  ID code,NAME value  FROM HW_X86 WHERE 1=1 ";
        StringBuilder sqlWhere = new StringBuilder();
        List<Object> paramList = new ArrayList<Object>();
        boolean checkWhere = false;//sql.toLowerCase().indexOf("where") == -1;
        PropertyFilterUtils.buildConfigurations(propertyFilters, sqlWhere,
                paramList, checkWhere);
        String selectSql = sql+sqlWhere;
        List<Map<String, Object>> list =  hwX86Dao.getJdbcTemplate().queryForList(selectSql,
                paramList.toArray());
        return list;
    }
}
