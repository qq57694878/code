package com.youi.business.project;

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
public class TestProjectController extends TestRestBussinessBase {



    @Test
    public void testDeleteProject()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map> ProjectList = getProjectList();
        if(ProjectList!=null&&ProjectList.size()>0){
            Map Project = ProjectList.get(0);
            param.put("id", Long.parseLong(String.valueOf(Project.get("id"))));
        }
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/project/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testgetProjectDetail()  throws Exception {
        getProjectDetail();
    }
    public Map getProjectDetail()  throws Exception {
        List<Map> ProjectList = getProjectList();
        if(ProjectList!=null){
            for(Map Project:ProjectList){
                Map<String,Object> param = new HashMap<String,Object>();
                param.put("id",Project.get("id"));
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/project/detail.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
                Map m =   r.as(Map.class);
               return (Map)m.get("data");
            }
        }
        return null;
    }

    @Test
    public void testSaveProject()  throws Exception {
                Map<String,Object> param = new HashMap<String,Object>();
        int i = new Random().nextInt(100);
        param.put("name","项目名称"+i);
        param.put("year","2017");
        param.put("apply_dept_id","2");
        param.put("total_investment",123.00);
        param.put("expected_sdate","2016-01-01");
        param.put("expected_edate","2016-12-31");
        param.put("description","描述"+i);
        param.put("is_submit","0");
        List<Map> attachments = new ArrayList<Map>();
        Map m1 = new HashMap();
        m1.put("attach_code","1");
        m1.put("file_id","444760939577344");
        attachments.add(m1);
        Map m2 = new HashMap();
        m2.put("attach_code","1");
        m2.put("file_id","444781940375552");
        attachments.add(m2);
        param.put("attachments",attachments);


                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/project/save.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
    }
    @Test
    public void testUpdateProject()  throws Exception {
            Map project = getProjectDetail();
            Map<String,Object> param = new HashMap<String,Object>();
            int i = new Random().nextInt(100);
        param.put("id",project.get("id"));
        param.put("name","项目名称"+i);
        param.put("year","2017");
        param.put("apply_dept_id","2");
        param.put("total_investment",123.00);
        param.put("expected_sdate","2016-01-01");
        param.put("expected_edate","2016-12-31");
        param.put("description","描述"+i);
        param.put("is_submit","0");
        List<Map> attachments = new ArrayList<Map>();
        Map m1 = new HashMap();
        m1.put("attach_code","1");
        m1.put("file_id","444760939577344");
        attachments.add(m1);
        Map m2 = new HashMap();
        m2.put("attach_code","1");
        m2.put("file_id","444760939577344");
        attachments.add(m2);
        param.put("attachments",attachments);
            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/project/save.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();


    }


   @Test
    public void testgetProjectList()  throws Exception {
       getProjectList();
    }
    public List getProjectList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        param.put("year", "2017");
        param.put("name", "项目");
        param.put("apply_dept", "2");
        param.put("filter_applicant", "1");

        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/project/query.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("projects");
        return list;
    }


}
