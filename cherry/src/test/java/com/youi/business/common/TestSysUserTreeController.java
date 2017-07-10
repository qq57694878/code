package com.youi.business.common;

import com.youi.business.base.TestRestBussinessBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.*;

/**
 * Created by jinliang on 2016/8/19.
 */
public class TestSysUserTreeController extends TestRestBussinessBase {


    @Test
    public void testGetOrgUserTreeList() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();

        Response r = RestAssured.given().contentType("application/json")
                //.body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/common/get_users_tree.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }

    @Test
    public void testGetOrgUserTree() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("with_valid","0");
        param.put("with_users","1");
        Map<String,Object> root =new HashMap<String,Object>();
        root.put("id",1);
        root.put("code","xxzx");
        param.put("root",root);

        Response r = RestAssured.given().contentType("application/json")
                .body(objectMapper.writeValueAsString(param))
                .header(Constants.AUTHENTICATION_HEADER, this.token).header(Constants.VERSION, Constants.DEFAULT_VERSION)
                .post(""+PRE_PATH+"/organization/gettree.do");
        r.then().statusCode(200).body("error_code", Matchers.equalTo(200));
        r.prettyPrint();
    }



}
