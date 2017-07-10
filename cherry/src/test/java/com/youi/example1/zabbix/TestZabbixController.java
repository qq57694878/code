package com.youi.example1.zabbix;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.*;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestZabbixController extends TestRestBussinessBase {


    public List getHostItems()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("host","testserver");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/zabbix/host/get.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List items =(List)((Map)((List)((Map)m.get("data")).get("result")).get(0)).get("items");
        return items;

    }

    @Test
    public void testGetHost()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("host","testserver");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/zabbix/host/get.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testCreateHost()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("host","biz_218.60.41.68_8080_2");
        param.put("ip","218.60.41.68");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/zabbix/host/create.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testGetEvent()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("time_from",(Long)(System.currentTimeMillis()/1000-3600*24*365));
        param.put("time_till",(Long)(System.currentTimeMillis()/1000));
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION ,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/zabbix/event/get.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testGetTrend()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> items = getHostItems();
        List itemids = new ArrayList();
        for(Map m:items){
           String itemid =  (String)m.get("itemid");
            itemids.add(itemid);
        }
        itemids.add("23680");
        itemids.add("23681");
        param.put("itemids",itemids.toArray());
        param.put("time_from",(Long)(System.currentTimeMillis()/1000-3600*24*365));
        param.put("time_till",(Long)(System.currentTimeMillis()/1000));
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/zabbix/trend/get.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testGetTrigger()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("hostid", "10111");
        param.put("templateid","10001" );
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/zabbix/trigger/get.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void test()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("id","3");
        param.put("ip","218.60.41.68");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/zabbix/test.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

}
