package com.youi.business.ip.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/10/7.
 */
public class VIpAddressGroupDetail {
    /**
     *
     */
    private Long id;

    /**
     *网段名称
     */
    private String name;

    /**
     *用途
     */
    private String description;

    /**
     *ip地址
     */
    private String ip_add;

    /**
     *子网掩码，存数字，读字符串
     */
    private String subnet_mask;

    /**
     *网关
     */
    private String gateway;

    /**
     *未规划的活动IP数量
     */
    private Long red_count;

    /**
     *规划的活动IP数量
     */
    private Long green_count;

    /**
     *规划的非活动IP数量
     */
    private Long yellow_count;

    /**
     *未规划的非活动IP数量
     */
    private Long gray_count;

    /**
     *创建人
     */
    private Long cuserid;

    /**
     *创建时间
     */
    private Date ctime;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getRed_count() {
        return red_count;
    }

    public void setRed_count(Long red_count) {
        this.red_count = red_count;
    }

    public Long getGreen_count() {
        return green_count;
    }

    public void setGreen_count(Long green_count) {
        this.green_count = green_count;
    }

    public Long getYellow_count() {
        return yellow_count;
    }

    public void setYellow_count(Long yellow_count) {
        this.yellow_count = yellow_count;
    }

    public Long getGray_count() {
        return gray_count;
    }

    public void setGray_count(Long gray_count) {
        this.gray_count = gray_count;
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
}
