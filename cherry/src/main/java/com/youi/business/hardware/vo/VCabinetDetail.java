package com.youi.business.hardware.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/9/26.
 */
public class VCabinetDetail {
    /**
     *
     */
    private Long id;

    /**
     *品牌
     */
    private String brand;

    /**
     *机房ID
     */
    private Long room_id;

    /**
     *机柜名称
     */
    private String cabinet_name;

    /**
     *型号
     */
    private String type;

    /**
     *资产编号
     */
    private String asset_num;

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
     *详细描述
     */
    private String description;

    /**
     *机柜容量（U）
     */
    private Long unit;

    /**
     *材料及工艺
     */
    private String materials;

    /**
     *防护等级
     */
    private String def_level;

    /**
     *承重（kg）
     */
    private Long load_capacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getCabinet_name() {
        return cabinet_name;
    }

    public void setCabinet_name(String cabinet_name) {
        this.cabinet_name = cabinet_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAsset_num() {
        return asset_num;
    }

    public void setAsset_num(String asset_num) {
        this.asset_num = asset_num;
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

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getDef_level() {
        return def_level;
    }

    public void setDef_level(String def_level) {
        this.def_level = def_level;
    }

    public Long getLoad_capacity() {
        return load_capacity;
    }

    public void setLoad_capacity(Long load_capacity) {
        this.load_capacity = load_capacity;
    }
}
