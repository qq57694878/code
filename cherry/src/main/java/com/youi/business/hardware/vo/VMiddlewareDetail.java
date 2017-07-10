package com.youi.business.hardware.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/28.
 */
public class VMiddlewareDetail {
    /**
     *
     */
    private Long id;

    /**
     *名称
     */
    private String name;

    /**
     *品牌
     */
    private String brand;

    /**
     *版本
     */
    private String version;

    /**
     *控制台地址
     */
    private String console;

    /**
     *
     */
    private Long cuserid;

    /**
     *
     */
    private Date ctime;

    /**
     * x86信息集合
     */
    private List<Map<String,Object>> x86_list;

    /**
     * storage信息集合
     */
    private List<Map<String,Object>> vm_list;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public Long getCuserid() {
        return cuserid;
    }

    public void setCuserid(Long cuserid) {
        this.cuserid = cuserid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public List<Map<String, Object>> getX86_list() {
        return x86_list;
    }

    public void setX86_list(List<Map<String, Object>> x86_list) {
        this.x86_list = x86_list;
    }

    public List<Map<String, Object>> getVm_list() {
        return vm_list;
    }

    public void setVm_list(List<Map<String, Object>> vm_list) {
        this.vm_list = vm_list;
    }
}
