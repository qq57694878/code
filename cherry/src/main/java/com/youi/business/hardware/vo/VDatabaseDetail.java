package com.youi.business.hardware.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/28.
 */
public class VDatabaseDetail {
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
     *版本号
     */
    private String version;

    /**
     *实例名
     */
    private String instance_name;

    /**
     *端口
     */
    private String port;

    /**
     *IP地址
     */
    private String address;

    /**
     *
     */
    private Long cuserid;

    /**
     *
     */
    private Date ctime;

    /**
     *zabbix监控项ID
     */
    private Long zabbixid;

    /**
     *监控开启状态1-开启，0-关闭
     */
    private String zabbix_switch;

    /**
     * x86信息集合
     */
    private List<Map<String,Object>> x86_list;

    /**
     * storage信息集合
     */
    private List<Map<String,Object>> storage_list;

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

    public String getInstance_name() {
        return instance_name;
    }

    public void setInstance_name(String instance_name) {
        this.instance_name = instance_name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getZabbixid() {
        return zabbixid;
    }

    public void setZabbixid(Long zabbixid) {
        this.zabbixid = zabbixid;
    }

    public String getZabbix_switch() {
        return zabbix_switch;
    }

    public void setZabbix_switch(String zabbix_switch) {
        this.zabbix_switch = zabbix_switch;
    }

    public List<Map<String, Object>> getX86_list() {
        return x86_list;
    }

    public void setX86_list(List<Map<String, Object>> x86_list) {
        this.x86_list = x86_list;
    }

    public List<Map<String, Object>> getStorage_list() {
        return storage_list;
    }

    public void setStorage_list(List<Map<String, Object>> storage_list) {
        this.storage_list = storage_list;
    }
}
