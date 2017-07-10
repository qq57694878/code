package com.youi.business.project.service;

import com.youi.business.common.dao.PmProcurementAttachmentDao;
import com.youi.business.common.dao.PmProcurementDao;
import com.youi.business.common.dao.VPmProcurementDao;
import com.youi.business.common.entity.PM_PROCUREMENT;
import com.youi.business.common.entity.PM_PROCUREMENT_ATTACHMENT;
import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 项目采购
 */
@Service
public class ProcurementService {
    @Autowired
    private PmProcurementDao pmProcurementDao;
    @Autowired
    private VPmProcurementDao vPmProcurementDao;
    @Autowired
    private PmProcurementAttachmentDao pmProcurementAttachmentDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private StoreConnector storeConnector;

    public Page queryProcurementList(Map<String, String> param) throws Exception{
            Page result = new Page();
            //不分页
            StringBuilder sql = new StringBuilder();
            List<Object> ps = new ArrayList<Object>();
            sql.append(" select v1.*,t1.apply_dept_id from V_PM_PROCUREMENT v1,PM_PROJECT t1 where v1.pid=t1.id ") ;

            if(!StringUtils.isEmpty(param.get("project_id"))){
                sql.append(" and v1.pid=?");
                ps.add(Long.parseLong(StringUtils.null2String(param.get("project_id"))));
            }
            sql.append(" order by v1.apply_date desc");
            List<Map<String,Object>>  list =  pmProcurementDao.createSQLQuery(sql.toString(),ps.toArray()).list();
            List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
            if (list != null) {
                System.out.println(list);
                for (Map<String,Object> p : list) {
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("id", p.get("id"));
                    m.put("pid", p.get("pid"));
                    m.put("name", p.get("name"));
                    m.put("budget_amount", p.get("budget_amount"));
                    m.put("apply_dept_name",  CodeTableUtil.code2mean("SYSORG",p.get("apply_dept_id")));
                    m.put("responsible_person_id",p.get("responsible_person"));
                    m.put("responsible_person", CodeTableUtil.code2mean("SYSUSER",p.get("responsible_person")));
                    m.put("procurement_method", p.get("procurement_method"));
                    m.put("status",p.get("status"));
                    results.add(m);
                }
            }
            result.setResult(results);
            result.setTotalCount(results.size());
        return result;
    }



