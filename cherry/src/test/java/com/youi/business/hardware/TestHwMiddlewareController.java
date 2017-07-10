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
public class TestHwMiddlewareController extends TestRestBussinessBase {


    @Test
    public void testDeleteMiddleware() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map> middlewareList = getMiddlewareList();
        if (middlewareList != null && middlewareList.size() > 0) {
            Map middleware = middlewareList.get(0);
            param.put("id", middleware.get("id"));
        }
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/middleware/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetMiddlewareDetail() throws Exception {
        getMiddlewareDetail();
    }

    public Map getMiddlewareDetail() throws Exception {
        List<Map> middlewareList = getMiddlewareList();
        if (middlewareList != null) {
            for (Map middleware : middlewareList) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", middleware.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/hardware/middleware/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveMiddleware() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);

        param.put("name", "名称"+i);
        param.put("brand","品牌"+i );
        param.put("version", "版本"+i);
        param.put("console","http://192.168.1.2/index.html");

        List<Map<String,Object>>x86_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> mx1 = new HashMap<String,Object>();
        mx1.put("x86_id","321562958364672");
        x86_list.add(mx1);
        Map<String,Object> mx2 = new HashMap<String,Object>();
        mx2.put("x86_id","321563043889152");
        x86_list.add(mx2);
        param.put("x86_list",x86_list);
        List<Map<String,Object>>vm_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> ms1 = new HashMap<String,Object>();
        ms1.put("vm_id","322772732821504");
        vm_list.add(ms1);
        Map<String,Object> ms2 = new HashMap<String,Object>();
        ms2.put("vm_id","322776929189888");
        vm_list.add(ms2);
        param.put("vm_list",vm_list);

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/middleware/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateMiddleware() throws Exception {
        Map obj = getMiddlewareDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id",obj.get("id"));
        param.put("name", "名称"+i);
        param.put("brand","品牌"+i );
        param.put("version", "版本"+i);
        param.put("console","http://192.168.1.2/index.html");

        List<Map<String,Object>>x86_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> mx1 = new HashMap<String,Object>();
        mx1.put("x86_id","321564298510336");
        x86_list.add(mx1);
        Map<String,Object> mx2 = new HashMap<String,Object>();
        mx2.put("x86_id","321564419407872");
        x86_list.add(mx2);
        param.put("x86_list",x86_list);
        List<Map<String,Object>>vm_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> ms1 = new HashMap<String,Object>();
        ms1.put("vm_id","322772732821504");
        vm_list.add(ms1);
        Map<String,Object> ms2 = new HashMap<String,Object>();
        ms2.put("vm_id","322776929189888");
        vm_list.add(ms2);
        param.put("vm_list",vm_list);

        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/middleware/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetMiddlewareList() throws Exception {
        getMiddlewareList();
    }

    public List getMiddlewareList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("pageno", "1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/hardware/middleware/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("list_data");
        return list;
    }


}
