package com.youi.business.common;

import com.youi.business.base.TestRestBussinessBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestFileCommonController extends TestRestBussinessBase {
     @Test
    public void testUpload() throws Exception {
         uploadAttachment();

    }
    public Map uploadAttachment() throws Exception {
            Map<String,Object> param = new HashMap<String,Object>();
            Response r = RestAssured.given().contentType("multipart/form-data").formParams(param)
                  //  .multiPart("file1",new File("D:\\a\\aa\\1.txt"))
                    .multiPart("file1",new File("D:\\a\\aa\\111.jpg"))
                    .header(Constants.AUTHENTICATION_HEADER,this.token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
                    .post(""+PRE_PATH+"/file/generic/upload.do");
            r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
            r.prettyPrint();
            Map m =   r.as(Map.class);
           Map m1 =(Map)m.get("data");
          return m1;

    }

    @Test
    public void testDeleteAttachment() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        Map m = uploadAttachment();
        param.put("file_id", m.get("file_id"));
        Response r = RestAssured.given()
                .body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post("" + PRE_PATH + "/file/generic/delete.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }





}
