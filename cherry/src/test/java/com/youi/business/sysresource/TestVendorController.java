package com.youi.business.sysresource;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestVendorController extends TestRestBussinessBase {



    @Test
    public void testdisabledVendor()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> vendorList = getVendorList();
        if(vendorList!=null&&vendorList.size()>0){
            Map vendor = vendorList.get(0);
            param.put("id", vendor.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/vendor/disable.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testeanbleVendor()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> vendorList = getVendorList();
        if(vendorList!=null&&vendorList.size()>0){
            Map vendor = vendorList.get(0);
            param.put("id", vendor.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/vendor/recovery.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetTaskDetail()  throws Exception {
        getTaskDetail();
    }
    public Map getTaskDetail()  throws Exception {
        List<Map> vendorList = getVendorList();
        if(vendorList!=null){
            for(Map vendor:vendorList){
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("id",vendor.get("id"));
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/sysresource/vendor/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m =   r.as(Map.class);
               return (Map)m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveVendor()  throws Exception {
                Map<String,Object> param = new HashMap<String,Object>();
        int i = new Random().nextInt(100);
        param.put("vendor_name","外协单位名称"+i);
        param.put("manager","负责人"+i);
        param.put("tel","13888888888");
        param.put("property","企业性质"+i);
        param.put("scope","经营范围"+i);
        param.put("register_date","2016-01-01");
        param.put("register_money","10000000");
        param.put("num_people",i);
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/sysresource/vendor/save.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
    }
    @Test
    public void testUpdateVendor()  throws Exception {
            Map vendor = getTaskDetail();
            Map<String,Object> param = new HashMap<String,Object>();
            int i = new Random().nextInt(100);
            System.out.println("开始更新id:"+vendor.get("id"));
            param.put("id",vendor.get("id"));
            param.put("org_id",vendor.get("org_id"));
            param.put("vendor_name","外协单位名称"+i);
            param.put("manager","负责人"+i);
            param.put("tel","13888888888");
            param.put("property","企业性质"+i);
            param.put("scope","经营范围"+i);
            param.put("register_date","2016-01-01");
            param.put("register_money","10000000");
            param.put("num_people",i);
            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/sysresource/vendor/save.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();


    }


   @Test
    public void testgetVendorList()  throws Exception {
       getVendorList();
    }
    public List getVendorList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/vendor/init.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("list_data");
        return list;

    }

    @Test
    public void testGetVendorList1()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/vendor/get-vendor-list.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("list_data");
    }
   @Test
    public void testGetVendorManager()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("vendor_id","1");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/vendor/get-vender-manager.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("list_data");
    }
}
