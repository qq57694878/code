package com.youi.business.tools.service;

import com.youi.business.common.dao.ToolsNoticeDao;
import com.youi.business.common.entity.HW_COMPUTER_ROOM;
import com.youi.business.common.entity.TOOLS_NOTICE;
import com.youi.business.sysresource.vo.VCmputerRoomDetail;
import com.youi.business.tools.vo.VNoticeDetail;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by jinliang on 2016/9/28.
 */
@Transactional
@Service
public class NoticeService {
    @Autowired
    private ToolsNoticeDao toolsNoticeDao;
    @Autowired
    private BeanMapper beanMapper;
    /**
     * 获得未读notice列表
     * @param userid
     * @return
     */
    public List getUnreadNoticeList(Long userid) {
        String sql ="SELECT  T1.ID ,T1.notice_type,T1.notice_info,T1.CTIME,T2.USERNAME CUSER_NAME  FROM TOOLS_NOTICE T1,sys_user T2" +
                " WHERE T1.cuserid=T2.USER_ID AND T1.ruserid=? AND T1.HAS_READ='0'";
        List list = toolsNoticeDao.createSQLQuery(sql,userid).list();
        return list;
    }

    public boolean sendNotice(Map<String, Object> param) {
        String cuserid = String.valueOf(param.get("cuserid"));
        List ruserid_list = (List)param.get("ruserid_list");
        String notice_info = String.valueOf(param.get("notice_info"));
        String notice_type = String.valueOf(param.get("notice_type"));
        if(StringUtils.isEmpty(notice_type)){
            notice_type="1";
        }
        if(ruserid_list!=null){
            for(int i=0;i<ruserid_list.size();i++){
               String ruserid =  String.valueOf(ruserid_list.get(i));
                TOOLS_NOTICE notice = new TOOLS_NOTICE();
                notice.setCuserid(Long.parseLong(cuserid));
                notice.setCtime(new Date());
                notice.setHas_read("0");
                notice.setNotice_info(notice_info);
                notice.setNotice_type(notice_type);
                notice.setRuserid(Long.parseLong(ruserid));
                toolsNoticeDao.save(notice);
            }
        }

        return true;

    }

    /**
     * 查询已发送通知
     * @param userid
     * @param page
     * @return
     */
    public Page querySendNotice(Long userid, Page page) {
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        List<PropertyFilter>  propertyFilter = new ArrayList<PropertyFilter>();
        propertyFilter.add(new PropertyFilter("EQL_cuserid",String.valueOf(userid)));
        Page page1 = toolsNoticeDao.pagedQuery(page,propertyFilter);
        long totalCount = page1.getTotalCount();
        List<TOOLS_NOTICE> list =   (List<TOOLS_NOTICE>)page1.getResult();
        if(list!=null){
            for(TOOLS_NOTICE p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("notice_type",p.getNotice_type());
                m.put("notice_info",p.getNotice_info());
                m.put("ctime",p.getCtime());
                String cuser_name ="";
                if(p.getCuserid()!=null){
                    cuser_name = CodeTableUtil.code2mean("SYSUSER",p.getCuserid());
                }
                m.put("cuser_name",cuser_name);
                String ruser_name ="";
                if(p.getRuserid()!=null){
                    ruser_name = CodeTableUtil.code2mean("SYSUSER",p.getRuserid());
                }
                m.put("ruser_name",ruser_name);
                m.put("has_read",p.getHas_read());
                m.put("read_time",p.getRead_time());
                results.add(m);
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }

    /**
     * 查询接收的通知
     * @param userid
     * @param page
     * @return
     */
    public Page queryReceiveNotice(Long userid, Page page) {
        int pageno = page.getPageNo();
        int pagesize = page.getPageSize();
        List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
        List<PropertyFilter>  propertyFilter = new ArrayList<PropertyFilter>();
        propertyFilter.add(new PropertyFilter("EQL_ruserid",String.valueOf(userid)));
        Page page1 = toolsNoticeDao.pagedQuery(page,propertyFilter);
        long totalCount = page1.getTotalCount();
        List<TOOLS_NOTICE> list =   (List<TOOLS_NOTICE>)page1.getResult();
        if(list!=null){
            for(TOOLS_NOTICE p:list){
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("id",p.getId());
                m.put("notice_type",p.getNotice_type());
                m.put("notice_info",p.getNotice_info());
                m.put("ctime",p.getCtime());
                String cuser_name ="";
                if(p.getCuserid()!=null){
                    cuser_name = CodeTableUtil.code2mean("SYSUSER",p.getCuserid());
                }
                m.put("cuser_name",cuser_name);
                String ruser_name ="";
                if(p.getRuserid()!=null){
                    ruser_name = CodeTableUtil.code2mean("SYSUSER",p.getRuserid());
                }
                m.put("ruser_name",ruser_name);
                m.put("has_read",p.getHas_read());
                m.put("read_time",p.getRead_time());
                results.add(m);
            }
        }
        page = new Page(results, totalCount);
        page.setPageNo(pageno);
        page.setPageSize(pagesize);
        return page;
    }

    public Object selectNoticeDetail(Long id) {
        TOOLS_NOTICE toolsNotice =  toolsNoticeDao.get(id);
        if(toolsNotice==null){return null;}
        VNoticeDetail detail = new VNoticeDetail();
        beanMapper.copy(toolsNotice,detail);
        String cuser_name="";
        if(toolsNotice.getCuserid()!=null){
            cuser_name = CodeTableUtil.code2mean("SYSUSER",toolsNotice.getCuserid());
        }
        detail.setCuser_name(cuser_name);
        String ruser_name="";
        if(toolsNotice.getRuserid()!=null){
            ruser_name = CodeTableUtil.code2mean("SYSUSER",toolsNotice.getRuserid());
        }
        detail.setRuser_name(ruser_name);
        return  detail;
    }

    public boolean readNotice(Long id) {
        TOOLS_NOTICE toolsNotice =  toolsNoticeDao.get(id);
        if(toolsNotice==null){return false;}
        toolsNotice.setHas_read("1");
        toolsNotice.setRead_time(new Date());
        toolsNoticeDao.update(toolsNotice);
        return true;
    }

    public boolean deleteNotice(Map<String, String> param) {
      Long id = Long.parseLong(param.get("id"));
      String userid =  param.get("userid");
        TOOLS_NOTICE toolsNotice =  toolsNoticeDao.get(id);
        if(toolsNotice==null){return false;}
       if(userid.equals(String.valueOf(toolsNotice.getCuserid()))&&"0".equals(toolsNotice.getHas_read())) {
           toolsNoticeDao.remove(toolsNotice);
           return true;
       }
        return false;
    }
}
