package com.youi.business.hardware.vo;

/**
 * Created by jinliang on 2016/9/26.
 */
public class VX86Detail {
    /**
     *
     */
    private Long id;

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
     *名称
     */
    private String name;

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
     *频率(Hz)
     */
    private String cpu_frequency;

    /**
     *核心数
     */
    private Long core_num;

    /**
     *当前cpu数
     */
    private Long cpu_current_num;

    /**
     *cpu最大数
     */
    private Long cpu_max_num;

    /**
     *内存类型
     */
    private String memory_type;

    /**
     *内存当前容量
     */
    private Long memory_current_capacity;

    /**
     *内存最大容量
     */
    private Long memory_max_capacity;

    /**
     *主板型号
     */
    private String mainboard_type;

    /**
     *存储接口类型
     */
    private String disk_interface_type;

    /**
     *磁盘容量
     */
    private Long disk_size;

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

    private String asset_num;

    public String getAsset_num() {
        return asset_num;
    }

    public void setAsset_num(String asset_num) {
        this.asset_num = asset_num;
    }

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

    public String getCpu_frequency() {
        return cpu_frequency;
    }

    public void setCpu_frequency(String cpu_frequency) {
        this.cpu_frequency = cpu_frequency;
    }

    public Long getCore_num() {
        return core_num;
    }

    public void setCore_num(Long core_num) {
        this.core_num = core_num;
    }

    public Long getCpu_current_num() {
        return cpu_current_num;
    }

    public void setCpu_current_num(Long cpu_current_num) {
        this.cpu_current_num = cpu_current_num;
    }

    public Long getCpu_max_num() {
        return cpu_max_num;
    }

    public void setCpu_max_num(Long cpu_max_num) {
        this.cpu_max_num = cpu_max_num;
    }

    public String getMemory_type() {
        return memory_type;
    }

    public void setMemory_type(String memory_type) {
        this.memory_type = memory_type;
    }

    public Long getMemory_current_capacity() {
        return memory_current_capacity;
    }

    public void setMemory_current_capacity(Long memory_current_capacity) {
        this.memory_current_capacity = memory_current_capacity;
    }

    public Long getMemory_max_capacity() {
        return memory_max_capacity;
    }

    public void setMemory_max_capacity(Long memory_max_capacity) {
        this.memory_max_capacity = memory_max_capacity;
    }

    public String getMainboard_type() {
        return mainboard_type;
    }

    public void setMainboard_type(String mainboard_type) {
        this.mainboard_type = mainboard_type;
    }

    public String getDisk_interface_type() {
        return disk_interface_type;
    }

    public void setDisk_interface_type(String disk_interface_type) {
        this.disk_interface_type = disk_interface_type;
    }

    public Long getDisk_size() {
        return disk_size;
    }

    public void setDisk_size(Long disk_size) {
        this.disk_size = disk_size;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
