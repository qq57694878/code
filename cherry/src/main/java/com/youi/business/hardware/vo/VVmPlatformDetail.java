package com.youi.business.hardware.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/28.
 */
public class VVmPlatformDetail {
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
     *型号
     */
    private String type;

    /**
     *控制台地址
     */
    private String console;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
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
