package com.youi.business.common.vo;


import com.youi.business.common.Constants;

/**
 * Created by jinliang on 2016/7/27.
 */
public class RestResult {

    private String ver;

    private int error_code;

    private Object data;

    private String token;

    public RestResult() {}


    public RestResult(String ver, int error_code, Object data, String token) {
        this.ver = ver;
        this.error_code = error_code;
        this.data = data;
        this.token = token;
    }

    public RestResult(String token,Object data) {
        this.error_code = Constants.ERROR_CODE_200;
        this.ver = Constants.DEFAULT_VERSION;
        this.data = data;
        this.token = token;
    }
    public RestResult(int error_code) {
        this.ver = Constants.DEFAULT_VERSION;
        this.error_code = error_code;
        this.token = "";
        this.data=new EmptyObject();

    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
