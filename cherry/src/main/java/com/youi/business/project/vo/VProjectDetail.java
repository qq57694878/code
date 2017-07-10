package com.youi.business.project.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/12/22.
 */
public class VProjectDetail {
    /**
     *项目ID
     */
    private Long id;

    /**
     *项目名称
     */
    private String name;

    /**
     *年度
     */
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    /**

     *申请部门
     */
    private Long apply_dept_id;

    /**
     *概算总投资
     */
    private BigDecimal total_investment;

    /**
     *未分解投资额
     */
    private BigDecimal balance_investment;

    /**
     *项目预计开始时间
     */
    private Date expected_sdate;

    /**
     *项目预计完成时间
     */
    private Date expected_edate;

    /**
     *项目概述
     */
    private String description;

    /**
     *项目状态 1 已申请 0 草稿
     */
    private String status;

    /**
     *申请人
     */
    private Long applicant_id;
    /**
     *申请人姓名
     */
    private String applicant_name;

    /**
     *申请时间
     */
    private Date apply_date;

    private String apply_dept_name;

    public String getApply_dept_name() {
        return apply_dept_name;
    }

    public void setApply_dept_name(String apply_dept_name) {
        this.apply_dept_name = apply_dept_name;
    }

    private Map<String,Object> attachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getApply_dept_id() {
        return apply_dept_id;
    }

    public void setApply_dept_id(Long apply_dept_id) {
        this.apply_dept_id = apply_dept_id;
    }

    public BigDecimal getTotal_investment() {
        return total_investment;
    }

    public void setTotal_investment(BigDecimal total_investment) {
        this.total_investment = total_investment;
    }

    public Date getExpected_sdate() {
        return expected_sdate;
    }

    public void setExpected_sdate(Date expected_sdate) {
        this.expected_sdate = expected_sdate;
    }

    public Date getExpected_edate() {
        return expected_edate;
    }

    public void setExpected_edate(Date expected_edate) {
        this.expected_edate = expected_edate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getApply_date() {
        return apply_date;
    }

    public void setApply_date(Date apply_date) {
        this.apply_date = apply_date;
    }

    public Long getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(Long applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }
    public BigDecimal getBalance_investment() {
        return balance_investment;
    }

    public void setBalance_investment(BigDecimal balance_investment) {
        this.balance_investment = balance_investment;
    }
}
