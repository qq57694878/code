package com.youi.business.pim;


import com.youi.base.TestRestBase;
import com.youi.business.base.TestRestBussinessBase;
import com.youi.business.common.Constants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinliang on 2016/8/23.
 */
public class TestLogin extends TestRestBussinessBase{
  /*  public void getToken()throws Exception{
        Map<String,String> param = new HashMap<String,String>();
        param.put("username","ljl");
        param.put("passwd", "88888888");

       Response r = RestAssured.given().contentType("application/json").body(param)
                .header("ver","1.0").post(""+PRE_PATH+"/login.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
        Map m =   r.as(Map.class);
        this.token =(String)m.get("token");
    }*/
    @Test
     public void verify()throws Exception{
       Response r = RestAssured.given().contentType("application/json")
               .header(Constants.AUTHENTICATION_HEADER,token).header(Constants.VERSION,Constants.DEFAULT_VERSION)
               .post(""+PRE_PATH+"/verify.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }
}
