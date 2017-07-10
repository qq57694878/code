package com.youi.business.ip.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/10/7.
 */
public class VIpAddressDetail {
    /**
     *
     */
    private Long id;

    /**
     *业务类型,表名
     */
    private String biz_type;

    /**
     *业务表主键
     */
    private Long biz_id;

    /**
     *ip地址
     */
    private String ip_add;

    /**
     *子网掩码
     */
    private String subnet_mask;

    /**
     *网关地址
     */
    private String gateway;

    /**
     *mac地址
     */
    private String mac;

    /**
     *
     */
    private Long cuserid;

    /**
     *
     */
    private Date ctime;

    /**
     *mac地址刷新时间
     */
    private Date mac_fresh_time;

    /**
     *ip类型1-主IP，2-从IP
     */
    private String ip_type;

    /**
     *zabbix监控项ID
     */
    private Long zabbixid;

    /**
     *监控开启状态1-开启，0-关闭
     */
    private String zabbix_switch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(String biz_type) {
        this.biz_type = biz_type;
    }

    public Long getBiz_id() {
        return biz_id;
    }

    public void setBiz_id(Long biz_id) {
        this.biz_id = biz_id;
    }

    public String getIp_add() {
        return ip_add;
    }

    public void setIp_add(String ip_add) {
        this.ip_add = ip_add;
    }

    public String getSubnet_mask() {
        return subnet_mask;
    }

    public void setSubnet_mask(String subnet_mask) {
        this.subnet_mask = subnet_mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

    public Date getMac_fresh_time() {
        return mac_fresh_time;
    }

    public void setMac_fresh_time(Date mac_fresh_time) {
        this.mac_fresh_time = mac_fresh_time;
    }

    public String getIp_type() {
        return ip_type;
    }

    public void setIp_type(String ip_type) {
        this.ip_type = ip_type;
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
}
