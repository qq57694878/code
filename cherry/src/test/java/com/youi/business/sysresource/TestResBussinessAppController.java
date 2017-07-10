package com.youi.business.sysresource;

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
public class TestResBussinessAppController extends TestRestBussinessBase {



    @Test
    public void testdisabledBussinessApp()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> bussinessAppList = getBussinessAppList();
        if(bussinessAppList!=null&&bussinessAppList.size()>0){
            Map bussinessApp = bussinessAppList.get(0);
            param.put("id", bussinessApp.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/biz_app/disable.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testeanbleBussinessApp()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> bussinessAppList = getBussinessAppList();
        if(bussinessAppList!=null&&bussinessAppList.size()>0){
            Map bussinessApp = bussinessAppList.get(0);
            param.put("id", bussinessApp.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/biz_app/recovery.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetBussinessAppDetail()  throws Exception {
        getBussinessAppDetail();
    }
    public Map getBussinessAppDetail()  throws Exception {
        List<Map> bussinessAppList = getBussinessAppList();
        if(bussinessAppList!=null){
            for(Map bussinessApp:bussinessAppList){
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("id",bussinessApp.get("id"));
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/sysresource/biz_app/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m =   r.as(Map.class);
               return (Map)m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveBussinessApp()  throws Exception {
                Map<String,Object> param = new HashMap<String,Object>();
        int i = new Random().nextInt(100);
        param.put("name","业务系统名称"+i);
        param.put("vendor_id",311829182316544l);
        param.put("org_id_list","311829178597376,318537768927232");
        param.put("online_time","2016-01-01");
        param.put("description","业务系统描述"+i);
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/sysresource/biz_app/save.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
    }
    @Test
    public void testUpdateBussinessApp()  throws Exception {
            Map bussinessApp = getBussinessAppDetail();
            Map<String,Object> param = new HashMap<String,Object>();
            int i = new Random().nextInt(100);
            System.out.println("开始更新id:"+bussinessApp.get("id"));
            param.put("id",bussinessApp.get("id"));
            param.put("org_id_list",bussinessApp.get("org_id_list"));
            param.put("description","外协单位描述"+i);
            param.put("name","业务系统名称"+i);
            param.put("online_time","2016-01-01");
            param.put("vendor_id",311829182316544l);
            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/sysresource/biz_app/save.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();


    }


   @Test
    public void testgetBussinessAppList()  throws Exception {
       getBussinessAppList();
    }
    public List getBussinessAppList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/biz_app/init.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("list_data");
        return list;
    }


    @Test
    public void testgetBussinessAppCodeTableList()  throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/biz_app/get-app-list.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }

}
