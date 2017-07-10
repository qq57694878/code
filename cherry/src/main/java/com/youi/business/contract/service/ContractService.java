package com.youi.business.contract.service;

import com.youi.business.common.dao.*;
import com.youi.business.common.entity.*;
import com.youi.business.common.store.StoreConnector;
import com.youi.business.common.store.StoreDTO;
import com.youi.business.common.util.DateKit;
import com.youi.business.common.util.DevelopKit;
import com.youi.core.codetable.CodeTableUtil;
import com.youi.core.mapper.BeanMapper;
import com.youi.core.page.Page;
import com.youi.core.query.PropertyFilter;
import com.youi.core.spring.DateConverter;
import com.youi.core.util.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目采购
 */
@Service
public class ContractService {
    @Autowired
    private PmContractDao pmContractDao;
    @Autowired
    private PmPaymentDao pmPaymentDao;
    @Autowired
    private PmContractExecDao pmContractExecDao;
    @Autowired
    private PmContractHwDao pmContractHwDao;
    @Autowired
    private PmContractHwmanDao pmContractHwmanDao;
    @Autowired
    private PmContractHwwarrantyDao pmContractHwwarrantyDao;
    @Autowired
    private PmContractSoftDao pmContractSoftDao;
    @Autowired
    private PmBizAttachmentDao pmBizAttachmentDao;
    @Autowired
    private PmContractSoftmanDao pmContractSoftmanDao;
    @Autowired
    private BeanMapper beanMapper;
    @Autowired
    private StoreConnector storeConnector;

