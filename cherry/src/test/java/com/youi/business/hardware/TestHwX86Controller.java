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
public class TestHwX86Controller extends TestRestBussinessBase {


    @Test
    public void testDeleteX86() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> x86List = getX86List();
        if (x86List != null && x86List.size() > 0) {
            Map x86 = x86List.get(0);
            param.put("id", x86.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/x86/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetX86Detail() throws Exception {
        getX86Detail();
    }

    public Map getX86Detail() throws Exception {
        List<Map> x86List = getX86List();
        if (x86List != null) {
            for (Map x86 : x86List) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", x86.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/hardware/x86/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveX86() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);

        param.put("room_id", 318749119954944l);
        param.put("cabinet_id", 319927881285632l);
        param.put("brand", "联想");
        param.put("type", "X86-x-0");
        param.put("high", 2000);
        param.put("width", 1000);
        param.put("depth", 3000);
        param.put("main_usage", "1");//主要用途,1-业务服务器,2-虚拟化服务器,3-数据库服务器
        param.put("cpu_type", "CPU型号" + i);
        param.put("cpu_frequency", 2.5F);
        param.put("cpu_current_num", 1);
        param.put("core_num", 8);
        param.put("cpu_max_num", 1);
        param.put("memory_type", "内存类型" + i);
        param.put("memory_current_capacity", 8 * 1024 * 1024);
        param.put("memory_max_capacity", 64 * 1024 * 1024);
        param.put("mainboard_type", "主板型号" + i);
        param.put("disk_interface_type", "存储接口类型" + i);
        param.put("disk_size", 1000);
        param.put("disk_current_num", 10000000);
        param.put("disk_max_num", 900000000);
        param.put("disk_raid", i);
        param.put("description", "其他描述" + i);


        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/x86/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateX86() throws Exception {
        Map x86 = getX86Detail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id", x86.get("id"));
        param.put("room_id", 318749119954944l);
        param.put("cabinet_id", 319927881285632l);
        param.put("brand", "联想");
        param.put("type", "X86-x-0");
        param.put("high", 2000);
        param.put("width", 1000);
        param.put("depth", 3000);
        param.put("main_usage", "1");//主要用途,1-业务服务器,2-虚拟化服务器,3-数据库服务器
        param.put("cpu_type", "CPU型号" + i);
        param.put("cpu_frequency", 2.5F);
        param.put("core_num", 8);
        param.put("cpu_current_num", 1);
        param.put("cpu_max_num", 1);
        param.put("memory_type", "内存类型" + i);
        param.put("memory_current_capacity", 8 * 1024 * 1024);
        param.put("memory_max_capacity", 64 * 1024 * 1024);
        param.put("mainboard_type", "主板型号" + i);
        param.put("disk_interface_type", "存储接口类型" + i);
        param.put("disk_size", 1000);
        param.put("disk_current_num", 10000000);
        param.put("disk_max_num", 900000000);
        param.put("disk_raid", i);
        param.put("description", "其他描述" + i);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/x86/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetX86List() throws Exception {
        getX86List();
    }

    public List getX86List() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/x86/init.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }
@Test
    public void getX86CodeTableList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("filter_EQS_main_usage", "3");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/x86/get-x86-list-by-main-usage.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }
    @Test
    public void getX86CodeTableList1() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("main_usage", "3");
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/x86/get-x86-list-by-main-usage.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }


}
