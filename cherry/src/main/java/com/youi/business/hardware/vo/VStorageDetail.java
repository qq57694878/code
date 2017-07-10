package com.youi.business.hardware.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/9/26.
 */
public class VStorageDetail {
    /**
     *
     */
    private Long id;

    private String name;

    /**
     *
     */
    private Long room_id;

    /**
     *
     */
    private Long cabinet_id;

    /**
     *品牌
     */
    private String brand;

    /**
     *型号
     */
    private String type;

    /**
     *
     */
    private Long high;

    /**
     *
     */
    private Long width;

    /**
     *
     */
    private Long depth;

    /**
     *cpu型号
     */
    private String cpu_type;

    /**
     *磁盘容量
     */
    private Long disk_size;

    /**
     *存储接口类型
     */
    private String disk_interface_type;

    /**
     *当前磁盘数
     */
    private Long disk_current_num;

    /**
     *最大磁盘数
     */
    private Long disk_max_num;

    /**
     *
     */
    private String disk_raid;

    /**
     *主要用途,1-业务服务器,2-虚拟化服务器,3-数据库服务器
     */
    private String main_usage;

    /**
     *其他描述
     */
    private String description;

    /**
     *创建时间
     */
    private Date cdate;

    /**
     *创建者userid
     */
    private Long userid;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Long getCabinet_id() {
        return cabinet_id;
    }

    public void setCabinet_id(Long cabinet_id) {
        this.cabinet_id = cabinet_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getHigh() {
        return high;
    }

    public void setHigh(Long high) {
        this.high = high;
    }

    public Long getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    public String getCpu_type() {
        return cpu_type;
    }

    public void setCpu_type(String cpu_type) {
        this.cpu_type = cpu_type;
    }

    public Long getDisk_size() {
        return disk_size;
    }

    public void setDisk_size(Long disk_size) {
        this.disk_size = disk_size;
    }

    public String getDisk_interface_type() {
        return disk_interface_type;
    }

    public void setDisk_interface_type(String disk_interface_type) {
        this.disk_interface_type = disk_interface_type;
    }

    public Long getDisk_current_num() {
        return disk_current_num;
    }

    public void setDisk_current_num(Long disk_current_num) {
        this.disk_current_num = disk_current_num;
    }

    public Long getDisk_max_num() {
        return disk_max_num;
    }

    public void setDisk_max_num(Long disk_max_num) {
        this.disk_max_num = disk_max_num;
    }

    public String getDisk_raid() {
        return disk_raid;
    }

    public void setDisk_raid(String disk_raid) {
        this.disk_raid = disk_raid;
    }

    public String getMain_usage() {
        return main_usage;
    }

    public void setMain_usage(String main_usage) {
        this.main_usage = main_usage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