    public Page queryContractList(Map<String, Object> param) throws Exception {
        Page result = new Page();
        //分页
        Page page = new Page(Integer.parseInt(StringUtils.null2String(param.get("pageno"))), Integer.parseInt(StringUtils.null2String(param.get("pagesize"))));
        page.addOrder("status",Page.ASC);
        page.addOrder("apply_date",Page.DESC);
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

        if (!StringUtils.isEmpty(param.get("cno"))) { //合同编号
            filters.add(new PropertyFilter("EQS_cno", StringUtils.null2String(param.get("cno"))));
        }
        if (!StringUtils.isEmpty(param.get("name"))) {
            filters.add(new PropertyFilter("LIKES_t1.name", "%" + param.get("name") + "%"));
        }
        if (!StringUtils.isEmpty(param.get("vender"))) {
            filters.add(new PropertyFilter("EQL_vender", StringUtils.null2String(param.get("vender"))));
        }
        if ("1".equals(param.get("filter_applicant"))) {
            filters.add(new PropertyFilter("EQL_t1.applicant", StringUtils.null2String(param.get("userid"))));
        }
        if (!StringUtils.isEmpty(param.get("status"))) {
            List<Object> statuss= (List<Object>)param.get("status");
            filters.add(new PropertyFilter("INS_t1.status",DevelopKit.array2String(statuss.toArray()) ));//DevelopKit.array2SqlString(statuss.toArray())
        }
        String sql = "select t1.*,t2.name as procurement_name,t3.ctype FROM PM_CONTRACT t1 left join PM_PROCUREMENT t2 on(t1.pid= t2.id) left join PM_CONTRACT_EXEC t3  on (t3.cid= t1.id)   ";
        Page page1 = pmContractDao.pagedSQLQuery(sql, page, filters);
        List<Map<String, Object>> list = (List<Map<String, Object>>) page1.getResult();

        if (list != null) {
            for (Map<String, Object> p : list) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("id", p.get("id"));
                m.put("pid", p.get("pid"));
                m.put("name", p.get("name"));
                m.put("procurement_name", p.get("procurement_name"));
                m.put("cno", p.get("cno"));
                m.put("vender_id", p.get("vender"));
                m.put("vender_name", CodeTableUtil.code2mean("SYSORG", p.get("vender")));
                m.put("amount", p.get("amount"));
                m.put("sdate", p.get("sdate"));
                m.put("edate", p.get("edate"));
                m.put("applicant_id", p.get("applicant"));
                m.put("applicant_name", CodeTableUtil.code2mean("SYSUSER", p.get("applicant")));
                m.put("apply_date", p.get("apply_date"));
                m.put("responsible_person_id", p.get("responsible_person"));
                m.put("responsible_person_name", CodeTableUtil.code2mean("SYSUSER", p.get("responsible_person")));
                m.put("reception_time", p.get("reception_time"));
               // m.put("estimated_date", p.get("estimated_date"));
                m.put("contract_file", p.get("contract_file"));
                m.put("status", p.get("status"));
                if (!StringUtils.isEmpty(p.get("type"))) {
                    String type = StringUtils.null2String(p.get("ctype"));
                    m.put("ctype", type.split(","));
                }
                m.put("amount_paid", p.get("amount_paid"));
                m.put("unpaid_amount", p.get("unpaid_amount"));
                m.put("next_estimated_date", p.get("next_estimated_date"));
                m.put("nextpay_amount", p.get("nextpay_amount"));
                m.put("payment_status", p.get("payment_status"));
                results.add(m);
            }
        }
        result.setResult(results);
        result.setTotalCount(page1.getTotalCount());
        return result;
    }


    public Object selectContractDetail(Long id) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select t1.*,t2.name as procurement_name,t3.ctype FROM PM_CONTRACT t1 left join PM_PROCUREMENT t2 on(t1.pid= t2.id) left join PM_CONTRACT_EXEC t3  on (t3.cid= t1.id) where t1.id = ?");
        List<Map<String, Object>> list = pmContractDao.createSQLQuery(sql.toString(), new Object[]{id}).list();
        if (list == null || list.size() == 0) {
            return null;
        }
        Map<String, Object> p = list.get(0);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("id", p.get("id"));
        m.put("pid", p.get("pid"));
        m.put("supervision", p.get("supervision"));
        m.put("procurement_name", p.get("procurement_name"));
        m.put("cno", p.get("cno"));
        m.put("name", p.get("name"));
        m.put("vender_id", p.get("vender"));
        m.put("vender_name", CodeTableUtil.code2mean("SYSORG", p.get("vender")));
        m.put("amount", p.get("amount"));
        m.put("sdate", p.get("sdate"));
        m.put("edate", p.get("edate"));
        m.put("applicant_id", p.get("applicant"));
        m.put("applicant_name", CodeTableUtil.code2mean("SYSUSER", p.get("applicant")));
        m.put("apply_date", p.get("apply_date"));
        m.put("responsible_person_id", p.get("responsible_person"));
        m.put("responsible_person_name", CodeTableUtil.code2mean("SYSUSER", p.get("responsible_person")));
        m.put("estimated_reception_time", p.get("estimated_reception_time"));
        m.put("status", p.get("status"));
        if (!StringUtils.isEmpty(p.get("type"))) {
            String type = StringUtils.null2String(p.get("ctype"));
            m.put("ctype", type.split(","));
        }
        m.put("reception_time", p.get("reception_time"));
        m.put("expert", p.get("expert"));
        List<Map<String,Object>> payments = new ArrayList<Map<String,Object>>();
        m.put("payment", payments);
        List<PM_PAYMENT> payList = pmPaymentDao.find("from PM_PAYMENT where cid=?", new Object[]{id});
        if (payList != null) {
            for (PM_PAYMENT pay : payList) {
                Map<String, Object> m1 = new HashMap<String, Object>();
                payments.add(m1);
                m1.put("terms", pay.getTerms());
                m1.put("amount", pay.getAmount());
                m1.put("estimated_date", pay.getEstimated_date());
                m1.put("paydate", pay.getPaydate());
                m1.put("invoice_no", pay.getInvoice_no());
                if (!StringUtils.isEmpty(pay.getInvoice_file())) {
                    StoreDTO storeDTO = storeConnector.getStore(pay.getInvoice_file());
                    Map<String, Object> attachment = new HashMap<String, Object>();
                    attachment.put("file_name", storeDTO.getShow_name());
                    attachment.put("file_type", storeDTO.getFile_type());
                    attachment.put("url", storeDTO.getUrl());
                    attachment.put("file_id", storeDTO.getId());
                    attachment.put("cdate", storeDTO.getCreate_date());
                    attachment.put("creator_id", storeDTO.getCreate_user());
                    attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
                    m1.put("invoice_file", attachment);
                }
                m1.put("check_no", pay.getCheck_no());
                if (!StringUtils.isEmpty(pay.getCheck_file())) {
                    StoreDTO storeDTO = storeConnector.getStore(pay.getCheck_file());
                    Map<String, Object> attachment = new HashMap<String, Object>();
                    attachment.put("file_name", storeDTO.getShow_name());
                    attachment.put("file_type", storeDTO.getFile_type());
                    attachment.put("url", storeDTO.getUrl());
                    attachment.put("file_id", storeDTO.getId());
                    attachment.put("cdate", storeDTO.getCreate_date());
                    attachment.put("creator_id", storeDTO.getCreate_user());
                    attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
                    m1.put("check_file", attachment);
                }
                m1.put("status", pay.getStatus());
            }
        }

        if (!StringUtils.isEmpty(p.get("contract_file"))) {
            StoreDTO storeDTO = storeConnector.getStore(Long.parseLong(String.valueOf(p.get("contract_file"))));
            Map<String, Object> attachment = new HashMap<String, Object>();
            attachment.put("file_name", storeDTO.getShow_name());
            attachment.put("file_type", storeDTO.getFile_type());
            attachment.put("url", storeDTO.getUrl());
            attachment.put("file_id", storeDTO.getId());
            attachment.put("cdate", storeDTO.getCreate_date());
            attachment.put("creator_id", storeDTO.getCreate_user());
            attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
            m.put("contract_file", attachment);
        }
        if (!StringUtils.isEmpty(p.get("expert_opinion_file"))) {
            StoreDTO storeDTO = storeConnector.getStore(Long.parseLong(String.valueOf(p.get("expert_opinion_file"))));
            Map<String, Object> attachment = new HashMap<String, Object>();
            attachment.put("file_name", storeDTO.getShow_name());
            attachment.put("file_type", storeDTO.getFile_type());
            attachment.put("url", storeDTO.getUrl());
            attachment.put("file_id", storeDTO.getId());
            attachment.put("cdate", storeDTO.getCreate_date());
            attachment.put("creator_id", storeDTO.getCreate_user());
            attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
            m.put("expert_opinion_file", attachment);
        }
        if (!StringUtils.isEmpty(p.get("verdict_file"))) {
            StoreDTO storeDTO = storeConnector.getStore(Long.parseLong(String.valueOf(p.get("verdict_file"))));
            Map<String, Object> attachment = new HashMap<String, Object>();
            attachment.put("file_name", storeDTO.getShow_name());
            attachment.put("file_type", storeDTO.getFile_type());
            attachment.put("url", storeDTO.getUrl());
            attachment.put("file_id", storeDTO.getId());
            attachment.put("cdate", storeDTO.getCreate_date());
            attachment.put("creator_id", storeDTO.getCreate_user());
            attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
            m.put("verdict_file", attachment);
        }
        return m;
    }

    public Object saveContract(Map<String, Object> param) throws Exception {
        Long id = null;
        if (StringUtils.isEmpty(param.get("id"))) {
            //新增
            PM_CONTRACT obj = new PM_CONTRACT();
            if (!StringUtils.isEmpty(param.get("pid"))) {
                obj.setPid(Long.parseLong(StringUtils.null2String(param.get("pid"))));
            }
            if (!StringUtils.isEmpty(param.get("name"))) {
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if (!StringUtils.isEmpty(param.get("cno"))) {
                obj.setCno(StringUtils.null2String(param.get("cno")));
            }
            if (!StringUtils.isEmpty(param.get("vender_id"))) {
                obj.setVender(Long.parseLong(StringUtils.null2String(param.get("vender_id"))));
            }
            if (!StringUtils.isEmpty(param.get("amount"))) {
                obj.setAmount(new BigDecimal(StringUtils.null2String(param.get("amount"))));
            }
            if (!StringUtils.isEmpty(param.get("sdate"))) {
                DateConverter d = new DateConverter();
                obj.setSdate(d.convert(StringUtils.null2String(param.get("sdate"))));
            }
            if (!StringUtils.isEmpty(param.get("edate"))) {
                DateConverter d = new DateConverter();
                obj.setEdate(d.convert(StringUtils.null2String(param.get("edate"))));
            }
            if (!StringUtils.isEmpty(param.get("responsible_person_id"))) {
                obj.setResponsible_person(Long.parseLong(StringUtils.null2String(param.get("responsible_person_id"))));
            }
            if (!StringUtils.isEmpty(param.get("estimated_reception_time"))) {
                DateConverter d = new DateConverter();
                obj.setEstimated_reception_time(d.convert(StringUtils.null2String(param.get("estimated_reception_time"))));
            }
            if (!StringUtils.isEmpty(param.get("contract_file"))) {
                obj.setContract_file(Long.parseLong(StringUtils.null2String(param.get("contract_file"))));
            }
            if (!StringUtils.isEmpty(param.get("supervision"))) {
                obj.setSupervision(StringUtils.null2String(param.get("supervision")));
            }

         /*   1 登记中：草稿状态，可编辑、可删除
            2 计划中：合同登记已提交，执行计划未提交，合同登记只读，合同计划可编辑
            3 执行中：执行计划已提交，未验收，合同登记只读，合同计划可编辑
            4 已完成：完成验收，均只读*/
            obj.setStatus("1");//登记中
            obj.setApply_date(new Date());
            obj.setApplicant(Long.parseLong(StringUtils.null2String(param.get("userid"))));
            pmContractDao.save(obj);
            id = obj.getId();
        } else {
            //更新
            id = Long.parseLong(StringUtils.null2String(param.get("id")));

            PM_CONTRACT obj = pmContractDao.get(Long.parseLong(StringUtils.null2String(param.get("id"))));
            if (!StringUtils.isEmpty(param.get("pid"))) {
                obj.setPid(Long.parseLong(StringUtils.null2String(param.get("pid"))));
            }
            if (!StringUtils.isEmpty(param.get("name"))) {
                obj.setName(StringUtils.null2String(param.get("name")));
            }
            if (!StringUtils.isEmpty(param.get("cno"))) {
                obj.setCno(StringUtils.null2String(param.get("cno")));
            }
            if (!StringUtils.isEmpty(param.get("vender_id"))) {
                obj.setVender(Long.parseLong(StringUtils.null2String(param.get("vender_id"))));
            }
            if (!StringUtils.isEmpty(param.get("amount"))) {
                obj.setAmount(new BigDecimal(StringUtils.null2String(param.get("amount"))));
            }
            if (!StringUtils.isEmpty(param.get("sdate"))) {
                DateConverter d = new DateConverter();
                obj.setSdate(d.convert(StringUtils.null2String(param.get("sdate"))));
            }
            if (!StringUtils.isEmpty(param.get("edate"))) {
                DateConverter d = new DateConverter();
                obj.setEdate(d.convert(StringUtils.null2String(param.get("edate"))));
            }
            if (!StringUtils.isEmpty(param.get("estimated_reception_time"))) {
                DateConverter d = new DateConverter();
                obj.setEstimated_reception_time(d.convert(StringUtils.null2String(param.get("estimated_reception_time"))));
            }
            if (!StringUtils.isEmpty(param.get("responsible_person_id"))) {
                obj.setResponsible_person(Long.parseLong(StringUtils.null2String(param.get("responsible_person_id"))));
            }
            if (!StringUtils.isEmpty(param.get("contract_file"))) {
                obj.setContract_file(Long.parseLong(StringUtils.null2String(param.get("contract_file"))));
            }
            if (!StringUtils.isEmpty(param.get("supervision"))) {
                obj.setSupervision(StringUtils.null2String(param.get("supervision")));
            }
            pmContractDao.update(obj);

            //删除原来的合同付款PM_PAYMENT关联
            pmPaymentDao.batchUpdate("delete from PM_PAYMENT where cid=?", id);
        }
         //  更新付款信息表
        List<Map> payments = (List<Map>) param.get("payment");
        if (payments != null) {
            for (Map m : payments) {
                String terms = StringUtils.null2String(m.get("terms"));
                PM_PAYMENT o = new PM_PAYMENT();
                o.setCid(id);
                o.setTerms(terms);
                if (!StringUtils.isEmpty(param.get("cno"))) {
                    o.setCno(StringUtils.null2String(param.get("cno")));
                }
                if (!StringUtils.isEmpty(m.get("estimated_amount"))) {
                    BigDecimal estimated_amount = new BigDecimal(StringUtils.null2String(m.get("estimated_amount")));
                    o.setEstimated_amount(estimated_amount);
                }
                if (!StringUtils.isEmpty(m.get("estimated_date"))) {
                    DateConverter d = new DateConverter();
                    o.setEstimated_date(d.convert(StringUtils.null2String(m.get("estimated_date"))));
                }
                o.setStatus("0");
                pmPaymentDao.save(o);
            }
        }

        //更新合同表
        PM_CONTRACT contract = pmContractDao.get(id);
        //已付金额
        BigDecimal amount_paid = new BigDecimal(0);
        //未付款金额
        BigDecimal unpaid_amount = new BigDecimal(0);
        //下次付款金额
        BigDecimal nextpay_amount = new BigDecimal(0);
        //合同下次预计付款时间
        Date next_estimated_date = new Date();
        //付款状态 0 未付款  2 部分付款 1:已付款
        String payment_status="0";
        BigDecimal totalAmount = contract.getAmount();
        List<PM_PAYMENT> payList =  pmPaymentDao.find("from PM_PAYMENT where cid=? order by estimated_date asc",id);
        if(payList!=null){
            for(PM_PAYMENT p:payList){
                if("1".equals(p.getStatus())){
                    amount_paid = amount_paid.add(p.getAmount());
                }
            }
        }
        if(totalAmount.compareTo(amount_paid)<=0){
            payment_status="1";
        }else if(amount_paid.doubleValue()>0){
            payment_status="2";
        }else{
            payment_status="0";
        }
        PM_PAYMENT nextPayMent= findNextPayMent(payList);
        if(nextPayMent!=null){
            BigDecimal xf= new BigDecimal(0);
            BigDecimal yf= new BigDecimal(0);
            for(PM_PAYMENT p:payList){
                if(p.getEstimated_date().compareTo(nextPayMent.getEstimated_date())<=0){
                    xf = xf.add(p.getEstimated_amount()==null?new BigDecimal(0):p.getEstimated_amount());
                    yf = yf.add(p.getAmount()==null?new BigDecimal(0):p.getAmount());
                }
            }
            BigDecimal cf = xf.subtract(yf);
            nextpay_amount = cf.doubleValue()>=0?cf:new BigDecimal(0);
            next_estimated_date=nextPayMent.getEstimated_date();
        }else{
            nextpay_amount=null;
            next_estimated_date=null;
        }
        unpaid_amount = totalAmount.subtract(amount_paid);
        contract.setAmount_paid(amount_paid);
        contract.setUnpaid_amount(unpaid_amount);
        contract.setNext_estimated_date(next_estimated_date);
        contract.setNextpay_amount(nextpay_amount);
        contract.setPayment_status(payment_status);
        pmContractDao.update(contract);




        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        return result;
    }

    public void deleteContract(Map<String, Object> param) throws Exception {
        Long id = Long.parseLong(String.valueOf(param.get("id")));
        pmContractDao.removeById(id);
    }


    public Object paymentContract(Map<String, Object> param) throws Exception {
        Long cid = Long.parseLong(StringUtils.null2String(param.get("cid")));
        PM_CONTRACT contract = pmContractDao.get(cid);
        List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("payment");
        if (list != null) {
            //删除原来的合同付款PM_PAYMENT关联
            pmPaymentDao.batchUpdate("delete from PM_PAYMENT where cid=?", cid);
            for (Map<String, Object> m : list) {
                PM_PAYMENT obj = new PM_PAYMENT();
                obj.setCid(cid);
                if(contract!=null){
                    obj.setCno(contract.getCno());
                }
                if (!StringUtils.isEmpty(m.get("terms"))) {
                    obj.setTerms(StringUtils.null2String(m.get("terms")));
                }
                if (!StringUtils.isEmpty(m.get("amount"))) {
                    BigDecimal amount = new BigDecimal(StringUtils.null2String(m.get("amount")));
                    obj.setAmount(amount);
                }
                if (!StringUtils.isEmpty(m.get("estimated_amount"))) {
                    BigDecimal estimated_amount = new BigDecimal(StringUtils.null2String(m.get("estimated_amount")));
                    obj.setEstimated_amount(estimated_amount);
                }
                if (!StringUtils.isEmpty(m.get("estimated_date"))) {
                    DateConverter d = new DateConverter();
                    obj.setEstimated_date(d.convert(StringUtils.null2String(m.get("estimated_date"))));
                }
                if (!StringUtils.isEmpty(m.get("paydate"))) {
                    DateConverter d = new DateConverter();
                    obj.setPaydate(d.convert(StringUtils.null2String(m.get("paydate"))));
                }
                if (!StringUtils.isEmpty(m.get("invoice_no"))) {
                    obj.setInvoice_no(StringUtils.null2String(m.get("invoice_no")));
                }
                if (!StringUtils.isEmpty(m.get("invoice_file"))) {
                    obj.setInvoice_file(Long.parseLong(StringUtils.null2String(m.get("invoice_file"))));
                }
                if (!StringUtils.isEmpty(m.get("check_no"))) {
                    obj.setCheck_no(StringUtils.null2String(m.get("check_no")));
                }
                if (!StringUtils.isEmpty(m.get("check_file"))) {
                    obj.setCheck_file(Long.parseLong(StringUtils.null2String(m.get("check_file"))));
                }
                if (!StringUtils.isEmpty(m.get("amount"))&&Double.parseDouble(StringUtils.null2String(m.get("amount")))>0) {
                    obj.setStatus("1");
                } else {
                    obj.setStatus("0");
                }
                pmPaymentDao.save(obj);
            }
        }
        //更新合同表
        //已付金额
        BigDecimal amount_paid = new BigDecimal(0);
        //未付款金额
        BigDecimal unpaid_amount = new BigDecimal(0);
        //下次付款金额
        BigDecimal nextpay_amount = new BigDecimal(0);
        //合同下次预计付款时间
        Date next_estimated_date = new Date();
        //付款状态 0 未付款  2 部分付款 1:已付款
        String payment_status="0";
        BigDecimal totalAmount = contract.getAmount();
        List<PM_PAYMENT> payList =  pmPaymentDao.find("from PM_PAYMENT where cid=? order by estimated_date asc",cid);
        if(payList!=null){
            for(PM_PAYMENT p:payList){
                if("1".equals(p.getStatus())){
                    amount_paid = amount_paid.add(p.getAmount());
                }
            }
        }
        if(totalAmount.compareTo(amount_paid)<=0){
            payment_status="1";
        }else if(amount_paid.doubleValue()>0){
            payment_status="2";
        }else{
            payment_status="0";
        }
        PM_PAYMENT nextPayMent= findNextPayMent(payList);
        if(nextPayMent!=null){
           BigDecimal xf= new BigDecimal(0);
            BigDecimal yf= new BigDecimal(0);
            for(PM_PAYMENT p:payList){
                if(p.getEstimated_date().compareTo(nextPayMent.getEstimated_date())<=0){
                    xf = xf.add(p.getEstimated_amount()==null?new BigDecimal(0):p.getEstimated_amount());
                    yf = yf.add(p.getAmount()==null?new BigDecimal(0):p.getAmount());
                }
            }
            BigDecimal cf = xf.subtract(yf);
            nextpay_amount = cf.doubleValue()>=0?cf:new BigDecimal(0);
            next_estimated_date=nextPayMent.getEstimated_date();
        }else{
            nextpay_amount=null;
            next_estimated_date=null;
        }
        unpaid_amount = totalAmount.subtract(amount_paid);
        contract.setAmount_paid(amount_paid);
        contract.setUnpaid_amount(unpaid_amount);
        contract.setNext_estimated_date(next_estimated_date);
        contract.setNextpay_amount(nextpay_amount);
        contract.setPayment_status(payment_status);
        pmContractDao.update(contract);
        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }

    private PM_PAYMENT findNextPayMent(List<PM_PAYMENT> payList){
        PM_PAYMENT result = null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date curDate=null;
        try {
            curDate= sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(PM_PAYMENT p:payList){
            if(p.getStatus().equals("0")&&p.getEstimated_date().after(curDate)){
                   if(result==null){
                       result = p;
                   }else if(p.getEstimated_date().before(result.getEstimated_date())){
                       result = p;
                   }
            }
        }
        return result;
    }

    public Object acceptanceContract(Map<String, Object> param) throws Exception {

        Long id = Long.parseLong(StringUtils.null2String(param.get("id")));
        PM_CONTRACT obj = pmContractDao.get(id);
        if (!StringUtils.isEmpty(param.get("expert"))) {
            obj.setExpert(StringUtils.null2String(param.get("expert")));
        }
        if (!StringUtils.isEmpty(param.get("reception_time"))) {
            DateConverter dc = new DateConverter();
            obj.setReception_time(dc.convert(StringUtils.null2String(param.get("reception_time"))));
        }
        if (!StringUtils.isEmpty(param.get("expert_opinion_file"))) {
            obj.setExpert_opinion_file(Long.parseLong(StringUtils.null2String(param.get("expert_opinion_file"))));
        }
        if (!StringUtils.isEmpty(param.get("verdict_file"))) {
            obj.setVerdict_file(Long.parseLong(StringUtils.null2String(param.get("verdict_file"))));
        }
        //更新合同状态
        obj.setStatus("4");//已完成
        pmContractDao.update(obj);

        return new Object();
    }
   //TODO
    public Object saveExecContract(Map<String, Object> param) throws Exception {
        Long id = Long.parseLong(StringUtils.null2String(param.get("id")));
        //更新ctype
        boolean isUpdate=true;
        List<PM_CONTRACT_EXEC> exec = pmContractExecDao.find("from PM_CONTRACT_EXEC where cid=?",id);
        PM_CONTRACT_EXEC pmContractExec = null;
        if (exec!=null&&exec.size()>0) {
            pmContractExec = exec.get(0);
        }else{
            isUpdate = false;
            pmContractExec = new PM_CONTRACT_EXEC();
        }
        pmContractExec.setCid(id);
        //删除原来的表数据
        pmContractSoftDao.batchUpdate("delete from PM_CONTRACT_SOFT where cid =?",id);
        pmContractSoftmanDao.batchUpdate("delete from PM_CONTRACT_SOFTMAN where cid =?",id);
        pmContractHwDao.batchUpdate("delete from PM_CONTRACT_HW where cid =?",id);
        pmContractHwmanDao.batchUpdate("delete from PM_CONTRACT_HWMAN where cid =?",id);
        pmContractHwwarrantyDao.batchUpdate("delete from PM_CONTRACT_HWWARRANTY where cid =?",id);
        //增加新数据
        List<String> types = new ArrayList<String>();
        Map<String, Object> soft = (Map<String, Object>) param.get("soft");
        Map<String, Object> softman = (Map<String, Object>) param.get("softman");
        Map<String, Object> hw = (Map<String, Object>) param.get("hw");
        List<Object> hwman = (List<Object>) param.get("hwman");
        List<Map<String,Object>> hw_warranty = (List<Map<String,Object>>) param.get("hw_warranty");
        StringBuilder ctype = new StringBuilder();
        if (!StringUtils.isEmpty(soft)) {
            ctype.append("0").append(",");
            List<Map<String, Object>> bizs = (List<Map<String, Object>>) soft.get("bizs");
            List<Object> biz_ids = (List<Object>) soft.get("biz_ids");
            if (bizs != null) {
                for (Map<String, Object> biz : bizs) {
                    //保存软件执行关联表
                    Long biz_id = Long.parseLong(StringUtils.null2String(biz.get("biz_id")));
                    PM_CONTRACT_SOFT s = new PM_CONTRACT_SOFT();
                    s.setCid(id);
                    s.setBiz_id(biz_id);
                    s.setStatus("1");
                    pmContractSoftDao.save(s);
                    //保存软件执行附件表信息
                    List<Map<String, Object>> attachments = (List<Map<String, Object>>) biz.get("attachments");
                    if (attachments != null) {
                        for (Map<String, Object> attachment : attachments) {
                            String attach_code = StringUtils.null2String(attachment.get("attach_code"));
                            Long file_id = Long.parseLong(StringUtils.null2String(attachment.get("file_id")));
                            PM_BIZ_ATTACHMENT a = new PM_BIZ_ATTACHMENT();
                            a.setBiz_id(biz_id);
                            a.setAttach_id(file_id);
                            a.setAttach_code(attach_code);
                            pmBizAttachmentDao.save(a);
                        }
                    }
                }
            }
            if (biz_ids != null) {
                for (Object biz : biz_ids) {
                    //保存软件执行关联表
                    Long biz_id = Long.parseLong(StringUtils.null2String(biz));
                    PM_CONTRACT_SOFT s = new PM_CONTRACT_SOFT();
                    s.setCid(id);
                    s.setBiz_id(biz_id);
                    s.setStatus("1");
                    pmContractSoftDao.save(s);
                }
            }
        }
        if (!StringUtils.isEmpty(softman)) {
            ctype.append("1").append(",");
            //需求变更 1 数据修改 2 数据查询 3 程序发布,以逗号分隔
            List<Object> bizs = (List<Object>) softman.get("bizs");
            List<Object> scopes = (List<Object>) softman.get("scope");
            if (bizs != null) {
                for (int i = 0; i < bizs.size(); i++) {
                    Long biz_id = Long.parseLong(StringUtils.null2String(bizs.get(i)));
                    PM_CONTRACT_SOFTMAN s = new PM_CONTRACT_SOFTMAN();
                    if (scopes != null) {
                        String scope = DevelopKit.array2String(scopes.toArray());
                        s.setScope(scope);
                    }
                    s.setStatus("1");
                    s.setBiz_id(biz_id);
                    s.setCid(id);
                    pmContractSoftmanDao.save(s);
                }
            }
        }
        if (!StringUtils.isEmpty(hw)) {
            ctype.append("2").append(",");

            if(!StringUtils.isEmpty(hw.get("hwlist_file"))){
                Long  hwlist_file = Long.parseLong(StringUtils.null2String(hw.get("hwlist_file")));
                pmContractExec.setHwlist_file(hwlist_file);
            }
            if(!StringUtils.isEmpty(hw.get("hwlist_arrival_file"))){
                Long  hwlist_arrival_file = Long.parseLong(StringUtils.null2String(hw.get("hwlist_arrival_file")));
                pmContractExec.setHwlist_arrival_file(hwlist_arrival_file);
            }
            List<Map<String,Object>> dev_list = (List<Map<String,Object>>)hw.get("dev_list");
            if(dev_list!=null){
                for(Map<String,Object> dev:dev_list){
                    Long dev_id =  Long.parseLong(StringUtils.null2String(dev.get("dev_id")));
                    String dev_type = StringUtils.null2String(dev.get("dev_type"));
                    PM_CONTRACT_HW h = new PM_CONTRACT_HW();
                    h.setCid(id);
                    h.setStatus("1");
                    h.setDevid(dev_id);
                    h.setHw_type(dev_type);
                    pmContractHwDao.save(h);
                }
            }
        }
        if (!StringUtils.isEmpty(hwman)) {
            ctype.append("3").append(",");
            for(Object o:hwman){
                PM_CONTRACT_HWMAN m = new PM_CONTRACT_HWMAN();
                m.setCid(id);
                m.setStatus("1");
                m.setInspection_id(Long.parseLong(StringUtils.null2String(o)));
                pmContractHwmanDao.save(m);
            }
        }
        if (!StringUtils.isEmpty(hw_warranty)) {
            ctype.append("4").append(",");
            for(Map<String,Object> dev:hw_warranty){
                Long dev_id =  Long.parseLong(StringUtils.null2String(dev.get("dev_id")));
                String dev_type = StringUtils.null2String(dev.get("dev_type"));
                PM_CONTRACT_HWWARRANTY m = new PM_CONTRACT_HWWARRANTY();
                m.setCid(id);
                m.setStatus("1");
                m.setDevid(dev_id);
                m.setHw_type(dev_type);
                pmContractHwmanDao.save(m);
            }
        }
        if (ctype.length() > 0) {
            ctype.deleteCharAt(ctype.length() - 1);
        }
        //更新ctype类型
        pmContractExec.setCtype(ctype.toString());
        pmContractExec.setStatus("1");
        if(isUpdate){
            pmContractExecDao.update(pmContractExec);
        }else{
            pmContractExecDao.save(pmContractExec);
        }
        PM_CONTRACT contract =  pmContractDao.get(id);
        contract.setStatus("3");//执行中
        pmContractDao.update(contract);
        Map<String, Object> result = new HashMap<String, Object>();
        return result;
    }


    //TODO
    public Object selectExecContractDetail(Long id) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        PM_CONTRACT contract = pmContractDao.get(id);
        List<PM_CONTRACT_EXEC> exec = pmContractExecDao.find("from PM_CONTRACT_EXEC where cid=?",id);
        if (exec == null && exec.size() == 0) {
            return new Object();
        }
        PM_CONTRACT_EXEC pmContractExec = exec.get(0);
        String ctype = pmContractExec.getCtype();
        String[] ctypes = ctype.split(",");
        for (int i = 0; i < ctypes.length; i++) {
            String t = ctypes[i];
            if ("0".equals(t)) {//软件开发类
                Map<String, Object> soft = new HashMap<String, Object>();
                List<Map<String, Object>> list = pmContractSoftDao.createSQLQuery("select t1.biz_id,t2.name biz_name from PM_CONTRACT_SOFT t1,RES_BUSSINESS_APP t2 where t1.biz_id = t2.id and cid=?", new Object[]{id}).list();
                List<Map<String, Object>> bizs = new ArrayList<Map<String, Object>>();
                if (list != null) {
                    for (Map<String, Object> m : list) {
                        Map<String, Object> biz = new HashMap<String, Object>();
                        Long biz_id = Long.parseLong(StringUtils.null2String(m.get("biz_id")));
                        biz.put("biz_id", biz_id);
                        biz.put("biz_name", m.get("biz_name"));
                        Map<String, Object> attachments = new HashMap<String, Object>();
                        List<PM_BIZ_ATTACHMENT> bizAttachmentList = pmBizAttachmentDao.createQuery("from PM_BIZ_ATTACHMENT where biz_id =?", new Object[]{biz_id}).list();
                        if (bizAttachmentList != null) {
                            for (PM_BIZ_ATTACHMENT b : bizAttachmentList) {
                                StoreDTO storeDTO = storeConnector.getStore(b.getAttach_id());
                                Map<String, Object> attachment = new HashMap<String, Object>();
                                attachment.put("file_name", storeDTO.getShow_name());
                                attachment.put("file_type", storeDTO.getFile_type());
                                attachment.put("url", storeDTO.getUrl());
                                attachment.put("file_id", storeDTO.getId());
                                attachment.put("cdate", storeDTO.getCreate_date());
                                attachment.put("creator_id", storeDTO.getCreate_user());
                                attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
                                attachment.put("attach_code", b.getAttach_code());
                                if (!StringUtils.isEmpty(b.getAttach_code())) {
                                    Map<String,Object> attach = (Map<String,Object>)attachments.get("attach_code");
                                    if(attach==null){
                                        attach = new  HashMap<String,Object>();
                                        attachments.put("attach_code",attach);
                                    }
                                    attach.put(b.getAttach_code(),attachment);
                                } else {
                                    List<Map<String, Object>> others = (List<Map<String, Object>>) attachments.get("others");
                                    if (others == null) {
                                        others = new ArrayList<Map<String, Object>>();
                                        attachments.put("others", others);
                                    }
                                    others.add(attachment);
                                }
                            }
                        }
                        biz.put("attachments", attachments);
                        bizs.add(biz);
                    }
                }
                soft.put("bizs", bizs);
                result.put("soft", soft);

            } else if ("1".equals(t)) {//软件运维类
                Map<String, Object> softman = new HashMap<String, Object>();
                //需求变更 1 数据修改 2 数据查询 3 程序发布,以逗号分隔
                List<Map<String, Object>> list = pmContractSoftmanDao.createSQLQuery("select t1.biz_id,t1.scope,t2.name biz_name from PM_CONTRACT_SOFTMAN t1,RES_BUSSINESS_APP t2 where t1.biz_id = t2.id and cid=?", new Object[]{id}).list();
                List<Object> scopeList = new ArrayList<Object>();
                String scope ="";
                List<Map<String, Object>> bizs = new ArrayList<Map<String, Object>>();
                if (list != null) {
                    for (Map<String, Object> m : list) {
                        scope= StringUtils.null2String(m.get("scope"));
                        Map<String, Object> biz = new HashMap<String, Object>();
                        biz.put("biz_id", m.get("biz_id"));
                        biz.put("biz_name", m.get("biz_name"));
                        bizs.add(biz);
                    }
                }
                softman.put("bizs", bizs);
                if(!StringUtils.isEmpty(scope)){
                    softman.put("scope", scope.split(","));
                }else{
                    softman.put("scope", new ArrayList());
                }

               /* workload:[
                {
                    scope:'1',
                            count:'', //记录数
                        total:'' //工作量合计
                }
                ]*/
                List<Map<String, Object>> workload = new ArrayList<Map<String, Object>>();

                softman.put("workload", workload);
                result.put("softman", softman);

            } else if ("2".equals(t)) {//硬件购置类
                Map<String, Object> hw = new HashMap<String, Object>();
                if (pmContractExec.getHwlist_file() != null) {
                    StoreDTO storeDTO = storeConnector.getStore(pmContractExec.getHwlist_file());
                    Map<String, Object> attachment = new HashMap<String, Object>();
                    attachment.put("file_name", storeDTO.getShow_name());
                    attachment.put("file_type", storeDTO.getFile_type());
                    attachment.put("url", storeDTO.getUrl());
                    attachment.put("file_id", storeDTO.getId());
                    attachment.put("cdate", storeDTO.getCreate_date());
                    attachment.put("creator_id", storeDTO.getCreate_user());
                    attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
                    hw.put("hwlist_file", attachment);
                }
                if (pmContractExec.getHwlist_arrival_file() != null) {
                    StoreDTO storeDTO = storeConnector.getStore(pmContractExec.getHwlist_arrival_file());
                    Map<String, Object> attachment = new HashMap<String, Object>();
                    attachment.put("file_name", storeDTO.getShow_name());
                    attachment.put("file_type", storeDTO.getFile_type());
                    attachment.put("url", storeDTO.getUrl());
                    attachment.put("file_id", storeDTO.getId());
                    attachment.put("cdate", storeDTO.getCreate_date());
                    attachment.put("creator_id", storeDTO.getCreate_user());
                    attachment.put("creator_name", CodeTableUtil.code2mean("SYSUSER", storeDTO.getCreate_user()));
                    hw.put("hwlist_arrival_file", attachment);
                }
                List<Map<String, Object>> dev_list = new ArrayList<Map<String, Object>>();
                List<PM_CONTRACT_HW> list = pmContractHwDao.find("from PM_CONTRACT_HW where cid=?", new Object[]{id});
                if (list != null) {
                    for (PM_CONTRACT_HW p : list) {
                       /* dev_type:'',
                                brand:'',
                                model:'',
                                arrival_date:'',
                                warranty_sdate:'', //质保开始日期
                                warranty_year:'', //质保年限
                                deploy_status:'' //部署状态*/
                        Map<String, Object> m = new HashMap<String, Object>();
                        m.put("dev_type", p.getHw_type());
                        m.put("dev_id", p.getDevid());
                        dev_list.add(m);
                    }
                }
                hw.put("dev_list", dev_list);
                result.put("hw", hw);
            } else if ("3".equals(t)) {//硬件运维类
                List<Map<String, Object>> hwman = new ArrayList<Map<String, Object>>();
                List<PM_CONTRACT_HWMAN> list = pmContractHwmanDao.createQuery("from PM_CONTRACT_HWMAN where cid =?", new Object[]{id}).list();
                if (list != null) {
                    //TODO
                    for (PM_CONTRACT_HWMAN p : list) {
                        Map<String, Object> m = new HashMap<String, Object>();
                        m.put("inspection_id", p.getInspection_id());
                        m.put("inspection_name", p.getInspection_id());
                        hwman.add(m);
                    }
                }
                result.put("hwman", hwman);
            } else if ("4".equals(t)) {//硬件质保类
                List<Map<String, Object>> hw_warranty = new ArrayList<Map<String, Object>>();
                List<PM_CONTRACT_HWWARRANTY> list = pmContractHwwarrantyDao.createQuery("from PM_CONTRACT_HWWARRANTY where cid =?", new Object[]{id}).list();
                if (list != null) {
                    for (PM_CONTRACT_HWWARRANTY p : list) {
                        Map<String, Object> m = new HashMap<String, Object>();
                        //TODO
                        m.put("dev_name", p.getDevid());
                        m.put("dev_type", p.getHw_type());
                        m.put("arrival_date", new Date());//到货日期
                        m.put("warranty_date", new Date());//原质保到期日期
                        hw_warranty.add(m);
                    }
                }
                result.put("hw_warranty", hw_warranty);
            }
        }
        return result;
    }
}
