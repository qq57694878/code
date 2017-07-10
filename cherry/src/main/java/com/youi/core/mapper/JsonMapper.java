package com.youi.core.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * json转换.
 */
public class JsonMapper  extends ObjectMapper{
    /** logger. */
    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);


    /** constructor. */
    public JsonMapper() {
   
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 设置 SerializationFeature.FAIL_ON_EMPTY_BEANS 为 false
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      //  this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public String toJson(Object object) throws IOException {
        return this.writeValueAsString(object);
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) throws IOException {
        if ((jsonString == null) || "".equals(jsonString.trim())) {
            return null;
        }
        return this.readValue(jsonString, clazz);
    }

    /**
     * new TypeReference<List<String>>(){}
     */
    public <T> T fromJson(String jsonString, TypeReference typeReference)
            throws IOException {
        if ((jsonString == null) || "".equals(jsonString.trim())) {
            return null;
        }

        return (T) this.readValue(jsonString, typeReference);
    }

    public String toJsonP(String functionName, Object object)
            throws IOException {
        return toJson(new JSONPObject(functionName, object));
    }
}
