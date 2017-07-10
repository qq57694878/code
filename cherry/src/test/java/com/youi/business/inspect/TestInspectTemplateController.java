package com.youi.business.inspect;

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
public class TestInspectTemplateController extends TestRestBussinessBase {


    @Test
    public void testgetInspectTemplateDetail() throws Exception {
        getInspectTemplateDetail();
    }

    public Map getInspectTemplateDetail() throws Exception {
        List<Map> list = getInspectTemplateList();
        if (list != null) {
            for (Map inspectTemplate : list) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", inspectTemplate.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post("" + PRE_PATH + "/ri/tpl/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveInspectTemplate() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("name", "模板名称"+i);
        param.put("ri_code", "1");
        param.put("description", "模板描述"+i);
        param.put("status", "1");
        List<String> items = new ArrayList<String>();
        items.add("item1");
        items.add("item2");
        param.put("items", items);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/ri/tpl/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateInspectTemplate() throws Exception {
        Map inspect = getInspectTemplateDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id", inspect.get("id"));
        param.put("name", "模板名称"+i);
        param.put("ri_code", "1");
        param.put("description", "模板描述"+i);
        param.put("status", "1");
        List<String> items = new ArrayList<String>();
        items.add("item1");
        items.add("item2");
        param.put("items", items);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/ri/tpl/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();


    }


    @Test
    public void testgetInspectTemplateList() throws Exception {
        getInspectTemplateList();
    }

    public List getInspectTemplateList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        param.put("ri_code", "1");
        param.put("name", "模板名称");
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/ri/tpl/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        List list = (List) ((Map) m.get("data")).get("tpls");
        return list;
    }


}
