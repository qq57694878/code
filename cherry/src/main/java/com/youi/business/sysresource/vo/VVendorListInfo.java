package com.youi.business.sysresource.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by jinliang on 2016/9/21.
 */
public class VVendorListInfo {
    public VVendorListInfo(){}

    /**
     *主键
     */
    private Long id;

    /**
     *外协单位名称
     */
    private String vendor_name;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 电话
     */
    private String tel;
    /**
     * 状态,1有效，0无效
     */
    private String state;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
