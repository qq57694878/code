package com.youi.business.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youi.base.TestRestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/8/24.
 */
public class TestRestBussinessBase extends TestRestBase{
    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String token;



    // 执行测试方法之前初始化模拟request,response
    @Before
    public void init() throws Exception{
        super.init();
        this.getToken();
    }

    public void getToken()throws Exception{
        Map<String,String> param = new HashMap<String,String>();
    //   param.put("username","ljl");
   //     param.put("username","czl");
   //     param.put("username","hq");
    //    param.put("username","wkq");
    //    param.put("username","cml");

    //      param.put("username","bize1");
   //     param.put("username","bize2");
    //  param.put("username","ice1");
     //  param.put("username","ice2");
        param.put("username","bizl1");
     //   param.put("username","bizl2");
     //   param.put("username","vendor1");
    //   param.put("username","vendor2");



        param.put("passwd", "1");

        Response r = RestAssured.given().contentType("application/json").body(param)
                .header("ver","1.0").post(""+PRE_PATH+"/login.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        this.token =(String)m.get("token");
    }
}
