package com.youi.business.hardware.service;

import com.youi.business.common.dao.HwCabinetDao;
import com.youi.business.common.dao.HwComputerRoomDao;
import com.youi.business.common.dao.HwVirtualMachineDao;
import com.youi.business.common.dao.HwVmPlatformDao;
import com.youi.business.common.entity.HW_CABINET;
import com.youi.business.common.entity.HW_COMPUTER_ROOM;
import com.youi.business.common.entity.HW_VIRTUAL_MACHINE;
import com.youi.business.common.entity.HW_VM_PLATFORM;
import com.youi.business.hardware.vo.VVirtualMachineDetail;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
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
public class HwVirtualMachineService {
    @Autowired
    private HwVirtualMachineDao hwVirtualMachineDao;
    @Autowired
    private HwVmPlatformDao hwVmPlatformDao;

    @Autowired
    private BeanMapper beanMapper;



    /**
     * 分页获得业务系统list
     * @param userid
     * @param page
     * @return
     * @throws Exception
     */
    public Page getVirtualMachineList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwVirtualMachineDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_VIRTUAL_MACHINE> list =   (List<HW_VIRTUAL_MACHINE>)page1.getResult();
        if(list!=null){
            for(HW_VIRTUAL_MACHINE p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("name",p.getName());
                m.put("core_num",p.getCore_num());
                m.put("memory",p.getMemory());
                m.put("disk",p.getDisk());
                m.put("system",p.getSystem());
                String  plat_name="";
                if(p.getPlatform_id()!=null){
                   HW_VM_PLATFORM platform = hwVmPlatformDao.get(p.getPlatform_id());
                    if(platform!=null){
                        plat_name = platform.getName();
                    }
                }
                m.put("plat_name",plat_name);
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
    public boolean deleteVirtualMachine(Long userid, Long id) {
            hwVirtualMachineDao.removeById(id);
          /*  if(hwVirtualMachineDao.get(id)!=null){
                return false;
            }*/
        return true;

    }


    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectVirtualMachineDetail(Long id)throws  Exception {
        HW_VIRTUAL_MACHINE hwVirtualMachine =  hwVirtualMachineDao.get(id);
        if(hwVirtualMachine==null){return null;}
        VVirtualMachineDetail detail = new VVirtualMachineDetail();
        beanMapper.copy(hwVirtualMachine,detail);
        String  plat_name="";
        if(hwVirtualMachine.getPlatform_id()!=null){
            HW_VM_PLATFORM platform = hwVmPlatformDao.get(hwVirtualMachine.getPlatform_id());
            if(platform!=null){
                plat_name = platform.getName();
            }
        }
        detail.setPlatform_name(plat_name);
        return  detail;
    }

    public Object saveVirtualMachine(Map<String, String> param)throws  Exception  {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            HW_VIRTUAL_MACHINE hwVirtualMachine = new HW_VIRTUAL_MACHINE();
            if(!StringUtils.isEmpty(param.get("platform_id"))){
                hwVirtualMachine.setPlatform_id(Long.parseLong(param.get("platform_id")));
            }
            if(!StringUtils.isEmpty(param.get("core_num"))){
                hwVirtualMachine.setCore_num(Long.parseLong(param.get("core_num")));
            }
            if(!StringUtils.isEmpty(param.get("memory"))){
                hwVirtualMachine.setMemory(Long.parseLong(param.get("memory")));
            }
            if(!StringUtils.isEmpty(param.get("disk"))){
                hwVirtualMachine.setDisk(Long.parseLong(param.get("disk")));
            }
            if(!StringUtils.isEmpty(param.get("system"))){
                hwVirtualMachine.setSystem(param.get("system"));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                hwVirtualMachine.setName(param.get("name"));
            }

            hwVirtualMachine.setCtime(new Date());
            hwVirtualMachine.setCuserid(Long.parseLong(param.get("userid")));
            hwVirtualMachineDao.save(hwVirtualMachine);
            id=hwVirtualMachine.getId();
        }else{
            //更新
            HW_VIRTUAL_MACHINE hwVirtualMachine = hwVirtualMachineDao.get(Long.parseLong(param.get("id")));
            if(!StringUtils.isEmpty(param.get("platform_id"))){
                hwVirtualMachine.setPlatform_id(Long.parseLong(param.get("platform_id")));
            }
            if(!StringUtils.isEmpty(param.get("core_num"))){
                hwVirtualMachine.setCore_num(Long.parseLong(param.get("core_num")));
            }
            if(!StringUtils.isEmpty(param.get("memory"))){
                hwVirtualMachine.setMemory(Long.parseLong(param.get("memory")));
            }
            if(!StringUtils.isEmpty(param.get("disk"))){
                hwVirtualMachine.setDisk(Long.parseLong(param.get("disk")));
            }
            if(!StringUtils.isEmpty(param.get("system"))){
                hwVirtualMachine.setSystem(param.get("system"));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                hwVirtualMachine.setName(param.get("name"));
            }
            hwVirtualMachineDao.update(hwVirtualMachine);
            id=hwVirtualMachine.getId();
        }
        return this.selectVirtualMachineDetail(id);
    }


}
