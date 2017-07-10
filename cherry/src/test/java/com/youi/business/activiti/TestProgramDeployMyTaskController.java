package com.youi.business.activiti;

import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestProgramDeployMyTaskController extends TestRestBussinessBase {
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
    public List getHasdoTaskList()  throws Exception {
        Map<String,String> param = new HashMap<String,String>();
        param.put("pageno","1");
        param.put("pagesize", "10");
        Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/mywork/task/hasdo.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        List list =(List)((Map)m.get("data")).get("tasks");
        return list;
    }
    @Test
    public void testGetTodoTaskList()  throws Exception {
       List tasks =  getTodoTaskList();
    }
    @Test
    public void testStartTaskLoop() throws Exception{
        for(int i=0;i<11;i++){
            testStartTask();
        }
    }

    @Test
    public void testStartTask()  throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        int i = new Random().nextInt(100);
        param.put("title","chengxufabu"+ i);
        param.put("description","chengxufabumiaoshu1"+ i);
        param.put("stime","2016-08-24 10:10:10");
        param.put("etime","2016-08-26 10:10:10");
        param.put("business_app","1");
        param.put("task_type","sw_program_deploy");
        param.put("remark","remark"+ i);
        param.put("plan_deploy_time","2016-08-26 10:10:10");
        param.put("req_change_id","1");
        Response r = RestAssured.given().contentType("multipart/form-data").formParams(param)
               .multiPart("file1",new File("D:\\a\\aa\\1.txt"))
                .multiPart("file2",new File("D:\\a\\ab\\2.txt"))
                .multiPart("file3",new File("D:\\a\\ac\\3.txt"))
                .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/task/sw_program_deploy/start.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
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
    @Test
    public void testgetTaskDetail()  throws Exception {
        List<Map> tasks = getTodoTaskList();
        if(tasks!=null){
            for(Map task:tasks){
                Map<String,String> param = new HashMap<String,String>();
                param.put("instance_id",(String)task.get("instance_id"));
                param.put("task_id", (String)task.get("task_id"));
                param.put("task_type",(String)task.get("task_type"));
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/task/sw_program_deploy/get.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
            }
        }
    }

    @Test
    public void finishedTask()  throws Exception{
       for(int i=0;i<6;i++){
           testSubmitTask();
       }
    }
    @Test
    public void testSubmitTask()  throws Exception {
        List<Map> tasks = getTodoTaskList();
        if(tasks!=null&&tasks.size()>0){
                Map task =tasks.get(0);
                Map<String,Object> param = new HashMap<String,Object>();
               Map task_info = ((Map)task.get("task_info"));
          String node_id=  (String)task_info.get("node_id");
                param.put("task_type",(String)task.get("task_type"));
                param.put("task_id", (String)task.get("task_id"));
                param.put("node_id",node_id);
                param.put("instance_id",(String)task_info.get("instance_id"));
                Map<String,Object> form_data = new HashMap<String,Object>();
                for(int i=1;i<=6;i++){
                    form_data.put("step"+i+"Approved","true");
                }
                    form_data.put("option","同意");
                    if("step1".equals(node_id)){
                        int i = new Random().nextInt(100);
                        form_data.put("title","chengxufabusubmit"+ i);
                        form_data.put("description","chengxufabumiaoshu1submit"+ i);
                        form_data.put("remark","remark"+ i);
                        form_data.put("stime","2016-08-24 11:11:11");
                        form_data.put("etime","2016-08-26 11:11:11");
                        form_data.put("business_app","2");
                        form_data.put("plan_deploy_time","2016-08-26 11:11:11");
                        form_data.put("req_change_id","2");

                    }
            if("step2".equals(node_id)){
                List<String>orgids = new ArrayList<String>();
                orgids.add("2");
                orgids.add("3");
                form_data.put("org_id_list",orgids);
            }
            if("step3".equals(node_id)){

            }
            if("step4".equals(node_id)){
                form_data.put("actual_deploy_time", "2016-09-13");

            }

                param.put("form_data",form_data);
                Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                        .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                        .post(""+PRE_PATH+"/task/sw_program_deploy/submit.do");
                r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
                r.prettyPrint();
            }
    }
    @Test
    public void testRejectTask()  throws Exception {
        List<Map> tasks = getTodoTaskList();
        if(tasks!=null&&tasks.size()>0){
            Map task =tasks.get(0);
            Map<String,Object> param = new HashMap<String,Object>();
            Map task_info = ((Map)task.get("task_info"));
            param.put("task_type",(String)task.get("task_type"));
            param.put("task_id", (String)task.get("task_id"));
            param.put("node_id", (String)task_info.get("node_id"));
            param.put("instance_id",(String)task_info.get("instance_id"));
            param.put("opinion","理由不充分！");
            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/task/sw_program_deploy/return.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();
        }
    }

    @Test
    public void testStopTask()  throws Exception {
        List<Map> tasks = getTodoTaskList();
        if(tasks!=null&&tasks.size()>0){
            Map task =tasks.get(0);
            Map<String,Object> param = new HashMap<String,Object>();
            Map task_info = ((Map)task.get("task_info"));
            param.put("task_type",(String)task.get("task_type"));
            param.put("task_id", (String)task.get("task_id"));
            param.put("node_id", (String)task_info.get("node_id"));
            param.put("instance_id",(String)task_info.get("instance_id"));

            Response r = RestAssured.given().contentType("application/json").body( objectMapper.writeValueAsString(param))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/task/sw_program_deploy/stop.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();
        }
    }


}
