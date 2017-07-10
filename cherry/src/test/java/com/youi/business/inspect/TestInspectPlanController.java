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
public class TestInspectPlanController extends TestRestBussinessBase {


    @Test
    public void testgetInspectPlanDetail() throws Exception {
        getInspectPlanDetail();
    }

    public Map getInspectPlanDetail() throws Exception {
        List<Map> list = getInspectPlanList();
        if (list != null) {
            for (Map inspectTemplate : list) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("id", inspectTemplate.get("id"));
                Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                        .post("" + PRE_PATH + "/ri/plan/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m = r.as(Map.class);
                return (Map) m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveInspectPlan() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("name", "巡检计划名称"+i);
        param.put("ri_code", "1");
        param.put("tpl_id", 464323132522496l);
        param.put("period", "01");
        param.put("worker_id", "11");
        param.put("status", "1");
        List<Map<String,Object>> dev_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> m1 = new HashMap<String,Object>();
        m1.put("dev_id","396814403993600");
        m1.put("dev_type","HW_X86");
        dev_list.add(m1);
        Map<String,Object> m2 = new HashMap<String,Object>();
        m2.put("dev_id","396685959380992");
        m2.put("dev_type","HW_X86");
        dev_list.add(m2);
        param.put("dev_list", dev_list);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/ri/plan/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testUpdateInspectPlan() throws Exception {
        Map inspect = getInspectPlanDetail();
        Map<String, Object> param = new HashMap<String, Object>();
        int i = new Random().nextInt(100);
        param.put("id", inspect.get("id"));
        param.put("name", "巡检计划名称"+i);
        param.put("ri_code", "1");
        param.put("tpl_id", 464323132522496l);
        param.put("period", "01");
        param.put("worker_id", "11");
        param.put("status", "1");
        List<Map<String,Object>> dev_list = new ArrayList<Map<String,Object>>();
        Map<String,Object> m1 = new HashMap<String,Object>();
        m1.put("dev_id","396814403993600");
        m1.put("dev_type","HW_X86");
        dev_list.add(m1);
        Map<String,Object> m2 = new HashMap<String,Object>();
        m2.put("dev_id","396685959380992");
        m2.put("dev_type","HW_X86");
        dev_list.add(m2);
        param.put("dev_list", dev_list);
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/ri/plan/save.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
    @Test
    public void testgetInspectPlanList() throws Exception {
        getInspectPlanList();
    }
    public List getInspectPlanList() throws Exception {
        Map<String, String> param = new HashMap<String, String>();
        Response r = RestAssured.given().contentType("application/json").body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/ri/plan/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m = r.as(Map.class);
        Map<String,Object> map = (Map<String,Object>) ((Map) m.get("data")).get("plan");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(map!=null){
            for(String key:map.keySet()){
                List<Map<String,Object>> list1 = (List<Map<String,Object>>)map.get(key);
                if(list1!=null){
                    list.addAll(list1);
                }
            }
        }
        return list;
    }


}
