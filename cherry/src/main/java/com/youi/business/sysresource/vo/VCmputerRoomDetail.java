package com.youi.business.sysresource.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/9/26.
 */
public class VCmputerRoomDetail {
    private Long id; // 主键ID
    private String name;//机房名称
    private String description;//机房描述
    private String  area;//使用面积
    private Date complete_date;//竣工时间

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getComplete_date() {
        return complete_date;
    }

    public void setComplete_date(Date complete_date) {
        this.complete_date = complete_date;
    }
}
