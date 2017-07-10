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
public class TestHwStorageController extends TestRestBussinessBase {

    @Test
    public void getStorageCodeTableList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("main_usage", "2");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/storage/get-storage-list-by-main-usage.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }

    @Test
    public void testDeleteStorage() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> storageList = getStorageList();
        if (storageList != null && storageList.size() > 0) {
            Map storage = storageList.get(0);
            param.put("id", storage.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/storage/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetStorageDetail() throws Exception {
        getStorageDetail();
    }

    public Map getStorageDetail() throws Exception {
        List<Map> storageList = getStorageList();
        if (storageList != null) {
            for (Map storage : storageList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", storage.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/hardware/storage/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveStorage() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("name", "存储名称"+i);
        param.put("room_id", 318749119954944l);
        param.put("cabinet_id", 319927881285632l);
        param.put("brand", "联想");
        param.put("type", "X86-x-0");
        param.put("high", 2000);
        param.put("width", 1000);
        param.put("depth", 3000);
        param.put("main_usage", "1");//主要用途,1-业务服务器,2-虚拟化服务器,3-数据库服务器
        param.put("disk_interface_type", "存储接口类型" + i);
        param.put("disk_size", 1000);
        param.put("disk_current_num", 10000000);
        param.put("disk_max_num", 900000000);
        param.put("disk_raid", i);
        param.put("description", "其他描述" + i);

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/storage/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateStorage() throws Exception {
        Map storage = getStorageDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("name", "存储名称"+i);
        param.put("id", storage.get("id"));
        param.put("room_id", 318749119954944l);
        param.put("cabinet_id", 319927881285632l);
        param.put("brand", "联想");
        param.put("type", "X86-x-0");
        param.put("high", 2000);
        param.put("width", 1000);
        param.put("depth", 3000);
        param.put("main_usage", "1");//主要用途,1-业务服务器,2-虚拟化服务器,3-数据库服务器
        param.put("disk_interface_type", "存储接口类型" + i);
        param.put("disk_size", 1000);
        param.put("disk_current_num", 10000000);
        param.put("disk_max_num", 900000000);
        param.put("disk_raid", i);
        param.put("description", "其他描述" + i);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/storage/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetStorageList() throws Exception {
        getStorageList();
    }

    public List getStorageList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/storage/init.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }


}
