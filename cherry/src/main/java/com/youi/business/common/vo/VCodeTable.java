package com.youi.business.common.vo;

/**
 * Created by jinliang on 2016/9/26.
 */
public class VCodeTable {

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VCodeTable(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
