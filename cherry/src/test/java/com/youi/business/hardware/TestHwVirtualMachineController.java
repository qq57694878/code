package com.youi.business.hardware;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestHwVirtualMachineController extends TestRestBussinessBase {


    @Test
    public void testDeleteVirtualMachine() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> storageList = getVirtualMachineList();
        if (storageList != null && storageList.size() > 0) {
            Map storage = storageList.get(0);
            param.put("id", storage.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetVirtualMachineDetail() throws Exception {
        getVirtualMachineDetail();
    }

    public Map getVirtualMachineDetail() throws Exception {
        List<Map> storageList = getVirtualMachineList();
        if (storageList != null) {
            for (Map storage : storageList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", storage.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/hardware/vm/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveVirtualMachine() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("name","名称"+i );
        param.put("platform_id", 321579447468032l);
        param.put("core_num",16 );
        param.put("memory", 1024*1024*16);
        param.put("disk", 1024*1024*1024);
        param.put("system", "centos6.5");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateVirtualMachine() throws Exception {
        Map obj = getVirtualMachineDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id",obj.get("id"));
        param.put("name","名称"+i );
        param.put("platform_id", 321579447468032l);
        param.put("core_num",32 );
        param.put("memory", 1024*1024*32);
        param.put("disk", 1024*1024*1024*4);
        param.put("system", "centos7.0");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetVirtualMachineList() throws Exception {
        getVirtualMachineList();
    }

    public List getVirtualMachineList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }


}
