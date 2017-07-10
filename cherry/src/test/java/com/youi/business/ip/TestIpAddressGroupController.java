package com.youi.business.ip;

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
public class TestIpAddressGroupController extends TestRestBussinessBase {


    @Test
    public void testSelectIpAddressGroupMonitoringDetail() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> storageList = getIpAddressGroupList();
        if (storageList != null && storageList.size() > 0) {
            Map storage = storageList.get(0);
            param.put("id", storage.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_group/get_ip_monitoring_detail.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testDeleteIpAddressGroup() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> storageList = getIpAddressGroupList();
        if (storageList != null && storageList.size() > 0) {
            Map storage = storageList.get(0);
            param.put("id", storage.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_group/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetIpAddressGroupDetail() throws Exception {
        getIpAddressGroupDetail();
    }

    public Map getIpAddressGroupDetail() throws Exception {
        List<Map> storageList = getIpAddressGroupList();
        if (storageList != null) {
            for (Map storage : storageList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", storage.get("id"));//
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/ip/ip_group/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveIpAddressGroup() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("name", "ip名称"+i);
        param.put("description","ip用途"+i );
        param.put("ip_add", "192.168.2.4");
        param.put("subnet_mask", "30");
        param.put("gateway", "192.168.2.1");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_group/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateIpAddressGroup() throws Exception {
        Map obj = getIpAddressGroupDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id",obj.get("id"));
        param.put("name", "ip名称"+i);
        param.put("description","ip用途"+i );
        param.put("ip_add", "192.168.2.1");
        param.put("subnet_mask", "30");
        param.put("gateway", "192.168.2.1");

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_group/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetIpAddressGroupList() throws Exception {
        getIpAddressGroupList();
    }

    public List getIpAddressGroupList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json")
                //.body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_group/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }


}
