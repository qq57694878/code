package com.youi.business.common.vo;



/**
 * Created by jinliang on 2016/7/27.
 */
public class RestResultBak {

    private int e;

    private String m;

    private Object r;

    public RestResultBak() {}

    public RestResultBak(int e, String m, Object r) {
        this.e = e;
        this.m = m;
        this.r = r;
    }

    public RestResultBak(int e, String m) {
        this.e = e;
        this.m = m;
        this.r = "";
    }

    public RestResultBak(Object r) {
        this.e = 1;
        this.m = "";
        this.r = r;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public Object getR() {
        return r;
    }

    public void setR(Object r) {
        this.r = r;
    }
/*
    public static void main(String args[]) throws IOException {
         JsonMapper jsonMapper = new JsonMapper();

        String r = jsonMapper.toJson(new RestResult(1,"",new Page("1",100)));
        System.out.println(r);
    }*/
}
