package com.youi.business.sysresource.service;

import com.youi.business.common.dao.HwComputerRoomDao;
import com.youi.business.common.entity.HW_COMPUTER_ROOM;
import com.youi.business.sysresource.vo.VCmputerRoomDetail;
import com.youi.business.sysresource.vo.VVendorDetailInfo;
import com.youi.core.codetable.CodeTableUtil;
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
public class HwComputerRoomService {
    @Autowired
    private HwComputerRoomDao hwComputerRoomDao;

    @Autowired
    private BeanMapper beanMapper;

    public List<Map<String,Object>> getComputerRoomCodeTableList() {
        List list = hwComputerRoomDao.createSQLQuery("SELECT  ID CODE,NAME VALUE  FROM HW_COMPUTER_ROOM WHERE STATUS='1'").list();
        return list;
    }

    /**
     * 分页获得业务系统list
     * @param userid
     * @param page
     * @return
     * @throws Exception
     */
    public Page getComputerRoomList(Long userid, Page page) throws Exception{
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        Page page1 = hwComputerRoomDao.pagedQuery(page,null);
        long totalCount = page1.getTotalCount();
        List<HW_COMPUTER_ROOM> list =   (List<HW_COMPUTER_ROOM>)page1.getResult();
        if(list!=null){
            for(HW_COMPUTER_ROOM p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("name",p.getName());
                m.put("description",p.getDescription());
                m.put("area",p.getArea());//开发单位
                m.put("complete_date",p.getComplete_date());//开发单位
                m.put("status",p.getStatus());//开发单位
                results.add(m);
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }


    /**
     * 禁用
     * @param userid
     * @param id
     * @return
     */
    public boolean disableComputerRoom(Long userid, Long id) {
       HW_COMPUTER_ROOM hwComputerRoom =  hwComputerRoomDao.get(id);
        if(hwComputerRoom==null){return false;}
        hwComputerRoom.setStatus("0");
        hwComputerRoomDao.update(hwComputerRoom);
        return true;

    }

    /**
     * 启用
     * @param userid
     * @param id
     * @return
     */
    public boolean enableComputerRoom(Long userid, Long id) {
        HW_COMPUTER_ROOM hwComputerRoom =  hwComputerRoomDao.get(id);
        if(hwComputerRoom==null){return false;}
        hwComputerRoom.setStatus("1");
        hwComputerRoomDao.update(hwComputerRoom);
        return true;
    }

    /**
     * 查询详细
     * @param id
     * @return
     */
    public Object selectComputerRoomDetail(Long id)throws  Exception {
        HW_COMPUTER_ROOM hwComputerRoom =  hwComputerRoomDao.get(id);
        if(hwComputerRoom==null){return null;}
        VCmputerRoomDetail detail = new VCmputerRoomDetail();
        beanMapper.copy(hwComputerRoom,detail);
        return  detail;
    }

    public Object saveComputerRoom(Map<String, String> param)throws  Exception  {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            HW_COMPUTER_ROOM hwComputerRoom = new HW_COMPUTER_ROOM();
            hwComputerRoom.setStatus("1");
            hwComputerRoom.setDescription(param.get("description"));
            hwComputerRoom.setArea(param.get("area"));
            hwComputerRoom.setName(param.get("name"));
            if(!StringUtils.isEmpty(param.get("complete_date"))){
                DateConverter d = new DateConverter();
                hwComputerRoom.setComplete_date(d.convert( param.get("complete_date")));
            }
            hwComputerRoom.setCdate(new Date());
            hwComputerRoom.setUserid(Long.parseLong(param.get("userid")));
            hwComputerRoomDao.save(hwComputerRoom);
            id=hwComputerRoom.getId();
        }else{
            //更新
            HW_COMPUTER_ROOM hwComputerRoom = hwComputerRoomDao.get(Long.parseLong(param.get("id")));
            hwComputerRoom.setStatus("1");
            hwComputerRoom.setDescription(param.get("description"));
            hwComputerRoom.setArea(param.get("area"));
            hwComputerRoom.setName(param.get("name"));
            if(!StringUtils.isEmpty(param.get("complete_date"))){
                DateConverter d = new DateConverter();
                hwComputerRoom.setComplete_date(d.convert( param.get("complete_date")));
            }
            hwComputerRoomDao.update(hwComputerRoom);
            id=hwComputerRoom.getId();
        }
        return this.selectComputerRoomDetail(id);
    }



}
