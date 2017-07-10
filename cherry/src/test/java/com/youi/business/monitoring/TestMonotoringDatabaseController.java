package com.youi.business.monitoring;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestMonotoringDatabaseController extends TestRestBussinessBase {


    @Test
    public void testgetDatabaseMonitoringList() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageno",1);
        param.put("pagesize",10);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/monitoring/database.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    private List getDatabaseMonitoringList() throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pageno",1);
        param.put("pagesize",10);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/monitoring/database.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list");
        return list;
    }

    @Test
    public void testdelteMnotoring() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List list = getDatabaseMonitoringList();
        if(list==null)return;
        param.put("id",((Map)list.get(0)).get("db_id"));
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/monitoring/delete_database.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testcreateMnotoring() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List list = getDatabaseMonitoringList();
        if(list==null)return;
        param.put("id",((Map)list.get(0)).get("db_id"));
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/monitoring/create_database.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

}
