package com.youi.business.project.service;

import com.youi.business.common.dao.PmProjectAttachmentDao;
import com.youi.business.common.dao.PmProjectDao;
import com.youi.business.common.entity.*;
import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import com.youi.business.common.util.DateKit;
import com.youi.business.common.util.IPv4Util;
import com.youi.business.hardware.vo.VCabinetDetail;
import com.youi.business.project.vo.VProjectDetail;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jinliang on 2016/12/22.
 */
@Service
public class ProjectService {
    @Autowired
    private PmProjectDao pmProjectDao;
    @Autowired
    private PmProjectAttachmentDao pmProjectAttachmentDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private StoreConnector storeConnector;
    /**
     *
     * @param param
     * @return
     * @throws Exception
     */
    public Page queryProjectList(Map<String,String>param) throws Exception {
            Page result = new Page();
        if(param!=null&&param.get("pageno")!=null){
            //分页
            Page page = new Page(Integer.parseInt(param.get("pageno")),Integer.parseInt(param.get("pagesize")),"apply_date",Page.DESC);
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            if(!StringUtils.isEmpty(param.get("year"))){
           /*     Calendar s =  Calendar.getInstance();
                s.set(Integer.parseInt(param.get("year")),1,1,0,0,0);*/
              /*  filters.add(new PropertyFilter("GED_apply_date",param.get("year")+"-01-01 00:00:00"));
                filters.add(new PropertyFilter("LED_apply_date",param.get("year")+"-12-31 23:59:59"));*/
                filters.add(new PropertyFilter("EQS_year",param.get("year")));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                filters.add(new PropertyFilter("LIKES_name",param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("apply_dept"))){
                filters.add(new PropertyFilter("EQL_apply_dept_id",param.get("apply_dept")));
            }
            if("1".equals(param.get("filter_applicant"))){
                filters.add(new PropertyFilter("EQL_applicant",param.get("userid")));
            }

            Page page1 = pmProjectDao.pagedQuery(page, filters);
            List<PM_PROJECT> list = (List<PM_PROJECT>) page1.getResult();
            if (list != null) {
                for (PM_PROJECT p : list) {
                    Map<String, Object> m = new HashMap<String, Object>();
                    BigDecimal total_investment = p.getTotal_investment();
                    BigDecimal allocate_investment = pmProjectDao.getJdbcTemplate().queryForObject("select ifnull(sum(budget_amount),0) as allocate_investment from pm_procurement  where pid =?",new Object[]{p.getId()},BigDecimal.class);
                    m.put("balance_investment", total_investment .subtract(allocate_investment));
                    m.put("id", p.getId());
                    m.put("name", p.getName());
                    m.put("year", p.getYear());
                    m.put("apply_dept_id", p.getApply_dept_id());
                   // System.out.println( "dept_name:"+CodeTableUtil.code2mean("SYSORG",p.getApply_dept_id()));
                    m.put("apply_dept_name",  CodeTableUtil.code2mean("SYSORG",p.getApply_dept_id()));
                    m.put("total_investment", total_investment);
                    m.put("apply_date", DateKit.date2StrSecond( p.getApply_date()));
                    m.put("expected_sdate",DateKit.date2StrSecond( p.getExpected_sdate()));
                    m.put("expected_edate",DateKit.date2StrSecond( p.getExpected_edate()));
                    m.put("status",p.getStatus());
                    results.add(m);
                }
            }
            result.setResult(results);
            result.setTotalCount(page1.getTotalCount());
        }else{
            //不分页
            StringBuilder hql = new StringBuilder();
            List<Object> ps = new ArrayList<Object>();
            hql.append(" from PM_PROJECT where 1=1") ;
            if(!StringUtils.isEmpty(param.get("year"))){
                hql.append(" and apply_date>=?");
                ps.add(param.get("year")+"-01-01 00:00:00");
                hql.append(" and apply_date<=?");
                ps.add(param.get("year")+"-12-31 23:59:59");
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                hql.append(" and name=?");
                ps.add(param.get("name"));
            }
            if(!StringUtils.isEmpty(param.get("apply_dept"))){
                hql.append(" and apply_dept_id=?");
                ps.add(param.get("apply_dept"));
            }
            if("1".equals(param.get("filter_applicant"))){
                hql.append(" and applicant=?");
                ps.add(Long.parseLong(param.get("userid")));
            }

            hql.append(" order by apply_date desc");
            List<PM_PROJECT> list =  pmProjectDao.createQuery(hql.toString(),ps.toArray()).list();
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            if (list != null) {
                for (PM_PROJECT p : list) {
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("id", p.getId());
                    m.put("name", p.getName());
                    m.put("year", p.getYear());
                    m.put("apply_dept_id", p.getApply_dept_id());
                    m.put("apply_dept_name",  CodeTableUtil.code2mean("SYSORG",p.getApply_dept_id()));
                    BigDecimal total_investment = p.getTotal_investment();
                    BigDecimal allocate_investment = pmProjectDao.getJdbcTemplate().queryForObject("select ifnull(sum(budget_amount),0) as allocate_investment from pm_procurement  where pid =?",new Object[]{p.getId()},BigDecimal.class);
                    m.put("balance_investment", total_investment .subtract(allocate_investment));
                    m.put("total_investment", p.getTotal_investment());
                    m.put("apply_date", DateKit.date2StrSecond( p.getApply_date()));
                    m.put("expected_sdate",DateKit.date2StrSecond( p.getExpected_sdate()));
                    m.put("expected_edate",DateKit.date2StrSecond( p.getExpected_edate()));
                    m.put("status",p.getStatus());
                    results.add(m);
                }
            }
            result.setResult(results);
            result.setTotalCount(results.size());
        }
        return result;
    }

    public Object selectProjectDetail(Long id) throws Exception {
        PM_PROJECT pmProject =  pmProjectDao.get(id);
        if(pmProject==null){return null;}
        VProjectDetail detail = new VProjectDetail();
        beanMapper.copy(pmProject,detail);
        //计算未分解投资额
        BigDecimal total_investment = pmProject.getTotal_investment();
        BigDecimal allocate_investment = pmProjectDao.getJdbcTemplate().queryForObject("select ifnull(sum(budget_amount),0) as allocate_investment from PM_PROCUREMENT  where pid =?",new Object[]{pmProject.getId()},BigDecimal.class);
        detail.setBalance_investment(total_investment .subtract(allocate_investment));
        detail.setApplicant_id(pmProject.getApplicant());
        detail.setApplicant_name(CodeTableUtil.code2mean("SYSUSER",pmProject.getApplicant()));
        detail.setApply_dept_name(CodeTableUtil.code2mean("SYSORG",pmProject.getApply_dept_id()));
        List<PM_PROJECT_ATTACHMENT> paList =  pmProjectAttachmentDao.find("from PM_PROJECT_ATTACHMENT where pid=?",id);
        Map<String, Object> attachments = new HashMap<String, Object>();
        if(paList!=null){
            for(PM_PROJECT_ATTACHMENT pa:paList){
                StoreDTO storeDTO = storeConnector.getStore(pa.getAttach_id());
                Map<String, Object> attachment = new HashMap<String, Object>();
                attachment.put("file_name", storeDTO.getShow_name());
                attachment.put("file_type", storeDTO.getFile_type());
                attachment.put("url", storeDTO.getUrl());
                attachment.put("file_id", storeDTO.getId());
                attachment.put("cdate", storeDTO.getCreate_date());
                attachment.put("creator_id", storeDTO.getCreate_user());
                attachment.put("creator_name",CodeTableUtil.code2mean("SYSUSER",storeDTO.getCreate_user()));
                attachment.put("attach_code",pa.getAttach_code());
                if(!StringUtils.isEmpty(pa.getAttach_code())){
                    Map<String,Object> attach = (Map<String,Object>)attachments.get("attach_code");
                    if(attach==null){
                        attach = new  HashMap<String,Object>();
                        attachments.put("attach_code",attach);
                    }
                    attach.put(pa.getAttach_code(),attachment);
                }else{
                    List<Map<String,Object>> others = (List<Map<String,Object>>)attachments.get("others");
                    if(others==null){
                        others = new ArrayList<Map<String,Object>>();
                        attachments.put("others",others);
                    }
                    others.add(attachment);
                }
            }
        }
        detail.setAttachments(attachments);
        return  detail;
    }

    public Object saveProject(Map<String, Object> param)throws Exception {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            PM_PROJECT obj = new PM_PROJECT();
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("year"))){
                obj.setYear(StringUtils.null2String(param.get("year")));
            }
            if(!StringUtils.isEmpty(param.get("apply_dept_id"))){
                obj.setApply_dept_id(Long.parseLong(StringUtils.null2String(param.get("apply_dept_id"))));
            }
            if(!StringUtils.isEmpty(param.get("total_investment"))){
                obj.setTotal_investment(new BigDecimal(StringUtils.null2String(param.get("total_investment"))));
            }
            DateConverter d = new DateConverter();
            if(!StringUtils.isEmpty(param.get("expected_sdate"))){
                obj.setExpected_sdate(d.convert(StringUtils.null2String(param.get("expected_sdate"))));
            }
            if(!StringUtils.isEmpty(param.get("expected_edate"))){
                obj.setExpected_edate(d.convert(StringUtils.null2String(param.get("expected_edate"))));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                obj.setDescription(StringUtils.null2String(param.get("description")));
            }
            if(!StringUtils.isEmpty(param.get("is_submit"))){
                obj.setStatus(StringUtils.null2String(param.get("is_submit")));
            }else{
                obj.setStatus("0");
            }
            obj.setApply_date(new Date());
            obj.setApplicant(Long.parseLong(StringUtils.null2String(param.get("userid"))));
            pmProjectDao.save(obj);
            id=obj.getId();
            List<Map> attachments = ( List<Map>)param.get("attachments");
            if(attachments!=null){
                for(Map m:attachments){
                   String attach_code = StringUtils.null2String( m.get("attach_code"));
                    Long file_id = Long.parseLong(StringUtils.null2String( m.get("file_id")));
                    PM_PROJECT_ATTACHMENT o = new PM_PROJECT_ATTACHMENT();
                    o.setAttach_code(attach_code);
                    o.setAttach_id(file_id);
                    o.setPid(id);
                    pmProjectAttachmentDao.save(o);
                }
            }
        }else{
            //更新
            PM_PROJECT obj = pmProjectDao.get(Long.parseLong(StringUtils.null2String(param.get("id"))));
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("year"))){
                obj.setYear(StringUtils.null2String(param.get("year")));
            }
            if(!StringUtils.isEmpty(param.get("apply_dept_id"))){
                obj.setApply_dept_id(Long.parseLong(StringUtils.null2String(param.get("apply_dept_id"))));
            }
            if(!StringUtils.isEmpty(param.get("total_investment"))){
                obj.setTotal_investment(new BigDecimal(StringUtils.null2String(param.get("total_investment"))));
            }
            DateConverter d = new DateConverter();
            if(!StringUtils.isEmpty(param.get("expected_sdate"))){
                obj.setExpected_sdate(d.convert(StringUtils.null2String(param.get("expected_sdate"))));
            }
            if(!StringUtils.isEmpty(param.get("expected_edate"))){
                obj.setExpected_edate(d.convert(StringUtils.null2String(param.get("expected_edate"))));
            }
            if(!StringUtils.isEmpty(param.get("description"))){
                obj.setDescription(StringUtils.null2String(param.get("description")));
            }
            if(!StringUtils.isEmpty(param.get("is_submit"))){
                obj.setStatus(StringUtils.null2String(param.get("is_submit")));
            }else{
                obj.setStatus("0");
            }
            pmProjectDao.update(obj);
            id=obj.getId();
            //删除原来的attachments关联
            pmProjectAttachmentDao.batchUpdate("delete from PM_PROJECT_ATTACHMENT where pid=?",id);
            //添加
            List<Map> attachments = ( List<Map>)param.get("attachments");
            if(attachments!=null){
                for(Map m:attachments){
                    String attach_code = StringUtils.null2String( m.get("attach_code"));
                    Long file_id = Long.parseLong(StringUtils.null2String( m.get("file_id")));
                    PM_PROJECT_ATTACHMENT o = new PM_PROJECT_ATTACHMENT();
                    o.setAttach_code(attach_code);
                    o.setAttach_id(file_id);
                    o.setPid(id);
                    pmProjectAttachmentDao.save(o);
                }
            }
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("id",id);
        return result;
    }

    public void deleteProject(Map<String, Object> param) throws Exception {
        Long id = Long.parseLong(String.valueOf(param.get("id")));
        pmProjectDao.removeById(id);
        pmProjectAttachmentDao.batchUpdate("delete from PM_PROJECT_ATTACHMENT where pid=?",id);
    }
}
