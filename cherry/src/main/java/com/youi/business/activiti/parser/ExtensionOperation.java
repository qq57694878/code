package com.youi.business.activiti.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/9/1.
 */
public class ExtensionOperation {
    public ExtensionOperation(String name){
        this.name = name;
    }
    private  String name;

    private Map<String,String> propertys = new HashMap<String,String>();

    public void addProperty(String key,String value){
        propertys.put(key,value);
    }
    public String getProperty(String key){
        return propertys.get(key);
    }
    public boolean isEnable(){
        boolean isEnable=true;
        if("false".equals(propertys.get("enable"))||"FALSE".equals(propertys.get("enable"))||"0".equals(propertys.get("enable"))){
            isEnable =false;
        }
        return isEnable;
    }
}
