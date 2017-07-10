package com.youi.business.sysresource.vo;

/**
 * Created by jinliang on 2016/8/19.
 */
public class VBussinessApp {
    private String id;
    private String name;
    private String description;

    public VBussinessApp(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
