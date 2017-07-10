package com.youi.core.spring;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.Properties;

public class PropertiesUtils{

    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPropertiesValue(String name){
        Object r = properties.get(name);
        return r==null?"":String.valueOf(r);
    }
}