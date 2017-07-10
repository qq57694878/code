package com.youi.business.activiti;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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
public class TestMyTaskController extends TestRestBussinessBase {
    @Test
    public void getHasdoTaskList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        param.put("biz_id", "1");
        param.put("task_type", "sw_requirement_change");
        param.put("start", "2016-11-01");
        param.put("end", "2016-11-18");
        System.out.println(objectMapper.writeValueAsString(param));
        RequestSpecification request= RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION);

        Response r = request.post(""+PRE_PATH+"/mywork/task/history.do");


        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("tasks");
    }
    public List getTodoTaskList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/mywork/task/waiting.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("tasks");
        return list;
    }

    public List<Map> uploadFile()  throws Exception {
        List<Map> tasks = getTodoTaskList();
        if(tasks!=null&&tasks.size()>0){
            Map task =tasks.get(0);
            Map<String,Object> param = new HashMap<String,Object>();
            Map task_info = ((Map)task.get("task_info"));
            param.put("instance_id",(String)task_info.get("instance_id"));
            Response r = RestAssured.given().contentType("multipart/form-data").formParams(param)
                    .multiPart("file1",new File("D:\\a\\aa\\1.txt"))
                    .multiPart("file2",new File("D:\\a\\ab\\2.txt"))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/file/upload.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();
            Map m =   r.as(Map.class);
            List<Map> list =((List<Map>)((Map)m.get("data")).get("attachments"));
            return list;
        }
        return null;
    }
    @Test
    public void testDeleteFile()  throws Exception {
           List<Map>list = uploadFile();
        for(Map m:list){
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("file_id",m.get("file_id"));
            Response r = RestAssured.given().body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/file/delete.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();
        }
    }
    public Map getBussinessAppDetail()  throws Exception {
        List<Map> bussinessAppList = getBussinessAppCodeTableList();
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

    public List getBussinessAppCodeTableList()  throws Exception {
        Response r = RestAssured.given().contentType("application/json")
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/sysresource/biz_app/get-app-list.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("list_data");
        return list;

    }

}
