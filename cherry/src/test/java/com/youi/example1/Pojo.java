package com.youi.example1;

/**
 * Created by jinliang on 2016/7/13.
 */
public class Pojo {

    public Pojo(){}

    private long id;
    private String name;

    public Pojo(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
