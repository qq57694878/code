package com.youi.business.hardware;

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
public class TestHwVmPlatformController extends TestRestBussinessBase {


    @Test
    public void testDeleteVmPlatform() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> vm_platformList = getVmPlatformList();
        if (vm_platformList != null && vm_platformList.size() > 0) {
            Map vm_platform = vm_platformList.get(0);
            param.put("id", vm_platform.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm_platform/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetVmPlatformDetail() throws Exception {
        getVmPlatformDetail();
    }

    public Map getVmPlatformDetail() throws Exception {
        List<Map> vm_platformList = getVmPlatformList();
        if (vm_platformList != null) {
            for (Map vm_platform : vm_platformList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", vm_platform.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/hardware/vm_platform/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveVmPlatform() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("brand", "联想"+i);
        param.put("type", "X86-x-0"+i);
        param.put("name", "平台名称"+i);
        param.put("console", "控制台地址"+i);
        List<Map<String,Object>>x86_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> mx1 = new HashMap<String,Object>();
        mx1.put("x86_id","321563043889152");
        x86_list.add(mx1);
        Map<String,Object> mx2 = new HashMap<String,Object>();
        mx2.put("x86_id","321564298510336");
        x86_list.add(mx2);
        param.put("x86_list",x86_list);
        List<Map<String,Object>>storage_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> ms1 = new HashMap<String,Object>();
        ms1.put("storage_id","320213498249216");
        storage_list.add(ms1);
        Map<String,Object> ms2 = new HashMap<String,Object>();
        ms2.put("storage_id","321562793263104");
        storage_list.add(ms2);
        param.put("storage_list",storage_list);

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm_platform/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateVmPlatform() throws Exception {
        Map vm_platform = getVmPlatformDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id", vm_platform.get("id"));
        param.put("brand", "联想"+i);
        param.put("type", "X86-x-0"+i);
        param.put("name", "平台名称"+i);
        param.put("console", "控制台地址"+i);
        List<Map<String,Object>>x86_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> mx1 = new HashMap<String,Object>();
        mx1.put("x86_id","321564298510336");
        x86_list.add(mx1);
        Map<String,Object> mx2 = new HashMap<String,Object>();
        mx2.put("x86_id","321564419407872");
        x86_list.add(mx2);
        param.put("x86_list",x86_list);
        List<Map<String,Object>>storage_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> ms1 = new HashMap<String,Object>();
        ms1.put("storage_id","321563092533248");
        storage_list.add(ms1);
        Map<String,Object> ms2 = new HashMap<String,Object>();
        ms2.put("storage_id","321564376956928");
        storage_list.add(ms2);
        param.put("storage_list",storage_list);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm_platform/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetVmPlatformList() throws Exception {
        getVmPlatformList();
    }

    public List getVmPlatformList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm_platform/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }


    @Test
    public void testgetVmPlatformCodeTableList()  throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/vm_platform/get-vm-platform-list.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }


}
