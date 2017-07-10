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
public class TestHwCabinetController extends TestRestBussinessBase {



    @Test
    public void testDeleteCabinet()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> cabinetList = getCabinetList();
        if(cabinetList!=null&&cabinetList.size()>0){
            Map cabinet = cabinetList.get(0);
            param.put("id", Long.parseLong(String.valueOf(cabinet.get("id"))));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/cabinet/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetCabinetDetail()  throws Exception {
        getCabinetDetail();
    }
    public Map getCabinetDetail()  throws Exception {
        List<Map> cabinetList = getCabinetList();
        if(cabinetList!=null){
            for(Map cabinet:cabinetList){
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("id",cabinet.get("id"));
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/hardware/cabinet/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m =   r.as(Map.class);
               return (Map)m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveCabinet()  throws Exception {
                Map<String,Object> param = new HashMap<String,Object>();
        int i = new Random().nextInt(100);
        param.put("cabinet_name","机柜名称"+i);
        param.put("brand","IBM");
        param.put("room_id",318749119954944L);
        param.put("type","X86");
        param.put("high",2000);
        param.put("width",1000);
        param.put("depth",3000);
        param.put("description","详细说明"+i);
        param.put("unit",1000);
        param.put("materials","材料及工艺"+i);
        param.put("def_level","防护等级"+i);
        param.put("load_capacity",i);
        param.put("asset_num","资产编号"+i);

                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/hardware/cabinet/save.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
    }
    @Test
    public void testUpdateCabinet()  throws Exception {
            Map cabinet = getCabinetDetail();
            Map<String,Object> param = new HashMap<String,Object>();
            int i = new Random().nextInt(100);
        param.put("id",cabinet.get("id"));
        param.put("cabinet_name","机柜名称"+i);
        param.put("brand","IBM");
        param.put("room_id",318749119954944L);
        param.put("type","X86");
        param.put("high",2000);
        param.put("width",1000);
        param.put("depth",3000);
        param.put("description","详细说明"+i);
        param.put("unit",1000);
        param.put("materials","材料及工艺"+i);
        param.put("def_level","防护等级"+i);
        param.put("load_capacity",i);
        param.put("asset_num","资产编号"+i);
            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/hardware/cabinet/save.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();


    }


   @Test
    public void testgetCabinetList()  throws Exception {
       getCabinetList();
    }
    public List getCabinetList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/cabinet/init.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("list_data");
        return list;
    }


    @Test
    public void testgetCabinetCodeTableList()  throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/cabinet/get-cabinet-list.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();

    }

}
