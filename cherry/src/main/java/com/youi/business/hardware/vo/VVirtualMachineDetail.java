package com.youi.business.hardware.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/9/26.
 */
public class VVirtualMachineDetail {
    /**
     *
     */
    private Long id;

    /**
     *虚拟化平台ID
     */
    private Long platform_id;
    /**
     * 平台名称
     */
    private String platform_name;

    /**
     *名称
     */
    private String name;

    /**
     *cpu核心数
     */
    private Long core_num;

    /**
     *内存容量
     */
    private Long memory;

    /**
     *硬盘容量
     */
    private Long disk;

    /**
     *操作系统
     */
    private String system;

    /**
     *
     */
    private Date ctime;

    /**
     *
     */
    private Long cuserid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Long platform_id) {
        this.platform_id = platform_id;
    }

    public String getPlatform_name() {
        return platform_name;
    }

    public void setPlatform_name(String platform_name) {
        this.platform_name = platform_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCore_num() {
        return core_num;
    }

    public void setCore_num(Long core_num) {
        this.core_num = core_num;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public Long getDisk() {
        return disk;
    }

    public void setDisk(Long disk) {
        this.disk = disk;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getCuserid() {
        return cuserid;
    }

    public void setCuserid(Long cuserid) {
        this.cuserid = cuserid;
    }
}