    public Object selectProcurementDetail(Long id) throws Exception {

        StringBuilder sql = new StringBuilder();
        sql.append(" select v1.*,t1.apply_dept_id from V_PM_PROCUREMENT v1,PM_PROJECT t1 where v1.pid=t1.id  and v1.id = ?") ;
        List<Map<String,Object>>  list =  pmProcurementDao.createSQLQuery(sql.toString(),new Object[]{id}).list();
        if(list==null||list.size()==0){return null;}
        Map<String,Object> m = list.get(0);
        BigInteger bid_notification = (BigInteger)m.remove("bid_notification");
        Map<String,Object> detail = new HashMap<String,Object>();
        detail=m;
        detail.put("apply_dept_id",m.get("apply_dept_id"));
        detail.put("apply_dept_name",CodeTableUtil.code2mean("SYSORG",m.get("apply_dept_id")));
        detail.put("responsible_person_id",m.get("responsible_person"));
        detail.put("responsible_person",CodeTableUtil.code2mean("SYSUSER",m.get("responsible_person")));
        if(bid_notification!=null){
            StoreDTO storeDTO = storeConnector.getStore(bid_notification.longValue());
            Map<String, Object> attachment = new HashMap<String, Object>();
            attachment.put("file_name", storeDTO.getShow_name());
            attachment.put("file_type", storeDTO.getFile_type());
            attachment.put("url", storeDTO.getUrl());
            attachment.put("file_id", storeDTO.getId());
            attachment.put("cdate", storeDTO.getCreate_date());
            attachment.put("creator_id", storeDTO.getCreate_user());
            attachment.put("creator_name",CodeTableUtil.code2mean("SYSUSER",storeDTO.getCreate_user()));
            detail.put("bid_notification",attachment);
        }
        List<PM_PROCUREMENT_ATTACHMENT> paList =  pmProcurementAttachmentDao.find("from PM_PROCUREMENT_ATTACHMENT where pid=?",id);
        Map<String, Object> attachments = new HashMap<String, Object>();
        if(paList!=null){
            for(PM_PROCUREMENT_ATTACHMENT pa:paList){
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
        detail.put("attachments",attachments);
        return  detail;
    }

    public Object saveProcurement(Map<String, Object> param)throws Exception {
        Long id=null;
        if(StringUtils.isEmpty(param.get("id"))){
            //新增
            PM_PROCUREMENT obj = new PM_PROCUREMENT();
            if(!StringUtils.isEmpty(param.get("pid"))){
                obj.setPid(Long.parseLong(StringUtils.null2String(param.get("pid"))));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("budget_amount"))){
                obj.setBudget_amount(StringUtils.null2String(param.get("budget_amount")));
            }
            if(!StringUtils.isEmpty(param.get("bidding_company"))){
                obj.setBidding_company(StringUtils.null2String(param.get("bidding_company")));
            }
            if(!StringUtils.isEmpty(param.get("responsible_person_id"))){
                obj.setResponsible_person(Long.parseLong(StringUtils.null2String(param.get("responsible_person_id"))));
            }
            if(!StringUtils.isEmpty(param.get("procurement_method"))){
                obj.setProcurement_method(StringUtils.null2String(param.get("procurement_method")));
            }
            if(!StringUtils.isEmpty(param.get("bidding_hang_date"))){
                DateConverter d = new DateConverter();
                obj.setBidding_hang_date(d.convert(StringUtils.null2String(param.get("bidding_hang_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bidding_open_date"))){
                DateConverter d = new DateConverter();
                obj.setBidding_open_date(d.convert(StringUtils.null2String(param.get("bidding_open_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bid_amount"))){
                obj.setBid_amount(new BigDecimal(StringUtils.null2String(param.get("bid_amount"))));
            }
            if(!StringUtils.isEmpty(param.get("signup_end_date"))){
                DateConverter d = new DateConverter();
                obj.setSignup_end_date(d.convert(StringUtils.null2String(param.get("signup_end_date"))));
            }
            if(!StringUtils.isEmpty(param.get("contract_record_date"))){
                DateConverter d = new DateConverter();
                obj.setContract_record_date(d.convert(StringUtils.null2String(param.get("contract_record_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bidding_win_date"))){
                DateConverter d = new DateConverter();
                obj.setBidding_win_date(d.convert(StringUtils.null2String(param.get("bidding_win_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bidding_win_company"))){
                obj.setBidding_win_company(StringUtils.null2String(param.get("bidding_win_company")));
            }
            if(!StringUtils.isEmpty(param.get("bid_notification"))){
                obj.setBid_notification(Long.parseLong(StringUtils.null2String(param.get("bid_notification"))));
            }
           // obj.setStatus("0");//0 未招标 1 招标中 2 已招标 3 签订合同',
            obj.setApply_date(new Date());
            obj.setApplicant(Long.parseLong(StringUtils.null2String(param.get("userid"))));
            pmProcurementDao.save(obj);
            id=obj.getId();
            List<Map> attachments = ( List<Map>)param.get("attachments");
            if(attachments!=null){
                for(Map m:attachments){
                    String attach_code = StringUtils.null2String( m.get("attach_code"));
                    Long file_id = Long.parseLong(StringUtils.null2String( m.get("file_id")));
                    PM_PROCUREMENT_ATTACHMENT o = new PM_PROCUREMENT_ATTACHMENT();
                    o.setAttach_code(attach_code);
                    o.setAttach_id(file_id);
                    o.setPid(id);
                    pmProcurementAttachmentDao.save(o);
                }
            }
        }else{
            //更新
            PM_PROCUREMENT obj = pmProcurementDao.get(Long.parseLong(StringUtils.null2String(param.get("id"))));
            if(!StringUtils.isEmpty(param.get("pid"))){
                obj.setPid(Long.parseLong(StringUtils.null2String(param.get("pid"))));
            }
            if(!StringUtils.isEmpty(param.get("name"))){
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if(!StringUtils.isEmpty(param.get("budget_amount"))){
                obj.setBudget_amount(StringUtils.null2String(param.get("budget_amount")));
            }
            if(!StringUtils.isEmpty(param.get("responsible_person_id"))){
                obj.setResponsible_person(Long.parseLong(StringUtils.null2String(param.get("responsible_person_id"))));
            }
            if(!StringUtils.isEmpty(param.get("procurement_method"))){
                obj.setProcurement_method(StringUtils.null2String(param.get("procurement_method")));
            }
            if(!StringUtils.isEmpty(param.get("bidding_company"))){
                obj.setBidding_company(StringUtils.null2String(param.get("bidding_company")));
            }
            if(!StringUtils.isEmpty(param.get("bidding_hang_date"))){
                DateConverter d = new DateConverter();
                obj.setBidding_hang_date(d.convert(StringUtils.null2String(param.get("bidding_hang_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bidding_open_date"))){
                DateConverter d = new DateConverter();
                obj.setBidding_open_date(d.convert(StringUtils.null2String(param.get("bidding_open_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bid_amount"))){
                obj.setBid_amount(new BigDecimal(StringUtils.null2String(param.get("bid_amount"))));
            }
            if(!StringUtils.isEmpty(param.get("signup_end_date"))){
                DateConverter d = new DateConverter();
                obj.setSignup_end_date(d.convert(StringUtils.null2String(param.get("signup_end_date"))));
            }
            if(!StringUtils.isEmpty(param.get("contract_record_date"))){
                DateConverter d = new DateConverter();
                obj.setContract_record_date(d.convert(StringUtils.null2String(param.get("contract_record_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bidding_win_company"))){
                obj.setBidding_win_company(StringUtils.null2String(param.get("bidding_win_company")));
            }
            if(!StringUtils.isEmpty(param.get("bidding_win_date"))){
                DateConverter d = new DateConverter();
                obj.setBidding_win_date(d.convert(StringUtils.null2String(param.get("bidding_win_date"))));
            }
            if(!StringUtils.isEmpty(param.get("bid_notification"))){
                obj.setBid_notification(Long.parseLong(StringUtils.null2String(param.get("bid_notification"))));
            }
            pmProcurementDao.update(obj);
            id=obj.getId();
            //删除原来的attachments关联
            pmProcurementAttachmentDao.batchUpdate("delete from PM_PROCUREMENT_ATTACHMENT where pid=?",id);
            //添加
            List<Map> attachments = ( List<Map>)param.get("attachments");
            if(attachments!=null){
                for(Map m:attachments){
                    String attach_code = StringUtils.null2String( m.get("attach_code"));
                    Long file_id = Long.parseLong(StringUtils.null2String( m.get("file_id")));
                    PM_PROCUREMENT_ATTACHMENT o = new PM_PROCUREMENT_ATTACHMENT();
                    o.setAttach_code(attach_code);
                    o.setAttach_id(file_id);
                    o.setPid(id);
                    pmProcurementAttachmentDao.save(o);
                }
            }
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("id",id);
        return result;
    }

    public void deleteProcurement(Map<String, Object> param) throws Exception {
        Long id = Long.parseLong(String.valueOf(param.get("id")));
        pmProcurementDao.removeById(id);
        pmProcurementAttachmentDao.batchUpdate("delete from PM_PROCUREMENT_ATTACHMENT where pid=?",id);
    }
    
    
    
}
