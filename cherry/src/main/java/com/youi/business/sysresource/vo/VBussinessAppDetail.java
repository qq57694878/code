package com.youi.business.sysresource.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/9/26.
 */
public class VBussinessAppDetail {
    private Long id;// 主键ID
    private String name;//系统名称
    private String org_id_list;//部门名称

    private Long vendor_id;//开发单位
    private Date online_time;//上线时间
    private String  description;//系统业务说明
    private String status;

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

    public String getOrg_id_list() {
        return org_id_list;
    }

    public void setOrg_id_list(String org_id_list) {
        this.org_id_list = org_id_list;
    }

    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
        this.vendor_id = vendor_id;
    }

    public Date getOnline_time() {
        return online_time;
    }

    public void setOnline_time(Date online_time) {
        this.online_time = online_time;
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
}
