package com.youi.business.hardware.service;

import com.youi.business.common.dao.HwCabinetDao;
import com.youi.business.common.entity.HW_CABINET;
import com.youi.business.hardware.vo.VCabinetDetail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinliang on 2016/9/21.
 */
@Transactional
@Service
public class HwCabinetService {
    @Autowired
    private HwCabinetDao hwCabinetDao;

    @Autowired
    private BeanMapper beanMapper;



    public List<Map<String,Object>> getCabinetCodeTableList() {
        List list = hwCabinetDao.createSQLQuery("SELECT  ID CODE,NAME VALUE  FROM HW_CABINET").list();
        return list;
    }

    /**
     * 分页获得业务系统list
     * @param userid
     * @param page
     * @return
     * @throws Exception
     */
    public Page getCabinetList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwCabinetDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_CABINET> list =   (List<HW_CABINET>)page1.getResult();
        if(list!=null){
            for(HW_CABINET p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("cabinet_name",p.getName());
                m.put("brand",p.getBrand());
                m.put("type",p.getType());
                m.put("high",p.getHigh());
                m.put("width",p.getWidth());
                m.put("depth",p.getDepth());
                m.put("unit",p.getUnit());
                m.put("asset_num",p.getAsset_num());
                m.put("load_capacity",p.getLoad_capacity());
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
    public boolean deleteCabinet(Long userid, Long id) {
    /*   HW_CABINET hwCabinet =  hwCabinetDao.get(id);
        if(hwCabinet==null){return false;}
        hwCabinetDao.remove(hwCabinet);*/

            hwCabinetDao.removeById(id);
         /*   if(hwCabinetDao.get(id)!=null){
                return false;
            }*/

        return true;


    }


    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectCabinetDetail(Long id)throws  Exception {
        HW_CABINET hwCabinet =  hwCabinetDao.get(id);
        if(hwCabinet==null){return null;}
        VCabinetDetail detail = new VCabinetDetail();
        beanMapper.copy(hwCabinet,detail);
        detail.setCabinet_name(hwCabinet.getName());
        return  detail;
    }

    public Object saveCabinet(Map<String, String> param)throws  Exception  {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            HW_CABINET hwCabinet = new HW_CABINET();
            hwCabinet.setDescription(param.get("description"));
            hwCabinet.setName(param.get("cabinet_name"));
            if(!StringUtils.isEmpty(param.get("brand"))){
                hwCabinet.setBrand(StringUtils.null2String(param.get("brand")));
            }
            if(!StringUtils.isEmpty(param.get("room_id"))){
                hwCabinet.setRoom_id(Long.parseLong(param.get("room_id")));
            }
            if(!StringUtils.isEmpty(param.get("type"))){
                hwCabinet.setType(param.get("type"));
            }
            if(!StringUtils.isEmpty(param.get("high"))){
                hwCabinet.setHigh(Long.parseLong(param.get("high")));
            }
            if(!StringUtils.isEmpty(param.get("depth"))){
                hwCabinet.setDepth(Long.parseLong(param.get("depth")));
            }
            if(!StringUtils.isEmpty(param.get("width"))){
                hwCabinet.setWidth(Long.parseLong(param.get("width")));
            }
            if(!StringUtils.isEmpty(param.get("unit"))){
                hwCabinet.setUnit(Long.parseLong(param.get("unit")));
            }
            if(!StringUtils.isEmpty(param.get("materials"))){
                hwCabinet.setMaterials(param.get("materials"));
            }
            if(!StringUtils.isEmpty(param.get("def_level"))){
                hwCabinet.setDef_level(param.get("def_level"));
            }
            if(!StringUtils.isEmpty(param.get("load_capacity"))){
                hwCabinet.setLoad_capacity(Long.parseLong(param.get("load_capacity")));
            }
            if(!StringUtils.isEmpty(param.get("asset_num"))){
                hwCabinet.setAsset_num(param.get("asset_num"));
            }
            hwCabinet.setCdate(new Date());
            hwCabinet.setUserid(Long.parseLong(param.get("userid")));
            hwCabinetDao.save(hwCabinet);
            id=hwCabinet.getId();
        }else{
            //更新
            HW_CABINET hwCabinet = hwCabinetDao.get(Long.parseLong(param.get("id")));
            hwCabinet.setDescription(param.get("description"));
            hwCabinet.setName(param.get("cabinet_name"));
            if(!StringUtils.isEmpty(param.get("brand"))){
                hwCabinet.setBrand(StringUtils.null2String(param.get("brand")));
            }
            if(!StringUtils.isEmpty(param.get("room_id"))){
                hwCabinet.setRoom_id(Long.parseLong(param.get("room_id")));
            }
            if(!StringUtils.isEmpty(param.get("type"))){
                hwCabinet.setType(param.get("type"));
            }
            if(!StringUtils.isEmpty(param.get("high"))){
                hwCabinet.setHigh(Long.parseLong(param.get("high")));
            }
            if(!StringUtils.isEmpty(param.get("depth"))){
                hwCabinet.setDepth(Long.parseLong(param.get("depth")));
            }
            if(!StringUtils.isEmpty(param.get("width"))){
                hwCabinet.setWidth(Long.parseLong(param.get("width")));
            }
            if(!StringUtils.isEmpty(param.get("unit"))){
                hwCabinet.setUnit(Long.parseLong(param.get("unit")));
            }
            if(!StringUtils.isEmpty(param.get("materials"))){
                hwCabinet.setMaterials(param.get("materials"));
            }
            if(!StringUtils.isEmpty(param.get("def_level"))){
                hwCabinet.setDef_level(param.get("def_level"));
            }
            if(!StringUtils.isEmpty(param.get("load_capacity"))){
                hwCabinet.setLoad_capacity(Long.parseLong(param.get("load_capacity")));
            }
            if(!StringUtils.isEmpty(param.get("asset_num"))){
                hwCabinet.setAsset_num(param.get("asset_num"));
            }
            hwCabinetDao.update(hwCabinet);
            id=hwCabinet.getId();
        }
        return this.selectCabinetDetail(id);
    }


}
