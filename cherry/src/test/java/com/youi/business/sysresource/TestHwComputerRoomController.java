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
public class TestHwComputerRoomController extends TestRestBussinessBase {



    @Test
    public void testdisabledComputerRoom()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> bussinessAppList = getComputerRoomList();
        if(bussinessAppList!=null&&bussinessAppList.size()>0){
            Map bussinessApp = bussinessAppList.get(0);
            param.put("id", bussinessApp.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/room/disable.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testeanbleComputerRoom()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> bussinessAppList = getComputerRoomList();
        if(bussinessAppList!=null&&bussinessAppList.size()>0){
            Map bussinessApp = bussinessAppList.get(0);
            param.put("id", bussinessApp.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/room/recovery.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetComputerRoomDetail()  throws Exception {
        getComputerRoomDetail();
    }
    public Map getComputerRoomDetail()  throws Exception {
        List<Map> bussinessAppList = getComputerRoomList();
        if(bussinessAppList!=null){
            for(Map bussinessApp:bussinessAppList){
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("id",bussinessApp.get("id"));
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/sysresource/room/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m =   r.as(Map.class);
               return (Map)m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveComputerRoom()  throws Exception {
                Map<String,Object> param = new HashMap<String,Object>();
        int i = new Random().nextInt(100);
        param.put("name","机房名称"+i);
        param.put("area","辽宁大连中山");
        param.put("complete_date","2016-10-06");
        param.put("description","机房描述"+i);
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/sysresource/room/save.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
    }
    @Test
    public void testUpdateComputerRoom()  throws Exception {
            Map bussinessApp = getComputerRoomDetail();
            Map<String,Object> param = new HashMap<String,Object>();
            int i = new Random().nextInt(100);
            System.out.println("开始更新id:"+bussinessApp.get("id"));
            param.put("id",bussinessApp.get("id"));
        param.put("name","机房名称"+i);
        param.put("area","辽宁大连中山"+i);
        param.put("complete_date","2016-10-01");
        param.put("description","机房描述"+i);
            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/sysresource/room/save.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();


    }


   @Test
    public void testgetComputerRoomList()  throws Exception {
       getComputerRoomList();
    }
    public List getComputerRoomList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/room/init.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("list_data");
        return list;
    }


    @Test
    public void testgetComputerRoomCodeTableList()  throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/room/get-app-list.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }

}
