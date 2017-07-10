package com.youi.business.sysresource.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/9/21.
 */
public class VVendorDetailInfo {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 组织机构表ID
     */
    private Long org_id;
    /**
     * 外协单位名称
     */
    private String vendor_name;
    /**
     * 负责人
     */
    private String manager;

    /**
     * 联系电话
     */
    private String tel;
    /**
     * 企业性质
     */
    private String property;
    /**
     * 经营范围
     */
    private String scope;
    /**
     *注册日期
     */
    private Date register_date;

    /**
     *注册资金
     */
    private String register_money;

    /**
     *员工人数
     */
    private String num_people;

    public String getNum_people() {
        return num_people;
    }

    public void setNum_people(String num_people) {
        this.num_people = num_people;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Long org_id) {
        this.org_id = org_id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public String getRegister_money() {
        return register_money;
    }

    public void setRegister_money(String register_money) {
        this.register_money = register_money;
    }
}
