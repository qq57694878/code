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
public class TestIpAddressController extends TestRestBussinessBase {
 
    @Test
    public void testDeleteIpAddress() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> storageList = getIpAddressList();
        if (storageList != null && storageList.size() > 0) {
            Map storage = storageList.get(0);
            param.put("id", storage.get("id"));
            param.put("id", 392248736169984l);

        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_manage/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testsetMainIpAddress() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> storageList = getIpAddressList();
        if (storageList != null && storageList.size() > 0) {
            Map storage = storageList.get(0);
            param.put("id", storage.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_manage/set_main_ip.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetIpAddressDetail() throws Exception {
        getIpAddressDetail();
    }

    public Map getIpAddressDetail() throws Exception {
        List<Map> storageList = getIpAddressList();
        if (storageList != null) {
            for (Map storage : storageList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", storage.get("id"));//
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/ip/ip_manage/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveIpAddress() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("biz_type", "HW_X86");
        param.put("biz_id",321564419407872l );
        param.put("ip_add", "218.60.41.68");
        param.put("subnet_mask", "24");
        param.put("gateway", "218.60.41.1");
        param.put("mac", "FE.12.34.FA.6E.79");
        param.put("mac_fresh_time", "2016-01-02");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_manage/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateIpAddress() throws Exception {
        Map obj = getIpAddressDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id",obj.get("id"));

        param.put("biz_type", "HW_X86");
        param.put("biz_id",321564419407872l );
        param.put("ip_add", "218.60.41.68");
        param.put("subnet_mask", "30");
        param.put("gateway", "218.60.41.1");
        param.put("mac", "FE.12.34.FA.6E.78");
        param.put("mac_fresh_time", "2016-01-01");

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_manage/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetIpAddressList() throws Exception {
        getIpAddressList();
    }

    public List getIpAddressList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
     //  param.put("pageno", "1");
       // param.put("pagesize", "10");
        param.put("biz_type", "HW_X86");
      //  param.put("biz_id", "321563043889152");
        Response r = RestAssured.given().contentType("application/json")
                .body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/ip/ip_manage/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }


}
